package com.kabouzeid.appthemehelper.views;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.kabouzeid.appthemehelper.Config;
import com.kabouzeid.appthemehelper.util.TintHelper;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATECheckBox extends AppCompatCheckBox {

    public ATECheckBox(Context context) {
        super(context);
        init(context);
    }

    public ATECheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ATECheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        TintHelper.setTintAuto(this, Config.accentColor(context), false);
    }
}
