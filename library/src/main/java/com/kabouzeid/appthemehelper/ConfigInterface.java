package com.kabouzeid.appthemehelper;

import android.support.annotation.AttrRes;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.StyleRes;

/**
 * @author Aidan Follestad (afollestad), Karim Abou Zeid (kabouzeid)
 */
interface ConfigInterface {

    @CheckResult
    boolean isConfigured();

    @CheckResult
    boolean isConfigured(@IntRange(from = 0, to = Integer.MAX_VALUE) int version);

    // Activity theme

    Config activityTheme(@StyleRes int theme);

    // Primary colors

    Config primaryColor(@ColorInt int color);

    Config primaryColorRes(@ColorRes int colorRes);

    Config primaryColorAttr(@AttrRes int colorAttr);

    Config autoGeneratePrimaryDark(boolean autoGenerate);

    Config primaryColorDark(@ColorInt int color);

    Config primaryColorDarkRes(@ColorRes int colorRes);

    Config primaryColorDarkAttr(@AttrRes int colorAttr);

    // Accent colors

    Config accentColor(@ColorInt int color);

    Config accentColorRes(@ColorRes int colorRes);

    Config accentColorAttr(@AttrRes int colorAttr);

    // Status bar color

    Config statusBarColor(@ColorInt int color);

    Config statusBarColorRes(@ColorRes int colorRes);

    Config statusBarColorAttr(@AttrRes int colorAttr);

    // Navigation bar color

    Config navigationBarColor(@ColorInt int color);

    Config navigationBarColorRes(@ColorRes int colorRes);

    Config navigationBarColorAttr(@AttrRes int colorAttr);

    // Primary text color

    Config textColorPrimary(@ColorInt int color);

    Config textColorPrimaryRes(@ColorRes int colorRes);

    Config textColorPrimaryAttr(@AttrRes int colorAttr);

    Config textColorPrimaryInverse(@ColorInt int color);

    Config textColorPrimaryInverseRes(@ColorRes int colorRes);

    Config textColorPrimaryInverseAttr(@AttrRes int colorAttr);

    // Secondary text color

    Config textColorSecondary(@ColorInt int color);

    Config textColorSecondaryRes(@ColorRes int colorRes);

    Config textColorSecondaryAttr(@AttrRes int colorAttr);

    Config textColorSecondaryInverse(@ColorInt int color);

    Config textColorSecondaryInverseRes(@ColorRes int colorRes);

    Config textColorSecondaryInverseAttr(@AttrRes int colorAttr);

    // Toggle configurations

    Config coloredStatusBar(boolean colored);

    Config coloredNavigationBar(boolean applyToNavBar);

    // Commit/apply

    void commit();
}
