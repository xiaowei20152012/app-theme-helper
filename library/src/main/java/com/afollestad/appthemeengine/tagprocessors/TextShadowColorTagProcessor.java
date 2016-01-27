package com.afollestad.appthemeengine.tagprocessors;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;

import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TextShadowColorTagProcessor implements TagProcessor {

    public static final String PREFIX = "text_color_shadow";

    @Override
    public boolean isTypeSupported(@NonNull View view) {
        return view instanceof TextView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void process(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String suffix) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            return;
        final TextView tv = (TextView) view;
        int newShadowColor = Color.TRANSPARENT;

        switch (suffix) {
            case PRIMARY_COLOR:
                newShadowColor = Config.primaryColor(context, key);
                break;
            case PRIMARY_COLOR_DARK:
                newShadowColor = Config.primaryColorDark(context, key);
                break;
            case ACCENT_COLOR:
                newShadowColor = Config.accentColor(context, key);
                break;
            case PRIMARY_TEXT_COLOR:
                newShadowColor = Config.textColorPrimary(context, key);
                break;
            case PRIMARY_TEXT_COLOR_INVERSE:
                newShadowColor = Config.textColorPrimaryInverse(context, key);
                break;
            case SECONDARY_TEXT_COLOR:
                newShadowColor = Config.textColorSecondary(context, key);
                break;
            case SECONDARY_TEXT_COLOR_INVERSE:
                newShadowColor = Config.textColorSecondaryInverse(context, key);
                break;

            case PARENT_DEPENDENT: {
                final String viewName = view.getId() != 0 ? context.getResources().getResourceName(view.getId()) : "(no id)";
                if (view.getParent() == null)
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses text_color|parent_dependent tag but has no parent.", viewName));
                final View parent = (View) view.getParent();
                if (parent.getBackground() == null || !(parent.getBackground() instanceof ColorDrawable))
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses text_color|parent_dependent tag but parent doesn't have a ColorDrawable as its background.", viewName));
                final ColorDrawable bg = (ColorDrawable) parent.getBackground();
                newShadowColor = ATEUtil.isColorLight(bg.getColor()) ? Color.BLACK : Color.WHITE;
                break;
            }
            case PRIMARY_COLOR_DEPENDENT:
                newShadowColor = ATEUtil.isColorLight(Config.primaryColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case ACCENT_COLOR_DEPENDENT:
                newShadowColor = ATEUtil.isColorLight(Config.accentColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case WINDOW_BG_DEPENDENT:
                newShadowColor = ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground)) ?
                        Color.BLACK : Color.WHITE;
                break;
        }

        if (newShadowColor != Color.TRANSPARENT)
            tv.setShadowLayer(tv.getShadowRadius(), tv.getShadowDx(), tv.getShadowDy(), newShadowColor);
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