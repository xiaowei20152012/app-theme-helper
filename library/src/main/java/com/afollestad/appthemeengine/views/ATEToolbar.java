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
        init(context, null, null);
    }

    public ATEToolbar(Context context, @Nullable AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, attrs, keyContext);
    }

    private String mKey;

    private void init(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEToolbar, 0, 0);
            try {
                mKey = a.getString(R.styleable.ATEToolbar_ateKey_toolbar);
            } finally {
                a.recycle();
            }
        }
        if (keyContext == null && context instanceof ATEActivity)
            keyContext = (ATEActivity) context;
        if (mKey == null && keyContext != null)
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