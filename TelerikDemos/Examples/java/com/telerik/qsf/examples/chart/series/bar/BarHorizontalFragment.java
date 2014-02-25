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
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

public class BarHorizontalFragment extends Fragment {

    private RadCartesianChartView chart;
    private Context context;

    public BarHorizontalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_bar_horizontal, container, false);
        this.context = this.getActivity();
        this.chart = (RadCartesianChartView) result.findViewById(R.id.chart);
        this.prepareChart();

        return result;
    }

    private void prepareChart() {
        BarSeries barSeries = new BarSeries(context);
        CategoricalAxis horizontal = new CategoricalAxis(context);
        LinearAxis vertical = new LinearAxis(context);
        vertical.setMajorStep(20);
        this.chart.setHorizontalAxis(vertical);
        this.chart.setVerticalAxis(horizontal);
        this.chart.getSeries().add(barSeries);

        barSeries.setCategoryBinding(new GenericDataPointBinding<DataClass, String>(new Function<DataClass, String>() {
            @Override
            public String apply(DataClass argument) {
                return argument.category;
            }
        }));
        barSeries.setValueBinding(new GenericDataPointBinding<DataClass, Double>(new Function<DataClass, Double>() {
            @Override
            public Double apply(DataClass argument) {
                return argument.value;
            }
        }));

        barSeries.setData(ExampleDataProvider.barData());
    }
}
