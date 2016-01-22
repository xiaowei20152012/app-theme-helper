package com.kabouzeid.appthemehelper.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.kabouzeid.appthemehelper.Config;
import com.kabouzeid.appthemehelper.util.TintHelper;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATERadioButton extends RadioButton {

    public ATERadioButton(Context context) {
        super(context);
        init(context);
    }

    public ATERadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ATERadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ATERadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        TintHelper.setTintAuto(this, Config.accentColor(context), false);
    }
}
