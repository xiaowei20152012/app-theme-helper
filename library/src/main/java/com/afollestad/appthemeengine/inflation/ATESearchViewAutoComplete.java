package com.afollestad.appthemeengine.inflation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.tagprocessors.TextColorTagProcessor;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATESearchViewAutoComplete extends SearchView.SearchAutoComplete implements ViewInterface {

    public ATESearchViewAutoComplete(Context context) {
        super(context);
        init(context, null);
    }

    public ATESearchViewAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ATESearchViewAutoComplete(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, keyContext);
    }

    private void init(Context context, @Nullable ATEActivity keyContext) {
        setTag(String.format("%s|primary_text", TextColorTagProcessor.PREFIX));
        try {
            ATEViewUtil.init(keyContext, this, context);
        } catch (Throwable t) {
            throw new RuntimeException(t.getMessage(), t);
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