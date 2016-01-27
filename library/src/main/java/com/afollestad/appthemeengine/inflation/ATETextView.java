package com.afollestad.appthemeengine.inflation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.afollestad.appthemeengine.ATEActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATETextView extends TextView implements ViewInterface {

    public ATETextView(Context context) {
        super(context);
        init(context, null);
    }

    public ATETextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ATETextView(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, keyContext);
    }

    private void init(Context context, @Nullable ATEActivity keyContext) {
        if (getTag() == null)
            setTag("text_link_accent_color"); // default implementation sets link colors to accent
        ATEViewUtil.init(keyContext, this, context);
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