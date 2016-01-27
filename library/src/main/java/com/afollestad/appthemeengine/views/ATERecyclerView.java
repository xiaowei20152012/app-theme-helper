package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATERecyclerView extends RecyclerView implements ViewInterface {

    public ATERecyclerView(Context context) {
        super(context);
        init(context, null, null);
    }

    public ATERecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public ATERecyclerView(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, attrs, keyContext);
    }

    private void init(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        ATEViewUtil.init(keyContext, this, context, attrs, R.styleable.ATERecyclerView, R.styleable.ATERecyclerView_ateKey_recyclerView);
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
