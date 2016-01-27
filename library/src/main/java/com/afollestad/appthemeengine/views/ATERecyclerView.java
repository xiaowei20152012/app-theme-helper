package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATERecyclerView extends RecyclerView implements ViewInterface {

    public ATERecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public ATERecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATERecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ATEViewUtil.init(this, context, attrs, R.styleable.ATERecyclerView, R.styleable.ATERecyclerView_ateKey_recyclerView);
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
