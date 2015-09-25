package com.telerik.examples.examples.chart.series.bubble;

import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.examples.chart.series.scatter.ScatterFragment;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BubbleSeries;

public class CategoricalBubbleFragment extends ScatterFragment {
    private Integer category = 1;

    public CategoricalBubbleFragment() {
        dataLength = 10;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_bubble;
    }

    @Override
    protected void setupAxes() {
        super.setupAxes();

        LinearAxis verticalAxis = (LinearAxis) this.cartesianChart.getVerticalAxis();
        verticalAxis.setLabelFormat("%s%%");
        verticalAxis.setMajorStep(10);
        verticalAxis.setMinimum(0);
        verticalAxis.setMaximum(100);

        CategoricalAxis horizontalAxis = new CategoricalAxis();
        cartesianChart.setHorizontalAxis(horizontalAxis);
    }

    @Override
    protected void createSeries() {
        BubbleSeries series = new BubbleSeries();
        series.setCategoryBinding(new FieldNameDataPointBinding("category"));
        series.setValueBinding(new FieldNameDataPointBinding("value2"));
        series.setBubbleSizeBinding(new FieldNameDataPointBinding("value3"));
        series.setData(this.generateData());
        series.setLegendTitle("Equities");
        this.cartesianChart.getSeries().add(series);

        series = new BubbleSeries();
        series.setCategoryBinding(new FieldNameDataPointBinding("category"));
        series.setValueBinding(new FieldNameDataPointBinding("value2"));
        series.setBubbleSizeBinding(new FieldNameDataPointBinding("value3"));
        series.setData(this.generateData());
        series.setLegendTitle("Mutual Funds");
        this.cartesianChart.getSeries().add(series);
    }

    protected void initDataObject(DataClass dataObject) {
        dataObject.category = category.toString();
        category += 1;
        dataObject.value2 = (float) this.random.nextInt(100);
        dataObject.value3 = (float) this.random.nextInt(15000);
    }
}
