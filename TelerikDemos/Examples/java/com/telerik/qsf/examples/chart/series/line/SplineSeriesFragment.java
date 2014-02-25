package com.telerik.qsf.examples.chart.series.line;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.qsf.R;
import com.telerik.qsf.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SplineSeries;

public class SplineSeriesFragment extends Fragment {

    private static final double VERTICAL_AXIS_SINGLE_STEP = 5;
    private static final double VERTICAL_AXIS_SINGLE_MIN = 60;
    private static final double VERTICAL_AXIS_SINGLE_MAX = 90;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_spline, container, false);
        RadCartesianChartView chart = (RadCartesianChartView) root.findViewById(R.id.chart);
        prepareChart(chart);

        return root;
    }

    private void prepareChart(final RadCartesianChartView chart) {
        Context activity = getActivity();

        LinearAxis vAxis = new LinearAxis(activity);
        vAxis.setMinimum(VERTICAL_AXIS_SINGLE_MIN);
        vAxis.setMaximum(VERTICAL_AXIS_SINGLE_MAX);
        vAxis.setMajorStep(VERTICAL_AXIS_SINGLE_STEP);

        CategoricalAxis hAxis = new CategoricalAxis(activity);
        hAxis.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        SplineSeries series = new SplineSeries(activity);
        Resources res = this.getResources();
        series.setStrokeThickness(res.getDimension(R.dimen.twodp));
        series.setCategoryBinding(new FieldNameDataPointBinding("category"));
        series.setValueBinding(new FieldNameDataPointBinding("value"));
        series.setData(ExampleDataProvider.lineData());

        CartesianChartGrid grid = new CartesianChartGrid(activity);
        grid.setStripLinesVisibility(GridLineVisibility.Y);

        chart.setGrid(grid);
        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(hAxis);
        chart.getSeries().add(series);
    }
}
