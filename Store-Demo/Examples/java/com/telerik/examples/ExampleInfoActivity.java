package com.telerik.examples;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;

public class ExampleInfoActivity extends Activity {

    private TextView txtInfo;
    private ExamplesApplicationContext app;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.actionBar = this.getActionBar();

        if(this.actionBar != null) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        }

        setContentView(R.layout.activity_example_info);

        this.txtInfo = (TextView) this.findViewById(R.id.txtInfo);
        this.app = (ExamplesApplicationContext) this.getApplicationContext();

        this.loadExampleInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
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

        ExampleGroup selectedControl = this.app.selectedControl();
        if (selectedControl != null) {
            exampleDescription = selectedControl.getExampleInfo();
            infoTitle = selectedControl.getHeaderText();
        }

        Example selectedExample = this.app.selectedExample();
        if (selectedExample != null) {
            exampleDescription = selectedExample.getExampleInfo();
            infoTitle = selectedExample.getHeaderText();
        }

        if(this.actionBar != null) {
            this.actionBar.setTitle(infoTitle);
        }

        this.txtInfo.setText(exampleDescription);
    }
}
