package com.telerik.qsf.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.telerik.qsf.R;

public class FallbackExamples extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fallback_examples);
        Bundle bundle = getIntent().getExtras();
        TextView messageView = (TextView) findViewById(R.id.message);
        String controlName = bundle.getString("controlName");
        if (controlName != null && messageView != null) {
            messageView.setText("This is a default examples page. You need to create a new activity and put " + controlName + "'s examples there!");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
