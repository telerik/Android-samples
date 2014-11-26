package fragments.features;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.manual.tests.R;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;
import com.telerik.widget.primitives.legend.RadLegendView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ginev on 11/26/2014.
 */
public class ChartLegendFragment extends Fragment {

    private RadCartesianChartView chartView;
    private RadLegendView legendView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart_legend, container, false);
        this.chartView = (RadCartesianChartView) rootView.findViewById(R.id.chartView);
        this.legendView = (RadLegendView) rootView.findViewById(R.id.legendView);

        this.prepareChart();

        this.legendView.setLegendProvider(this.chartView);

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
        ArrayList<DataEntity> result = new ArrayList<DataEntity>(8);

        for (int i = 0; i < 8; i++) {
            DataEntity entity = new DataEntity();
            entity.value = numberGenerator.nextInt(10) + 1;
            entity.category = "Item " + i;
            result.add(entity);
        }

        return result;
    }

    public class DataEntity {
        public String category;
        public int value;
    }
}
