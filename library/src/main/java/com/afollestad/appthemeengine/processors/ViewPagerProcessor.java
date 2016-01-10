package com.afollestad.appthemeengine.processors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.afollestad.appthemeengine.util.EdgeGlowUtil;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ViewPagerProcessor implements Processor<ViewPager, Void> {

    public static final String MAIN_CLASS = "android.support.v4.view.ViewPager";

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable ViewPager target, @Nullable Void extra) {
        if (target == null) return;
        EdgeGlowUtil.setEdgeGlowColor(target, ScrollViewProcessor.processTag(context, key, target));
    }
}
