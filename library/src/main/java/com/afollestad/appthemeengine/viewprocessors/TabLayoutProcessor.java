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
import com.afollestad.appthemeengine.tagprocessors.TagProcessor;
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
        if (!tag.contains("|")) return;
        final String prefix = tag.substring(0, tag.indexOf('|'));
        final String suffix = tag.substring(tag.indexOf('|') + 1);
        final TagProcessor.ColorResult result = TagProcessor.getColorFromSuffix(context, key, view, suffix);
        if (result == null) return;

        if (prefix.equals("tab_text"))
            mTabTextColorSelected = result.getColor();
        else if (prefix.equals("tab_indicator"))
            mTabIndicatorColorSelected = result.getColor();
    }
}