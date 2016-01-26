package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEListView extends ListView {

    public ATEListView(Context context) {
        super(context);
        init(context, null);
    }

    public ATEListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATEListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ATEViewUtil.init(this, context, attrs, R.styleable.ATEListView, R.styleable.ATEListView_ateKey_listView);
    }
}
