package com.kabouzeid.appthemehelper.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.kabouzeid.appthemehelper.ATH;
import com.kabouzeid.appthemehelper.Config;
import com.kabouzeid.appthemehelper.R;
import com.afollestad.materialdialogs.prefs.MaterialDialogPreference;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEDialogPreference extends MaterialDialogPreference {

    public ATEDialogPreference(Context context) {
        super(context);
        init(context, null);
    }

    public ATEDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATEDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ATEDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private String mKey;

    private void init(Context context, AttributeSet attrs) {
        setLayoutResource(R.layout.ate_preference_custom);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEDialogPreference, 0, 0);
            try {
                mKey = a.getString(R.styleable.ATEDialogPreference_ateKey_pref_dialog);
            } finally {
                a.recycle();
            }
        }

        if (!Config.usingMaterialDialogs(context, mKey)) {
            ATH.config(context, mKey)
                    .usingMaterialDialogs(true)
                    .commit();
        }
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        ATH.apply(view, mKey);
    }
}