package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;

import com.telerik.android.sdk.R;

public class ControlActivity extends AppCompatActivity {

    public static ExamplesProvider selectedControl;
    private ExpandableListView expList;
    private static final int INDICATOR_LEFT = 40;
    private static final int INDICATOR_RIGHT = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        if(getSupportActionBar() != null && selectedControl != null) {
            getSupportActionBar().setTitle(selectedControl.controlName());
        }

        this.expList = (ExpandableListView)this.findViewById(R.id.expListView);
        moveIndicatorImage();

        final ExamplesAdapter ea = new ExamplesAdapter(selectedControl.examples());
        this.expList.setAdapter(ea);

        for (int i = 0; i < ea.getGroupCount(); i++ ) {
            expList.expandGroup(i);
        }

        this.expList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ExampleActivity.selectedExampleFragment = (android.support.v4.app.Fragment) ea.getChild(groupPosition, childPosition);
                Intent exampleIntent = new Intent(ControlActivity.this, ExampleActivity.class);
                ControlActivity.this.startActivity(exampleIntent);
                return true;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        moveIndicatorImage();
    }

    private void moveIndicatorImage() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int left = (int) (INDICATOR_LEFT * metrics.density + 0.5f);
        int right = (int) (INDICATOR_RIGHT * metrics.density + 0.5f);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expList.setIndicatorBoundsRelative(width - left, width - right);
        } else {
            expList.setIndicatorBounds(width - left, width - right);
        }
    }
}
