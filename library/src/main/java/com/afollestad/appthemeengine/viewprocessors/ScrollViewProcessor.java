package com.afollestad.appthemeengine.viewprocessors;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ScrollView;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.tagprocessors.TagProcessor;
import com.afollestad.appthemeengine.util.EdgeGlowUtil;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ScrollViewProcessor implements ViewProcessor<ScrollView, Void> {

    private static final String PREFIX = "edge_glow";

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
                final TagProcessor.ColorResult result = TagProcessor.getColorFromSuffix(context, key, forView, suffix);
                if (result == null) return defaultColor;
                return result.getColor();
            }
            return defaultColor;
        } else {
            if (!tag.contains("|")) return defaultColor;
            final String prefix = tag.substring(0, tag.indexOf('|'));
            if (!prefix.equals(PREFIX)) return defaultColor;
            final String suffix = tag.substring(tag.indexOf('|') + 1);
            final TagProcessor.ColorResult result = TagProcessor.getColorFromSuffix(context, key, forView, suffix);
            if (result == null) return defaultColor;
            return result.getColor();
        }
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable ScrollView target, @Nullable Void extra) {
        if (target == null) return;
        EdgeGlowUtil.setEdgeGlowColor(target, processTags(context, key, target));
    }
}