package com.afollestad.appthemeengine.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.afollestad.appthemeengine.R;
import com.afollestad.appthemeengine.util.TypefaceHelper;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATETextView extends TextView {

    public ATETextView(Context context) {
        super(context);
    }

    public ATETextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ATETextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ATETextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs) {
        String font = null;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATETextView, 0, 0);
            try {
                font = a.getString(R.styleable.ATETextView_ateFont);
            } finally {
                a.recycle();
            }
        }
        if (font != null)
            setTypeface(TypefaceHelper.get(context, font));
    }
}