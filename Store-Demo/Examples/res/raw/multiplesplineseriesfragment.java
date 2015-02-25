package com.telerik.examples.examples.chart.series.line;

import android.content.res.Resources;
import android.util.TypedValue;

import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.examples.chart.series.area.AreaFragment;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SplineSeries;

public class MultipleSplineSeriesFragment extends AreaFragment {
    private static final float VERTICAL_AXIS_MULTIPLE_MAX = 200;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_spline_multi;
    }

    @Override
    protected void prepareAreaChart() {
        LinearAxis vAxis = new LinearAxis();
        vAxis.setMaximum(VERTICAL_AXIS_MULTIPLE_MAX);

        CategoricalAxis hAxis = new CategoricalAxis();
        hAxis.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        DataPointBinding categoryBinding = new FieldNameDataPointBinding("category");
        DataPointBinding valueBinding = new FieldNameDataPointBinding("value");

        SplineSeries series = new SplineSeries();
        Resources res = this.getResources();
        series.setStrokeThickness(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 2));
        series.setCategoryBinding(categoryBinding);
        series.setValueBinding(valueBinding);
        series.setData(ExampleDataProvider.lineData());

        SplineSeries secondarySeries = new SplineSeries();
        res = this.getResources();
        secondarySeries.setStrokeThickness(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 2));
        secondarySeries.setCategoryBinding(categoryBinding);
        secondarySeries.setValueBinding(valueBinding);
        secondarySeries.setData(ExampleDataProvider.lineDataSecondary());

        CartesianChartGrid grid = new CartesianChartGrid();
        grid.setStripLinesVisibility(GridLineVisibility.Y);

        cartesianChart.setGrid(grid);
        cartesianChart.setVerticalAxis(vAxis);
        cartesianChart.setHorizontalAxis(hAxis);
        cartesianChart.getSeries().add(series);
        cartesianChart.getSeries().add(secondarySeries);
    }
}
