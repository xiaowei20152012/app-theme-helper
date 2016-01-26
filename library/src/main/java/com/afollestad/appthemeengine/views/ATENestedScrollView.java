package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATENestedScrollView extends NestedScrollView {

    public ATENestedScrollView(Context context) {
        super(context);
        init(context, null);
    }

    public ATENestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATENestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ATEViewUtil.init(this, context, attrs, R.styleable.ATENestedScrollView, R.styleable.ATENestedScrollView_ateKey_nestedScrollView);
    }
}