package com.telerik.examples.common.fragments;

import android.app.Activity;

import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.ExamplesAdapter;

public class FavoritesFragment extends ExampleGroupListFragment implements ExamplesApplicationContext.FavouritesChangedListener {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.app.addOnFavouritesChangedListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.app.removeOnFavouritesChangedListener(this);
    }

    @Override
    protected ExamplesAdapter getAdapter(int mode) {
        ExamplesAdapter adapter = new ExamplesAdapter(this.app, this.app.getControlExamples(), mode, this, true);
        adapter.getFilter().filter(ExamplesAdapter.FAVOURITES_FILTER_KEY);
        return adapter;
    }

    @Override
    public void favouritesChanged() {
        ((ExamplesAdapter) this.gridView.getAdapter()).getFilter().filter(ExamplesAdapter.FAVOURITES_FILTER_KEY);
    }
}
