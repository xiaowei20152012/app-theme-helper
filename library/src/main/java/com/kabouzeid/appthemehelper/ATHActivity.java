package com.kabouzeid.appthemehelper;

import android.view.Menu;

import com.kabouzeid.appthemehelper.util.ToolbarHelper;
import com.kabouzeid.appthemehelper.util.ToolbarUtil;

public class ATHActivity extends ATHBaseActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ToolbarHelper.handleOnCreateOptionsMenu(this, ToolbarUtil.getSupportActionBarView(getSupportActionBar()), menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ToolbarHelper.handleOnPrepareOptionsMenu(this, ToolbarUtil.getSupportActionBarView(getSupportActionBar()));
        return super.onPrepareOptionsMenu(menu);
    }
}
