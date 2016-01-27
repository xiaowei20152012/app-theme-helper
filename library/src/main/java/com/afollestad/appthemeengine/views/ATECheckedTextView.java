package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATECheckedTextView extends CheckedTextView implements ViewInterface {

    public ATECheckedTextView(Context context) {
        super(context);
        init(context, null, null);
    }

    public ATECheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public ATECheckedTextView(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, attrs, keyContext);
    }

    private void init(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        setTag("tint_accent_color,text_primary");
        ATEViewUtil.init(keyContext, this, context, attrs, R.styleable.ATECheckBox, R.styleable.ATECheckBox_ateKey_checkBox);
    }

    public void setKey(String key) {
        ATE.apply(getContext(), this, key);
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
