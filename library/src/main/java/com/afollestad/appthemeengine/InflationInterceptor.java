package com.afollestad.appthemeengine;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.afollestad.appthemeengine.views.ATECoordinatorLayout;
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

    @Nullable
    private final ATEActivity mKeyContext;
    @NonNull
    private final LayoutInflater mLi;
    @Nullable
    private AppCompatDelegate mDelegate;
    private static Method mOnCreateViewMethod;
    private static Method mCreateViewMethod;
    private static Field mConstructorArgsField;
    private static int[] ATTRS_THEME;

    public InflationInterceptor(@Nullable Activity keyContext, @NonNull LayoutInflater li, @Nullable AppCompatDelegate delegate) {
        if (keyContext instanceof ATEActivity)
            mKeyContext = (ATEActivity) keyContext;
        else mKeyContext = null;

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
                final Field attrsThemeField = LayoutInflater.class.getDeclaredField("ATTRS_THEME");
                attrsThemeField.setAccessible(true);
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
        View view;

        if (name.equals("EditText")) {
            view = new ATEEditText(context, attrs, mKeyContext);
        } else if (name.equals("CheckBox")) {
            view = new ATECheckBox(context, attrs, mKeyContext);
        } else if (name.equals("RadioButton")) {
            view = new ATERadioButton(context, attrs, mKeyContext);
        } else if (name.equals("Switch")) {
            view = new ATESwitch(context, attrs, mKeyContext);
        } else if (name.equals(SwitchCompat.class.getName())) {
            view = new ATEStockSwitch(context, attrs, mKeyContext);
        } else if (name.equals("SeekBar")) {
            view = new ATESeekBar(context, attrs, mKeyContext);
        } else if (name.equals("ProgressBar")) {
            view = new ATEProgressBar(context, attrs, mKeyContext);
        } else if (name.equals(ToolbarProcessor.MAIN_CLASS)) {
            ATEToolbar toolbar = new ATEToolbar(context, attrs, mKeyContext);
            ATE.addPostInflationView(toolbar);
            view = toolbar;
        } else if (name.equals("ListView")) {
            view = new ATEListView(context, attrs, mKeyContext);
        } else if (name.equals("ScrollView")) {
            view = new ATEScrollView(context, attrs, mKeyContext);
        } else if (name.equals(RecyclerViewProcessor.MAIN_CLASS)) {
            view = new ATERecyclerView(context, attrs, mKeyContext);
        } else if (name.equals(NestedScrollViewProcessor.MAIN_CLASS)) {
            view = new ATENestedScrollView(context, attrs, mKeyContext);
        } else if (name.equals(DrawerLayoutProcessor.MAIN_CLASS)) {
            view = new ATEDrawerLayout(context, attrs, mKeyContext);
        } else if (name.equals(NavigationViewProcessor.MAIN_CLASS)) {
            view = new ATENavigationView(context, attrs, mKeyContext);
        } else if (name.equals(TabLayoutProcessor.MAIN_CLASS)) {
            view = new ATETabLayout(context, attrs, mKeyContext);
        } else if (name.equals(ViewPagerProcessor.MAIN_CLASS)) {
            view = new ATEViewPager(context, attrs, mKeyContext);
        } else if (name.equals("android.support.design.widget.CoordinatorLayout")) {
            view = new ATECoordinatorLayout(context, attrs, mKeyContext);
        } else {
            // First, check if the AppCompatDelegate will give us a view, usually (maybe always) null.
            view = mDelegate != null ? mDelegate.createView(parent, name, context, attrs) : null;

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
        }

        return view;
    }
}