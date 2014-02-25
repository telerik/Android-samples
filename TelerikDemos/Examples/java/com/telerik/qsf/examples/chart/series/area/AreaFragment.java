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
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AreaFragment extends Fragment {

    private RadCartesianChartView chart;

    public AreaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_area, container, false);
        this.chart = (RadCartesianChartView) result.findViewById(R.id.chart);
        this.prepareChart(getActivity().getBaseContext());

        return result;
    }

    private void prepareChart(Context context) {
        AreaSeries areaSeries = new AreaSeries(context);


        CategoricalAxis horizontal = new CategoricalAxis(context);
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis(context);

        this.chart.setHorizontalAxis(horizontal);
        this.chart.setVerticalAxis(vertical);
        this.chart.getSeries().add(areaSeries);

        areaSeries.setCategoryBinding(new DataPointBinding() {
            @Override
            public Object getValue(Object instance) {
                return ((DataClass) instance).category;
            }
        });

        areaSeries.setValueBinding(new DataPointBinding() {
            @Override
            public Object getValue(Object instance) {
                return ((DataClass) instance).value;
            }
        });

        areaSeries.setData(ExampleDataProvider.areaData());
    }
}
