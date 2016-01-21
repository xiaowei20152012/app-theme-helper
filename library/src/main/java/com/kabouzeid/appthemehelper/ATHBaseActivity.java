package com.kabouzeid.appthemehelper;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.kabouzeid.appthemehelper.util.ToolbarUtil;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATHBaseActivity extends AppCompatActivity {

    private long updateTime = -1;

    @Nullable
    protected String getATEKey() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ATH.didValuesChange(this, updateTime, getATEKey()))
            // hack to prevent java.lang.RuntimeException: Performing pause of activity that is not resumed
            // makes sure recreate() is called right after and not in onResume()
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            });
    }
}