package com.telerik.examples.examples.chart.series.bar;

import com.telerik.examples.R;
import com.telerik.examples.examples.chart.ChartSelectionAndTooltipFragment;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.behaviors.ChartAnimationPanel;
import com.telerik.widget.chart.visualization.behaviors.animations.ChartFadeAnimation;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

public class BarFragment extends ChartSelectionAndTooltipFragment {
    public BarFragment() {
    }

    protected RadCartesianChartView cartesianChart() {
        return (RadCartesianChartView) this.chart;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_bar;
    }

    protected void prepareChart() {
        super.prepareChart();
        this.prepareBarChart();
    }

    protected void prepareBarChart() {
        BarSeries barSeries = new BarSeries();
        CategoricalAxis horizontal = new CategoricalAxis();
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis();

        this.cartesianChart().setHorizontalAxis(horizontal);
        this.cartesianChart().setVerticalAxis(vertical);
        this.cartesianChart().getSeries().add(barSeries);

        barSeries.setCategoryBinding(new FieldNameDataPointBinding("category"));
        barSeries.setValueBinding(new FieldNameDataPointBinding("value"));

        barSeries.setData(ExampleDataProvider.barData());
    }
}
