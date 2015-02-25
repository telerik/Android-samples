package com.telerik.examples.examples.chart.series.bar;

import com.telerik.examples.R;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.RangeBarSeries;

public class BarRangeFragment extends BarFragment {
    public BarRangeFragment() {
    }

    protected int getLayoutID() {
        return R.layout.fragment_bar_range;
    }

    @Override
    protected void prepareBarChart() {
        RangeBarSeries barSeries = new RangeBarSeries();

        CategoricalAxis horizontal = new CategoricalAxis();
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis();

        this.cartesianChart().setHorizontalAxis(horizontal);
        this.cartesianChart().setVerticalAxis(vertical);
        this.cartesianChart().getSeries().add(barSeries);

        barSeries.setCategoryBinding(new FieldNameDataPointBinding("category"));
        barSeries.setHighBinding(new FieldNameDataPointBinding("value"));
        barSeries.setLowBinding(new FieldNameDataPointBinding("value2"));

        barSeries.setData(ExampleDataProvider.barData());
    }
}
