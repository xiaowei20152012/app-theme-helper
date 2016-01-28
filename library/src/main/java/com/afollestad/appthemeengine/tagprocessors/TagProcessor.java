package com.afollestad.appthemeengine.tagprocessors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;

import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public abstract class TagProcessor {

    public TagProcessor() {
    }

    public static class ColorResult {

        private int mColor;

        public ColorResult(@ColorInt int color) {
            mColor = color;
        }

        public int getColor() {
            return mColor;
        }

        public void adjustAlpha(float factor) {
            mColor = ATEUtil.adjustAlpha(mColor, factor);
        }
    }

    /**
     * Returns null if no action should be taken in the tag processors now (e.g. if the view
     * is being added to the post inflation views).
     */
    @Nullable
    protected ColorResult getColorFromSuffix(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String suffix) {
        final int result;
        switch (suffix) {
            case PRIMARY_COLOR:
                result = Config.primaryColor(context, key);
                break;
            case PRIMARY_COLOR_DARK:
                result = Config.primaryColorDark(context, key);
                break;
            case ACCENT_COLOR:
                result = Config.accentColor(context, key);
                break;
            case PRIMARY_TEXT_COLOR:
                result = Config.textColorPrimary(context, key);
                break;
            case PRIMARY_TEXT_COLOR_INVERSE:
                result = Config.textColorPrimaryInverse(context, key);
                break;
            case SECONDARY_TEXT_COLOR:
                result = Config.textColorSecondary(context, key);
                break;
            case SECONDARY_TEXT_COLOR_INVERSE:
                result = Config.textColorSecondaryInverse(context, key);
                break;

            case PARENT_DEPENDENT: {
                final String viewName = ATEUtil.getIdName(context, view.getId());
                if (view.getParent() == null) {
                    ATE.addPostInflationView(view);
                    return null;
                }
                final View parent = (View) view.getParent();
                if (parent.getBackground() == null || !(parent.getBackground() instanceof ColorDrawable))
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses a parent_dependent tag but parent doesn't have a ColorDrawable as its background.", viewName));
                final ColorDrawable bg = (ColorDrawable) parent.getBackground();
                result = ATEUtil.isColorLight(bg.getColor()) ? Color.BLACK : Color.WHITE;
                break;
            }
            case PRIMARY_COLOR_DEPENDENT:
                result = ATEUtil.isColorLight(Config.primaryColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case ACCENT_COLOR_DEPENDENT:
                result = ATEUtil.isColorLight(Config.accentColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case WINDOW_BG_DEPENDENT:
                result = ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground)) ?
                        Color.BLACK : Color.WHITE;
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown suffix: %s", suffix));
        }
        return new ColorResult(result);
    }

    public abstract boolean isTypeSupported(@NonNull View view);

    public abstract void process(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String suffix);


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