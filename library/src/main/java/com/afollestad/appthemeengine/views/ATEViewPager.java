package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEViewPager extends ViewPager implements ViewInterface {

    public ATEViewPager(Context context) {
        super(context);
        init(context, null, null);
    }

    public ATEViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public ATEViewPager(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, attrs, keyContext);
    }

    private void init(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        ATEViewUtil.init(keyContext, this, context, attrs, R.styleable.ATEViewPager, R.styleable.ATEViewPager_ateKey_viewPager);
    }

    @Override
    public boolean setsStatusBarColor() {
        return false;
    }

    @Override
    public boolean setsToolbarColor() {
        return false;
    }
}