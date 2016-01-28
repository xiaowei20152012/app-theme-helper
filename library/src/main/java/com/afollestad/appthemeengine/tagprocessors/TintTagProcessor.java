package com.afollestad.appthemeengine.tagprocessors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.util.TintHelper;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TintTagProcessor extends TagProcessor {

    public static final String PREFIX = "tint";
    public static final String BACKGROUND_PREFIX = "tint_background";
    public static final String SELECTOR_PREFIX_LIGHT = "tint_selector_lighter";
    public static final String SELECTOR_PREFIX = "tint_selector";

    private final String mPrefix;
    private final boolean mBackgroundMode;
    private final boolean mSelectorMode;
    private final boolean mLightSelector;

    public TintTagProcessor(String prefix, boolean backgroundMode, boolean selectorMode, boolean lighter) {
        mPrefix = prefix;
        mBackgroundMode = backgroundMode;
        mSelectorMode = selectorMode;
        mLightSelector = lighter;
    }

    @Override
    public boolean isTypeSupported(@NonNull View view) {
        return mBackgroundMode ||
                mSelectorMode ||
                view instanceof CheckBox ||
                view instanceof RadioButton ||
                view instanceof ProgressBar || // includes SeekBar
                view instanceof EditText ||
                view instanceof ImageView ||
                view instanceof Switch ||
                view instanceof SwitchCompat ||
                view instanceof CheckedTextView ||
                view instanceof Spinner;
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String suffix) {
        final boolean isDark = !ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground));
        final ColorResult result = getColorFromSuffix(context, key, view, suffix);
        if (result == null) return;

        if (mSelectorMode) {
            TintHelper.setTintSelector(view, result.getColor(), !mLightSelector, isDark);
        } else {
            TintHelper.setTintAuto(view, result.getColor(), mBackgroundMode);
        }
    }
}