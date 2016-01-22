package com.kabouzeid.appthemehelpersample.rv;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.kabouzeid.appthemehelpersample.R;
import com.kabouzeid.appthemehelpersample.base.BaseThemedActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class RecyclerViewSampleActivity extends BaseThemedActivity {

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
}
