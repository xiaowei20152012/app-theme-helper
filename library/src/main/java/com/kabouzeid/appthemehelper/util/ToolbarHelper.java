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

    public static void handleOnPrepareOptionsMenu(Activity activity, Toolbar toolbar) {
        handleOnPrepareOptionsMenu(activity, toolbar, Config.accentColor(activity));
    }

    public static void handleOnPrepareOptionsMenu(Activity activity, Toolbar toolbar, int widgetColor) {
        ToolbarUtil.applyOverflowMenuTint(activity, toolbar, widgetColor);
    }

    public static void handleOnCreateOptionsMenu(Context context, Toolbar toolbar, Menu menu) {
        handleOnCreateOptionsMenu(context, toolbar, menu, Config.toolbarContentColor(context), Config.toolbarTitleColor(context), Config.toolbarSubtitleColor(context), Config.accentColor(context));
    }

    public static void handleOnCreateOptionsMenu(Context context, Toolbar toolbar, Menu menu, int toolbarColor) {
        handleOnCreateOptionsMenu(context, toolbar, menu, Config.toolbarContentColor(context, toolbarColor), Config.toolbarTitleColor(context, toolbarColor), Config.toolbarSubtitleColor(context, toolbarColor), Config.accentColor(context));
    }

    public static void handleOnCreateOptionsMenu(Context context, Toolbar toolbar, Menu menu, @ColorInt int toolbarContentColor, @ColorInt int titleTextColor, @ColorInt int subtitleTextColor, @ColorInt int menuWidgetColor) {
        ToolbarUtil.setToolbarContentColor(context, toolbar, menu, toolbarContentColor, titleTextColor, subtitleTextColor, menuWidgetColor);
    }

    private ToolbarHelper() {
    }
}
