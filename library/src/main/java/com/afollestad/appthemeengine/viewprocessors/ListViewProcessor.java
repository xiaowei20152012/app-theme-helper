package com.afollestad.appthemeengine.viewprocessors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.afollestad.appthemeengine.util.EdgeGlowUtil;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ListViewProcessor implements ViewProcessor<ListView, Void> {

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable ListView target, @Nullable Void extra) {
        if (target == null) return;
        EdgeGlowUtil.setEdgeGlowColor(target, ScrollViewProcessor.processTags(context, key, target));
    }
}