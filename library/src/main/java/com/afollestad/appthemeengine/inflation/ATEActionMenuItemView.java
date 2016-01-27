package com.afollestad.appthemeengine.inflation;

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
class ATEActionMenuItemView extends ActionMenuItemView implements ViewInterface {

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
    private int mTintColor;
    private Drawable mIcon;

    private void init(Context context, @Nullable ATEActivity keyContext) {
        if (keyContext == null && context instanceof ATEActivity)
            keyContext = (ATEActivity) context;
        mKey = null;
        if (keyContext != null)
            mKey = keyContext.getATEKey();

        if (mIcon != null)
            setIcon(mIcon); // invalidates initial icon tint
        else invalidateTintColor();

        ATE.themeView(context, this, mKey);
        setTextColor(mTintColor); // sets menu item text color
    }

    private void invalidateTintColor() {
        // TODO get a reference to toolbar instead of null here?
        final int mToolbarColor = Config.toolbarColor(getContext(), mKey, null);
        mTintColor = Config.getToolbarTitleColor(getContext(), null, mKey, mToolbarColor);
    }

    @Override
    public void setIcon(Drawable icon) {
        invalidateTintColor();
        mIcon = TintHelper.tintDrawable(icon, mTintColor);
        super.setIcon(mIcon);
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
