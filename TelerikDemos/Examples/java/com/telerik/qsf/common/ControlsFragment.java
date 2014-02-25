package com.telerik.qsf.common;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.telerik.qsf.R;

// Instances of this class are fragments representing a single object in our collection.
public class ControlsFragment extends BaseFragment implements ActionBar.OnNavigationListener {

    private static ControlsFragment instance;
    private ControlsAdapter controlsAdapter;

    public ControlsFragment() {
        super(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.fragment_controls, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.controlsListView);
        App app = (App) mainActivity.getApplicationContext();
        controlsAdapter = new ControlsAdapter(app, app.getExampleGroups());
        listView.setAdapter(controlsAdapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                mainActivity.showExampleGroup(i);
            }
        });

        return rootView;
    }

    public void showHighlighted() {
        controlsAdapter.getFilter().filter("highlighted");
    }

    public void showAll() {
        controlsAdapter.getFilter().filter("all");
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // do something with the data
//        this.mainActivity.showExampleGroup(position);
//
//    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }

    public static ControlsFragment newInstance() {
        if(instance == null)
            instance = new ControlsFragment();

        return instance;
    }
}