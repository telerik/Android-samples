package com.telerik.qsf.examples.chart.series.pie;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.qsf.R;
import com.telerik.qsf.common.DataClass;
import com.telerik.qsf.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.dataPoints.PieDataPoint;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.visualization.pieChart.PieSeries;
import com.telerik.widget.chart.visualization.pieChart.PieSeriesLabelRenderer;
import com.telerik.widget.chart.visualization.pieChart.RadPieChartView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PieSeriesSecondFragment extends Fragment {

    class CustomLabelRenderer extends PieSeriesLabelRenderer {
        CustomLabelRenderer(PieSeries owner) {
            super(owner);
        }

        @Override
        protected String getDefaultLabel(PieDataPoint point) {
            String defaultLabel = super.getDefaultLabel(point);
            DataClass dataItem = (DataClass) point.getDataItem();
            return dataItem.category + " " + defaultLabel;
        }
    }

    private RadPieChartView pieChart;

    public PieSeriesSecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_pie_series_second, container, false);
        this.pieChart = (RadPieChartView) result.findViewById(R.id.chart);
        this.preparePieChart();

        return result;
    }

    private void preparePieChart() {
        PieSeries pieSeries = new PieSeries(this.getActivity().getBaseContext());

        Resources res = this.getResources();
        pieSeries.setSliceOffset(res.getDimension(R.dimen.twodp));
        pieSeries.setShowLabels(true);

        pieSeries.setValueBinding(new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((DataClass) instance).value;
            }
        });

        CustomLabelRenderer renderer = new CustomLabelRenderer(pieSeries);
        pieSeries.setLabelRenderer(renderer);

        pieSeries.setData(ExampleDataProvider.pieDataAdditional());

        this.pieChart.getSeries().add(pieSeries);
    }
}
