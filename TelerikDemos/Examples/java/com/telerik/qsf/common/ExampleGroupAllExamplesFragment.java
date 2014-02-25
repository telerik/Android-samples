package com.telerik.qsf.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.telerik.qsf.R;

public class ExampleGroupAllExamplesFragment extends ListFragment {

    private FragmentActivity parentActivity;
    private App app;

    public ExampleGroupAllExamplesFragment() {
    }

    @Override
    public String toString() {
        return "All";
    }

    @Override
    public ListView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        ListView rootView = (ListView) inflater.inflate(R.layout.fragment_example_group_all, container, false);
        this.app = (App) parentActivity.getApplicationContext();
        ExamplesAdapter controlsAdapter = new ExamplesAdapter(this.app, this.app.selectedGroup().getExamples());
        rootView.setAdapter(controlsAdapter);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        // do something with the data
//        //this.parentActivity.showExampleGroup(position);
//        FragmentManager fragmentManager = this.parentActivity.getSupportFragmentManager();
//        Fragment newFragment = new ExamplesActivity();
//        fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();
//
//        this.app.currentFragment = newFragment;

        Intent intent = new Intent(this.parentActivity, ExamplesActivity.class);
        startActivity(intent);

    }

    @Override
    public void onAttach(Activity activity) {
        this.parentActivity = (FragmentActivity) activity;
        super.onAttach(activity);
    }
}
