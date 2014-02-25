package com.telerik.qsf.examples.chart.series.area;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.qsf.R;
import com.telerik.qsf.common.DataClass;
import com.telerik.qsf.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class StackedAreaFragment extends Fragment {

    private RadCartesianChartView chart;

    public StackedAreaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_stacked_area, container, false);
        this.chart = (RadCartesianChartView) result.findViewById(R.id.chart);
        this.prepareChart(this.getActivity().getBaseContext());

        return result;
    }

    private void prepareChart(Context context) {
        CategoricalAxis horizontal = new CategoricalAxis(context);
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis(context);

        this.chart.setHorizontalAxis(horizontal);
        this.chart.setVerticalAxis(vertical);

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
        areaSeries.setCombineMode(ChartSeriesCombineMode.STACK);
        areaSeries.setData(ExampleDataProvider.areaData());

        AreaSeries secondaryAreaSeries = new AreaSeries(context);
        secondaryAreaSeries.setCategoryBinding(categoryBinding);
        secondaryAreaSeries.setValueBinding(valueBinding);
        secondaryAreaSeries.setCombineMode(ChartSeriesCombineMode.STACK);
        secondaryAreaSeries.setData(ExampleDataProvider.areaDataSecondary());

        this.chart.getSeries().add(areaSeries);
        this.chart.getSeries().add(secondaryAreaSeries);
    }
}
