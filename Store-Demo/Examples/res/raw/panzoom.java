package com.telerik.examples.examples.chart.behaviors;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.FinancialDataClass;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.AxisTickModel;
import com.telerik.widget.chart.engine.axes.common.DateTimeComponent;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.visualization.behaviors.ChartPanAndZoomBehavior;
import com.telerik.widget.chart.visualization.behaviors.ChartPanZoomMode;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeCategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PanZoom extends ExampleFragmentBase implements CompoundButton.OnCheckedChangeListener {
    private RadCartesianChartView chart;
    private Context context;
    private LineSeries openSeries, highSeries, lowSeries;
    private AreaSeries closeSeries;
    private int orientation;

    public PanZoom() {
    }

    private View initContent(LayoutInflater inflater, ViewGroup container) {
        if (this.orientation == Configuration.ORIENTATION_PORTRAIT) {
            return inflater.inflate(R.layout.fragment_behaviors_panzoom, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_behaviors_panzoom_landscape, container, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.orientation = this.getResources().getConfiguration().orientation;
        this.context = this.getActivity();
        View root = this.initContent(inflater, container);

        this.chart = Util.getLayoutPart(root, R.id.panZoomChart, RadCartesianChartView.class);
        this.prepareChart();

        ViewGroup checkBoxesPanel = Util.getLayoutPart(root, R.id.checkBoxesPanel, ViewGroup.class);
        Typeface serifLight = Typeface.create("sans-serif-light", Typeface.NORMAL);
        for (int i = 0; i < checkBoxesPanel.getChildCount(); ++i) {
            CheckBox view = (CheckBox) checkBoxesPanel.getChildAt(i);
            view.setTypeface(serifLight);
            view.setOnCheckedChangeListener(this);
            this.updateGravity(view);

            this.onCheckedChanged(view, view.isChecked());
        }

        TextView title = Util.getLayoutPart(root, R.id.panZoomTitle, TextView.class);
        title.setTypeface(serifLight);

        return root;
    }

    private void prepareChart() {
        LinearAxis verticalAxis = new LinearAxis();
        verticalAxis.setLabelMargin(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 5));
        verticalAxis.setMinimum(9000);
        verticalAxis.setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                AxisTickModel tick = (AxisTickModel) argument;
                Double val = tick.value();
                return Integer.toString(val.intValue());
            }
        });
        this.chart.setVerticalAxis(verticalAxis);

        DateTimeCategoricalAxis horizontalAxis = new DateTimeCategoricalAxis();
        horizontalAxis.setLabelMargin(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 15));
        horizontalAxis.setMajorTickInterval(50);
        horizontalAxis.setDateTimeFormat(new SimpleDateFormat("MM/dd/yy"));
        horizontalAxis.setDateTimeComponent(DateTimeComponent.DATE);
        this.chart.setHorizontalAxis(horizontalAxis);

        GenericDataPointBinding<FinancialDataClass, Calendar> categoryBinding = new GenericDataPointBinding<FinancialDataClass, Calendar>(new Function<FinancialDataClass, Calendar>() {
            @Override
            public Calendar apply(FinancialDataClass argument) {
                return argument.date;
            }
        });

        GenericDataPointBinding<FinancialDataClass, Float> valueBinding = new GenericDataPointBinding<FinancialDataClass, Float>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.open;
            }
        });

        openSeries = new LineSeries();
        openSeries.setCategoryBinding(categoryBinding);
        openSeries.setValueBinding(valueBinding);
        openSeries.setData(ExampleDataProvider.panZoomData(getResources()));

        valueBinding = new GenericDataPointBinding<FinancialDataClass, Float>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.high;
            }
        });

        highSeries = new LineSeries();
        highSeries.setCategoryBinding(categoryBinding);
        highSeries.setValueBinding(valueBinding);
        highSeries.setData(ExampleDataProvider.panZoomData(getResources()));

        valueBinding = new GenericDataPointBinding<FinancialDataClass, Float>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.low;
            }
        });

        lowSeries = new LineSeries();
        lowSeries.setCategoryBinding(categoryBinding);
        lowSeries.setValueBinding(valueBinding);
        lowSeries.setData(ExampleDataProvider.panZoomData(getResources()));

        valueBinding = new GenericDataPointBinding<FinancialDataClass, Float>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.close;
            }
        });

        closeSeries = new AreaSeries();
        closeSeries.setCategoryBinding(categoryBinding);
        closeSeries.setValueBinding(valueBinding);
        closeSeries.setData(ExampleDataProvider.panZoomData(getResources()));

        this.chart.getSeries().add(closeSeries);
        this.chart.getSeries().add(lowSeries);
        this.chart.getSeries().add(highSeries);
        this.chart.getSeries().add(openSeries);

        this.closeSeries.setStrokeColor(Color.TRANSPARENT);
        this.closeSeries.setFillColor(0xFFF9BA1A);
        this.lowSeries.setStrokeColor(0xFF9DCC00);
        this.highSeries.setStrokeColor(0xFFA666CE);
        this.openSeries.setStrokeColor(0xFF4FB6E7);

        CartesianChartGrid grid = new CartesianChartGrid();
        grid.setMajorLinesVisibility(GridLineVisibility.Y);
        this.chart.setGrid(grid);

        ChartPanAndZoomBehavior panZoom = new ChartPanAndZoomBehavior();
        panZoom.setPanMode(ChartPanZoomMode.BOTH);
        panZoom.setZoomMode(ChartPanZoomMode.BOTH);
        this.chart.getBehaviors().add(panZoom);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (this.orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.updateGravity(buttonView, isChecked);
        }

        int id = buttonView.getId();
        switch (id) {
            case R.id.open:
                this.openSeries.setVisible(isChecked);
                break;
            case R.id.close:
                this.closeSeries.setVisible(isChecked);
                break;
            case R.id.high:
                this.highSeries.setVisible(isChecked);
                break;
            case R.id.low:
                this.lowSeries.setVisible(isChecked);
                break;
        }
    }

    private void updateGravity(CheckBox buttonView) {
        this.updateGravity(buttonView, buttonView.isChecked());
    }

    private void updateGravity(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            buttonView.setGravity(Gravity.TOP | Gravity.LEFT);
        } else {
            buttonView.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        }
    }
}
