package com.telerik.examples.examples.chart.series.area;

import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.examples.chart.ChartSelectionAndLabelsFragment;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionBehavior;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionMode;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SphericalDataPointIndicatorRenderer;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AreaFragment extends ChartSelectionAndLabelsFragment {
    protected RadCartesianChartView cartesianChart;

    public AreaFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_area;
    }

    @Override
    protected void prepareChart() {
        super.prepareChart();
        this.cartesianChart = (RadCartesianChartView)this.chart;
        this.prepareAreaChart();
    }

    protected void prepareAreaChart() {
        AreaSeries areaSeries = new AreaSeries(context);

        CategoricalAxis horizontal = new CategoricalAxis(context);
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis(context);

        this.cartesianChart.setHorizontalAxis(horizontal);
        this.cartesianChart.setVerticalAxis(vertical);
        this.cartesianChart.getSeries().add(areaSeries);

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
        areaSeries.setDataPointIndicatorRenderer(new SphericalDataPointIndicatorRenderer(areaSeries));
    }

    @Override
    protected void initSelectionBehavior(ChartSelectionBehavior behavior) {
        behavior.setDataPointsSelectionMode(ChartSelectionMode.NONE);
        behavior.setSeriesSelectionMode(ChartSelectionMode.SINGLE);
    }
}
