package com.afollestad.appthemeenginesample.misc;

import android.os.Bundle;

import com.afollestad.appthemeenginesample.R;
import com.afollestad.appthemeenginesample.base.BaseThemedActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class MiscActivity extends BaseThemedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);

        assert getSupportActionBar() != null;
        getSupportActionBar().setSubtitle("Test subtitle");
    }
}