package com.telerik.examples.examples.chart.series.pie;

import android.util.TypedValue;

import com.telerik.android.common.Util;
import com.telerik.examples.examples.chart.ChartSelectionFragment;
import com.telerik.widget.chart.visualization.pieChart.RadPieChartView;

public abstract class PieFragment extends ChartSelectionFragment {
    protected RadPieChartView pieChart;

    @Override
    protected void prepareChart() {
        super.prepareChart();
        this.pieChart = (RadPieChartView)chart;
        float padding = Util.getDimen(this.context, TypedValue.COMPLEX_UNIT_DIP, 20);
        this.pieChart.setChartPadding(padding);
        this.preparePieChart();
    }

    protected abstract void preparePieChart();
}
