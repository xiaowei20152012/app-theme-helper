package com.afollestad.appthemeengine.customizers;

import android.support.annotation.ColorInt;
import android.support.v7.widget.Toolbar;

import com.afollestad.appthemeengine.Config;

/**
 * @author Aidan Follestad (afollestad)
 */
public interface ATEToolbarCustomizer {

    @Config.LightToolbarMode
    int getLightToolbarMode(Toolbar toolbar);

    @ColorInt
    int getToolbarColor(Toolbar toolbar);
}
