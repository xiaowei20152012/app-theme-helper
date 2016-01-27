package com.afollestad.appthemeengine.viewprocessors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.tagprocessors.TagProcessor;

/**
 * @author Aidan Follestad (afollestad)
 */
public class DefaultProcessor implements ViewProcessor<View, Void> {

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable View view, @Nullable Void extra) {
        if (view == null || view.getTag() == null || !(view.getTag() instanceof String))
            return;
        final String tag = (String) view.getTag();
        if (tag.contains(",")) {
            final String[] splitTag = tag.split(",");
            for (String part : splitTag)
                processTagPart(context, key, view, part);
        } else {
            processTagPart(context, key, view, tag);
        }
    }

    private void processTagPart(@NonNull Context context, @Nullable String key, @NonNull View view, @NonNull String tag) {
        if (!tag.contains("|")) return;
        final String prefix = tag.substring(0, tag.indexOf('|'));
        final String suffix = tag.substring(tag.indexOf('|') + 1);
        final TagProcessor processor = ATE.getTagProcessor(prefix);
        if (processor != null) {
            if (!processor.isTypeSupported(view)) {
                throw new IllegalStateException(String.format("A view of type %s cannot use %s tags.",
                        view.getClass().getName(), prefix));
            }
            processor.process(context, key, view, suffix);
        }
    }
}