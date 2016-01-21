package com.kabouzeid.appthemehelper.util;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public final class TabLayoutUtil {

    public static void setTabLayoutColors(@Nullable TabLayout tabLayout, @ColorInt int tabTextColorSelected, @ColorInt int tabIndicatorColorSelected) {
        if (tabLayout == null)
            return;

        Drawable bg = tabLayout.getBackground();
        if (bg == null)
            bg = ((View) tabLayout.getParent()).getBackground();
        if (bg != null && bg instanceof ColorDrawable) {
            final ColorDrawable cd = (ColorDrawable) bg;
            if (ColorUtil.isColorLight(cd.getColor()))
                tabTextColorSelected = tabIndicatorColorSelected = Color.BLACK;
        }

        tabLayout.setTabTextColors(ColorUtil.adjustAlpha(tabTextColorSelected, 0.5f), tabTextColorSelected);
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
                TintHelper.tintDrawable(tab.getIcon(), sl);
            }
        }
    }

    private TabLayoutUtil() {
    }
}
