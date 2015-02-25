package com.telerik.examples.examples.chart.series.line;

import android.content.res.Resources;
import android.util.TypedValue;

import com.telerik.android.common.Util;
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
        LinearAxis vAxis = new LinearAxis();
        vAxis.setMinimum(VERTICAL_AXIS_SINGLE_MIN);
        vAxis.setMaximum(VERTICAL_AXIS_SINGLE_MAX);
        vAxis.setMajorStep(VERTICAL_AXIS_SINGLE_STEP);

        CategoricalAxis hAxis = new CategoricalAxis();
        hAxis.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        SplineSeries series = new SplineSeries();
        Resources res = this.getResources();
        series.setStrokeThickness(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 2));
        series.setCategoryBinding(new FieldNameDataPointBinding("category"));
        series.setValueBinding(new FieldNameDataPointBinding("value"));
        series.setData(ExampleDataProvider.lineData());

        CartesianChartGrid grid = new CartesianChartGrid();
        grid.setStripLinesVisibility(GridLineVisibility.Y);

        cartesianChart.setGrid(grid);
        cartesianChart.setVerticalAxis(vAxis);
        cartesianChart.setHorizontalAxis(hAxis);
        cartesianChart.getSeries().add(series);
    }
}
