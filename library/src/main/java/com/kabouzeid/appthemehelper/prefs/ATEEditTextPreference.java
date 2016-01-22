package com.kabouzeid.appthemehelper.prefs;

import android.content.Context;
import android.util.AttributeSet;

import com.afollestad.materialdialogs.prefs.MaterialEditTextPreference;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEEditTextPreference extends MaterialEditTextPreference {

    public ATEEditTextPreference(Context context) {
        super(context);
        init();
    }

    public ATEEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ATEEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ATEEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setLayoutResource(com.kabouzeid.appthemehelper.R.layout.ate_preference_custom);
    }
}
