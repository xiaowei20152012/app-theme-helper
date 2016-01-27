package com.afollestad.appthemeenginesample.misc;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.StyleRes;

import com.afollestad.appthemeengine.customizers.ATEActivityThemeCustomizer;
import com.afollestad.appthemeenginesample.R;
import com.afollestad.appthemeenginesample.base.BaseThemedActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class MiscActivity extends BaseThemedActivity implements ATEActivityThemeCustomizer {

    @StyleRes
    @Override
    public int getActivityTheme() {
        // Overrides what's set in the current ATE Config
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_theme", false) ?
                R.style.AppThemeDark_ActionBar : R.style.AppTheme_ActionBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);

        assert getSupportActionBar() != null;
        getSupportActionBar().setSubtitle("Test subtitle");
    }
}