package com.afollestad.appthemeengine.inflation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.tagprocessors.TextColorTagProcessor;
import com.afollestad.appthemeengine.tagprocessors.TintTagProcessor;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATEEditText extends EditText implements ViewInterface, PostInflationApplier {

    public ATEEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ATEEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ATEEditText(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext, boolean waitForInflate) {
        super(context, attrs);
        mWaitForInflate = waitForInflate;
        init(context, keyContext);
    }

    private boolean mWaitForInflate;
    private ATEActivity mKeyContext;

    private void init(Context context, @Nullable ATEActivity keyContext) {
        if (getTag() == null)
            setTag(String.format("%s|accent_color,%s|primary_text,%s|primary_text",
                    TintTagProcessor.PREFIX, TextColorTagProcessor.PREFIX, TextColorTagProcessor.HINT_PREFIX));
        if (mWaitForInflate) {
            mKeyContext = keyContext;
            ATE.addPostInflationView(this);
            return;
        }
        ATEViewUtil.init(keyContext, this, context);
    }

    @Override
    public boolean setsStatusBarColor() {
        return false;
    }

    @Override
    public boolean setsToolbarColor() {
        return false;
    }

    @Override
    public void postApply() {
        if (mKeyContext != null)
            ATEViewUtil.init(mKeyContext, this, getContext());
        mKeyContext = null;
    }
}
