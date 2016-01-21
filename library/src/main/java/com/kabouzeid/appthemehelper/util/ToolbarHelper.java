package com.kabouzeid.appthemehelper.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.kabouzeid.appthemehelper.Config;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public final class ToolbarHelper {

    public static void handleOnPrepareOptionsMenu(Activity activity, Toolbar toolbar, @Nullable String key) {
        handleOnPrepareOptionsMenu(activity, toolbar, Config.accentColor(activity, key));
    }

    public static void handleOnPrepareOptionsMenu(Activity activity, Toolbar toolbar, int widgetColor) {
        ToolbarUtil.applyOverflowMenuTint(activity, toolbar, widgetColor);
    }

    public static void handleOnCreateOptionsMenu(Context context, Toolbar toolbar, Menu menu, @Nullable String key) {
        handleOnCreateOptionsMenu(context, toolbar, menu, Config.toolbarContentColor(context, key), Config.toolbarTitleColor(context, key), Config.toolbarSubtitleColor(context, key), Config.accentColor(context, key));
    }

    public static void handleOnCreateOptionsMenu(Context context, Toolbar toolbar, Menu menu, int toolbarColor, @Nullable String key) {
        handleOnCreateOptionsMenu(context, toolbar, menu, Config.toolbarContentColor(context, key, toolbarColor), Config.toolbarTitleColor(context, key, toolbarColor), Config.toolbarSubtitleColor(context, key, toolbarColor), Config.accentColor(context, key));
    }

    public static void handleOnCreateOptionsMenu(Context context, Toolbar toolbar, Menu menu, @ColorInt int toolbarContentColor, @ColorInt int titleTextColor, @ColorInt int subtitleTextColor, @ColorInt int menuWidgetColor) {
        ToolbarUtil.setToolbarContentColor(context, toolbar, menu, toolbarContentColor, titleTextColor, subtitleTextColor, menuWidgetColor);
    }

    private ToolbarHelper() {
    }
}
