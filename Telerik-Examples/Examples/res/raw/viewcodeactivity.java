package com.telerik.examples;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.telerik.examples.common.ExamplesApplicationContext;

import java.io.InputStream;
import java.io.StringWriter;

import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.options.JavaSourceConversionOptions;


public class ViewCodeActivity extends Activity {

    private WebView codeVisualizer;
    private ExamplesApplicationContext app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_code);
        this.app = (ExamplesApplicationContext) this.getApplicationContext();
        this.setTitle(this.app.selectedExample().getHeaderText() + " code");
        this.codeVisualizer = (WebView)this.findViewById(R.id.webViewCode);
        final ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        String fileName = this.getIntent().getStringExtra("file_name");
        this.loadSourceCode(fileName.toLowerCase());
    }

    private void loadSourceCode(String rawFile) {
        try {
            InputStream stream = this.getResources().openRawResource(getResources().getIdentifier(rawFile, "raw", getPackageName()));
            byte[] buffer = new byte[stream.available()];
            while(stream.read(buffer) != -1);
            stream.close();

            JavaSource source = new JavaSourceParser().parse(new String(buffer));
            StringWriter writer = new StringWriter();
            JavaSource2HTMLConverter converter = new JavaSource2HTMLConverter();
            converter.convert(source, JavaSourceConversionOptions.getDefault(), writer);
            this.codeVisualizer.loadData(writer.toString(), "text/html", "UTF-8");
        } catch (Exception e) {
            Log.e("Examples", "Could not read example source.", e);
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
}
