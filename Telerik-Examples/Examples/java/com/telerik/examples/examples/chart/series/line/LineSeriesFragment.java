package com.telerik.examples.examples.chart.series.line;

import android.util.TypedValue;

import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.examples.chart.series.area.AreaFragment;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SphericalDataPointIndicatorRenderer;

public class LineSeriesFragment extends AreaFragment {
    private static final float VERTICAL_AXIS_SINGLE_STEP = 5;
    private static final float VERTICAL_AXIS_SINGLE_MIN = 60;
    private static final float VERTICAL_AXIS_SINGLE_MAX = 90;

    protected int getLayoutID() {
        return R.layout.fragment_line;
    }

    @Override
    protected void prepareAreaChart() {
        LinearAxis vAxis = new LinearAxis();
        vAxis.setMinimum(VERTICAL_AXIS_SINGLE_MIN);
        vAxis.setMaximum(VERTICAL_AXIS_SINGLE_MAX);
        vAxis.setMajorStep(VERTICAL_AXIS_SINGLE_STEP);

        CategoricalAxis hAxis = new CategoricalAxis();
        hAxis.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LineSeries series = new LineSeries();
        series.setStrokeThickness(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 2));
        series.setCategoryBinding(new FieldNameDataPointBinding("category"));
        series.setValueBinding(new FieldNameDataPointBinding("value"));
        series.setData(ExampleDataProvider.lineData());
        series.setDataPointIndicatorRenderer(new SphericalDataPointIndicatorRenderer(series));
        cartesianChart.setVerticalAxis(vAxis);
        cartesianChart.setHorizontalAxis(hAxis);
        cartesianChart.getSeries().add(series);
    }
}
