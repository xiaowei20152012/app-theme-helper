package com.afollestad.appthemeengine.processors;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.appthemeengine.Config;
import com.afollestad.materialdialogs.internal.ThemeSingleton;

/**
 * @author Aidan Follestad (afollestad)
 */
public class MaterialDialogsProcessor implements Processor<View, Void> {

    public static final String MAIN_CLASS = "com.afollestad.materialdialogs.MaterialDialog";

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable View target, @Nullable Void extra) {
        final ThemeSingleton md = ThemeSingleton.get();
        md.titleColor = Config.textColorPrimary(context, key);
        md.contentColor = Config.textColorSecondary(context, key);
        md.itemColor = md.titleColor;
        md.widgetColor = Config.accentColor(context, key);
        md.linkColor = ColorStateList.valueOf(md.widgetColor);
        md.positiveColor = ColorStateList.valueOf(md.widgetColor);
        md.neutralColor = ColorStateList.valueOf(md.widgetColor);
        md.negativeColor = ColorStateList.valueOf(md.widgetColor);
    }
}
