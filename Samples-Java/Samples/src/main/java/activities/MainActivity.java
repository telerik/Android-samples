package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.telerik.android.sdk.R;


public class MainActivity extends AppCompatActivity {

    private ListView listControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listControls = (ListView) this.findViewById(R.id.listControls);
        this.listControls.setAdapter(new ControlsAdapter(this, 0));
        this.listControls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ControlActivity.selectedControl = (ExamplesProvider)listControls.getAdapter().getItem(position);

                Intent examplesActivity = new Intent(MainActivity.this, ControlActivity.class);
                MainActivity.this.startActivity(examplesActivity);
            }
        });

    }


}
