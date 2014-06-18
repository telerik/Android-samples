package com.telerik.examples.examples.chart.series.area;

import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SplineAreaSeries;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class StackedSplineAreaFragment extends AreaFragment {
    public StackedSplineAreaFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_stacked_spline_area;
    }

    @Override
    protected void prepareAreaChart() {
        CategoricalAxis horizontal = new CategoricalAxis(context);
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis(this.getActivity().getBaseContext());

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

        SplineAreaSeries areaSeries = new SplineAreaSeries(context);
        areaSeries.setCategoryBinding(categoryBinding);
        areaSeries.setValueBinding(valueBinding);
        areaSeries.setCombineMode(ChartSeriesCombineMode.STACK);
        areaSeries.setData(ExampleDataProvider.areaData());

        SplineAreaSeries secondaryAreaSeries = new SplineAreaSeries(context);
        secondaryAreaSeries.setCategoryBinding(categoryBinding);
        secondaryAreaSeries.setValueBinding(valueBinding);
        secondaryAreaSeries.setCombineMode(ChartSeriesCombineMode.STACK);
        secondaryAreaSeries.setData(ExampleDataProvider.areaDataSecondary());

        this.cartesianChart.getSeries().add(areaSeries);
        this.cartesianChart.getSeries().add(secondaryAreaSeries);
    }
}
