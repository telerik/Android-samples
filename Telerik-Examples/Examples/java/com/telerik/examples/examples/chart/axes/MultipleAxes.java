package com.telerik.examples.examples.chart.axes;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.telerik.android.common.Function;
import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.chart.engine.axes.AxisTickModel;
import com.telerik.widget.chart.engine.axes.common.AxisHorizontalLocation;
import com.telerik.widget.chart.engine.axes.common.AxisLastLabelVisibility;
import com.telerik.widget.chart.engine.axes.common.AxisPlotMode;
import com.telerik.widget.chart.engine.axes.common.DateTimeComponent;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeCategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class MultipleAxes extends ExampleFragmentBase implements CompoundButton.OnCheckedChangeListener {
    RadCartesianChartView chart;
    Context context;
    Random random = new Random();
    CheckBox price;
    CheckBox volume;
    BarSeries barSeries;
    AreaSeries areaSeries;
    LinearAxis barAxis;
    LinearAxis areaAxis;

    public MultipleAxes() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = this.getActivity();
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_axes_multipleaxes, container, false);
        this.price = Util.getLayoutPart(result, R.id.axesPrice, CheckBox.class);
        this.volume = Util.getLayoutPart(result, R.id.axesVolume, CheckBox.class);
        this.price.setOnCheckedChangeListener(this);
        this.volume.setOnCheckedChangeListener(this);

        this.chart = (RadCartesianChartView) result.findViewById(R.id.chart);
        this.prepareChart();

        return result;
    }

    private void prepareChart() {
        Resources res = this.getResources();

        GenericDataPointBinding<DataClass, Calendar> categoryBinding = new GenericDataPointBinding<DataClass, Calendar>(new Function<DataClass, Calendar>() {
            @Override
            public Calendar apply(DataClass argument) {
                return argument.date;
            }
        });

        GenericDataPointBinding<DataClass, Float> valueBinding = new GenericDataPointBinding<DataClass, Float>(new Function<DataClass, Float>() {
            @Override
            public Float apply(DataClass argument) {
                return argument.value;
            }
        });

        this.barSeries = new BarSeries();
        this.barSeries.setCategoryBinding(categoryBinding);
        this.barSeries.setValueBinding(valueBinding);
        this.barSeries.setData(this.createData(17, 39));

        barAxis = new LinearAxis();

        barAxis.setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                AxisTickModel tick = (AxisTickModel) argument;
                Double val = tick.value();
                return Integer.toString(val.intValue()) + "M";
            }
        });
        barAxis.setLabelMargin(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 5));
        barAxis.setMajorStep(5);
        barAxis.setMaximum(45);
        barAxis.setMinimum(15);
        barAxis.setHorizontalLocation(AxisHorizontalLocation.RIGHT);
        this.barSeries.setVerticalAxis(barAxis);

        this.areaSeries = new AreaSeries();
        areaAxis = new LinearAxis();
        areaAxis.setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                AxisTickModel tick = (AxisTickModel) argument;
                Double val = tick.value();
                return Integer.toString(val.intValue());
            }
        });
        areaAxis.setLabelMargin(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 5));
        areaAxis.setMaximum(800);
        areaAxis.setMinimum(500);
        this.areaSeries.setVerticalAxis(areaAxis);
        this.areaSeries.setCategoryBinding(categoryBinding);
        this.areaSeries.setValueBinding(valueBinding);
        this.areaSeries.setData(this.createData(600, 700));

        DateTimeCategoricalAxis horizontalAxis = new DateTimeCategoricalAxis();
        horizontalAxis.setDateTimeComponent(DateTimeComponent.DAY_OF_YEAR);
        horizontalAxis.setLabelMargin(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 15));
        horizontalAxis.setLastLabelVisibility(AxisLastLabelVisibility.VISIBLE);
        horizontalAxis.setPlotMode(AxisPlotMode.ON_TICKS);
        horizontalAxis.setDateTimeFormat(new SimpleDateFormat("MMM-yyyy"));
        horizontalAxis.setMajorTickInterval(5);
        this.chart.setHorizontalAxis(horizontalAxis);
        this.chart.getSeries().add(this.barSeries);
        this.chart.getSeries().add(this.areaSeries);

        chart.setChartPadding(20, 0, 20, 0);

        this.updateAreaAxisColor();
        this.updateBarAxisColor();
    }

    private ArrayList<DataClass> createData(int minValue, int maxValue) {
        ArrayList<DataClass> source = new ArrayList<DataClass>();
        GregorianCalendar date = new GregorianCalendar(2013, 10, 0);

        for (int i = 0; i < 21; ++i) {
            DataClass data = new DataClass(date, minValue + random.nextInt(maxValue - minValue));
            source.add(data);
            date = (GregorianCalendar) date.clone();
            date.add(Calendar.DAY_OF_YEAR, 16);
        }

        return source;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.axesPrice) {
            this.togglePrice(isChecked);
        } else {
            this.toggleVolume(isChecked);
        }
    }

    private void toggleVolume(boolean isChecked) {
        if (isChecked) {
            this.chart.getSeries().add(0, this.barSeries);
            this.updateBarAxisColor();
            this.price.setEnabled(true);
        } else {
            this.chart.getSeries().remove(this.barSeries);
            this.price.setEnabled(false);
        }
    }

    private void togglePrice(boolean isChecked) {
        if (isChecked) {
            this.chart.getSeries().add(this.areaSeries);
            this.updateAreaAxisColor();
            this.volume.setEnabled(true);
        } else {
            this.chart.getSeries().remove(this.areaSeries);
            this.volume.setEnabled(false);
        }
    }

    private void updateBarAxisColor() {
        int color = this.chart.getPalette().getEntry(this.barSeries, 0).getFill();
        this.barAxis.setLabelTextColor(color);
        this.barAxis.setLineColor(color);
    }

    private void updateAreaAxisColor() {
        int color = this.chart.getPalette().getEntry(this.areaSeries, 1).getFill();
        this.areaAxis.setLabelTextColor(color);
        this.areaAxis.setLineColor(color);
    }
}
