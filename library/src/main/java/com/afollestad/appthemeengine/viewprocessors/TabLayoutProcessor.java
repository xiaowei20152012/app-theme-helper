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

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.util.TintHelper;

import java.util.Locale;

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
        else if (view.getParent() == null) {
            ATE.addPostInflationView(view);
            return;
        }

        mTabTextColorSelected = Color.WHITE;
        mTabIndicatorColorSelected = Color.WHITE;

        Drawable bg = view.getBackground();
        if (bg == null && view.getParent() != null)
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
                    processTagPart(context, view, part, key);
            } else processTagPart(context, view, tag, key);
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
            if (tab != null && tab.getIcon() != null)
                TintHelper.tintDrawable(tab.getIcon(), sl);
        }
    }

    private void processTagPart(@NonNull Context context, @NonNull View view, @NonNull String tag, @Nullable String key) {
        final int newColor;
        if (!tag.contains("|")) return;
        final String prefix = tag.substring(0, tag.indexOf('|'));
        final String suffix = tag.substring(tag.indexOf('|') + 1);

        switch (suffix) {
            case PRIMARY_COLOR:
                newColor = Config.primaryColor(context, key);
                break;
            case PRIMARY_COLOR_DARK:
                newColor = Config.primaryColorDark(context, key);
                break;
            case ACCENT_COLOR:
                newColor = Config.accentColor(context, key);
                break;
            case PRIMARY_TEXT_COLOR:
                newColor = Config.textColorPrimary(context, key);
                break;
            case PRIMARY_TEXT_COLOR_INVERSE:
                newColor = Config.textColorPrimaryInverse(context, key);
                break;
            case SECONDARY_TEXT_COLOR:
                newColor = Config.textColorSecondary(context, key);
                break;
            case SECONDARY_TEXT_COLOR_INVERSE:
                newColor = Config.textColorSecondaryInverse(context, key);
                break;

            case PARENT_DEPENDENT: {
                final String viewName = ATEUtil.getIdName(context, view.getId());
                if (view.getParent() == null) return;
                final View parent = (View) view.getParent();
                if (parent.getBackground() == null || !(parent.getBackground() instanceof ColorDrawable))
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses background|parent_dependent tag but parent doesn't have a ColorDrawable as its background.", viewName));
                final ColorDrawable bg = (ColorDrawable) parent.getBackground();
                newColor = ATEUtil.isColorLight(bg.getColor()) ? Color.BLACK : Color.WHITE;
                break;
            }
            case PRIMARY_COLOR_DEPENDENT:
                newColor = ATEUtil.isColorLight(Config.primaryColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case ACCENT_COLOR_DEPENDENT:
                newColor = ATEUtil.isColorLight(Config.accentColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case WINDOW_BG_DEPENDENT:
                newColor = ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground)) ?
                        Color.BLACK : Color.WHITE;
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown suffix: %s", suffix));
        }

        if (prefix.equals("tab_text"))
            mTabTextColorSelected = newColor;
        else if (prefix.equals("tab_indicator"))
            mTabIndicatorColorSelected = newColor;
    }

    private static final String PRIMARY_COLOR = "primary_color";
    private static final String PRIMARY_COLOR_DARK = "primary_color_dark";
    private static final String ACCENT_COLOR = "accent_color";
    private static final String PRIMARY_TEXT_COLOR = "primary_text";
    private static final String PRIMARY_TEXT_COLOR_INVERSE = "primary_text_inverse";
    private static final String SECONDARY_TEXT_COLOR = "secondary_text";
    private static final String SECONDARY_TEXT_COLOR_INVERSE = "secondary_text_inverse";

    private static final String PARENT_DEPENDENT = "parent_dependent";
    private static final String PRIMARY_COLOR_DEPENDENT = "primary_color_dependent";
    private static final String ACCENT_COLOR_DEPENDENT = "accent_color_dependent";
    private static final String WINDOW_BG_DEPENDENT = "window_bg_dependent";
}