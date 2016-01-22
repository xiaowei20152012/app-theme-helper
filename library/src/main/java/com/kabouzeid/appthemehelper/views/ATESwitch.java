package com.kabouzeid.appthemehelper.views;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kabouzeid.appthemehelper.Config;
import com.kabouzeid.appthemehelper.util.TintHelper;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATESwitch extends SwitchCompat {

    public ATESwitch(Context context) {
        super(context);
        init(context);
    }

    public ATESwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ATESwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        TintHelper.setTintAuto(this, Config.accentColor(context), false);
    }

    @Override
    public boolean isShown() {
        return getParent() != null && getVisibility() == View.VISIBLE;
    }
}
