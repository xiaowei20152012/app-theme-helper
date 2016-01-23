package com.kabouzeid.appthemehelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.kabouzeid.appthemehelper.util.ColorUtil;
import com.kabouzeid.appthemehelper.util.TintHelper;

/**
 * @author Aidan Follestad (afollestad), Karim Abou Zeid (kabouzeid)
 */
public final class ATH {

    public static Config config(@NonNull Context context) {
        return new Config(context);
    }

    @SuppressLint("CommitPrefEdits")
    public static boolean didValuesChange(@NonNull Context context, long since) {
        return ATH.config(context).isConfigured() && Config.prefs(context).getLong(Config.VALUES_CHANGED, -1) > since;
    }


    public static void setStatusbarColorAuto(Activity activity) {
        setStatusbarColor(activity, Config.statusBarColor(activity));
    }

    public static void setStatusbarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
            setLightStatusbarAuto(activity, color);
        }
    }

    public static void setLightStatusbarAuto(Activity activity, int bgColor) {
        setLightStatusbar(activity, ColorUtil.isColorLight(bgColor));
    }

    public static void setLightStatusbar(Activity activity, boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final View decorView = activity.getWindow().getDecorView();
            final int systemUiVisibility = decorView.getSystemUiVisibility();
            if (enabled) {
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    public static void setNavigationbarColorAuto(Activity activity) {
        setNavigationbarColor(activity, Config.navigationBarColor(activity));
    }

    public static void setNavigationbarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }

    public static void setTaskDescriptionColorAuto(@NonNull Activity activity) {
        setTaskDescriptionColor(activity, Config.primaryColor(activity));
    }

    public static void setTaskDescriptionColor(@NonNull Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Task description requires fully opaque color
            color = ColorUtil.stripAlpha(color);
            // Sets color of entry in the system recents page
            activity.setTaskDescription(new ActivityManager.TaskDescription((String) activity.getTitle(), null, color));
        }
    }

    public static void setTint(@NonNull View view, @ColorInt int color) {
        TintHelper.setTintAuto(view, color, false);
    }

    public static void setBackgroundTint(@NonNull View view, @ColorInt int color) {
        TintHelper.setTintAuto(view, color, true);
    }

    private ATH() {
    }
}