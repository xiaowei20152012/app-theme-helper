package com.afollestad.appthemeengine.inflation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.util.TintHelper;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATESpinner extends AppCompatSpinner implements ViewInterface {

    public ATESpinner(Context context) {
        super(context);
        init(context, null);
    }

    public ATESpinner(Context context, int mode) {
        super(context, mode);
        init(context, null);
    }

    public ATESpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ATESpinner(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, keyContext);
    }

    public ATESpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    public ATESpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init(context, null);
    }

    public ATESpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
        init(context, null);
    }

    private void init(Context context, @Nullable ATEActivity keyContext) {
        // TODO Need a parent background dependent tint tag? Primary dependent tag?
//        ATEViewUtil.init(keyContext, this, context);
        if (getBackground() != null) {
            ATEUtil.setBackgroundCompat(this,
                    TintHelper.tintDrawable(getBackground(), Color.WHITE));
        }
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
