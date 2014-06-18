package com.telerik.examples.examples.chart.series.financial;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.examples.chart.ChartSelectionAndTooltipFragment;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.MajorTickModel;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.axes.common.DateTimeComponent;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionBehavior;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeCategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.CandlestickSeries;

import java.text.SimpleDateFormat;

public class CandlestickSeriesFragment extends FinancialSeriesFragment {

    public CandlestickSeriesFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_candlestick;
    }

    @Override
    protected void prepareChart() {
        super.prepareChart();

        DateTimeCategoricalAxis hAxis = new DateTimeCategoricalAxis(context);
        hAxis.setDateTimeFormat(new SimpleDateFormat("MM/dd"));
        hAxis.setDateTimeComponent(DateTimeComponent.DATE);
        hAxis.setLabelFitMode(AxisLabelFitMode.ROTATE);

        LinearAxis vAxis = new LinearAxis(context);
        vAxis.setMinimum(10400);
        vAxis.getLabelRenderer().setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                return String.format("%sK", (((MajorTickModel) argument).value() / 1000));
            }
        });

        CandlestickSeries series = new CandlestickSeries(context);
        series.setCategoryBinding(new FieldNameDataPointBinding("date"));
        series.setHighBinding(new FieldNameDataPointBinding("high")); // h
        series.setLowBinding(new FieldNameDataPointBinding("low")); // l
        series.setOpenBinding(new FieldNameDataPointBinding("open")); // o
        series.setCloseBinding(new FieldNameDataPointBinding("close")); // c
        series.setData(ExampleDataProvider.ohlcData(getResources()));

        RadCartesianChartView chart = (RadCartesianChartView)this.chart;
        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(hAxis);
        chart.getSeries().add(series);
    }
}
