package activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ExpandableListView;

import com.telerik.android.sdk.R;

import java.util.ResourceBundle;

/**
 * Created by ginev on 12/5/2014.
 */
public class ControlActivity extends FragmentActivity {
    private ExpandableListView expList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        this.expList = (ExpandableListView)this.findViewById(R.id.expListView);
        final ExamplesAdapter ea = new ExamplesAdapter(new ChartExamples().examples());
        this.expList.setAdapter(ea);
        this.expList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ExampleActivity.selectedExampleFragment = (android.support.v4.app.Fragment)ea.getChild(groupPosition, childPosition);
                Intent exampleIntent = new Intent(ControlActivity.this, ExampleActivity.class);
                ControlActivity.this.startActivity(exampleIntent);
                return true;
            }
        });
    }
}
