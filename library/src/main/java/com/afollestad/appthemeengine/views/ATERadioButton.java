package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATERadioButton extends RadioButton implements ViewInterface {

    public ATERadioButton(Context context) {
        super(context);
        init(context, null, null);
    }

    public ATERadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public ATERadioButton(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, attrs, keyContext);
    }

    private void init(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        setTag("tint_accent_color,text_primary");
        ATEViewUtil.init(keyContext, this, context, attrs, R.styleable.ATERadioButton, R.styleable.ATERadioButton_ateKey_radioButton);
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
