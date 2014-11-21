package fragments.annotations;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.telerik.manual.tests.R;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.annotations.cartesian.CartesianPlotBandAnnotation;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ginev on 11/21/2014.
 */
public class GridLineAnnotationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout rootView = (FrameLayout)inflater.inflate(R.layout.fragment_grid_line_annotations, container, false);
        rootView.addView(this.createChart());
        return rootView;
    }

    private RadCartesianChartView createChart(){
        //Create the Chart View
        RadCartesianChartView chart = new RadCartesianChartView(this.getActivity());

        //Create the bar series and attach axes and value bindings.
        BarSeries barSeries = new BarSeries();

        barSeries.setValueBinding(new FieldNameDataPointBinding("value"));
        barSeries.setCategoryBinding(new FieldNameDataPointBinding("category"));

        LinearAxis verticalAxis = new LinearAxis();
        //The values in the linear axis will not have values after the decimal point.
        verticalAxis.setLabelFormat("%.0f");
        CategoricalAxis horizontalAxis = new CategoricalAxis();
        barSeries.setVerticalAxis(verticalAxis);
        barSeries.setHorizontalAxis(horizontalAxis);

        //Bind series to data
        barSeries.setData(this.getData());

        //Add series to chart
        chart.getSeries().add(barSeries);

        CartesianPlotBandAnnotation annotation = new CartesianPlotBandAnnotation(verticalAxis, 6, 8);
        chart.getAnnotations().add(annotation);
        annotation.setFillColor(Color.argb(150, 0, 148, 255));
        annotation.setStrokeColor(Color.argb(150, 0, 74, 127));
        annotation.setStrokeWidth(4);
        annotation.setZIndex(1001);


        return chart;
    }

    private ArrayList<DataEntity> getData(){
        ArrayList<DataEntity> result = new ArrayList<DataEntity>(8);

        for (int i = 0; i < 8; i++){
            DataEntity entity = new DataEntity();
            entity.value = i + 1;
            entity.category = "Item " + i;
            result.add(entity);
        }

        return result;
    }

    public class DataEntity{
        public String category;
        public int value;
    }
}
