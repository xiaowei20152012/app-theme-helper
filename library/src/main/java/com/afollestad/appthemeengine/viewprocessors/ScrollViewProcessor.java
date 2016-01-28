package com.afollestad.appthemeengine.viewprocessors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ScrollView;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.util.EdgeGlowUtil;

import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ScrollViewProcessor implements ViewProcessor<ScrollView, Void> {

    @ColorInt
    public static int processTags(@NonNull Context context, @Nullable String key, @NonNull View forView) {
        if (forView.getTag() == null)
            return Config.accentColor(context, key);
        final String tag = (String) forView.getTag();
        final int defaultColor = Config.accentColor(context, key);
        if (tag.contains(",")) {
            final String[] tags = tag.split(",");
            for (final String t : tags) {
                if (!t.contains("|")) continue;
                final String prefix = t.substring(0, t.indexOf('|'));
                if (!prefix.equals(PREFIX)) continue;
                final String suffix = t.substring(t.indexOf('|') + 1);
                return processTag(context, forView, suffix, key, defaultColor);
            }
            return defaultColor;
        } else {
            if (!tag.contains("|")) return defaultColor;
            final String prefix = tag.substring(0, tag.indexOf('|'));
            if (!prefix.equals(PREFIX)) return defaultColor;
            final String suffix = tag.substring(tag.indexOf('|') + 1);
            return processTag(context, forView, suffix, key, defaultColor);
        }
    }

    @ColorInt
    private static int processTag(@NonNull Context context, @NonNull View view, @NonNull String suffix, @Nullable String key, @ColorInt int defaultColor) {
        final int newColor;

        switch (suffix) {
            case PRIMARY_COLOR:
                newColor = Config.primaryColor(context, key);
                break;
            case PRIMARY_COLOR_DARK:
                newColor = Config.primaryColorDark(context, key);
                break;
            case ACCENT_COLOR:
                newColor = Config.accentColor(context, key);
                break;
            case PRIMARY_TEXT_COLOR:
                newColor = Config.textColorPrimary(context, key);
                break;
            case PRIMARY_TEXT_COLOR_INVERSE:
                newColor = Config.textColorPrimaryInverse(context, key);
                break;
            case SECONDARY_TEXT_COLOR:
                newColor = Config.textColorSecondary(context, key);
                break;
            case SECONDARY_TEXT_COLOR_INVERSE:
                newColor = Config.textColorSecondaryInverse(context, key);
                break;

            case PARENT_DEPENDENT: {
                final String viewName = ATEUtil.getIdName(context, view.getId());
                if (view.getParent() == null) {
                    ATE.addPostInflationView(view);
                    return defaultColor;
                }
                final View parent = (View) view.getParent();
                if (parent.getBackground() == null || !(parent.getBackground() instanceof ColorDrawable))
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses background|parent_dependent tag but parent doesn't have a ColorDrawable as its background.", viewName));
                final ColorDrawable bg = (ColorDrawable) parent.getBackground();
                newColor = ATEUtil.isColorLight(bg.getColor()) ? Color.BLACK : Color.WHITE;
                break;
            }
            case PRIMARY_COLOR_DEPENDENT:
                newColor = ATEUtil.isColorLight(Config.primaryColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case ACCENT_COLOR_DEPENDENT:
                newColor = ATEUtil.isColorLight(Config.accentColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case WINDOW_BG_DEPENDENT:
                newColor = ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground)) ?
                        Color.BLACK : Color.WHITE;
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown suffix: %s", suffix));
        }

        return newColor;
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable ScrollView target, @Nullable Void extra) {
        if (target == null) return;
        EdgeGlowUtil.setEdgeGlowColor(target, processTags(context, key, target));
    }

    private static final String PREFIX = "edge_glow";
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
