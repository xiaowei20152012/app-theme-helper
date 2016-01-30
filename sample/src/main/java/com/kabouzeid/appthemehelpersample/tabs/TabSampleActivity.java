package com.kabouzeid.appthemehelpersample.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kabouzeid.appthemehelper.ATH;
import com.kabouzeid.appthemehelper.ThemeStore;
import com.kabouzeid.appthemehelper.common.ATHToolbarActivity;
import com.kabouzeid.appthemehelper.util.TabLayoutUtil;
import com.kabouzeid.appthemehelper.util.ToolbarContentTintHelper;
import com.kabouzeid.appthemehelpersample.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TabSampleActivity extends ATHToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        mPager.setOffscreenPageLimit(2);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mPager);


        int appbarColor = ThemeStore.primaryColor(this);
        findViewById(R.id.appbar).setBackgroundColor(appbarColor);

        int normalColor = ToolbarContentTintHelper.toolbarSubtitleColor(this, appbarColor);
        int selectedColor = ToolbarContentTintHelper.toolbarTitleColor(this, appbarColor);
        TabLayoutUtil.setTabIconColors(tabs, normalColor, selectedColor);
        tabs.setTabTextColors(normalColor, selectedColor);
        tabs.setSelectedTabIndicatorColor(ThemeStore.accentColor(this));

        ATH.setStatusbarColorAuto(this);
        ATH.setActivityToolbarColorAuto(this, toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
