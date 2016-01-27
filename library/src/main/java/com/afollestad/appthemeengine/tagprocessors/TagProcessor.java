package com.afollestad.appthemeengine.tagprocessors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author Aidan Follestad (afollestad)
 */
public interface TagProcessor {

    boolean isTypeSupported(@NonNull View view);

    void process(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String suffix);
}