package com.kabouzeid.appthemehelpersample.rv;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.kabouzeid.appthemehelper.common.ATHActionBarActivity;
import com.kabouzeid.appthemehelpersample.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class RecyclerViewSampleActivity extends ATHActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setAdapter(new SampleRVAdapter());

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getThemeRes() {
        int defaultThemeRes = super.getThemeRes();
        switch (defaultThemeRes) {
            case R.style.AppTheme:
                return R.style.AppTheme_ActionBar;
            case R.style.AppThemeDark:
                return R.style.AppThemeDark_ActionBar;
        }
        return defaultThemeRes;
    }
}
