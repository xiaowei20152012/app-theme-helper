package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.ATEActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATESearchView extends SearchView implements ViewInterface {

    public ATESearchView(Context context) {
        super(context);
        init(context, null);
    }

    public ATESearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ATESearchView(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, keyContext);
    }

    private void init(Context context, @Nullable ATEActivity keyContext) {
        setTag("tint_accent_color,text_primary");
        ATEViewUtil.init(keyContext, this, context, null, null, 0);
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
