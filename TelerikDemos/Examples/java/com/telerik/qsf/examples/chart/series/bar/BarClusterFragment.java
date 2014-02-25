package com.telerik.qsf.examples.chart.series.bar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.common.Function;
import com.telerik.qsf.R;
import com.telerik.qsf.common.DataClass;
import com.telerik.qsf.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

public class BarClusterFragment extends Fragment {

    private RadCartesianChartView chart;
    private Context context;

    public BarClusterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_bar_cluster, container, false);
        this.context = this.getActivity();
        this.chart = (RadCartesianChartView) result.findViewById(R.id.chart);
        this.prepareChart();

        return result;
    }

    private void prepareChart() {
        CategoricalAxis horizontal = new CategoricalAxis(context);
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis(context);

        this.chart.setHorizontalAxis(horizontal);
        this.chart.setVerticalAxis(vertical);

        DataPointBinding categoryBinding = new GenericDataPointBinding<DataClass, String>(new Function<DataClass, String>() {
            @Override
            public String apply(DataClass argument) {
                return argument.category;
            }
        });

        DataPointBinding valueBinding = new GenericDataPointBinding<DataClass, Double>(new Function<DataClass, Double>() {
            @Override
            public Double apply(DataClass argument) {
                return argument.value;
            }
        });

        BarSeries barSeries = new BarSeries(context);
        barSeries.setCategoryBinding(categoryBinding);
        barSeries.setValueBinding(valueBinding);
        barSeries.setData(ExampleDataProvider.barData());
        barSeries.setCombineMode(ChartSeriesCombineMode.CLUSTER);

        BarSeries secondaryBarSeries = new BarSeries(context);
        secondaryBarSeries.setCategoryBinding(categoryBinding);
        secondaryBarSeries.setValueBinding(valueBinding);
        secondaryBarSeries.setData(ExampleDataProvider.barDataSecondary());
        secondaryBarSeries.setCombineMode(ChartSeriesCombineMode.CLUSTER);

        this.chart.getSeries().add(barSeries);
        this.chart.getSeries().add(secondaryBarSeries);
    }
}
