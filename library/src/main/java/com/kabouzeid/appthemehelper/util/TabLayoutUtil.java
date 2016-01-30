package com.kabouzeid.appthemehelper.util;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public final class TabLayoutUtil {

    public static void setTabLayoutColors(@Nullable TabLayout tabLayout, @ColorInt int tabIndicatorColorSelected) {
        if (tabLayout == null)
            return;

        tabLayout.setSelectedTabIndicatorColor(tabIndicatorColorSelected);

        final ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_selected},
                new int[]{android.R.attr.state_selected}
        },
                new int[]{
                        ColorUtil.adjustAlpha(tabIndicatorColorSelected, 0.5f),
                        tabIndicatorColorSelected
                });
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            final TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null && tab.getIcon() != null) {
                tab.setIcon(TintHelper.createTintedDrawable(tab.getIcon(), sl));
            }
        }
    }

    private TabLayoutUtil() {
    }
}
