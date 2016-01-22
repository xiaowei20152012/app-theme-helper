package com.kabouzeid.appthemehelper.prefs;

import android.content.Context;
import android.util.AttributeSet;

import com.afollestad.materialdialogs.prefs.MaterialListPreference;
import com.kabouzeid.appthemehelper.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEMultiSelectPreference extends MaterialListPreference {

    public ATEMultiSelectPreference(Context context) {
        super(context);
        init();
    }

    public ATEMultiSelectPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ATEMultiSelectPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ATEMultiSelectPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setLayoutResource(R.layout.ate_preference_custom);
    }
}
