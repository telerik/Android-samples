package com.telerik.examples.examples.chart;

import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Function;
import com.telerik.android.common.ObservableCollection;
import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.AxisLabelLayoutMode;
import com.telerik.widget.chart.engine.axes.common.TimeInterval;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeContinuousAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;

import java.util.ArrayList;
import java.util.Calendar;

public class LiveDataFragment extends ExampleFragmentBase implements Runnable {
    private RadCartesianChartView chart;
    private ObservableCollection<LiveData> liveData = new ObservableCollection<LiveData>();
    private ArrayList<LiveData> allData;
    private Handler handler = new Handler();
    private int index;
    private int millisecond;
    private boolean isRunning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_chart_livedata, container, false);
        this.chart = (RadCartesianChartView) result.findViewById(R.id.chart);
        this.prepareChart();

        this.allData = this.loadAllData();
        while (index < 50) {
            liveData.add(allData.get(index));
            index++;
        }

        this.isRunning = true;
        this.run();

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.isRunning = false;
    }

    private ArrayList<LiveData> loadAllData() {
        ArrayList<LiveData> result = new ArrayList<LiveData>(1000);

        ExampleDataProvider.parseFinancialDataFromXml(result, R.raw.dow_jones_adj_close, this.getResources(), new Function<String, LiveData>() {
            @Override
            public LiveData apply(String argument) {
                LiveData data = new LiveData();
                data.value = Double.parseDouble(argument);
                data.time = Calendar.getInstance();
                data.time.set(Calendar.MILLISECOND, millisecond++);
                return data;
            }
        });
        return result;
    }

    private void prepareChart() {
        DateTimeContinuousAxis hAxis = new DateTimeContinuousAxis();
        hAxis.setLabelMargin(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 20));
        hAxis.setMajorStepUnit(TimeInterval.MILLISECOND);
        hAxis.setMajorStep(10);
        this.chart.setHorizontalAxis(hAxis);

        LinearAxis vAxis = new LinearAxis();
        vAxis.setLabelLayoutMode(AxisLabelLayoutMode.INNER);
        vAxis.setMinimum(50);
        vAxis.setMaximum(400);
        this.chart.setVerticalAxis(vAxis);

        AreaSeries areaSeries = new AreaSeries();
        areaSeries.setCategoryBinding(new Function<LiveData, Calendar>() {
            @Override
            public Calendar apply(LiveData argument) {
                return argument.time;
            }
        });

        areaSeries.setValueBinding(new Function<LiveData, Double>() {
            @Override
            public Double apply(LiveData argument) {
                return argument.value;
            }
        });

        areaSeries.setData(this.liveData);

        this.chart.getSeries().add(areaSeries);
    }

    @Override
    public void run() {
        if (!this.isRunning) {
            return;
        }

        this.index++;
        if (this.index >= this.allData.size()) {
            this.index = 0;
        }

        this.chart.beginUpdate();
        this.liveData.remove(0);
        this.liveData.add(allData.get(index));
        this.chart.endUpdate(false);

        this.handler.postDelayed(this, 50);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.isRunning = false;
    }

    private class LiveData {
        public Calendar time;
        public double value;
    }
}
