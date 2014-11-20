package fragments.axes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.telerik.manual.tests.R;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeContinuousAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by ginev on 11/20/2014.
 */
public class DateTimeContinuousAxisFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.fragment_datetime_continuous_axis, container, false);
        rootView.addView(this.createChart());
        return rootView;
    }

    private RadCartesianChartView createChart(){
        RadCartesianChartView chart = new RadCartesianChartView(this.getActivity());

        BarSeries series = new BarSeries();

        DateTimeContinuousAxis axis = new DateTimeContinuousAxis();
        axis.setLabelFormat("MM/dd");
        series.setHorizontalAxis(axis);

        LinearAxis verticalAxis = new LinearAxis();
        verticalAxis.setLabelFormat("%.0f");
        series.setVerticalAxis(verticalAxis);

        series.setCategoryBinding(new FieldNameDataPointBinding("date"));
        series.setValueBinding(new FieldNameDataPointBinding("value"));

        series.setData(this.getData());

        chart.getSeries().add(series);

        return chart;
    }

    private ArrayList<DataEntity> getData(){
        Random numberGenerator = new Random();
        ArrayList<DataEntity> result = new ArrayList<DataEntity>(8);
        int startingMonth =0;
        for (int i = 0; i < 8; i++){

            DataEntity entity = new DataEntity();
            entity.value = numberGenerator.nextInt(10) + 1;
            Calendar date = Calendar.getInstance();
            date.set(Calendar.MONTH, startingMonth++);
            entity.date = date;
            if (i == 2 || i == 6){
                continue;
            }
            result.add(entity);
        }

        return result;
    }

    public class DataEntity{
        public Calendar date;
        public int value;
    }
}
