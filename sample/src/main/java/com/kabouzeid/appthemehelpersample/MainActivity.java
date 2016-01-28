package com.kabouzeid.appthemehelpersample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kabouzeid.appthemehelper.ATH;
import com.kabouzeid.appthemehelper.ThemeStore;
import com.kabouzeid.appthemehelper.common.ATHToolbarActivity;
import com.kabouzeid.appthemehelper.util.MaterialDialogsUtil;
import com.kabouzeid.appthemehelper.util.NavigationViewUtil;
import com.kabouzeid.appthemehelper.util.TintHelper;
import com.kabouzeid.appthemehelpersample.collapsingtb.CollapsingToolbarActivity;
import com.kabouzeid.appthemehelpersample.dialogs.AboutDialog;
import com.kabouzeid.appthemehelpersample.rv.RecyclerViewSampleActivity;
import com.kabouzeid.appthemehelpersample.tabs.TabSampleActivity;

public class MainActivity extends ATHToolbarActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Default config
        if (!ThemeStore.isConfigured(this, 1)) {
            ThemeStore.editTheme(this)
                    .activityTheme(R.style.AppTheme)
                    .primaryColorRes(R.color.colorPrimaryLightDefault)
                    .accentColorRes(R.color.colorAccentLightDefault)
                    .coloredNavigationBar(false)
                    .commit();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.appbar_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setBackgroundColor(ThemeStore.primaryColor(this));

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.setDrawerListener(new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close));

        int accentColor = ThemeStore.accentColor(this);

        final NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(this);
        navView.getHeaderView(0).setBackgroundColor(accentColor);

        NavigationViewUtil.setItemTextColors(navView, ThemeStore.textColorPrimary(this), accentColor);
        NavigationViewUtil.setItemIconColors(navView, ThemeStore.textColorSecondary(this), accentColor);

        TintHelper.setTintAuto(findViewById(R.id.fab), accentColor, true);
        TintHelper.setTintAuto(findViewById(R.id.button), accentColor, true);

        TintHelper.setTintAuto(findViewById(R.id.switchView), accentColor, false);

        boolean coloredStatusBar = ThemeStore.coloredStatusBar(this);
        int statusBarColor = coloredStatusBar ? Color.TRANSPARENT : Color.BLACK;
        ATH.setStatusbarColor(this, statusBarColor);
        if (coloredStatusBar) {
            statusBarColor = ThemeStore.statusBarColor(this);
        }
        mDrawer.setStatusBarBackgroundColor(statusBarColor);
        ATH.setLightStatusbarAuto(this, statusBarColor);

        MaterialDialogsUtil.updateMaterialDialogsThemeSingleton(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.search_view_example));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawer.closeDrawers();
        final int mItemId = item.getItemId();
        mDrawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (mItemId) {
                    case R.id.drawer_tabs:
                        startActivity(new Intent(MainActivity.this, TabSampleActivity.class));
                        break;
                    case R.id.drawer_recyclerview:
                        startActivity(new Intent(MainActivity.this, RecyclerViewSampleActivity.class));
                        break;
                    case R.id.drawer_collapsingtoolbar:
                        startActivity(new Intent(MainActivity.this, CollapsingToolbarActivity.class));
                        break;
                    case R.id.drawer_insets:
                        startActivity(new Intent(MainActivity.this, InsetActivity.class));
                        break;
                    case R.id.drawer_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.drawer_about:
                        AboutDialog.show(MainActivity.this);
                        break;
                }
            }
        }, 75);
        return true;
    }
}