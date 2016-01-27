package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEProgressBar extends ProgressBar implements ViewInterface {

    public ATEProgressBar(Context context) {
        super(context);
        init(context, null, null);
    }

    public ATEProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public ATEProgressBar(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, attrs, keyContext);
    }

    private void init(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        setTag("tint_accent_color");
        ATEViewUtil.init(keyContext, this, context, attrs, R.styleable.ATEProgressBar, R.styleable.ATEProgressBar_ateKey_progressBar);
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