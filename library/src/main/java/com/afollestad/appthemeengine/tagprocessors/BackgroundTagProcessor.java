package com.afollestad.appthemeengine.tagprocessors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public class BackgroundTagProcessor implements TagProcessor {

    public static final String PREFIX = "background";

    @Override
    public boolean isTypeSupported(@NonNull View view) {
        return true;
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String suffix) {
        final int newBgColor;

        switch (suffix) {
            case PRIMARY_COLOR:
                newBgColor = Config.primaryColor(context, key);
                break;
            case PRIMARY_COLOR_DARK:
                newBgColor = Config.primaryColorDark(context, key);
                break;
            case ACCENT_COLOR:
                newBgColor = Config.accentColor(context, key);
                break;
            case PRIMARY_TEXT_COLOR:
                newBgColor = Config.textColorPrimary(context, key);
                break;
            case PRIMARY_TEXT_COLOR_INVERSE:
                newBgColor = Config.textColorPrimaryInverse(context, key);
                break;
            case SECONDARY_TEXT_COLOR:
                newBgColor = Config.textColorSecondary(context, key);
                break;
            case SECONDARY_TEXT_COLOR_INVERSE:
                newBgColor = Config.textColorSecondaryInverse(context, key);
                break;

            case PARENT_DEPENDENT: {
                final String viewName = ATEUtil.getIdName(context, view.getId());
                if (view.getParent() == null)
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses background|parent_dependent tag but has no parent.", viewName));
                final View parent = (View) view.getParent();
                if (parent.getBackground() == null || !(parent.getBackground() instanceof ColorDrawable))
                    throw new IllegalStateException(String.format(Locale.getDefault(),
                            "View %s uses background|parent_dependent tag but parent doesn't have a ColorDrawable as its background.", viewName));
                final ColorDrawable bg = (ColorDrawable) parent.getBackground();
                newBgColor = ATEUtil.isColorLight(bg.getColor()) ? Color.BLACK : Color.WHITE;
                break;
            }
            case PRIMARY_COLOR_DEPENDENT:
                newBgColor = ATEUtil.isColorLight(Config.primaryColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case ACCENT_COLOR_DEPENDENT:
                newBgColor = ATEUtil.isColorLight(Config.accentColor(context, key)) ?
                        Color.BLACK : Color.WHITE;
                break;
            case WINDOW_BG_DEPENDENT:
                newBgColor = ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground)) ?
                        Color.BLACK : Color.WHITE;
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown suffix: %s", suffix));
        }

        if (ATEUtil.isInClassPath("android.support.v7.widget.CardView") &&
                (view.getClass().getName().equalsIgnoreCase("android.support.v7.widget.CardView") ||
                        view.getClass().getSuperclass().getName().equals("android.support.v7.widget.CardView"))) {
            try {
                final Class<?> cardViewCls = Class.forName("android.support.v7.widget.CardView");
                final Method setCardBg = cardViewCls.getMethod("setCardBackgroundColor", Integer.class);
                setCardBg.invoke(view, newBgColor);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else {
            view.setBackgroundColor(newBgColor);
        }
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