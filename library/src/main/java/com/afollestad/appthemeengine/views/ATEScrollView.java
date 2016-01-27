package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEScrollView extends ScrollView implements ViewInterface {

    public ATEScrollView(Context context) {
        super(context);
        init(context, null);
    }

    public ATEScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATEScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ATEViewUtil.init(this, context, attrs, R.styleable.ATEScrollView, R.styleable.ATEScrollView_ateKey_scrollView);
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
