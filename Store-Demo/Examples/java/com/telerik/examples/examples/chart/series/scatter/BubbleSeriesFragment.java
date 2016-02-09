package com.telerik.examples.examples.chart.series.scatter;

import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.scatter.ScatterBubbleSeries;

public class BubbleSeriesFragment extends ScatterFragment {
    public BubbleSeriesFragment() {
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

        LinearAxis horizontalAxis = (LinearAxis) this.cartesianChart.getHorizontalAxis();
        horizontalAxis.setMinimum(1);
        horizontalAxis.setMaximum(10);
    }

    @Override
    protected void createSeries() {
        ScatterBubbleSeries series = new ScatterBubbleSeries();
        series.setXValueBinding(new FieldNameDataPointBinding("value"));
        series.setYValueBinding(new FieldNameDataPointBinding("value2"));
        series.setBubbleSizeBinding(new FieldNameDataPointBinding("value3"));
        series.setData(this.generateData());
        series.setLegendTitle("Equities");
        this.cartesianChart.getSeries().add(series);

        series = new ScatterBubbleSeries();
        series.setXValueBinding(new FieldNameDataPointBinding("value"));
        series.setYValueBinding(new FieldNameDataPointBinding("value2"));
        series.setBubbleSizeBinding(new FieldNameDataPointBinding("value3"));
        series.setData(this.generateData());
        series.setLegendTitle("Mutual Funds");
        this.cartesianChart.getSeries().add(series);
    }

    protected void initDataObject(DataClass dataObject) {
        dataObject.value = 1.0f + (float) this.random.nextInt(9);
        dataObject.value2 = (float) this.random.nextInt(100);
        dataObject.value3 = (float) this.random.nextInt(15000);
    }
}
