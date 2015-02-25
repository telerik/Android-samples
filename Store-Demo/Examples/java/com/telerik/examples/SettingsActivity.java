package com.telerik.examples;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.telerik.examples.common.ExamplesApplicationContext;

public class SettingsActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private ExamplesApplicationContext app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.app = (ExamplesApplicationContext) this.getApplicationContext();
        this.setContentView(R.layout.activity_settings);

        ActionBar actionBar = this.getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        }

        Switch swToggleStats = (Switch) this.findViewById(R.id.swToggleStats);
        swToggleStats.setChecked(this.app.analyticsActive());
        swToggleStats.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.app.setAnalyticsActive(this, isChecked, false);
        this.app.setAnalyticsLearned(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
