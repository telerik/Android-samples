package com.telerik.examples.common.fragments;

import android.widget.GridView;

import com.telerik.examples.common.ExamplesAdapter;

public class ControlsFragment extends ExampleGroupListFragment implements NavigationDrawerFragment.SectionInfoProvider {

    protected boolean filterApplied = false;

    public void showHighlighted() {
        this.filterApplied = true;
        if (this.listExamples != null) {
            ((ExamplesAdapter) this.listExamples.getAdapter()).getFilter().filter(ExamplesAdapter.HIGHLIGHTED_FILTER_KEY);
        }
    }

    public void showAll() {

        this.filterApplied = false;
        if (this.listExamples != null) {
            ((ExamplesAdapter) this.listExamples.getAdapter()).getFilter().filter(ExamplesAdapter.ALL_FILTER_KEY);
        }
    }


    @Override
    public void refreshFilters() {
        super.refreshFilters();
        if (this.filterApplied) {
            this.showHighlighted();
        }
    }

    @Override
    protected ExamplesAdapter getAdapter(GridView forList, int mode) {
        ExamplesAdapter adapter = new ExamplesAdapter(this.app, this.app.getControlExamples(), mode, this, true);
        if (this.filterApplied) {
            adapter.getFilter().filter(ExamplesAdapter.HIGHLIGHTED_FILTER_KEY);
        } else {
            adapter.getFilter().filter(ExamplesAdapter.ALL_FILTER_KEY);
        }
        return adapter;
    }

    @Override
    public String getSectionName() {
        return NavigationDrawerFragment.NAV_DRAWER_SECTION_HOME;
    }
}