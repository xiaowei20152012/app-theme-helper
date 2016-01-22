package com.kabouzeid.appthemehelpersample;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.prefs.MaterialListPreference;
import com.kabouzeid.appthemehelper.ATH;
import com.kabouzeid.appthemehelper.Config;
import com.kabouzeid.appthemehelper.prefs.ATEColorPreference;
import com.kabouzeid.appthemehelper.prefs.ATESwitchPreference;
import com.kabouzeid.appthemehelpersample.base.BaseThemedActivity;
import com.kabouzeid.appthemehelpersample.dialogs.AboutDialog;

/**
 * @author Aidan Follestad (afollestad)
 */
@SuppressLint("NewApi")
public class SettingsActivity extends BaseThemedActivity
        implements ColorChooserDialog.ColorCallback {

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        final Config config = ATH.config(this);
        switch (dialog.getTitle()) {
            case R.string.primary_color:
                config.primaryColor(selectedColor);
                break;
            case R.string.accent_color:
                config.accentColor(selectedColor);
                break;
            case R.string.primary_text_color:
                config.textColorPrimary(selectedColor);
                break;
            case R.string.secondary_text_color:
                config.textColorSecondary(selectedColor);
                break;
        }
        config.commit();
        recreate();
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            invalidateSettings();
        }

        public void invalidateSettings() {
            ATEColorPreference primaryColorPref = (ATEColorPreference) findPreference("primary_color");
            primaryColorPref.setColor(Config.primaryColor(getActivity()), Color.BLACK);
            primaryColorPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new ColorChooserDialog.Builder((SettingsActivity) getActivity(), R.string.primary_color)
                            .preselect(Config.primaryColor(getActivity()))
                            .show();
                    return true;
                }
            });

            ATEColorPreference accentColorPref = (ATEColorPreference) findPreference("accent_color");
            accentColorPref.setColor(Config.accentColor(getActivity()), Color.BLACK);
            accentColorPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new ColorChooserDialog.Builder((SettingsActivity) getActivity(), R.string.accent_color)
                            .preselect(Config.accentColor(getActivity()))
                            .show();
                    return true;
                }
            });

            ATEColorPreference textColorPrimaryPref = (ATEColorPreference) findPreference("text_primary");
            textColorPrimaryPref.setColor(Config.textColorPrimary(getActivity()), Color.BLACK);
            textColorPrimaryPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new ColorChooserDialog.Builder((SettingsActivity) getActivity(), R.string.primary_text_color)
                            .preselect(Config.textColorPrimary(getActivity()))
                            .show();
                    return true;
                }
            });

            ATEColorPreference textColorSecondaryPref = (ATEColorPreference) findPreference("text_secondary");
            textColorSecondaryPref.setColor(Config.textColorSecondary(getActivity()), Color.BLACK);
            textColorSecondaryPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new ColorChooserDialog.Builder((SettingsActivity) getActivity(), R.string.secondary_text_color)
                            .preselect(Config.textColorSecondary(getActivity()))
                            .show();
                    return true;
                }
            });

            findPreference("dark_theme").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // Marks both theme configs as changed so MainActivity restarts itself on return
                    Config.markChanged(getActivity());
                    // The dark_theme preference value gets saved by Android in the default PreferenceManager.
                    // It's used in getATEKey() of both the Activities.
                    getActivity().recreate();
                    return true;
                }
            });

            final MaterialListPreference lightStatusMode = (MaterialListPreference) findPreference("light_status_bar_mode");
            final MaterialListPreference lightToolbarMode = (MaterialListPreference) findPreference("light_toolbar_mode");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                lightStatusMode.setEnabled(true);
                lightStatusMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        @Config.LightStatusBarMode
                        int constant = Integer.parseInt((String) newValue);
                        ATH.config(getActivity())
                                .lightStatusBarMode(constant)
                                .commit();
                        getActivity().recreate();
                        return true;
                    }
                });
            } else {
                lightStatusMode.setEnabled(false);
                lightStatusMode.setSummary(R.string.not_available_below_m);
            }

            lightToolbarMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    @Config.LightToolbarMode
                    int constant = Integer.parseInt((String) newValue);
                    ATH.config(getActivity())
                            .lightToolbarMode(constant)
                            .commit();
                    getActivity().recreate();
                    return true;
                }
            });

            final ATESwitchPreference statusBarPref = (ATESwitchPreference) findPreference("colored_status_bar");
            final ATESwitchPreference navBarPref = (ATESwitchPreference) findPreference("colored_nav_bar");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarPref.setChecked(Config.coloredStatusBar(getActivity()));
                statusBarPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        ATH.config(getActivity())
                                .coloredStatusBar((Boolean) newValue)
                                .commit();
                        getActivity().recreate();
                        return true;
                    }
                });


                navBarPref.setChecked(Config.coloredNavigationBar(getActivity()));
                navBarPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        ATH.config(getActivity())
                                .coloredNavigationBar((Boolean) newValue)
                                .commit();
                        getActivity().recreate();
                        return true;
                    }
                });
            } else {
                statusBarPref.setEnabled(false);
                statusBarPref.setSummary(R.string.not_available_below_lollipop);
                navBarPref.setEnabled(false);
                navBarPref.setSummary(R.string.not_available_below_lollipop);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_activity_custom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
        } else {
            SettingsFragment frag = (SettingsFragment) getFragmentManager().findFragmentById(R.id.content_frame);
            if (frag != null) frag.invalidateSettings();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.about) {
            AboutDialog.show(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}