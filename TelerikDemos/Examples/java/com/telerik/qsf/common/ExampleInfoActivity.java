package com.telerik.qsf.common;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import com.telerik.qsf.R;

public class ExampleInfoActivity extends Activity {

    private TextView txtInfo;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = this.getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff252939));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        setContentView(R.layout.activity_example_info);
        this.txtInfo = (TextView) this.findViewById(R.id.txtInfo);
        this.app = (App) this.getApplicationContext();

        this.loadExampleInfo();
    }

    private void loadExampleInfo() {
        String exampleDescription = null;
        String infoTitle = null;

        if (this.app.selectedGroup() != null) {
            exampleDescription = this.app.selectedGroup().getExampleInfo();
            infoTitle = this.app.selectedGroup().getHeaderText();
        }

        if (this.app.selectedExample() != null) {
            exampleDescription = this.app.selectedExample().getExampleInfo();
            infoTitle = this.app.selectedExample().getHeaderText();
        }
        this.getActionBar().setTitle(infoTitle);
        this.txtInfo.setText(exampleDescription);
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
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
