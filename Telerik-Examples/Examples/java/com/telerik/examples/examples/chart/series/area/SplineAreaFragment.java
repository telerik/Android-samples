package com.telerik.examples.examples.chart.series.area;

import com.telerik.examples.R;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SplineAreaSeries;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class SplineAreaFragment extends AreaFragment {
    public SplineAreaFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_spline_area;
    }

    @Override
    protected void prepareAreaChart() {
        SplineAreaSeries areaSeries = new SplineAreaSeries();

        CategoricalAxis horizontal = new CategoricalAxis();
        horizontal.setLabelFitMode(AxisLabelFitMode.MULTI_LINE);

        LinearAxis vertical = new LinearAxis();

        this.cartesianChart.setHorizontalAxis(horizontal);
        this.cartesianChart.setVerticalAxis(vertical);
        this.cartesianChart.getSeries().add(areaSeries);

        areaSeries.setCategoryBinding(new FieldNameDataPointBinding("category"));
        areaSeries.setValueBinding(new FieldNameDataPointBinding("value"));
        areaSeries.setData(ExampleDataProvider.areaData());
    }
}
