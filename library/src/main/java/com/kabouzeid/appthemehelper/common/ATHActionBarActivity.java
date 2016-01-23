package com.kabouzeid.appthemehelper.common;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.kabouzeid.appthemehelper.ATHActivity;
import com.kabouzeid.appthemehelper.util.ToolbarContentTintHelper;

public class ATHActionBarActivity extends ATHActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ToolbarContentTintHelper.handleOnCreateOptionsMenu(this, extractWrappedToolbar(), menu, getActionBarBackgroundColor());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ToolbarContentTintHelper.handleOnPrepareOptionsMenu(this, extractWrappedToolbar());
        return super.onPrepareOptionsMenu(menu);
    }

    @Nullable
    public Toolbar extractWrappedToolbar() {
        return ToolbarContentTintHelper.getSupportActionBarView(getSupportActionBar());
    }

    protected int getActionBarBackgroundColor() {
        Toolbar wrappedToolbar = extractWrappedToolbar();
        if (wrappedToolbar != null) {
            if (wrappedToolbar.getBackground() instanceof ColorDrawable) {
                return ((ColorDrawable) wrappedToolbar.getBackground()).getColor();
            }
        }
        return 0;
    }
}
