package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ListView;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEListView extends ListView implements ViewInterface {

    public ATEListView(Context context) {
        super(context);
        init(context, null, null);
    }

    public ATEListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public ATEListView(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, attrs, keyContext);
    }

    private void init(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        ATEViewUtil.init(keyContext, this, context, attrs, R.styleable.ATEListView, R.styleable.ATEListView_ateKey_listView);
    }

    @Override
    public boolean setsStatusBarColor() {
        return false;
    }

    @Override
    public boolean setsToolbarColor() {
        return false;
    }
}
