package com.afollestad.appthemeengine.inflation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Spinner;

import com.afollestad.appthemeengine.ATEActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEStockSpinner extends Spinner implements ViewInterface {

    public ATEStockSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public ATEStockSpinner(Context context, int mode) {
        super(context, mode);
        init(context, null);
    }

    public ATEStockSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ATEStockSpinner(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, keyContext);
    }

    public ATEStockSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    public ATEStockSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init(context, null);
    }

    private void init(Context context, @Nullable ATEActivity keyContext) {
        // TODO Need a parent background dependent tint tag? Primary dependent tag?
//        ATEViewUtil.init(keyContext, this, context);
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
