package com.telerik.examples.examples.chart.series.bar;

import com.telerik.examples.R;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

public class BarHorizontalFragment extends BarFragment {
    public BarHorizontalFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_bar_horizontal;
    }

    @Override
    protected void prepareBarChart() {
        BarSeries barSeries = new BarSeries();
        CategoricalAxis horizontal = new CategoricalAxis();
        LinearAxis vertical = new LinearAxis();
        vertical.setMajorStep(20);
        this.cartesianChart().setHorizontalAxis(vertical);
        this.cartesianChart().setVerticalAxis(horizontal);
        this.cartesianChart().getSeries().add(barSeries);

        barSeries.setCategoryBinding(new FieldNameDataPointBinding("category"));
        barSeries.setValueBinding(new FieldNameDataPointBinding("value"));

        barSeries.setData(ExampleDataProvider.barData());
    }
}
