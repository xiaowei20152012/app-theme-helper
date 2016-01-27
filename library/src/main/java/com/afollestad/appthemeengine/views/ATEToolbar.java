package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEToolbar extends Toolbar implements PostInflationApplier, ViewInterface {

    public ATEToolbar(Context context) {
        super(context);
        init(context, null);
    }

    public ATEToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATEToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private String mKey;

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEToolbar, 0, 0);
            try {
                mKey = a.getString(R.styleable.ATEToolbar_ateKey_toolbar);
            } finally {
                a.recycle();
            }
        }
        if (mKey == null && context instanceof ATEActivity)
            mKey = ((ATEActivity) context).getATEKey();
    }

    @Override
    public void postApply() {
        ATE.apply(getContext(), this, mKey);
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