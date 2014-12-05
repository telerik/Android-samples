package fragments.axes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.telerik.android.sdk.R;
import com.telerik.widget.chart.engine.axes.common.AxisHorizontalLocation;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;

import java.util.ArrayList;
import java.util.Random;

import activities.ExampleFragment;
import fragments.series.PieSeriesFragment;

/**
 * Created by ginev on 11/20/2014.
 */
public class MultipleAxesFragment extends Fragment implements ExampleFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.fragment_datetime_continuous_axis, container, false);
        rootView.addView(this.createChart());
        return rootView;
    }

    private RadCartesianChartView createChart(){
        RadCartesianChartView chart = new RadCartesianChartView(this.getActivity());

        CategoricalAxis horizontalAxis = new CategoricalAxis();
        chart.setHorizontalAxis(horizontalAxis);

        LinearAxis vertical1 = new LinearAxis();
        vertical1.setLabelFormat("%.0f");

        LinearAxis vertical2 = new LinearAxis();
        vertical2.setLabelFormat("%.0f");

        BarSeries series1 = new BarSeries();

        ArrayList<DataEntity> data = this.getData();

        series1.setValueBinding(new FieldNameDataPointBinding("value1"));
        series1.setCategoryBinding(new FieldNameDataPointBinding("category"));
        series1.setVerticalAxis(vertical1);
        series1.setData(data);

        chart.getSeries().add(series1);

        LineSeries series2 = new LineSeries();

        series2.setValueBinding(new FieldNameDataPointBinding("value2"));
        series2.setCategoryBinding(new FieldNameDataPointBinding("category"));
        series2.setVerticalAxis(vertical2);
        vertical2.setHorizontalLocation(AxisHorizontalLocation.RIGHT);
        series2.setData(data);

        chart.getSeries().add(series2);

        return chart;
    }

    private ArrayList<DataEntity> getData(){
        Random numberGenerator = new Random();
        ArrayList<DataEntity> result = new ArrayList<DataEntity>(8);

        for (int i = 0; i < 8; i++){
            DataEntity entity = new DataEntity();
            entity.value1 = numberGenerator.nextInt(10) + 1;
            entity.value2 = numberGenerator.nextInt(10) + 1;
            entity.category = "Item " + i;
            result.add(entity);
        }

        return result;
    }

    @Override
    public String title() {
        return "Multiple Axes";
    }

    public class DataEntity{
        public String category;
        public int value1;
        public int value2;
    }
}
