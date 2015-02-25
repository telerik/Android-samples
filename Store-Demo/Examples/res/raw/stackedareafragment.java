package com.telerik.examples.examples.chart.series.area;

import com.telerik.examples.R;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;
import com.telerik.widget.chart.visualization.common.ChartSeries;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class StackedAreaFragment extends AreaFragment {
    public StackedAreaFragment() {
        // Required empty public constructor
    }

    protected int getLayoutID() {
        return R.layout.fragment_stacked_area;
    }

    @Override
    protected boolean canSelectSeries(ChartSeries series) {
        AreaSeries areaSeries = (AreaSeries) series;
        if (areaSeries != null) {
            return areaSeries.getCollectionIndex() == areaSeries.getChart().getChartArea().getSeries().size() - 1;
        }
        return true;
    }

    @Override
    protected void prepareAreaChart() {
        CategoricalAxis horizontal = new CategoricalAxis();
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis();

        this.cartesianChart.setHorizontalAxis(horizontal);
        this.cartesianChart.setVerticalAxis(vertical);

        DataPointBinding categoryBinding = new FieldNameDataPointBinding("category");
        DataPointBinding valueBinding = new FieldNameDataPointBinding("value");

        AreaSeries areaSeries = new AreaSeries();
        areaSeries.setCategoryBinding(categoryBinding);
        areaSeries.setValueBinding(valueBinding);
        areaSeries.setCombineMode(ChartSeriesCombineMode.STACK);
        areaSeries.setData(ExampleDataProvider.areaData());

        AreaSeries secondaryAreaSeries = new AreaSeries();
        secondaryAreaSeries.setCategoryBinding(categoryBinding);
        secondaryAreaSeries.setValueBinding(valueBinding);
        secondaryAreaSeries.setCombineMode(ChartSeriesCombineMode.STACK);
        secondaryAreaSeries.setData(ExampleDataProvider.areaDataSecondary());

        this.cartesianChart.getSeries().add(areaSeries);
        this.cartesianChart.getSeries().add(secondaryAreaSeries);
    }
}
