package com.afollestad.appthemeengine.viewprocessors;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ScrollView;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.EdgeGlowUtil;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ScrollViewProcessor implements ViewProcessor<ScrollView, Void> {

    @ColorInt
    public static int processTag(@NonNull Context context, @Nullable String key, @NonNull View forView) {
        if (forView.getTag() == null)
            return Config.accentColor(context, key);
        final String tag = (String) forView.getTag();
        if (tag.contains(",")) {
            final String[] tags = tag.split(",");
            for (final String t : tags) {
                switch (t) {
                    case EDGEGLOW_PRIMARY_COLOR:
                        return Config.primaryColor(context, key);
                    case EDGEGLOW_PRIMARY_COLOR_DARK:
                        return Config.primaryColorDark(context, key);
                    case EDGEGLOW_ACCENT_COLOR:
                        return Config.accentColor(context, key);
                    case EDGEGLOW_TEXTPRIMARY:
                        return Config.textColorPrimary(context, key);
                    case EDGEGLOW_TEXTPRIMARY_INVERSE:
                        return Config.textColorPrimaryInverse(context, key);
                    case EDGEGLOW_TEXTSECONDARY:
                        return Config.textColorSecondary(context, key);
                    case EDGEGLOW_TEXTSECONDARY_INVERSE:
                        return Config.textColorSecondaryInverse(context, key);
                }
            }
        } else {
            switch (tag) {
                case EDGEGLOW_PRIMARY_COLOR:
                    return Config.primaryColor(context, key);
                case EDGEGLOW_PRIMARY_COLOR_DARK:
                    return Config.primaryColorDark(context, key);
                case EDGEGLOW_ACCENT_COLOR:
                    return Config.accentColor(context, key);
                case EDGEGLOW_TEXTPRIMARY:
                    return Config.textColorPrimary(context, key);
                case EDGEGLOW_TEXTPRIMARY_INVERSE:
                    return Config.textColorPrimaryInverse(context, key);
                case EDGEGLOW_TEXTSECONDARY:
                    return Config.textColorSecondary(context, key);
                case EDGEGLOW_TEXTSECONDARY_INVERSE:
                    return Config.textColorSecondaryInverse(context, key);
            }
        }
        return Config.accentColor(context, key);
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable ScrollView target, @Nullable Void extra) {
        if (target == null) return;
        EdgeGlowUtil.setEdgeGlowColor(target, processTag(context, key, target));
    }

    private static final String EDGEGLOW_PRIMARY_COLOR = "edge_glow_primary_color";
    private static final String EDGEGLOW_PRIMARY_COLOR_DARK = "edge_glow_primary_color_dark";
    private static final String EDGEGLOW_ACCENT_COLOR = "edge_glow_accent_color";
    private static final String EDGEGLOW_TEXTPRIMARY = "edge_glow_text_primary";
    private static final String EDGEGLOW_TEXTPRIMARY_INVERSE = "edge_glow_text_primary_inverse";
    private static final String EDGEGLOW_TEXTSECONDARY = "edge_glow_text_secondary";
    private static final String EDGEGLOW_TEXTSECONDARY_INVERSE = "edge_glow_text_secondary_inverse";
}
