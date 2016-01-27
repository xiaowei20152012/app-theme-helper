package com.afollestad.appthemeengine.viewprocessors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;

import com.afollestad.appthemeengine.Config;

/**
 * @author Aidan Follestad (afollestad)
 */
public class DrawerLayoutProcessor implements ViewProcessor<DrawerLayout, Void> {

    public static final String MAIN_CLASS = "android.support.v4.widget.DrawerLayout";

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable DrawerLayout target, @Nullable Void extra) {
        if (!(context instanceof Activity) || target == null) return;
        final Activity activity = (Activity) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final int color = Config.coloredStatusBar(activity, key) ?
                    Color.TRANSPARENT : Color.BLACK;
            activity.getWindow().setStatusBarColor(color);
        }
        if (Config.coloredStatusBar(activity, key))
            target.setStatusBarBackgroundColor(Config.statusBarColor(activity, key));
    }
}