package com.telerik.examples.examples.chart.series.bar;

import com.telerik.examples.R;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

public class BarHorizontalStack100Fragment extends BarFragment {
    public BarHorizontalStack100Fragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_bar_horizontal_stack100;
    }

    @Override
    protected void prepareBarChart() {
        CategoricalAxis horizontal = new CategoricalAxis();
        LinearAxis vertical = new LinearAxis();
        vertical.setMajorStep(.20);

        this.cartesianChart().setHorizontalAxis(vertical);
        this.cartesianChart().setVerticalAxis(horizontal);

        DataPointBinding categoryBinding = new FieldNameDataPointBinding("category");
        DataPointBinding valueBinding = new FieldNameDataPointBinding("value");

        BarSeries barSeries = new BarSeries();
        barSeries.setCategoryBinding(categoryBinding);
        barSeries.setValueBinding(valueBinding);
        barSeries.setData(ExampleDataProvider.barData());
        barSeries.setCombineMode(ChartSeriesCombineMode.STACK_100);

        BarSeries secondaryBarSeries = new BarSeries();
        secondaryBarSeries.setCategoryBinding(categoryBinding);
        secondaryBarSeries.setValueBinding(valueBinding);
        secondaryBarSeries.setData(ExampleDataProvider.barDataSecondary());
        secondaryBarSeries.setCombineMode(ChartSeriesCombineMode.STACK_100);

        this.cartesianChart().getSeries().add(barSeries);
        this.cartesianChart().getSeries().add(secondaryBarSeries);
    }
}
