package com.kabouzeid.appthemehelper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kabouzeid.appthemehelper.util.ColorUtil;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class ATH {

    public static Config config(@NonNull Context context, @Nullable String key) {
        return new Config(context, key);
    }

    @SuppressLint("CommitPrefEdits")
    public static boolean didValuesChange(@NonNull Context context, long since, @Nullable String key) {
        return ATH.config(context, key).isConfigured() && Config.prefs(context, key).getLong(Config.VALUES_CHANGED, -1) > since;
    }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            final Window window = activity.getWindow();
//            if (Config.coloredStatusBar(activity, key))
//                window.setStatusBarColor(Config.statusBarColor(activity, key));
//            else window.setStatusBarColor(Color.BLACK);
//            if (Config.coloredNavigationBar(activity, key))
//                window.setNavigationBarColor(Config.navigationBarColor(activity, key));
//            else window.setNavigationBarColor(Color.BLACK);
//            applyTaskDescription(activity, key);
//        }

//    if (rootView instanceof DrawerLayout) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            final int color = Config.coloredStatusBar(activity, key) ?
//                    Color.TRANSPARENT : Color.BLACK;
//            activity.getWindow().setStatusBarColor(color);
//        }
//        if (Config.coloredStatusBar(activity, key))
//            ((DrawerLayout) rootView).setStatusBarBackgroundColor(Config.statusBarColor(activity, key));
//    }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            final View decorView = activity.getWindow().getDecorView();
//            boolean lightStatusEnabled = false;
//            if (Config.coloredStatusBar(activity, key)) {
//                final int lightStatusMode = Config.lightStatusBarMode(activity, key);
//                switch (lightStatusMode) {
//                    case Config.LIGHT_STATUS_BAR_OFF:
//                    default:
//                        break;
//                    case Config.LIGHT_STATUS_BAR_ON:
//                        lightStatusEnabled = true;
//                        break;
//                    case Config.LIGHT_STATUS_BAR_AUTO:
//                        lightStatusEnabled = ATHUtil.isColorLight(Config.primaryColor(activity, key));
//                        break;
//                }
//            }
//
//            final int systemUiVisibility = decorView.getSystemUiVisibility();
//            if (lightStatusEnabled) {
//                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            } else {
//                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//        }

    private static void setTaskDescriptionColor(@NonNull Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTaskDescriptionColor(activity, null, color);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setTaskDescriptionColor(@NonNull Activity activity, @Nullable Bitmap icon, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Task description requires fully opaque color
            color = ColorUtil.stripAlpha(color);

            // Sets color of entry in the system recents page
            activity.setTaskDescription(new ActivityManager.TaskDescription((String) activity.getTitle(), icon, color));
        }
    }

    private ATH() {
    }
}