package com.telerik.examples.examples.chart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionBehavior;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionChangeListener;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionContext;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.common.RadChartViewBase;

public abstract class ChartSelectionFragment extends ExampleFragmentBase implements ChartSelectionChangeListener {
    protected RadChartViewBase chart;
    protected Context context;
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = this.getActivity();
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(this.getLayoutID(), container, false);
        this.chart = Util.getLayoutPart(this.rootView, R.id.chart, RadChartViewBase.class);

        if (this.chart instanceof RadCartesianChartView) {
            CartesianChartGrid grid = new CartesianChartGrid();
            grid.setStripLinesVisibility(GridLineVisibility.Y);
            ((RadCartesianChartView) chart).setGrid(grid);
        }
        this.prepareChart();

        return this.rootView;
    }

    protected abstract int getLayoutID();

    @Override
    public void onSelectionChanged(ChartSelectionContext selectionContext) {
    }

    protected void prepareChart() {
        ChartSelectionBehavior selectionBehavior = new ChartSelectionBehavior();
        chart.getBehaviors().add(selectionBehavior);
        selectionBehavior.setSelectionChangeListener(this);

        this.initSelectionBehavior(selectionBehavior);
    }

    protected void initSelectionBehavior(ChartSelectionBehavior behavior) {
    }
}
