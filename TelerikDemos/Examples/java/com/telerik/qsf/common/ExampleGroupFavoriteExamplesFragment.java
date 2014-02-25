package com.telerik.qsf.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.telerik.qsf.R;

import java.util.ArrayList;

public class ExampleGroupFavoriteExamplesFragment extends ListFragment {
    private FragmentActivity parentActivity;
    public ExampleGroupFavoriteExamplesFragment() {
    }

    @Override
    public String toString() {
        return "Favorites";
    }

    @Override
    public ListView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        ListView rootView = (ListView) inflater.inflate(R.layout.fragment_example_group_favorites, container, false);
        ArrayList<String> message = new ArrayList<String>();
        message.add("You have no favorites!");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.parentActivity, android.R.layout.simple_list_item_1, android.R.id.text1, message);
        rootView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    @Override
    public void onAttach(Activity activity) {
        this.parentActivity = (FragmentActivity) activity;
        super.onAttach(activity);
    }
}
