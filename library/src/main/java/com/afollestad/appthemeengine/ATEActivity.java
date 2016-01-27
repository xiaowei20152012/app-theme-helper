package com.afollestad.appthemeengine;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEActivity extends AppCompatActivity {

    private long updateTime = -1;

    @Nullable
    public String getATEKey() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ATE.preApply(this, getATEKey());
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new InflationInterceptor(getLayoutInflater(), getDelegate()));
        super.onCreate(savedInstanceState);
        updateTime = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ATE.postApply(this, getATEKey());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ATE.didValuesChange(this, updateTime, getATEKey()))
            // hack to prevent java.lang.RuntimeException: Performing pause of activity that is not resumed
            // makes sure recreate() is called right after and not in onResume()
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu.size() > 0)
            ATE.applyMenu(this, getATEKey(), menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ATE.applyOverflow(this, getATEKey());
        return super.onPrepareOptionsMenu(menu);
    }
}