package com.afollestad.appthemeengine.tagprocessors;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;

import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TextColorTagProcessor implements TagProcessor {

    public static final String PREFIX = "text_color";
    public static final String LINK_PREFIX = "text_color_link";
    public static final String HINT_PREFIX = "text_color_hint";

    private final String mPrefix;
    private final boolean mLinkMode;
    private final boolean mHintMode;

    public TextColorTagProcessor(String prefix, boolean links, boolean hints) {
        mPrefix = prefix;
        mLinkMode = links;
        mHintMode = hints;
    }

    @Override
    public boolean isTypeSupported(@NonNull View view) {
        return view instanceof TextView;
    }

    // TODO is dependent parameter needed?
    private static ColorStateList getTextSelector(@ColorInt int color, View view, boolean dependent) {
        if (dependent)
            color = ATEUtil.isColorLight(color) ? Color.BLACK : Color.WHITE;
        return new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        }, new int[]{
                // Buttons are gray when disabled, so the text needs to be black
                view instanceof Button ? Color.BLACK : ATEUtil.adjustAlpha(color, 0.3f),
                color
        });
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String suffix) {
        final TextView tv = (TextView) view;
        int newTextColor;

        switch (suffix) {
            case PRIMARY_COLOR:
                newTextColor = Config.primaryColor(context, key);
                break;
            case PRIMARY_COLOR_DARK:
                newTextColor = Config.primaryColorDark(context, key);
                break;
            case ACCENT_COLOR:
                newTextColor = Config.accentColor(context, key);
                break;
            case PRIMARY_TEXT_COLOR:
                newTextColor = Config.textColorPrimary(context, key);
                break;
            case PRIMARY_TEXT_COLOR_INVERSE:
                newTextColor = Config.textColorPrimaryInverse(context, key);
                break;
            case SECONDARY_TEXT_COLOR:
                newTextColor = Config.textColorSecondary(context, key);
                break;
            case SECONDARY_TEXT_COLOR_INVERSE:
                newTextColor = Config.textColorSecondaryInverse(context, key);
                break;

            case PARENT_DEPENDENT: {
                final String viewName = ATEUtil.getIdName(context, view.getId());
                if (view.getParent() == null) {
                    ATE.addPostInflationView(view);
                    return;
                }
                final View parent = (View) view.getParent();
                if (parent.getBackground() == null || !(parent.getBackground() instanceof ColorDrawable))
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses %s|parent_dependent tag but parent doesn't have a ColorDrawable as its background.",
                            viewName, mPrefix));
                final ColorDrawable bg = (ColorDrawable) parent.getBackground();
                newTextColor = ATEUtil.isColorLight(bg.getColor()) ? Color.BLACK : Color.WHITE;
                break;
            }
            case PRIMARY_COLOR_DEPENDENT:
                newTextColor = ATEUtil.isColorLight(Config.primaryColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case ACCENT_COLOR_DEPENDENT:
                newTextColor = ATEUtil.isColorLight(Config.accentColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case WINDOW_BG_DEPENDENT:
                newTextColor = ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground)) ?
                        Color.BLACK : Color.WHITE;
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown suffix: %s", suffix));
        }

        if (mHintMode)
            newTextColor = ATEUtil.adjustAlpha(newTextColor, 0.5f);

        final ColorStateList sl = getTextSelector(newTextColor, view, false);
        if (mLinkMode) {
            tv.setLinkTextColor(sl);
        } else if (mHintMode) {
            tv.setHintTextColor(sl);
        } else {
            tv.setTextColor(sl);
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