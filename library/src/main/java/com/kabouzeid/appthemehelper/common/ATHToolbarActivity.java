package com.kabouzeid.appthemehelper.common;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.kabouzeid.appthemehelper.ATHActivity;
import com.kabouzeid.appthemehelper.util.ToolbarContentTintHelper;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class ATHToolbarActivity extends ATHActivity {
    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ToolbarContentTintHelper.handleOnCreateOptionsMenu(this, toolbar, menu, getToolbarBackgroundColor());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ToolbarContentTintHelper.handleOnPrepareOptionsMenu(this, toolbar);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        this.toolbar = toolbar;
        super.setSupportActionBar(toolbar);
    }

    protected int getToolbarBackgroundColor() {
        if (toolbar != null) {
            if (toolbar.getBackground() instanceof ColorDrawable) {
                return ((ColorDrawable) toolbar.getBackground()).getColor();
            }
        }
        return 0;
    }
}