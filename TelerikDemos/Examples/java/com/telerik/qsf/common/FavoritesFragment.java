package com.telerik.qsf.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.telerik.qsf.R;
import com.telerik.qsf.viewmodels.ExampleGroup;

import java.util.ArrayList;

public class FavoritesFragment extends BaseFragment {

    private final static String title = "Favorites";
    private static FavoritesFragment instance;
    private static final String FAVOURITES_CLICKED_EXAMPLE_KEY = "Favourites item clicked";
    private App app;
    View rootView;

    public FavoritesFragment() {
        super(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        this.rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        return this.rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listView = (ListView) this.rootView.findViewById(R.id.controlsListView);
        this.app = (App) mainActivity.getApplicationContext();
        final Object[] array = app.getFavorites().toArray();
        final ArrayList<ExampleGroup> favorites = constructFavorites(array);
        ControlsAdapter controlsAdapter = new ControlsAdapter(this.app, favorites);
        listView.setAdapter(controlsAdapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                mainActivity.showExampleGroup(favorites.get(i));
                app.trackFeature(FAVOURITES_CLICKED_EXAMPLE_KEY, (String) array[i]);
            }
        });
    }

    private ArrayList<ExampleGroup> constructFavorites(final Object[] array) {
        ArrayList<ExampleGroup> favorites = new ArrayList<ExampleGroup>(array.length);
        for (int i = 0; i < array.length; i++) {
            String fragmentName = (String) array[i];
            favorites.add(getExampleGroupFromFragmentName(fragmentName));
        }

        return favorites;
    }

    private ExampleGroup getExampleGroupFromFragmentName(String fragmentName) {

        for (int i = 0; i < this.app.getExampleGroups().size(); i++) {
            ExampleGroup exampleGroup = this.app.getExampleGroups().get(i);
            if (exampleGroup.getFragmentName().toLowerCase().equals(fragmentName.toLowerCase())) {
                return exampleGroup;
            }
        }

        return null;
    }

    public static BaseFragment newInstance() {
        if (instance == null)
            instance = new FavoritesFragment();

        return instance;
    }
}
