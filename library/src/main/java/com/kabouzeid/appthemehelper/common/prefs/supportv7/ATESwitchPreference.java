package com.kabouzeid.appthemehelper.common.prefs.supportv7;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;

import com.kabouzeid.appthemehelper.ATH;
import com.kabouzeid.appthemehelper.R;
import com.kabouzeid.appthemehelper.ThemeStore;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATESwitchPreference extends CheckBoxPreference {

    public ATESwitchPreference(Context context) {
        super(context);
        init(context, null);
    }

    public ATESwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATESwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ATESwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setLayoutResource(R.layout.ate_preference_custom_support);
        setWidgetLayoutResource(R.layout.ate_preference_switch_support);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        View parentSwitch = findSwitchView(holder.itemView);
        if (parentSwitch != null) {
            ATH.setTint(parentSwitch, ThemeStore.accentColor(getContext()));
        }
    }

    @Nullable
    private View findSwitchView(View view) {
//        if (view instanceof ViewGroup) {
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//                View child = ((ViewGroup) view).getChildAt(i);
//                if (child instanceof Switch || child instanceof SwitchCompat) {
//                    return child;
//                } else if (child instanceof ViewGroup) {
//                    View potentialSwitch = findSwitchView(child);
//                    if (potentialSwitch != null) return potentialSwitch;
//                }
//            }
//        } else if (view instanceof Switch || view instanceof SwitchCompat) {
//            return view;
//        }
//        return null;
        return view.findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.id.checkbox : R.id.switchWidget);
    }
}