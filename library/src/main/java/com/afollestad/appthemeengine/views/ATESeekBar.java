package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATESeekBar extends SeekBar implements ViewInterface {

    public ATESeekBar(Context context) {
        super(context);
        init(context, null, null);
    }

    public ATESeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public ATESeekBar(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, attrs, keyContext);
    }

    private void init(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        setTag("tint_accent_color");
        ATEViewUtil.init(keyContext, this, context, attrs, R.styleable.ATESeekBar, R.styleable.ATESeekBar_ateKey_seekBar);
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
