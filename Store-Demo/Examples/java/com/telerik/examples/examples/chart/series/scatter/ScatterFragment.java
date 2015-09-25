package com.telerik.examples.examples.chart.series.scatter;

import android.util.TypedValue;

import com.telerik.android.common.Function;
import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.examples.chart.ChartSelectionAndTooltipFragment;
import com.telerik.widget.chart.engine.axes.MajorTickModel;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.scatter.ScatterPointSeries;
import com.telerik.widget.primitives.legend.RadLegendView;

import java.util.ArrayList;
import java.util.Random;

public class ScatterFragment extends ChartSelectionAndTooltipFragment {
    protected RadCartesianChartView cartesianChart;
    protected Random random = new Random();
    protected int dataLength = 20;

    public ScatterFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_scatter;
    }

    @Override
    protected void prepareChart() {
        super.prepareChart();

        this.cartesianChart = (RadCartesianChartView) this.chart;
        RadLegendView legend = Util.getLayoutPart(this.rootView, R.id.scatterLegendView, RadLegendView.class);
        legend.setLegendProvider(this.cartesianChart);
        this.setupAxes();

        CartesianChartGrid grid = this.cartesianChart.getGrid();
        grid.setMajorLinesVisibility(GridLineVisibility.XY);
        grid.setStripLinesVisibility(GridLineVisibility.NONE);
    }

    protected void setupAxes() {
        Function<Object, String> converter = new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                MajorTickModel tick = (MajorTickModel) argument;
                return Integer.toString((int) tick.value());
            }
        };

        LinearAxis horizontalAxis = new LinearAxis();
        horizontalAxis.setMaximum(70);
        horizontalAxis.setMinimum(10);
        horizontalAxis.setLabelValueToStringConverter(converter);
        horizontalAxis.setLabelMargin(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 5));

        LinearAxis verticalAxis = new LinearAxis();
        verticalAxis.setMajorStep(2);
        verticalAxis.setMaximum(20);
        verticalAxis.setMinimum(2);
        verticalAxis.setLabelValueToStringConverter(converter);
        verticalAxis.setLabelMargin(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 5));

        this.cartesianChart.setHorizontalAxis(horizontalAxis);
        this.cartesianChart.setVerticalAxis(verticalAxis);

        this.createSeries();
    }

    protected void createSeries() {
        ScatterPointSeries series = new ScatterPointSeries();
        series.setXValueBinding(new FieldNameDataPointBinding("value"));
        series.setYValueBinding(new FieldNameDataPointBinding("value2"));
        series.setPointSize(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 5));
        ArrayList<DataClass> data = this.generateData();
        series.setLegendTitle("Public Sector");
        series.setData(data);
        this.cartesianChart.getSeries().add(series);

        series = new ScatterPointSeries();
        series.setXValueBinding(new FieldNameDataPointBinding("value"));
        series.setYValueBinding(new FieldNameDataPointBinding("value2"));
        series.setPointSize(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 5));
        data = this.generateData();
        series.setLegendTitle("Private Sector");
        series.setData(data);
        this.cartesianChart.getSeries().add(series);
    }

    protected ArrayList<DataClass> generateData() {
        ArrayList<DataClass> result = new ArrayList<DataClass>();
        for (int i = 0; i < dataLength; ++i) {
            DataClass dataObject = new DataClass();
            this.initDataObject(dataObject);
            result.add(dataObject);
        }

        return result;
    }

    protected void initDataObject(DataClass dataObject) {
        dataObject.value2 = 2.0f + (float) this.random.nextInt(18);
        dataObject.value = 10.0f + (float) this.random.nextInt(60);
    }
}
