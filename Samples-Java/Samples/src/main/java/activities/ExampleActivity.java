package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.telerik.android.sdk.R;

/**
 * Created by ginev on 12/5/2014.
 */
public class ExampleActivity extends ActionBarActivity{

    static Fragment selectedExampleFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_example);

        FragmentManager fm = ExampleActivity.this.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, selectedExampleFragment);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
