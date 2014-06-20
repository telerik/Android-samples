package com.telerik.examples.common.fragments;

import com.telerik.examples.common.ExamplesAdapter;

public class ControlsFragment extends ExampleGroupListFragment {

    private boolean filterApplied = false;

    public void showHighlighted() {
        this.filterApplied = true;
        ((ExamplesAdapter)this.gridView.getAdapter()).getFilter().filter(ExamplesAdapter.HIGHLIGHTED_FILTER_KEY);
    }

    public void showAll() {
        this.filterApplied = false;
        ((ExamplesAdapter)this.gridView.getAdapter()).getFilter().filter(ExamplesAdapter.ALL_FILTER_KEY);
    }

    @Override
    public void refreshFilters() {
        super.refreshFilters();
        if (this.filterApplied){
            this.showHighlighted();
        }
    }

    @Override
    protected ExamplesAdapter getAdapter(int mode) {
        ExamplesAdapter adapter = new  ExamplesAdapter(this.app, this.app.getControlExamples(), mode, this, true);
        if (this.filterApplied) {
            adapter.getFilter().filter(ExamplesAdapter.HIGHLIGHTED_FILTER_KEY);
        }else {
            adapter.getFilter().filter(ExamplesAdapter.ALL_FILTER_KEY);
        }
        return adapter;
    }
}