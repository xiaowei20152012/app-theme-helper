package com.afollestad.appthemeengine.viewprocessors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.util.TintHelper;

import java.lang.reflect.Field;

/**
 * @author Aidan Follestad (afollestad)
 */
public class SearchViewProcessor implements ViewProcessor<View, Integer> {

    public static final String MAIN_CLASS = "android.support.v7.widget.SearchView";

    private void tintImageView(Object target, Field field, int tintColor) throws Exception {
        field.setAccessible(true);
        final ImageView imageView = (ImageView) field.get(target);
        if (imageView.getDrawable() != null)
            imageView.setImageDrawable(TintHelper.tintDrawable(imageView.getDrawable(), tintColor));
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable View target, @Nullable Integer tintColor) {
        if (target == null || tintColor == null) return;
        final Class<?> cls = target.getClass();
        try {
            final Field mSearchSrcTextViewField = cls.getDeclaredField("mSearchSrcTextView");
            mSearchSrcTextViewField.setAccessible(true);
            final EditText mSearchSrcTextView = (EditText) mSearchSrcTextViewField.get(target);
            mSearchSrcTextView.setTextColor(tintColor);
            mSearchSrcTextView.setHintTextColor(ATEUtil.adjustAlpha(tintColor, 0.5f));
            TintHelper.setCursorTint(mSearchSrcTextView, tintColor);

            Field field = cls.getDeclaredField("mSearchButton");
            tintImageView(target, field, tintColor);
            field = cls.getDeclaredField("mGoButton");
            tintImageView(target, field, tintColor);
            field = cls.getDeclaredField("mCloseButton");
            tintImageView(target, field, tintColor);
            field = cls.getDeclaredField("mVoiceButton");
            tintImageView(target, field, tintColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
