package com.telerik.examples.common.fragments;

import android.app.Activity;
import android.widget.GridView;

import com.telerik.examples.common.ExamplesAdapter;
import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.viewmodels.ExampleGroup;

public class ExampleGroupAllExamplesFragment extends ExampleGroupListFragment {

    private ExampleGroup selectedControl;

    public ExampleGroupAllExamplesFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.selectedControl = this.app.findControlById(this.getActivity().getIntent().getStringExtra(ExamplesApplicationContext.INTENT_CONTROL_ID));
    }

    @Override
    protected ExamplesAdapter getAdapter(GridView forList, int mode) {
        ExamplesAdapter adapter = new ExamplesAdapter(this.app, this.selectedControl.getExamples(), mode, this, false);
        adapter.getFilter().filter(ExamplesAdapter.ALL_FILTER_KEY);
        return adapter;
    }
}
