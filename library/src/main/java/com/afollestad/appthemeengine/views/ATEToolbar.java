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
public class ATEToolbar extends Toolbar {

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

    private void init(Context context, AttributeSet attrs) {
        String key = null;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEToolbar, 0, 0);
            try {
                key = a.getString(R.styleable.ATEToolbar_ateKey_toolbar);
            } finally {
                a.recycle();
            }
        }
        if (key == null && context instanceof ATEActivity)
            key = ((ATEActivity) context).getATEKey();
        ATE.apply(context, this, key);
    }
}