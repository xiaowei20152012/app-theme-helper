package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATENavigationView extends NavigationView implements ViewInterface {

    public ATENavigationView(Context context) {
        super(context);
        init(context, null);
    }

    public ATENavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATENavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ATEViewUtil.init(this, context, attrs, R.styleable.ATENavigationView, R.styleable.ATENavigationView_ateKey_navigationView);
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
