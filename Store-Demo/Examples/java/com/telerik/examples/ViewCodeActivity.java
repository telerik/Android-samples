package com.telerik.examples;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.SpinnerAdapter;

import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.TelerikActivityHelper;
import com.telerik.examples.common.TrackedApplication;
import com.telerik.examples.common.contracts.TrackedActivity;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;
import com.telerik.examples.viewmodels.ExampleSourceModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;

import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.options.JavaSourceConversionOptions;

public class ViewCodeActivity extends ActionBarActivity implements ActionBar.OnNavigationListener, TrackedActivity {
    private WebView codeVisualizer;
    ExampleSourceModel sourceModel;
    private ExamplesApplicationContext app;

    private ExampleGroup selectedControl;
    private Example selectedExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        this.app = (ExamplesApplicationContext) this.getApplicationContext();
        if (savedInstanceState == null) {
            this.selectedControl = this.app.findControlById(intent.getStringExtra(ExamplesApplicationContext.INTENT_CONTROL_ID));
            this.selectedExample = this.app.findExampleById(this.selectedControl, intent.getStringExtra(ExamplesApplicationContext.INTENT_EXAMPLE_ID));
            this.app.trackScreenOpened(this);
        } else {
            this.selectedControl = this.app.findControlById(savedInstanceState.getString(ExamplesApplicationContext.INTENT_CONTROL_ID));
            this.selectedExample = this.app.findExampleById(this.selectedControl, savedInstanceState.getString(ExamplesApplicationContext.INTENT_EXAMPLE_ID));
        }
        TelerikActivityHelper.updateActivityTaskDescription(this);
        setContentView(R.layout.activity_view_code);
        this.codeVisualizer = (WebView) this.findViewById(R.id.webViewCode);
        Toolbar tb = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(tb);
        final ActionBar actionBar = this.getSupportActionBar();


        this.sourceModel = (ExampleSourceModel) this.getIntent().getSerializableExtra(ExamplesApplicationContext.INTENT_SOURCE_MODEL_ID);
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            SpinnerAdapter adapter = new ExampleSourceAdapter(this.sourceModel, this);
            actionBar.setListNavigationCallbacks(adapter, this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ExamplesApplicationContext.INTENT_CONTROL_ID, this.selectedControl.getFragmentName());
        outState.putString(ExamplesApplicationContext.INTENT_EXAMPLE_ID, this.selectedExample.getFragmentName());
    }

    private void loadSourceCode(String rawFile) {
        try {
            Resources res = this.getResources();
            InputStream stream = res.openRawResource(res.getIdentifier(rawFile, "raw", getPackageName()));
            byte[] buffer = new byte[stream.available()];
            while (stream.read(buffer) != -1) ;
            stream.close();

            JavaSource source = new JavaSourceParser().parse(new String(buffer));
            StringWriter writer = new StringWriter();
            JavaSource2HTMLConverter converter = new JavaSource2HTMLConverter();
            converter.convert(source, JavaSourceConversionOptions.getDefault(), writer);
            this.codeVisualizer.loadData(writer.toString(), "text/html", "UTF-8");
            this.codeVisualizer.reload();
        } catch (IOException e) {
            Log.w("Examples", "Could not read example source: " + e.getMessage());
        } catch (Resources.NotFoundException e) {
            Log.w("Examples", "Example source code not found: " + e.getMessage());
        }
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return this.tryLoadSource(itemPosition);
    }

    private boolean tryLoadSource(int index) {
        String fileName = this.sourceModel.getDependencies().get(index);
        this.loadSourceCode(fileName.toLowerCase());
        this.sourceModel.setCurrentIndex(index);
        this.app.trackEvent(TrackedApplication.CODE_SCREEN, TrackedApplication.EVENT_CHANGE_SOURCE_FILE);
        return true;
    }

    @Override
    public String getScreenName() {
        return TrackedApplication.CODE_SCREEN;
    }

    @Override
    public HashMap<String, Object> getAdditionalParameters() {
        HashMap<String, Object> additionalParams = new HashMap<String, Object>();
        additionalParams.put(TrackedApplication.PARAM_CONTROL_NAME, this.selectedControl.getShortFragmentName());
        additionalParams.put(TrackedApplication.PARAM_EXAMPLE_NAME, this.selectedExample.getShortFragmentName());
        return additionalParams;
    }
}
