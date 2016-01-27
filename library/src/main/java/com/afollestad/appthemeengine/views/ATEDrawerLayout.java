package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEDrawerLayout extends DrawerLayout implements ATEViewInterface {

    public ATEDrawerLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ATEDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATEDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ATEViewUtil.init(this, context, attrs, R.styleable.ATEDrawerLayout, R.styleable.ATEDrawerLayout_ateKey_drawerLayout);
    }

    @Override
    public boolean setsStatusBarColor() {
        return true;
    }
}
