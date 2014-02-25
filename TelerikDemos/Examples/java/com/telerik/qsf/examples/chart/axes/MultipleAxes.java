package com.telerik.qsf.examples.chart.axes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.telerik.common.Function;
import com.telerik.qsf.R;
import com.telerik.qsf.common.DataClass;
import com.telerik.widget.chart.engine.axes.AxisTickModel;
import com.telerik.widget.chart.engine.axes.common.AxisHorizontalLocation;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.axes.common.AxisLastLabelVisibility;
import com.telerik.widget.chart.engine.axes.common.AxisPlotMode;
import com.telerik.widget.chart.engine.axes.common.DateTimeComponent;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeCategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;
import com.telerik.widget.chart.visualization.common.Axis;
import com.telerik.widget.chart.visualization.common.LineAxis;
import com.telerik.widget.palettes.ChartPalette;
import com.telerik.widget.palettes.ChartPalettes;
import com.telerik.widget.palettes.PaletteEntry;
import com.telerik.widget.palettes.PaletteEntryCollection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class MultipleAxes extends Fragment implements CompoundButton.OnCheckedChangeListener {
    RadCartesianChartView chart;
    Context context;
    Random random = new Random();
    CheckBox price;
    CheckBox volume;
    BarSeries barSeries;
    AreaSeries areaSeries;

    public MultipleAxes() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = this.getActivity();
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_axes_multipleaxes, container, false);
        this.price = (CheckBox)result.findViewById(R.id.axesPrice);
        this.volume = (CheckBox)result.findViewById(R.id.axesVolume);
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

        GenericDataPointBinding<DataClass, Double> valueBinding = new GenericDataPointBinding<DataClass, Double>(new Function<DataClass, Double>() {
            @Override
            public Double apply(DataClass argument) {
                return argument.value;
            }
        });

        this.barSeries = new BarSeries(this.context);
        this.barSeries.setCategoryBinding(categoryBinding);
        this.barSeries.setValueBinding(valueBinding);
        this.barSeries.setData(this.createData(17, 39));

        LinearAxis barAxis = new LinearAxis(this.context);

        barAxis.setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                AxisTickModel tick = (AxisTickModel)argument;
                Double val = tick.value();
                return Integer.toString(val.intValue()) + "M";
            }
        });
        barAxis.setLabelMargin(res.getDimension(R.dimen.fivedp));
        barAxis.setMajorStep(5);
        barAxis.setMaximum(45);
        barAxis.setMinimum(15);
        barAxis.setHorizontalLocation(AxisHorizontalLocation.RIGHT);
        this.barSeries.setVerticalAxis(barAxis);

        this.areaSeries = new AreaSeries(this.context);
        LinearAxis areaAxis = new LinearAxis(this.context);
        areaAxis.setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                AxisTickModel tick = (AxisTickModel)argument;
                Double val = tick.value();
                return Integer.toString(val.intValue());
            }
        });
        areaAxis.setLabelMargin(res.getDimension(R.dimen.fivedp));
        areaAxis.setMaximum(800);
        areaAxis.setMinimum(500);
        this.areaSeries.setVerticalAxis(areaAxis);
        this.areaSeries.setCategoryBinding(categoryBinding);
        this.areaSeries.setValueBinding(valueBinding);
        this.areaSeries.setData(this.createData(600, 700));

        DateTimeCategoricalAxis horizontalAxis = new DateTimeCategoricalAxis(this.context);
        horizontalAxis.setDateTimeComponent(DateTimeComponent.DAY_OF_YEAR);
        horizontalAxis.setLabelMargin(res.getDimension(R.dimen.fifteendp));
        horizontalAxis.setLastLabelVisibility(AxisLastLabelVisibility.VISIBLE);
        horizontalAxis.setPlotMode(AxisPlotMode.ON_TICKS);
        horizontalAxis.setDateTimeFormat(new SimpleDateFormat("MMM-yyyy"));
        horizontalAxis.setMajorTickInterval(5);
        this.chart.setHorizontalAxis(horizontalAxis);
        this.chart.getSeries().add(this.barSeries);
        this.chart.getSeries().add(this.areaSeries);

        chart.setChartPadding(20, 0, 20, 50);

        ChartPalette light = ChartPalettes.light();
        PaletteEntry axisEntry = light.getEntry(barAxis, 0).clone();
        PaletteEntry seriesEntry = light.getEntry(barSeries, 0).clone();


        String color = Integer.toHexString(seriesEntry.getFill());
        color = "#" + color;
        axisEntry.setCustomValue(Axis.LINE_COLOR_KEY, color);
        axisEntry.setCustomValue(Axis.LABEL_COLOR, color);

        ChartPalette newLight = new ChartPalette();
        PaletteEntryCollection newCollection = new PaletteEntryCollection();
        newCollection.setFamily(barAxis.paletteFamily());
        newCollection.add(axisEntry);
        newLight.seriesEntries().add(newCollection);
        barAxis.setPalette(newLight);


        axisEntry = light.getEntry(barSeries, 1).clone();
        color = "#" + Integer.toHexString(axisEntry.getFill());
        axisEntry.setCustomValue(Axis.LABEL_COLOR, color);
        axisEntry.setCustomValue(Axis.LINE_COLOR_KEY, color);
        newLight = new ChartPalette();
        newCollection = new PaletteEntryCollection();
        newCollection.setFamily(areaAxis.paletteFamily());
        newCollection.add(axisEntry);
        newLight.seriesEntries().add(newCollection);
        areaAxis.setPalette(newLight);
    }

    private ArrayList<DataClass> createData(int minValue, int maxValue) {
        ArrayList<DataClass> source = new ArrayList<DataClass>();
        GregorianCalendar date = new GregorianCalendar(2013, 10, 0);

        for(int i = 0; i < 21; ++i) {
            DataClass data = new DataClass(date, minValue + random.nextInt(maxValue - minValue));
            source.add(data);
            date = (GregorianCalendar)date.clone();
            date.add(Calendar.DAY_OF_YEAR, 16);
        }

        return source;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.axesPrice) {
            this.togglePrice(isChecked);
        } else {
            this.toggleVolume(isChecked);
        }
    }

    private void toggleVolume(boolean isChecked) {
        if(isChecked) {
            this.chart.getSeries().add(0, this.barSeries);
            this.price.setEnabled(true);
        } else {
            this.chart.getSeries().remove(this.barSeries);
            this.price.setEnabled(false);
        }
    }

    private void togglePrice(boolean isChecked) {
        if(isChecked) {
            this.chart.getSeries().add(1, this.areaSeries);
            this.volume.setEnabled(true);
        } else {
            this.chart.getSeries().remove(this.areaSeries);
            this.volume.setEnabled(false);
        }
    }
}
