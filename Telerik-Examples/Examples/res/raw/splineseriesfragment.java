package com.telerik.examples.examples.chart.series.line;

import android.content.Context;
import android.content.res.Resources;

import com.telerik.examples.R;
import com.telerik.examples.examples.chart.series.area.AreaFragment;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SplineSeries;

public class SplineSeriesFragment extends AreaFragment {
    private static final float VERTICAL_AXIS_SINGLE_STEP = 5;
    private static final float VERTICAL_AXIS_SINGLE_MIN = 60;
    private static final float VERTICAL_AXIS_SINGLE_MAX = 90;

    protected int getLayoutID() {
        return R.layout.fragment_spline;
    }

    @Override
    protected void prepareAreaChart() {
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

        cartesianChart.setGrid(grid);
        cartesianChart.setVerticalAxis(vAxis);
        cartesianChart.setHorizontalAxis(hAxis);
        cartesianChart.getSeries().add(series);
    }
}
