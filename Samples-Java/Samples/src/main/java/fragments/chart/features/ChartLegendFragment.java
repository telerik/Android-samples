package fragments.chart.features;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.chart.engine.dataPoints.DataPoint;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;
import com.telerik.widget.chart.visualization.common.ChartSeries;
import com.telerik.widget.primitives.legend.LegendSelectionListener;
import com.telerik.widget.primitives.legend.RadLegendView;

import java.util.ArrayList;
import java.util.Random;

import activities.ExampleFragment;

// >> chart-legend-interface
public class ChartLegendFragment extends Fragment implements ExampleFragment, LegendSelectionListener {
// << chart-legend-interface

    private RadCartesianChartView chartView;
    private RadLegendView legendView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart_legend, container, false);
        this.chartView = (RadCartesianChartView) rootView.findViewById(R.id.chartView);
        this.legendView = (RadLegendView) rootView.findViewById(R.id.legendView);

        this.prepareChart();

        this.legendView.setLegendProvider(this.chartView);

        // >> chart-legend-selection-add
        this.legendView.addLegendItemSelectedListener(this);
        // << chart-legend-selection-add

        return rootView;
    }

    private void prepareChart() {
        CategoricalAxis horizontalAxis = new CategoricalAxis();
        this.chartView.setHorizontalAxis(horizontalAxis);

        LinearAxis verticalAxis = new LinearAxis();
        verticalAxis.setLabelFormat("%.0f");
        this.chartView.setVerticalAxis(verticalAxis);

        for (int i = 0; i < 5; i++){
            BarSeries series = new BarSeries();
            series.setLegendTitle("Series " + (i + 1));
            series.setCategoryBinding(new FieldNameDataPointBinding("category"));
            series.setValueBinding(new FieldNameDataPointBinding("value"));
            series.setData(this.getData());
            this.chartView.getSeries().add(series);
        }
    }

    private ArrayList<DataEntity> getData() {
        Random numberGenerator = new Random();
        ArrayList<DataEntity> result = new ArrayList<>(8);

        for (int i = 0; i < 8; i++) {
            DataEntity entity = new DataEntity();
            entity.value = numberGenerator.nextInt(10) + 1;
            entity.category = "Item " + i;
            result.add(entity);
        }

        return result;
    }

    @Override
    public String title() {
        return "Chart legend";
    }

    // >> chart-legend-selection
    @Override
    public void onLegendItemSelected(Object item) {
        if(item instanceof DataPoint) { // The selected item is a data point for the pie and doughnut chart.
            DataPoint point = (DataPoint)item;
            point.setIsSelected(!point.getIsSelected());
        } else if (item instanceof ChartSeries) { // The selected item is a series for the cartesian chart.
            ChartSeries series = (ChartSeries)item;
            series.setIsSelected(!series.getIsSelected());
        }
    }
    // << chart-legend-selection

    public class DataEntity {
        public String category;
        public int value;
    }
}
