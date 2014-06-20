package com.telerik.examples.examples.chart.series.pie;

import android.content.res.Resources;

import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.examples.chart.ChartSelectionFragment;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.dataPoints.DataPoint;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.common.ChartSeries;
import com.telerik.widget.chart.visualization.pieChart.DoughnutSeries;
import com.telerik.widget.chart.visualization.pieChart.PieSeriesLabelRenderer;
import com.telerik.widget.chart.visualization.pieChart.RadPieChartView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class DoughnutSeriesSecondFragment extends PieFragment {

    class CustomLabelRenderer extends PieSeriesLabelRenderer {
        CustomLabelRenderer(ChartSeries owner) {
            super(owner);
        }

        @Override
        protected String getLabelText(DataPoint dataPoint) {
            String defaultLabel = super.getLabelText(dataPoint);
            DataClass dataItem = (DataClass) dataPoint.getDataItem();
            return dataItem.category + " " + defaultLabel;
        }
    }

    public DoughnutSeriesSecondFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_doughnut_series_second;
    }

    @Override
    protected void preparePieChart() {
        DoughnutSeries doughnutSeries = new DoughnutSeries(this.getActivity().getBaseContext());
        Resources res = this.getResources();
        doughnutSeries.setSliceOffset(res.getDimension(R.dimen.twodp));
        doughnutSeries.setShowLabels(true);
        doughnutSeries.setValueBinding(new FieldNameDataPointBinding("value"));

        CustomLabelRenderer renderer = new CustomLabelRenderer(doughnutSeries);
        doughnutSeries.setLabelRenderer(renderer);
        doughnutSeries.setData(ExampleDataProvider.pieDataAdditional());

        RadPieChartView pieChart = (RadPieChartView)this.chart;
        pieChart.getSeries().add(doughnutSeries);
    }
}
