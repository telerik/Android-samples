package fragments.series;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.telerik.android.sdk.R;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.pieChart.DoughnutSeries;
import com.telerik.widget.chart.visualization.pieChart.PieSeries;
import com.telerik.widget.chart.visualization.pieChart.RadPieChartView;

import java.util.ArrayList;
import java.util.Random;

import activities.ExampleFragment;

/**
 * Created by ginev on 11/19/2014.
 */
public class DoughnutSeriesFragment extends Fragment implements ExampleFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_doughnut_series, container, false);
        rootView.addView(this.createChart());
        return rootView;
    }

    private RadPieChartView createChart() {

        RadPieChartView pieChart = new RadPieChartView(this.getActivity());

        DoughnutSeries doughnutSeries = new DoughnutSeries();

        doughnutSeries.setValueBinding(new FieldNameDataPointBinding("value"));
        doughnutSeries.setShowLabels(true);
        doughnutSeries.setData(this.getData());
        pieChart.getSeries().add(doughnutSeries);

        return pieChart;
    }

    private ArrayList<DataEntity> getData() {
        Random r = new Random();

        ArrayList<DataEntity> result = new ArrayList<DataEntity>();

        for (int i = 0; i < 8; i++) {
            DataEntity entity = new DataEntity();
            entity.value = r.nextInt(10) + 1;
            result.add(entity);
        }

        return result;
    }

    @Override
    public String title() {
        return "Doughnut series";
    }

    public class DataEntity {
        public double value;
    }
}
