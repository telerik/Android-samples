package com.telerik.examples;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.TelerikActivityHelper;
import com.telerik.examples.common.TrackedApplication;
import com.telerik.examples.common.contracts.TrackedActivity;

import java.util.HashMap;

public class SettingsActivity extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener, TrackedActivity {
    private ExamplesApplicationContext app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TelerikActivityHelper.updateActivityTaskDescription(this);
        this.app = (ExamplesApplicationContext) this.getApplicationContext();
        this.setContentView(R.layout.activity_settings);

        Toolbar tb = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(tb);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        }

        SwitchCompat swToggleStats = (SwitchCompat) this.findViewById(R.id.swToggleStats);
        swToggleStats.setChecked(this.app.analyticsActive());
        swToggleStats.setOnCheckedChangeListener(this);

        if (savedInstanceState == null){
            this.app.trackScreenOpened(this);
        }
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
        return true;
    }

    @Override
    public String getScreenName() {
        return TrackedApplication.SETTINGS_SCREEN;
    }

    @Override
    public HashMap<String, Object> getAdditionalParameters() {
        return null;
    }
}
