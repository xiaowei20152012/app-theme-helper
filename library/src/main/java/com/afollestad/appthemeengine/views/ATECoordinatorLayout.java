package com.afollestad.appthemeengine.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATECoordinatorLayout extends CoordinatorLayout implements ViewInterface {

    public ATECoordinatorLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ATECoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATECoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        String key = null;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATECoordinatorLayout, 0, 0);
            try {
                key = a.getString(R.styleable.ATECoordinatorLayout_ateKey_coordinatorLayout);
            } finally {
                a.recycle();
            }
        }
        if (key == null && context instanceof ATEActivity)
            key = ((ATEActivity) context).getATEKey();
        if (context instanceof Activity && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Sets Activity status bar to transparent, DrawerLayout overlays a color.
            ((Activity) context).getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setStatusBarBackgroundColor(Config.statusBarColor(context, key));
    }

    @Override
    public boolean setsStatusBarColor() {
        return true;
    }

    @Override
    public boolean setsToolbarColor() {
        return true;
    }
}