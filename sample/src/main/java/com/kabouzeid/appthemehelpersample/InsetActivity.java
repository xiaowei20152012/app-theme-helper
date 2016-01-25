package com.kabouzeid.appthemehelpersample;

import android.os.Bundle;

import com.kabouzeid.appthemehelper.common.ATHToolbarActivity;

public class InsetActivity extends ATHToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inset);

//        boolean coloredStatusBar = ThemeStore.coloredStatusBar(this);
//        int statusBarColor = coloredStatusBar ? Color.TRANSPARENT : Color.BLACK;
//        ATH.setStatusbarColor(this, statusBarColor);
//        if (coloredStatusBar) {
//            statusBarColor = ThemeStore.statusBarColor(this);
//        }
//        ((CoordinatorLayout) findViewById(R.id.scrim_insets_layout)).setStatusBarBackgroundColor(statusBarColor);
//        ATH.setLightStatusbarAuto(this, statusBarColor);
    }
}
