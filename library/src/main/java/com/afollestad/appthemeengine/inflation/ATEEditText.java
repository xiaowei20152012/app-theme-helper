package com.afollestad.appthemeengine.inflation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.tagprocessors.TextColorTagProcessor;
import com.afollestad.appthemeengine.tagprocessors.TintTagProcessor;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATEEditText extends EditText implements ViewInterface {

    public ATEEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ATEEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ATEEditText(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, keyContext);
    }

    private void init(Context context, @Nullable ATEActivity keyContext) {
        if (getTag() == null)
            setTag(String.format("%s|accent_color,%s|primary_text", TintTagProcessor.PREFIX, TextColorTagProcessor.PREFIX));
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
