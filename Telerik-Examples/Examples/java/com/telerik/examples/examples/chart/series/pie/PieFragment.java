package com.telerik.examples.examples.chart.series.pie;

import com.telerik.examples.R;
import com.telerik.examples.examples.chart.ChartSelectionFragment;
import com.telerik.widget.chart.visualization.pieChart.RadPieChartView;

public abstract class PieFragment extends ChartSelectionFragment {
    protected RadPieChartView pieChart;

    @Override
    protected void prepareChart() {
        super.prepareChart();
        this.pieChart = (RadPieChartView)chart;
        float padding = this.getResources().getDimension(R.dimen.pie_margin);
        this.pieChart.setChartPadding(padding);
        this.preparePieChart();
    }

    protected abstract void preparePieChart();
}
