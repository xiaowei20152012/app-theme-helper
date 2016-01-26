package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEViewPager extends ViewPager {

    public ATEViewPager(Context context) {
        super(context);
        init(context, null);
    }

    public ATEViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ATEViewUtil.init(this, context, attrs, R.styleable.ATEViewPager, R.styleable.ATEViewPager_ateKey_viewPager);
    }
}
