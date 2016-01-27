package com.afollestad.appthemeengine.tagprocessors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Switch;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.util.TintHelper;

import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TintTagProcessor implements TagProcessor {

    public static final String PREFIX = "tint";
    public static final String BACKGROUND_PREFIX = "tint_background";
    public static final String SELECTOR_PREFIX_LIGHT = "tint_selector_lighter";
    public static final String SELECTOR_PREFIX = "tint_selector";

    private final boolean mBackgroundMode;
    private final boolean mSelectorMode;
    private final boolean mLightSelector;

    public TintTagProcessor(boolean backgroundMode, boolean selectorMode, boolean lighter) {
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
                view instanceof CheckedTextView;
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String suffix) {
        final int newTintColor;
        final boolean isDark = !ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground));

        switch (suffix) {
            case PRIMARY_COLOR:
                newTintColor = Config.primaryColor(context, key);
                break;
            case PRIMARY_COLOR_DARK:
                newTintColor = Config.primaryColorDark(context, key);
                break;
            case ACCENT_COLOR:
                newTintColor = Config.accentColor(context, key);
                break;
            case PRIMARY_TEXT_COLOR:
                newTintColor = Config.textColorPrimary(context, key);
                break;
            case PRIMARY_TEXT_COLOR_INVERSE:
                newTintColor = Config.textColorPrimaryInverse(context, key);
                break;
            case SECONDARY_TEXT_COLOR:
                newTintColor = Config.textColorSecondary(context, key);
                break;
            case SECONDARY_TEXT_COLOR_INVERSE:
                newTintColor = Config.textColorSecondaryInverse(context, key);
                break;

            case PARENT_DEPENDENT: {
                final String viewName = ATEUtil.getIdName(context, view.getId());
                if (view.getParent() == null)
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses background|parent_dependent tag but has no parent.", viewName));
                final View parent = (View) view.getParent();
                if (parent.getBackground() == null || !(parent.getBackground() instanceof ColorDrawable))
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses background|parent_dependent tag but parent doesn't have a ColorDrawable as its background.", viewName));
                final ColorDrawable bg = (ColorDrawable) parent.getBackground();
                newTintColor = ATEUtil.isColorLight(bg.getColor()) ? Color.BLACK : Color.WHITE;
                break;
            }
            case PRIMARY_COLOR_DEPENDENT:
                newTintColor = ATEUtil.isColorLight(Config.primaryColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case ACCENT_COLOR_DEPENDENT:
                newTintColor = ATEUtil.isColorLight(Config.accentColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case WINDOW_BG_DEPENDENT:
                newTintColor = ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground)) ?
                        Color.BLACK : Color.WHITE;
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown suffix: %s", suffix));
        }

        if (mSelectorMode) {
            TintHelper.setTintSelector(view, newTintColor, !mLightSelector, isDark);
        } else {
            TintHelper.setTintAuto(view, newTintColor, mBackgroundMode);
        }
    }

    private static final String PRIMARY_COLOR = "primary_color";
    private static final String PRIMARY_COLOR_DARK = "primary_color_dark";
    private static final String ACCENT_COLOR = "accent_color";
    private static final String PRIMARY_TEXT_COLOR = "primary_text";
    private static final String PRIMARY_TEXT_COLOR_INVERSE = "primary_text_inverse";
    private static final String SECONDARY_TEXT_COLOR = "secondary_text";
    private static final String SECONDARY_TEXT_COLOR_INVERSE = "secondary_text_inverse";

    private static final String PARENT_DEPENDENT = "parent_dependent";
    private static final String PRIMARY_COLOR_DEPENDENT = "primary_color_dependent";
    private static final String ACCENT_COLOR_DEPENDENT = "accent_color_dependent";
    private static final String WINDOW_BG_DEPENDENT = "window_bg_dependent";
}