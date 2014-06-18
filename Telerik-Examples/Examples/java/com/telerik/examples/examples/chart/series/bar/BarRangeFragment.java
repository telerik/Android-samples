package com.telerik.examples.examples.chart.series.bar;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
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
        RangeBarSeries barSeries = new RangeBarSeries(context);

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

        barSeries.setHighBinding(new GenericDataPointBinding<DataClass, Float>(new Function<DataClass, Float>() {
            @Override
            public Float apply(DataClass argument) {
                return argument.value;
            }
        }));

        barSeries.setLowBinding(new GenericDataPointBinding<DataClass, Float>(new Function<DataClass, Float>() {
            @Override
            public Float apply(DataClass argument) {
                return argument.value2;
            }
        }));

        barSeries.setData(ExampleDataProvider.barData());
    }
}
