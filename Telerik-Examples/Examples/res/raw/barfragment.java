package com.telerik.examples.examples.chart.series.bar;

import android.content.Context;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.examples.chart.ChartSelectionAndTooltipFragment;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

public class BarFragment extends ChartSelectionAndTooltipFragment {
    public BarFragment() {
    }

    protected RadCartesianChartView cartesianChart() {
        return (RadCartesianChartView)this.chart;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_bar;
    }

    protected void prepareChart() {
        super.prepareChart();
        this.prepareBarChart();
    }

    protected void prepareBarChart() {
        Context context = this.getActivity();

        BarSeries barSeries = new BarSeries(context);
        CategoricalAxis horizontal = new CategoricalAxis(context);
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis(context);

        this.cartesianChart().setHorizontalAxis(horizontal);
        this.cartesianChart().setVerticalAxis(vertical);
        this.cartesianChart().getSeries().add(barSeries);

        barSeries.setCategoryBinding(new GenericDataPointBinding<DataClass, String>(new Function<DataClass, String>() {
            @Override
            public String apply(DataClass argument) {
                return argument.category;
            }
        }));

        barSeries.setValueBinding(new GenericDataPointBinding<DataClass, Float>(new Function<DataClass, Float>() {
            @Override
            public Float apply(DataClass argument) {
                return argument.value;
            }
        }));

        barSeries.setData(ExampleDataProvider.barData());
    }
}
