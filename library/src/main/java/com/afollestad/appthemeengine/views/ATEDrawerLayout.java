package com.afollestad.appthemeengine.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEDrawerLayout extends DrawerLayout implements ViewInterface {

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
        if (context instanceof Activity && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Sets Activity status bar to transparent, DrawerLayout overlays a color.
            ((Activity) context).getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        final String key = ATEViewUtil.init(this, context, attrs, R.styleable.ATEDrawerLayout, R.styleable.ATEDrawerLayout_ateKey_drawerLayout);
        // Sets the status bar overlayed by the DrawerLayout
        setStatusBarBackgroundColor(Config.statusBarColor(context, key));
    }

    @Override
    public boolean setsStatusBarColor() {
        return true;
    }

    @Override
    public boolean setsToolbarColor() {
        return false;
    }
}
