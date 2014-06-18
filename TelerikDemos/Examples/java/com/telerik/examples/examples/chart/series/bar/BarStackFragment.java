package com.telerik.examples.examples.chart.series.bar;

import android.content.Context;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

public class BarStackFragment extends BarFragment {
    public BarStackFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_bar_stack;
    }

    @Override
    protected void prepareBarChart() {
        CategoricalAxis horizontal = new CategoricalAxis(context);
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis(context);

        this.cartesianChart().setHorizontalAxis(horizontal);
        this.cartesianChart().setVerticalAxis(vertical);

        DataPointBinding categoryBinding = new GenericDataPointBinding<DataClass, String>(new Function<DataClass, String>() {
            @Override
            public String apply(DataClass argument) {
                return argument.category;
            }
        });

        DataPointBinding valueBinding = new GenericDataPointBinding<DataClass, Float>(new Function<DataClass, Float>() {
            @Override
            public Float apply(DataClass argument) {
                return argument.value;
            }
        });

        BarSeries barSeries = new BarSeries(context);
        barSeries.setCategoryBinding(categoryBinding);
        barSeries.setValueBinding(valueBinding);
        barSeries.setData(ExampleDataProvider.barData());
        barSeries.setCombineMode(ChartSeriesCombineMode.STACK);

        BarSeries secondaryBarSeries = new BarSeries(context);
        secondaryBarSeries.setCategoryBinding(categoryBinding);
        secondaryBarSeries.setValueBinding(valueBinding);
        secondaryBarSeries.setData(ExampleDataProvider.barDataSecondary());
        secondaryBarSeries.setCombineMode(ChartSeriesCombineMode.STACK);

        this.cartesianChart().getSeries().add(barSeries);
        this.cartesianChart().getSeries().add(secondaryBarSeries);
    }
}
