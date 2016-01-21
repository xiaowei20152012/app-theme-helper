package com.kabouzeid.appthemeenginesample.base;

import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.kabouzeid.appthemehelper.ATHBaseActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class BaseThemedActivity extends ATHBaseActivity {

    @Nullable
    @Override
    protected final String getATEKey() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_theme", false) ?
                "dark_theme" : "light_theme";
    }
}
