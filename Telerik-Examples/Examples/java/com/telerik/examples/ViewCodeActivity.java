package com.telerik.examples;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.SpinnerAdapter;

import com.telerik.examples.viewmodels.ExampleSourceModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.options.JavaSourceConversionOptions;

public class ViewCodeActivity extends Activity implements ActionBar.OnNavigationListener {
    private WebView codeVisualizer;
    ExampleSourceModel sourceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_code);
        this.codeVisualizer = (WebView) this.findViewById(R.id.webViewCode);
        final ActionBar actionBar = this.getActionBar();

        this.sourceModel = (ExampleSourceModel) this.getIntent().getSerializableExtra("source_model");
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            SpinnerAdapter adapter = new ExampleSourceAdapter(this.sourceModel, this);
            actionBar.setListNavigationCallbacks(adapter, this);
        }
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
        return true;
    }
}
