package com.afollestad.appthemeengine;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.appthemeengine.processors.DrawerLayoutProcessor;
import com.afollestad.appthemeengine.processors.NavigationViewProcessor;
import com.afollestad.appthemeengine.processors.NestedScrollViewProcessor;
import com.afollestad.appthemeengine.processors.RecyclerViewProcessor;
import com.afollestad.appthemeengine.processors.TabLayoutProcessor;
import com.afollestad.appthemeengine.processors.ToolbarProcessor;
import com.afollestad.appthemeengine.processors.ViewPagerProcessor;
import com.afollestad.appthemeengine.views.ATECheckBox;
import com.afollestad.appthemeengine.views.ATEDrawerLayout;
import com.afollestad.appthemeengine.views.ATEEditText;
import com.afollestad.appthemeengine.views.ATEListView;
import com.afollestad.appthemeengine.views.ATENavigationView;
import com.afollestad.appthemeengine.views.ATENestedScrollView;
import com.afollestad.appthemeengine.views.ATEProgressBar;
import com.afollestad.appthemeengine.views.ATERadioButton;
import com.afollestad.appthemeengine.views.ATERecyclerView;
import com.afollestad.appthemeengine.views.ATEScrollView;
import com.afollestad.appthemeengine.views.ATESeekBar;
import com.afollestad.appthemeengine.views.ATEStockSwitch;
import com.afollestad.appthemeengine.views.ATESwitch;
import com.afollestad.appthemeengine.views.ATETabLayout;
import com.afollestad.appthemeengine.views.ATEToolbar;
import com.afollestad.appthemeengine.views.ATEViewPager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Aidan Follestad (afollestad)
 */
class InflationInterceptor implements LayoutInflaterFactory {

    private LayoutInflater mLi;
    private AppCompatDelegate mDelegate;
    private static Method mOnCreateViewMethod;
    private static Method mCreateViewMethod;
    private static Field mConstructorArgsField;
    private static int[] ATTRS_THEME;

    public InflationInterceptor(LayoutInflater li, AppCompatDelegate delegate) {
        mLi = li;
        mDelegate = delegate;
        if (mOnCreateViewMethod == null) {
            try {
                mOnCreateViewMethod = LayoutInflater.class.getDeclaredMethod("onCreateView",
                        View.class, String.class, AttributeSet.class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Failed to retrieve the onCreateView method.", e);
            }
        }
        if (mCreateViewMethod == null) {
            try {
                mCreateViewMethod = LayoutInflater.class.getDeclaredMethod("createView",
                        String.class, String.class, AttributeSet.class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Failed to retrieve the createView method.", e);
            }
        }
        if (mConstructorArgsField == null) {
            try {
                mConstructorArgsField = LayoutInflater.class.getDeclaredField("mConstructorArgs");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Failed to retrieve the mConstructorArgs field.", e);
            }
        }
        if (ATTRS_THEME == null) {
            try {
                final Field attrsThemeField = LayoutInflaterFactory.class.getDeclaredField("ATTRS_THEME");
                ATTRS_THEME = (int[]) attrsThemeField.get(null);
            } catch (Throwable t) {
                throw new RuntimeException("Failed to get the value of static field ATTRS_THEME.", t);
            }
        }
        mOnCreateViewMethod.setAccessible(true);
        mCreateViewMethod.setAccessible(true);
        mConstructorArgsField.setAccessible(true);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (name.equals("EditText")) {
            return new ATEEditText(context, attrs);
        } else if (name.equals("CheckBox")) {
            return new ATECheckBox(context, attrs);
        } else if (name.equals("RadioButton")) {
            return new ATERadioButton(context, attrs);
        } else if (name.equals("Switch")) {
            return new ATESwitch(context, attrs);
        } else if (name.equals(SwitchCompat.class.getName())) {
            return new ATEStockSwitch(context, attrs);
        } else if (name.equals("SeekBar")) {
            return new ATESeekBar(context, attrs);
        } else if (name.equals("ProgressBar")) {
            return new ATEProgressBar(context, attrs);
        } else if (name.equals(ToolbarProcessor.MAIN_CLASS)) {
            ATEToolbar toolbar = new ATEToolbar(context, attrs);
            ATE.addPostInflationView(toolbar);
            return toolbar;
        } else if (name.equals("ListView")) {
            return new ATEListView(context, attrs);
        } else if (name.equals("ScrollView")) {
            return new ATEScrollView(context, attrs);
        } else if (name.equals(RecyclerViewProcessor.MAIN_CLASS)) {
            return new ATERecyclerView(context, attrs);
        } else if (name.equals(NestedScrollViewProcessor.MAIN_CLASS)) {
            return new ATENestedScrollView(context, attrs);
        } else if (name.equals(DrawerLayoutProcessor.MAIN_CLASS)) {
            return new ATEDrawerLayout(context, attrs);
        } else if (name.equals(NavigationViewProcessor.MAIN_CLASS)) {
            return new ATENavigationView(context, attrs);
        } else if (name.equals(TabLayoutProcessor.MAIN_CLASS)) {
            return new ATETabLayout(context, attrs);
        } else if (name.equals(ViewPagerProcessor.MAIN_CLASS)) {
            return new ATEViewPager(context, attrs);
        } else {
            // First, check if the AppCompatDelegate will give us a view, usually (maybe always) null.
            View view = mDelegate.createView(parent, name, context, attrs);

            // Mimic code of LayoutInflater using reflection tricks.
            if (view == null) {
                Context viewContext;
                final boolean inheritContext = false; // TODO will this ever need to be true?
                //noinspection PointlessBooleanExpression,ConstantConditions
                if (parent != null && inheritContext) {
                    viewContext = parent.getContext();
                } else {
                    viewContext = mLi.getContext();
                }
                // Apply a theme wrapper, if requested.
                final TypedArray ta = viewContext.obtainStyledAttributes(attrs, ATTRS_THEME);
                final int themeResId = ta.getResourceId(0, 0);
                if (themeResId != 0) {
                    viewContext = new ContextThemeWrapper(viewContext, themeResId);
                }
                ta.recycle();

                Object[] mConstructorArgs;
                try {
                    mConstructorArgs = (Object[]) mConstructorArgsField.get(mLi);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to retrieve the mConstructorArgsField field.", e);
                }

                final Object lastContext = mConstructorArgs[0];
                mConstructorArgs[0] = viewContext;
                try {
                    if (-1 == name.indexOf('.')) {
                        view = (View) mOnCreateViewMethod.invoke(mLi, parent, name, attrs);
                    } else {
                        view = (View) mCreateViewMethod.invoke(mLi, name, null, attrs);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mConstructorArgs[0] = lastContext;
                }
            }

            // Don't allow ATE views to be applied here.
            if (view != null) {
                if (view.getClass().getSimpleName().startsWith("ATE"))
                    throw new IllegalStateException("Reached default processing for an ATE prefixed view. Must've missed registering it in the interceptor.");
                String key = null;
                if (context instanceof ATEActivity)
                    key = ((ATEActivity) context).getATEKey();
                ATE.apply(view, key);
            }

            // Return the resulting view to go into the layout.
            return view;
        }
    }
}