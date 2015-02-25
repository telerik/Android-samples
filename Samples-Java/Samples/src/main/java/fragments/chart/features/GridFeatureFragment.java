package fragments.chart.features;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.telerik.android.common.ObservableCollection;
import com.telerik.android.sdk.R;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineRenderMode;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

import java.util.ArrayList;
import java.util.Random;

import activities.ExampleFragment;

/**
 * Created by ginev on 11/21/2014.
 */
public class GridFeatureFragment extends Fragment implements ExampleFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout rootView = (FrameLayout)inflater.inflate(R.layout.fragment_vertical_bar_series, container, false);
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
        chart.setVerticalAxis(verticalAxis);
        chart.setHorizontalAxis(horizontalAxis);

        //Bind series to data
        barSeries.setData(this.getData());

        //Add series to chart
        chart.getSeries().add(barSeries);

        //Set an instance of the CartesianChartGrid to the chart.

        CartesianChartGrid grid = new CartesianChartGrid();
        grid.setMajorYLinesRenderMode(GridLineRenderMode.INNER_AND_LAST);
        grid.setLineThickness(1);
        grid.setLineColor(Color.WHITE);
        grid.setMajorLinesVisibility(GridLineVisibility.Y);
        grid.setStripLinesVisibility(GridLineVisibility.Y);
        ObservableCollection<Paint> yBrushes = grid.getYStripeBrushes();
        yBrushes.clear();
        chart.setGrid(grid);

        return chart;
    }

    private ArrayList<DataEntity> getData(){
        Random numberGenerator = new Random();
        ArrayList<DataEntity> result = new ArrayList<DataEntity>(8);

        for (int i = 0; i < 8; i++){
            DataEntity entity = new DataEntity();
            entity.value = numberGenerator.nextInt(10) + 1;
            entity.category = "Item " + i;
            result.add(entity);
        }

        return result;
    }

    @Override
    public String title() {
        return "Grid feature";
    }

    public class DataEntity{
        public String category;
        public int value;
    }
}
