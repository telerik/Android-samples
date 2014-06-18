package com.telerik.examples.examples.chart.series.bar;

import android.content.Context;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
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
        BarSeries barSeries = new BarSeries(context);
        CategoricalAxis horizontal = new CategoricalAxis(context);
        LinearAxis vertical = new LinearAxis(context);
        vertical.setMajorStep(20);
        this.cartesianChart().setHorizontalAxis(vertical);
        this.cartesianChart().setVerticalAxis(horizontal);
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
