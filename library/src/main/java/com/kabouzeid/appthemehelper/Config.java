package com.kabouzeid.appthemehelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.kabouzeid.appthemehelper.util.ATHUtil;
import com.kabouzeid.appthemehelper.util.ColorUtil;
import com.kabouzeid.appthemehelper.util.MaterialValueHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class Config extends ConfigBase {

    private final Context mContext;
    private final SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    protected Config(@NonNull Context context) {
        mContext = context;
        mEditor = prefs(context).edit();
    }

    @CheckResult
    @Override
    public boolean isConfigured() {
        return prefs(mContext).getBoolean(IS_CONFIGURED_KEY, false);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean isConfigured(@IntRange(from = 0, to = Integer.MAX_VALUE) int version) {
        final SharedPreferences prefs = prefs(mContext);
        final int lastVersion = prefs.getInt(IS_CONFIGURED_VERSION_KEY, -1);
        if (version > lastVersion) {
            prefs.edit().putInt(IS_CONFIGURED_VERSION_KEY, version).commit();
            return false;
        }
        return true;
    }

    @Override
    public Config activityTheme(@StyleRes int theme) {
        mEditor.putInt(KEY_ACTIVITY_THEME, theme);
        return this;
    }

    @Override
    public Config primaryColor(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR, color);
        if (autoGeneratePrimaryDark(mContext))
            primaryColorDark(ColorUtil.darkenColor(color));
        return this;
    }

    @Override
    public Config primaryColorRes(@ColorRes int colorRes) {
        return primaryColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config primaryColorAttr(@AttrRes int colorAttr) {
        return primaryColor(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config primaryColorDark(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR_DARK, color);
        return this;
    }

    @Override
    public Config primaryColorDarkRes(@ColorRes int colorRes) {
        return primaryColorDark(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config primaryColorDarkAttr(@AttrRes int colorAttr) {
        return primaryColorDark(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config accentColor(@ColorInt int color) {
        mEditor.putInt(KEY_ACCENT_COLOR, color);
        return this;
    }

    @Override
    public Config accentColorRes(@ColorRes int colorRes) {
        return accentColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config accentColorAttr(@AttrRes int colorAttr) {
        return accentColor(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config statusBarColor(@ColorInt int color) {
        mEditor.putInt(KEY_STATUS_BAR_COLOR, color);
        return this;
    }

    @Override
    public Config statusBarColorRes(@ColorRes int colorRes) {
        return statusBarColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config statusBarColorAttr(@AttrRes int colorAttr) {
        return statusBarColor(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config toolbarColor(@ColorInt int color) {
        mEditor.putInt(KEY_TOOLBAR_COLOR, color);
        return this;
    }

    @Override
    public Config toolbarColorRes(@ColorRes int colorRes) {
        return toolbarColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config toolbarColorAttr(@AttrRes int colorAttr) {
        return toolbarColor(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationBarColor(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATION_BAR_COLOR, color);
        return this;
    }

    @Override
    public Config navigationBarColorRes(@ColorRes int colorRes) {
        return navigationBarColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationBarColorAttr(@AttrRes int colorAttr) {
        return navigationBarColor(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config textColorPrimary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_PRIMARY, color);
        return this;
    }

    @Override
    public Config textColorPrimaryRes(@ColorRes int colorRes) {
        return textColorPrimary(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config textColorPrimaryAttr(@AttrRes int colorAttr) {
        return textColorPrimary(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config textColorSecondary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_SECONDARY, color);
        return this;
    }

    @Override
    public Config textColorSecondaryRes(@ColorRes int colorRes) {
        return textColorSecondary(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config textColorSecondaryAttr(@AttrRes int colorAttr) {
        return textColorSecondary(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config coloredStatusBar(boolean colored) {
        mEditor.putBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, colored);
        return this;
    }

    @Override
    public Config coloredActionBar(boolean applyToActionBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, applyToActionBar);
        return this;
    }

    @Override
    public Config coloredNavigationBar(boolean applyToNavBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_NAVBAR, applyToNavBar);
        return this;
    }

    @Override
    public Config autoGeneratePrimaryDark(boolean autoGenerate) {
        mEditor.putBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, autoGenerate);
        return this;
    }

    @Override
    public Config lightStatusBarMode(@LightStatusBarMode int mode) {
        mEditor.putInt(KEY_LIGHT_STATUS_BAR_MODE, mode);
        return this;
    }

    @Override
    public Config lightToolbarMode(@LightToolbarMode int mode) {
        mEditor.putInt(KEY_LIGHT_TOOLBAR_MODE, mode);
        return this;
    }

    @Override
    public Config navigationViewThemed(boolean themed) {
        mEditor.putBoolean(KEY_THEMED_NAVIGATION_VIEW, themed);
        return this;
    }

    @Override
    public Config navigationViewSelectedIcon(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_SELECTED_ICON, color);
        return this;
    }

    @Override
    public Config navigationViewSelectedIconRes(@ColorRes int colorRes) {
        return navigationViewSelectedIcon(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewSelectedIconAttr(@AttrRes int colorAttr) {
        return navigationViewSelectedIcon(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationViewSelectedText(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_SELECTED_TEXT, color);
        return this;
    }

    @Override
    public Config navigationViewSelectedTextRes(@ColorRes int colorRes) {
        return navigationViewSelectedText(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewSelectedTextAttr(@AttrRes int colorAttr) {
        return navigationViewSelectedText(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationViewNormalIcon(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_NORMAL_ICON, color);
        return this;
    }

    @Override
    public Config navigationViewNormalIconRes(@ColorRes int colorRes) {
        return navigationViewNormalIcon(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewNormalIconAttr(@AttrRes int colorAttr) {
        return navigationViewNormalIcon(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationViewNormalText(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_NORMAL_TEXT, color);
        return this;
    }

    @Override
    public Config navigationViewNormalTextRes(@ColorRes int colorRes) {
        return navigationViewNormalText(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewNormalTextAttr(@AttrRes int colorAttr) {
        return navigationViewNormalText(ATHUtil.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationViewSelectedBg(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_SELECTED_BG, color);
        return this;
    }

    @Override
    public Config navigationViewSelectedBgRes(@ColorRes int colorRes) {
        return navigationViewSelectedBg(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewSelectedBgAttr(@AttrRes int colorAttr) {
        return navigationViewSelectedBg(ATHUtil.resolveColor(mContext, colorAttr));
    }

    // Text size

    @Override
    public Config textSizePxForMode(@IntRange(from = 1, to = Integer.MAX_VALUE) int pxValue, @TextSizeMode String mode) {
        mEditor.putInt(mode, pxValue);
        return this;
    }

    @Override
    public Config textSizeSpForMode(@IntRange(from = 1, to = Integer.MAX_VALUE) int dpValue, @TextSizeMode String mode) {
        final int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dpValue, mContext.getResources().getDisplayMetrics());
        return textSizePxForMode(px, mode);
    }

    @Override
    public Config textSizeResForMode(@DimenRes int resId, @TextSizeMode String mode) {
        return textSizePxForMode(mContext.getResources().getDimensionPixelSize(resId), mode);
    }

    // Apply and commit methods

    @SuppressWarnings("unchecked")
    @Override
    public void commit() {
        mEditor.putLong(VALUES_CHANGED, System.currentTimeMillis())
                .putBoolean(IS_CONFIGURED_KEY, true)
                .commit();
    }

    // Static getters

    @CheckResult
    @NonNull
    protected static SharedPreferences prefs(@NonNull Context context) {
        return context.getSharedPreferences(CONFIG_PREFS_KEY_DEFAULT, Context.MODE_PRIVATE);
    }

    public static void markChanged(@NonNull Context context) {
        new Config(context).commit();
    }

    @Nullable
    protected static String getKey(@NonNull Context context) {
        if (context instanceof ATHBaseActivity)
            return ((ATHBaseActivity) context).getATEKey();
        return null;
    }

    @CheckResult
    @StyleRes
    public static int activityTheme(@NonNull Context context) {
        return prefs(context).getInt(KEY_ACTIVITY_THEME, 0);
    }

    @CheckResult
    @ColorInt
    public static int primaryColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_PRIMARY_COLOR, ATHUtil.resolveColor(context, R.attr.colorPrimary, Color.parseColor("#455A64")));
    }

    @CheckResult
    @ColorInt
    public static int primaryColorDark(@NonNull Context context) {
        return prefs(context).getInt(KEY_PRIMARY_COLOR_DARK, ATHUtil.resolveColor(context, R.attr.colorPrimaryDark, Color.parseColor("#37474F")));
    }

    @CheckResult
    @ColorInt
    public static int accentColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_ACCENT_COLOR, ATHUtil.resolveColor(context, R.attr.colorAccent, Color.parseColor("#263238")));
    }

    @CheckResult
    @ColorInt
    public static int statusBarColor(@NonNull Context context) {
        if (!coloredStatusBar(context)) {
            return Color.BLACK;
        }
        return prefs(context).getInt(KEY_STATUS_BAR_COLOR, primaryColorDark(context));
    }

    @CheckResult
    @ColorInt
    public static int toolbarColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_TOOLBAR_COLOR, primaryColor(context));
    }

    @CheckResult
    @ColorInt
    public static int navigationBarColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_NAVIGATION_BAR_COLOR, primaryColor(context));
    }

    @CheckResult
    @ColorInt
    public static int textColorPrimary(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_PRIMARY, ATHUtil.resolveColor(context, android.R.attr.textColorPrimary));
    }

    @CheckResult
    @ColorInt
    public static int textColorPrimaryInverse(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_PRIMARY_INVERSE, ATHUtil.resolveColor(context, android.R.attr.textColorPrimaryInverse));
    }

    @CheckResult
    @ColorInt
    public static int textColorSecondary(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_SECONDARY, ATHUtil.resolveColor(context, android.R.attr.textColorSecondary));
    }

    @CheckResult
    @ColorInt
    public static int textColorSecondaryInverse(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_SECONDARY_INVERSE, ATHUtil.resolveColor(context, android.R.attr.textColorSecondaryInverse));
    }

    @CheckResult
    public static boolean coloredStatusBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, true);
    }

    @CheckResult
    public static boolean coloredActionBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, true);
    }

    @CheckResult
    public static boolean coloredNavigationBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARY_NAVBAR, false);
    }

    @SuppressWarnings("ResourceType")
    @CheckResult
    @LightStatusBarMode
    public static int lightStatusBarMode(@NonNull Context context) {
        int value = prefs(context).getInt(KEY_LIGHT_STATUS_BAR_MODE, Config.LIGHT_STATUS_BAR_AUTO);
        if (value < 1) value = Config.LIGHT_STATUS_BAR_AUTO;
        return value;
    }

    @SuppressWarnings("ResourceType")
    @CheckResult
    @LightToolbarMode
    public static int lightToolbarMode(@NonNull Context context) {
        int value = prefs(context).getInt(KEY_LIGHT_TOOLBAR_MODE, Config.LIGHT_TOOLBAR_AUTO);
        if (value < 1) value = Config.LIGHT_TOOLBAR_AUTO;
        return value;
    }

    @CheckResult
    public static boolean lightToolbar(@NonNull Context context, int toolbarColor) {
        boolean isLightMode;
        @Config.LightToolbarMode
        final int lightToolbarMode = Config.lightToolbarMode(context);
        switch (lightToolbarMode) {
            case Config.LIGHT_TOOLBAR_ON:
                isLightMode = true;
                break;
            case Config.LIGHT_TOOLBAR_OFF:
                isLightMode = false;
                break;
            default:
            case Config.LIGHT_TOOLBAR_AUTO:
                isLightMode = ColorUtil.isColorLight(toolbarColor);
                break;
        }
        return isLightMode;
    }

    @CheckResult
    @ColorInt
    public static int toolbarContentColor(@NonNull Context context) {
        final int toolbarColor = Config.toolbarColor(context);
        return toolbarContentColor(context, toolbarColor);
    }

    @CheckResult
    @ColorInt
    public static int toolbarContentColor(@NonNull Context context, @ColorInt int toolbarColor) {
        if (lightToolbar(context, toolbarColor)) {
            return toolbarSubtitleColor(context, toolbarColor);
        }
        return toolbarTitleColor(context, toolbarColor);
    }

    @CheckResult
    @ColorInt
    public static int toolbarSubtitleColor(@NonNull Context context) {
        final int toolbarColor = Config.toolbarColor(context);
        return toolbarSubtitleColor(context, toolbarColor);
    }

    @CheckResult
    @ColorInt
    public static int toolbarSubtitleColor(@NonNull Context context, @ColorInt int toolbarColor) {
        return MaterialValueHelper.getSecondaryTextColor(context, lightToolbar(context, toolbarColor));
    }

    @CheckResult
    @ColorInt
    public static int toolbarTitleColor(@NonNull Context context) {
        final int toolbarColor = Config.toolbarColor(context);
        return toolbarTitleColor(context, toolbarColor);
    }

    @CheckResult
    @ColorInt
    public static int toolbarTitleColor(@NonNull Context context, @ColorInt int toolbarColor) {
        return MaterialValueHelper.getPrimaryTextColor(context, lightToolbar(context, toolbarColor));
    }

    @CheckResult
    public static boolean autoGeneratePrimaryDark(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, true);
    }

    @CheckResult
    @IntRange(from = 1, to = Integer.MAX_VALUE)
    public static int textSizeForMode(@NonNull Context context, @TextSizeMode String mode) {
        int size = prefs(context).getInt(mode, 0);
        if (size == 0) {
            switch (mode) {
                default:
                case TEXTSIZE_CAPTION:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_caption);
                    break;
                case TEXTSIZE_BODY:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_body);
                    break;
                case TEXTSIZE_SUBHEADING:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_subheading);
                    break;
                case TEXTSIZE_TITLE:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_title);
                    break;
                case TEXTSIZE_HEADLINE:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_headline);
                    break;
                case TEXTSIZE_DISPLAY1:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_display1);
                    break;
                case TEXTSIZE_DISPLAY2:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_display2);
                    break;
                case TEXTSIZE_DISPLAY3:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_display3);
                    break;
                case TEXTSIZE_DISPLAY4:
                    size = context.getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_display4);
                    break;
            }
        }
        return size;
    }


    @IntDef({LIGHT_STATUS_BAR_OFF, LIGHT_STATUS_BAR_ON, LIGHT_STATUS_BAR_AUTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LightStatusBarMode {
    }

    @IntDef({LIGHT_TOOLBAR_OFF, LIGHT_TOOLBAR_ON, LIGHT_TOOLBAR_AUTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LightToolbarMode {
    }

    @StringDef({TEXTSIZE_DISPLAY4, TEXTSIZE_DISPLAY3, TEXTSIZE_DISPLAY2, TEXTSIZE_DISPLAY1,
            TEXTSIZE_HEADLINE, TEXTSIZE_TITLE, TEXTSIZE_SUBHEADING, TEXTSIZE_BODY, TEXTSIZE_CAPTION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextSizeMode {
    }

    public static final int LIGHT_STATUS_BAR_AUTO = 1;
    public static final int LIGHT_STATUS_BAR_ON = 2;
    public static final int LIGHT_STATUS_BAR_OFF = 3;

    public static final int LIGHT_TOOLBAR_AUTO = 1;
    public static final int LIGHT_TOOLBAR_ON = 2;
    public static final int LIGHT_TOOLBAR_OFF = 3;

    public final static String TEXTSIZE_DISPLAY4 = "textsize_display4";
    public final static String TEXTSIZE_DISPLAY3 = "textsize_display3";
    public final static String TEXTSIZE_DISPLAY2 = "textsize_display2";
    public final static String TEXTSIZE_DISPLAY1 = "textsize_display1";
    public final static String TEXTSIZE_HEADLINE = "textsize_headline";
    public final static String TEXTSIZE_TITLE = "textsize_title";
    public final static String TEXTSIZE_SUBHEADING = "textsize_subheading";
    public final static String TEXTSIZE_BODY = "textsize_body";
    public final static String TEXTSIZE_CAPTION = "textsize_caption";
}