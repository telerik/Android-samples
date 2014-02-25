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
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;

public class MultipleLineSeriesFragment extends Fragment {

    private static final double VERTICAL_AXIS_MULTIPLE_STEP = 50;
    private static final double VERTICAL_AXIS_MULTIPLE_MIN = 0;
    private static final double VERTICAL_AXIS_MULTIPLE_MAX = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_line_multy, container, false);
        RadCartesianChartView chart = (RadCartesianChartView) root.findViewById(R.id.chart);
        prepareChart(chart);

        return root;
    }

    private void prepareChart(final RadCartesianChartView chart) {
        Context activity = getActivity();

        LinearAxis vAxis = new LinearAxis(activity);
        vAxis.setMinimum(VERTICAL_AXIS_MULTIPLE_MIN);
        vAxis.setMaximum(VERTICAL_AXIS_MULTIPLE_MAX);
        vAxis.setMajorStep(VERTICAL_AXIS_MULTIPLE_STEP);

        CategoricalAxis hAxis = new CategoricalAxis(activity);
        hAxis.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        DataPointBinding categoryBinding = new FieldNameDataPointBinding("category");
        DataPointBinding valueBinding = new FieldNameDataPointBinding("value");

        LineSeries series = new LineSeries(activity);
        Resources res = this.getResources();
        series.setStrokeThickness(res.getDimension(R.dimen.twodp));
        series.setCategoryBinding(categoryBinding);
        series.setValueBinding(valueBinding);
        series.setData(ExampleDataProvider.lineData());

        LineSeries secondarySeries = new LineSeries(activity);
        secondarySeries.setStrokeThickness(res.getDimension(R.dimen.twodp));
        secondarySeries.setCategoryBinding(categoryBinding);
        secondarySeries.setValueBinding(valueBinding);
        secondarySeries.setData(ExampleDataProvider.lineDataSecondary());

        CartesianChartGrid grid = new CartesianChartGrid(activity);
        grid.setStripLinesVisibility(GridLineVisibility.Y);

        chart.setGrid(grid);
        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(hAxis);
        chart.getSeries().add(series);
        chart.getSeries().add(secondarySeries);
    }
}
