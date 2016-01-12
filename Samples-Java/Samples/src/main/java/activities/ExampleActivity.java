package activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.telerik.android.sdk.R;

public class ExampleActivity extends AppCompatActivity{

    static Fragment selectedExampleFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_example);

        String title = String.format("%s - %s", ControlActivity.selectedControl.controlName(), ((ExampleFragment) selectedExampleFragment).title());
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        FragmentManager fm = ExampleActivity.this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, selectedExampleFragment);
        ft.commit();
    }
}
