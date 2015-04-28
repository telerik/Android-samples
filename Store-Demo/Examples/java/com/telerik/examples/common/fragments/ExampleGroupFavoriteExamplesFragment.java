package com.telerik.examples.common.fragments;

import android.app.Activity;
import android.widget.GridView;

import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.ExamplesAdapter;
import com.telerik.examples.viewmodels.ExampleGroup;

public class ExampleGroupFavoriteExamplesFragment extends ExampleGroupListFragment implements ExamplesApplicationContext.FavouritesChangedListener {

    private ExampleGroup selectedControl;

    public ExampleGroupFavoriteExamplesFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.app.addOnFavouritesChangedListener(this);
        this.selectedControl = this.app.findControlById(this.getActivity().getIntent().getStringExtra(ExamplesApplicationContext.INTENT_CONTROL_ID));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.app.removeOnFavouritesChangedListener(this);
    }

    @Override
    protected ExamplesAdapter getAdapter(GridView forList, int mode) {
        ExamplesAdapter adapter = new ExamplesAdapter(this.app, this.selectedControl.getExamples(), mode, this, false);
        adapter.getFilter().filter(ExamplesAdapter.FAVOURITES_FILTER_KEY);
        return adapter;
    }

    @Override
    public void favouritesChanged() {
        ((ExamplesAdapter) this.listExamples.getAdapter()).getFilter().filter(ExamplesAdapter.FAVOURITES_FILTER_KEY);
        this.refreshEmptyContent();
    }
}
