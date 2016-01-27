package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATETabLayout extends TabLayout implements ViewInterface {

    public ATETabLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ATETabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATETabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ATEViewUtil.init(this, context, attrs, R.styleable.ATETabLayout, R.styleable.ATETabLayout_ateKey_tabLayout);
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
