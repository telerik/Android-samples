package com.telerik.examples;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.TelerikActivityHelper;
import com.telerik.examples.common.TrackedApplication;
import com.telerik.examples.common.contracts.TrackedActivity;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;

import java.util.HashMap;

public class ExampleInfoActivity extends ActionBarActivity implements TrackedActivity{

    private TextView txtInfo;
    private ExamplesApplicationContext app;
    private ActionBar actionBar;
    private ExampleGroup selectedControl;
    private Example selectedExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.app = (ExamplesApplicationContext) this.getApplicationContext();
        this.selectedControl = this.app.findControlById(this.getIntent().getStringExtra(ExamplesApplicationContext.INTENT_CONTROL_ID));
        this.selectedExample = this.app.findExampleById(selectedControl, this.getIntent().getStringExtra(ExamplesApplicationContext.INTENT_EXAMPLE_ID));
        TelerikActivityHelper.updateActivityTaskDescription(this);
        setContentView(R.layout.activity_example_info);
        Toolbar tb = (Toolbar) this.findViewById(R.id.toolbar);
        tb.setTitle("");
        this.setSupportActionBar(tb);
        this.actionBar = this.getSupportActionBar();

        if (this.actionBar != null) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        }

        this.txtInfo = (TextView) this.findViewById(R.id.txtInfo);

        this.loadExampleInfo();

        if (savedInstanceState == null){
            this.app.trackScreenOpened(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadExampleInfo() {
        String exampleDescription = null;
        String infoTitle = null;


        if (selectedControl != null) {
            exampleDescription = selectedControl.getExampleInfo();
            infoTitle = selectedControl.getHeaderText();
        }

        if (selectedExample != null) {
            exampleDescription = selectedExample.getExampleInfo();
            infoTitle = selectedExample.getHeaderText();
        }

        if (this.actionBar != null) {
            this.actionBar.setTitle(infoTitle);
        }

        this.txtInfo.setText(exampleDescription);
    }

    @Override
    public String getScreenName() {
        return TrackedApplication.INFO_SCREEN;
    }

    @Override
    public HashMap<String, Object> getAdditionalParameters() {
        HashMap<String, Object> additionalParams = new HashMap<String, Object>();
        additionalParams.put(TrackedApplication.PARAM_CONTROL_NAME, this.selectedControl.getShortFragmentName());
        additionalParams.put(TrackedApplication.PARAM_EXAMPLE_NAME, this.selectedExample.getShortFragmentName());
        return additionalParams;
    }
}
