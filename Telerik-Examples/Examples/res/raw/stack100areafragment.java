package com.telerik.examples.examples.chart.series.area;

import android.content.Context;

import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class Stack100AreaFragment extends AreaFragment {
    public Stack100AreaFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_stack100_area;
    }

    @Override
    protected void prepareAreaChart() {
        CategoricalAxis horizontal = new CategoricalAxis(context);
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis(context);

        this.cartesianChart.setHorizontalAxis(horizontal);
        this.cartesianChart.setVerticalAxis(vertical);

        DataPointBinding categoryBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((DataClass) instance).category;
            }
        };

        DataPointBinding valueBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((DataClass) instance).value;
            }
        };

        AreaSeries areaSeries = new AreaSeries(context);
        areaSeries.setCategoryBinding(categoryBinding);
        areaSeries.setValueBinding(valueBinding);
        areaSeries.setCombineMode(ChartSeriesCombineMode.STACK_100);
        areaSeries.setData(ExampleDataProvider.areaData());

        AreaSeries secondaryAreaSeries = new AreaSeries(context);
        secondaryAreaSeries.setCategoryBinding(categoryBinding);
        secondaryAreaSeries.setValueBinding(valueBinding);
        secondaryAreaSeries.setCombineMode(ChartSeriesCombineMode.STACK_100);
        secondaryAreaSeries.setData(ExampleDataProvider.areaDataSecondary());

        this.cartesianChart.getSeries().add(areaSeries);
        this.cartesianChart.getSeries().add(secondaryAreaSeries);
    }
}
