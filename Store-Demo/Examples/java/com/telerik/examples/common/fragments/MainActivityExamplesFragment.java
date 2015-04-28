package com.telerik.examples.common.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.ControlExamplesAdapter;
import com.telerik.examples.common.ExamplesAdapter;
import com.telerik.examples.primitives.ExamplesGridView;

/**
 * Created by ginev on 1/8/2015.
 */
public class MainActivityExamplesFragment extends ControlsFragment {

    private ExamplesGridView listControls;
    private TextView controlsCaption;
    private TextView examplesCaption;

    @Override
    protected View initContent(LayoutInflater inflater, ViewGroup container) {
        View rootView = null;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rootView = inflater.inflate(R.layout.fragment_main_example_group_list, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_main_example_group_list_horizontal, container, false);
        }
        this.listControls = Util.getLayoutPart(rootView, R.id.listControls, ExamplesGridView.class);

        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        this.listExamples.expandWholeHeight = true;
        this.listControls.expandWholeHeight = true;
        this.controlsCaption = (TextView) rootView.findViewById(R.id.txtControls);
        this.examplesCaption = (TextView) rootView.findViewById(R.id.txtExamples);
        return rootView;
    }

    @Override
    protected void turnOnGridMode() {
        super.turnOnGridMode();
        if (this.listControls.getAdapter() == null) {
            ExamplesAdapter adapter = this.getAdapter(this.listControls, this.currentMode);
            adapter.registerDataSetObserver(new ExampleGroupDataSetObserver());
            this.listControls.setAdapter(adapter);
        }
        int columns = this.getResources().getInteger(R.integer.example_controls_list_fragment_columns_wrap);
        this.listControls.setNumColumns(columns);
        if (this.listControls.getAdapter() != null) {
            ((ExamplesAdapter) this.listControls.getAdapter()).setListMode(this.currentMode);
        }
    }

    @Override
    protected void turnOnListMode() {
        super.turnOnListMode();
        if (this.listControls.getAdapter() == null) {

            ControlExamplesAdapter adapter = (ControlExamplesAdapter) this.getAdapter(this.listControls, this.currentMode);
            adapter.registerDataSetObserver(new ExampleGroupDataSetObserver());
            this.listControls.setAdapter(adapter);
        }
        int columns = this.getResources().getInteger(R.integer.example_controls_list_fragment_columns);
        this.listControls.setNumColumns(columns);
        if (this.listControls.getAdapter() != null) {
            ((ExamplesAdapter) this.listControls.getAdapter()).setListMode(this.currentMode);
        }
    }

    @Override
    protected ExamplesAdapter getAdapter(GridView forList, int mode) {
        ExamplesAdapter adapter = null;
        if (this.filterApplied) {
            if (this.listControls == forList) {
                adapter = new ControlExamplesAdapter(this.app, this.app.getControlExamples(), mode, this, false);
                adapter.getFilter().filter(ExamplesAdapter.HIGHLIGHTED_CONTROLS_FILTER_KEY);
            } else if (this.listExamples == forList) {
                adapter = new ExamplesAdapter(this.app, this.app.getControlExamples(), mode, this, true);
                adapter.getFilter().filter(ExamplesAdapter.HIGHLIGHTED_EXAMPLES_FILTER_KEY);
            }
        } else {
            if (this.listControls == forList) {
                adapter = new ControlExamplesAdapter(this.app, this.app.getControlExamples(), mode, this, false);
                adapter.getFilter().filter(ExamplesAdapter.ALL_FILTER_KEY);
            } else if (this.listExamples == forList) {
                adapter = new ExamplesAdapter(this.app, this.app.getControlExamples(), mode, this, true);
                adapter.getFilter().filter(ExamplesAdapter.NOTHING_FILTER_KEY);
            }
        }

        return adapter;
    }

    @Override
    public boolean hasData() {
        boolean hasData = super.hasData();

        if (this.listControls != null) {
            hasData |= this.listControls.getAdapter() != null && this.listControls.getAdapter().getCount() > 0;
        }

        return hasData;
    }

    @Override
    public void showHighlighted() {
        this.filterApplied = true;
        if (this.listControls != null && this.listExamples != null) {
            ((ExamplesAdapter) this.listControls.getAdapter()).getFilter().filter(ExamplesAdapter.HIGHLIGHTED_CONTROLS_FILTER_KEY);
            ((ExamplesAdapter) this.listExamples.getAdapter()).getFilter().filter(ExamplesAdapter.HIGHLIGHTED_EXAMPLES_FILTER_KEY);
        }
    }

    @Override
    public void showAll() {
        this.filterApplied = false;
        if (this.listControls != null && this.listExamples != null) {
            ((ExamplesAdapter) this.listExamples.getAdapter()).getFilter().filter(ExamplesAdapter.NOTHING_FILTER_KEY);
            ((ExamplesAdapter) this.listControls.getAdapter()).getFilter().filter(ExamplesAdapter.ALL_FILTER_KEY);
        }
    }

    @Override
    protected void refreshEmptyContent() {
        if (this.getActivity() == null) {
            return;
        }

        this.emptyContentString.setVisibility(View.GONE);
        this.controlsCaption.setVisibility(this.listControls.getAdapter().getCount() == 0 ? View.GONE : View.VISIBLE);
        this.examplesCaption.setVisibility(this.listExamples.getAdapter().getCount() == 0 ? View.GONE : View.VISIBLE);
        this.getActivity().invalidateOptionsMenu();
    }
}
