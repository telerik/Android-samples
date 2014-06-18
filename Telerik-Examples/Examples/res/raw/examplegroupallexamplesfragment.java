package com.telerik.examples.common.fragments;

import com.telerik.examples.common.ExamplesAdapter;

public class ExampleGroupAllExamplesFragment extends ExampleGroupListFragment {


    public ExampleGroupAllExamplesFragment() {
    }

    @Override
    protected ExamplesAdapter getAdapter(int mode) {
        ExamplesAdapter adapter =  new ExamplesAdapter(this.app, (this.app.selectedControl()).getExamples(), mode, this, false);
        adapter.getFilter().filter(ExamplesAdapter.ALL_FILTER_KEY);
        return adapter;
    }
}
