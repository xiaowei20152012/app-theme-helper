package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATEViewUtil {

    public static String init(View view, Context context, AttributeSet attrs, @StyleableRes int[] viewAttr, @StyleableRes int ateKeyAttr) {
        String key = null;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, viewAttr, 0, 0);
            try {
                key = a.getString(ateKeyAttr);
            } finally {
                a.recycle();
            }
        }
        if (key == null && context instanceof ATEActivity)
            key = ((ATEActivity) context).getATEKey();
        ATE.apply(context, view, key);
        return key;
    }

    private ATEViewUtil() {
    }
}