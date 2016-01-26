package com.afollestad.appthemeengine;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
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
import com.afollestad.appthemeengine.views.ATEPrimaryTextView;
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

/**
 * @author Aidan Follestad (afollestad)
 */
class InflationInterceptor implements LayoutInflaterFactory {

    private AppCompatDelegate mDelegate;

    public InflationInterceptor(AppCompatDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (name.equals("TextView")) {
            return new ATEPrimaryTextView(context, attrs);
        } else if (name.equals("EditText")) {
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
            View view = mDelegate.createView(parent, name, context, attrs);
            return view;
        }
    }
}