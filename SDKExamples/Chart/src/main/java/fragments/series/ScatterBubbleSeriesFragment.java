package fragments.series;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.telerik.android.sdk.R;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.scatter.ScatterBubbleSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.scatter.ScatterPointSeries;

import java.util.ArrayList;
import java.util.Random;

import activities.ExampleFragment;

/**
 * Created by ginev on 11/18/2014.
 */
public class ScatterBubbleSeriesFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout rootView = (FrameLayout)inflater.inflate(R.layout.fragment_scatter_bubble_series, container, false);
        rootView.addView(this.createChart());
        return rootView;
    }

    private RadCartesianChartView createChart(){
        //Create the Chart View
        RadCartesianChartView chart = new RadCartesianChartView(this.getActivity());

        //Create the bar series and attach axes and value bindings.
        ScatterBubbleSeries scatterBubbleSeries = new ScatterBubbleSeries();
        scatterBubbleSeries.setBubbleScale(1000);


        scatterBubbleSeries.setXValueBinding(new FieldNameDataPointBinding("value1"));
        scatterBubbleSeries.setYValueBinding(new FieldNameDataPointBinding("value2"));
        scatterBubbleSeries.setBubbleSizeBinding(new FieldNameDataPointBinding("bubbleArea"));

        LinearAxis verticalAxis = new LinearAxis();
        //The values in the linear axis will not have values after the decimal point.
        verticalAxis.setLabelFormat("%.2f");
        LinearAxis horizontalAxis = new LinearAxis();
        horizontalAxis.setLabelFormat("%.2f");
        scatterBubbleSeries.setVerticalAxis(verticalAxis);
        scatterBubbleSeries.setHorizontalAxis(horizontalAxis);

        //Bind series to data
        scatterBubbleSeries.setData(this.getData());

        //Add series to chart
        chart.getSeries().add(scatterBubbleSeries);
        return chart;
    }

    private ArrayList<ScatterDataEntity> getData(){
        Random numberGenerator = new Random();
        ArrayList<ScatterDataEntity> result = new ArrayList<ScatterDataEntity>(8);

        for (int i = 0; i < 20; i++){
            ScatterDataEntity entity = new ScatterDataEntity();
            entity.value1 = numberGenerator.nextDouble() * 10;
            entity.value2 = numberGenerator.nextDouble() * 10;
            entity.bubbleArea = numberGenerator.nextInt(5) + 1;
            result.add(entity);
        }

        return result;
    }

    @Override
    public String title() {
        return "Scatter bubble series";
    }

    public class ScatterDataEntity{
        public double value1;
        public double value2;
        public int bubbleArea;
    }
}
