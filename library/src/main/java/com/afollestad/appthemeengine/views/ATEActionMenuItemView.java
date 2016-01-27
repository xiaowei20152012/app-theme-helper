package com.afollestad.appthemeengine.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.AttributeSet;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.TintHelper;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEActionMenuItemView extends ActionMenuItemView implements ViewInterface {

    public ATEActionMenuItemView(Context context) {
        super(context);
        init(context, null);
    }

    public ATEActionMenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public ATEActionMenuItemView(Context context, AttributeSet attrs, @Nullable ATEActivity keyContext) {
        super(context, attrs);
        init(context, keyContext);
    }

    private String mKey;

//    private static Field mIconField;

    private void init(Context context, @Nullable ATEActivity keyContext) {
        setTag("text_primary");

//        if (mIconField == null) {
//            try {
//                mIconField = ActionMenuItemView.class.getDeclaredField("mIcon");
//                mIconField.setAccessible(true);
//            } catch (Throwable t) {
//                throw new RuntimeException("Failed to get the mIcon field for ActionMenuItemView.", t);
//            }
//        }

        if (keyContext == null && context instanceof ATEActivity)
            keyContext = (ATEActivity) context;
        mKey = null;
        if (keyContext != null)
            mKey = keyContext.getATEKey();
        ATE.apply(context, this, mKey);
    }

    @Override
    public void setIcon(Drawable icon) {
        // TODO get a reference to toolbar instead of null here?
        final int toolbarColor = Config.toolbarColor(getContext(), mKey, null);
        final int tintColor = Config.getToolbarTitleColor(getContext(), null, mKey, toolbarColor);
        icon = TintHelper.tintDrawable(icon, tintColor);
        super.setIcon(icon);
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
