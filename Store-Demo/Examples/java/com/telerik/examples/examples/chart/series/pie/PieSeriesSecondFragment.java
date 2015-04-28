package com.telerik.examples.examples.chart.series.pie;

import android.util.TypedValue;

import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.dataPoints.DataPoint;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.common.ChartSeries;
import com.telerik.widget.chart.visualization.pieChart.PieSeries;
import com.telerik.widget.chart.visualization.pieChart.PieSeriesLabelRenderer;
import com.telerik.widget.chart.visualization.pieChart.RadPieChartView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PieSeriesSecondFragment extends PieFragment {

    class CustomLabelRenderer extends PieSeriesLabelRenderer {
        CustomLabelRenderer(PieSeries owner) {
            super(owner);
        }

        @Override
        protected String getLabelText(DataPoint dataPoint) {
            String defaultLabel = super.getLabelText(dataPoint);
            DataClass dataItem = (DataClass) dataPoint.getDataItem();
            return dataItem.category + " " + defaultLabel;
        }
    }

    public PieSeriesSecondFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_pie_series_second;
    }

    @Override
    protected void preparePieChart() {
        PieSeries pieSeries = new PieSeries();
        pieSeries.setSliceOffset(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 2));
        pieSeries.setShowLabels(true);

        pieSeries.setValueBinding(new FieldNameDataPointBinding("value"));

        CustomLabelRenderer renderer = new CustomLabelRenderer(pieSeries);
        pieSeries.setLabelRenderer(renderer);

        pieSeries.setData(ExampleDataProvider.pieDataAdditional());

        RadPieChartView pieChart = (RadPieChartView) this.chart;
        pieChart.getSeries().add(pieSeries);
    }
}
