package com.afollestad.appthemeengine.viewprocessors;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.util.TintHelper;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TabLayoutProcessor implements ViewProcessor<TabLayout, Void> {

    public static final String MAIN_CLASS = "android.support.design.widget.TabLayout";

    private int mTabTextColorSelected;
    private int mTabIndicatorColorSelected;

    public TabLayoutProcessor() {
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable TabLayout view, @Nullable Void extra) {
        if (view == null)
            return;

        mTabTextColorSelected = Color.WHITE;
        mTabIndicatorColorSelected = Color.WHITE;

        Drawable bg = view.getBackground();
        if (bg == null)
            bg = ((View) view.getParent()).getBackground();
        if (bg != null && bg instanceof ColorDrawable) {
            final ColorDrawable cd = (ColorDrawable) bg;
            if (ATEUtil.isColorLight(cd.getColor()))
                mTabTextColorSelected = mTabIndicatorColorSelected = Color.BLACK;
        }

        if (view.getTag() != null && view.getTag() instanceof String) {
            final String tag = (String) view.getTag();
            if (tag.contains(",")) {
                final String[] splitTag = tag.split(",");
                for (String part : splitTag)
                    processTagPart(context, part, key);
            } else {
                processTagPart(context, tag, key);
            }
        }

        view.setTabTextColors(ATEUtil.adjustAlpha(mTabTextColorSelected, 0.5f), mTabTextColorSelected);
        view.setSelectedTabIndicatorColor(mTabIndicatorColorSelected);

        final ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_selected},
                new int[]{android.R.attr.state_selected}
        },
                new int[]{
                        ATEUtil.adjustAlpha(mTabIndicatorColorSelected, 0.5f),
                        mTabIndicatorColorSelected
                });
        for (int i = 0; i < view.getTabCount(); i++) {
            final TabLayout.Tab tab = view.getTabAt(i);
            if (tab != null && tab.getIcon() != null) {
                TintHelper.tintDrawable(tab.getIcon(), sl);
            }
        }
    }

    private void processTagPart(@NonNull Context context, @Nullable String tag, @Nullable String key) {
        if (tag != null) {
            switch (tag) {
                case KEY_TAB_TEXT_PRIMARY_COLOR:
                    mTabTextColorSelected = Config.primaryColor(context, key);
                    break;
                case KEY_TAB_TEXT_PRIMARY_COLOR_DARK:
                    mTabTextColorSelected = Config.primaryColorDark(context, key);
                    break;
                case KEY_TAB_TEXT_ACCENT_COLOR:
                    mTabTextColorSelected = Config.accentColor(context, key);
                    break;
                case KEY_TAB_TEXT_PRIMARY:
                    mTabTextColorSelected = Config.textColorPrimary(context, key);
                    break;
                case KEY_TAB_TEXT_PRIMARY_INVERSE:
                    mTabTextColorSelected = Config.textColorPrimaryInverse(context, key);
                    break;
                case KEY_TAB_TEXT_SECONDARY:
                    mTabTextColorSelected = Config.textColorSecondary(context, key);
                    break;
                case KEY_TAB_TEXT_SECONDARY_INVERSE:
                    mTabTextColorSelected = Config.textColorSecondaryInverse(context, key);
                    break;

                case KEY_TAB_INDICATOR_PRIMARY_COLOR:
                    mTabIndicatorColorSelected = Config.primaryColor(context, key);
                    break;
                case KEY_TAB_INDICATOR_PRIMARY_COLOR_DARK:
                    mTabIndicatorColorSelected = Config.primaryColorDark(context, key);
                    break;
                case KEY_TAB_INDICATOR_ACCENT_COLOR:
                    mTabIndicatorColorSelected = Config.accentColor(context, key);
                    break;
                case KEY_TAB_INDICATOR_TEXT_PRIMARY:
                    mTabIndicatorColorSelected = Config.textColorPrimary(context, key);
                    break;
                case KEY_TAB_INDICATOR_TEXT_PRIMARY_INVERSE:
                    mTabIndicatorColorSelected = Config.textColorPrimaryInverse(context, key);
                    break;
                case KEY_TAB_INDICATOR_TEXT_SECONDARY:
                    mTabIndicatorColorSelected = Config.textColorSecondary(context, key);
                    break;
                case KEY_TAB_INDICATOR_TEXT_SECONDARY_INVERSE:
                    mTabIndicatorColorSelected = Config.textColorSecondaryInverse(context, key);
                    break;
            }
        }
    }

    private final static String KEY_TAB_TEXT_PRIMARY_COLOR = "tab_text_primary_color";
    private final static String KEY_TAB_TEXT_PRIMARY_COLOR_DARK = "tab_text_primary_color_dark";
    private final static String KEY_TAB_TEXT_ACCENT_COLOR = "tab_text_accent_color";
    private final static String KEY_TAB_TEXT_PRIMARY = "tab_text_primary";
    private final static String KEY_TAB_TEXT_PRIMARY_INVERSE = "tab_text_primary_inverse";
    private final static String KEY_TAB_TEXT_SECONDARY = "tab_text_secondary";
    private final static String KEY_TAB_TEXT_SECONDARY_INVERSE = "tab_text_secondary_inverse";

    private final static String KEY_TAB_INDICATOR_PRIMARY_COLOR = "tab_indicator_primary_color";
    private final static String KEY_TAB_INDICATOR_PRIMARY_COLOR_DARK = "tab_indicator_primary_color_dark";
    private final static String KEY_TAB_INDICATOR_ACCENT_COLOR = "tab_indicator_accent_color";
    private final static String KEY_TAB_INDICATOR_TEXT_PRIMARY = "tab_indicator_text_primary";
    private final static String KEY_TAB_INDICATOR_TEXT_PRIMARY_INVERSE = "tab_indicator_text_primary_inverse";
    private final static String KEY_TAB_INDICATOR_TEXT_SECONDARY = "tab_indicator_text_secondary";
    private final static String KEY_TAB_INDICATOR_TEXT_SECONDARY_INVERSE = "tab_indicator_text_secondary_inverse";
}