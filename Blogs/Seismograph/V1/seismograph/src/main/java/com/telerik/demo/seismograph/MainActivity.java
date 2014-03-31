package com.telerik.demo.seismograph;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

import com.telerik.common.Function;
import com.telerik.widget.chart.engine.axes.MajorTickModel;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineRenderMode;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;
import com.telerik.widget.palettes.ChartPalettes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class MainActivity extends Activity implements SensorEventListener {

    private static final int X_AXIS_INDEX = 0;
    private static final int Y_AXIS_INDEX = 1;
    private static final int Z_AXIS_INDEX = 2;

    private int defaultCoolDown; // Get from resources.
    private int coolDown;

    private int bufferSize; // Get from resources.
    private int currentAxisIndex = X_AXIS_INDEX;

    private int framesCount = 0;
    boolean stopped = true;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private ViewGroup chartContainer;

    private RadCartesianChartView chart;
    private Needle needle;

    private Queue<SeismicDataPoint> seismicActivityBuffer;
    private List<SeismicDataPoint> allSeismicActivity;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (this.stopped || this.coolDown-- > 0)
            return;

        this.coolDown = this.defaultCoolDown;
        if (this.seismicActivityBuffer.size() > this.bufferSize)
            this.seismicActivityBuffer.remove();

        SeismicDataPoint point = new SeismicDataPoint(this.framesCount++, event.values[this.currentAxisIndex]);

        this.seismicActivityBuffer.add(point);
        this.allSeismicActivity.add(point);

        this.chartContainer.removeAllViews();
        this.chart = createChart(seismicActivityBuffer);
        this.chartContainer.addView(chart);

        this.needle.updateNeedle(point.y);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide the actionBar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        // To be tweaked for performance on different devices
        Resources resources = getResources();
        this.defaultCoolDown = Integer.parseInt(resources.getString(R.string.default_cool_down));
        this.bufferSize = Integer.parseInt(resources.getString(R.string.buffer_size));

        this.coolDown = this.defaultCoolDown;

        setContentView(R.layout.activity_main);

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.seismicActivityBuffer = new LinkedList<SeismicDataPoint>();
        this.allSeismicActivity = new ArrayList<SeismicDataPoint>();

        // Adding points to fill the screen at initial state.
        for (int i = -this.bufferSize; i < 0; i++) {
            this.seismicActivityBuffer.add(new SeismicDataPoint(i, 0));
        }

        this.chartContainer = (ViewGroup) findViewById(R.id.chart_container);
        this.needle = new Needle(this, chartContainer.getPaddingRight());
        ((ViewGroup) findViewById(R.id.main_container)).addView(this.needle);

        this.chart = createChart(this.seismicActivityBuffer);
        this.chartContainer.addView(this.chart);

        Button startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                stopped = false;
            }
        });

        Button stopBtn = (Button) findViewById(R.id.stop_btn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        Button resetBtn = (Button) findViewById(R.id.reset_btn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        // The activity is forced to landscape, so X and Y are different in phone and tablet devices.
        final RadioGroup axisSelectionMenu = (RadioGroup) findViewById(R.id.axis_selection);
        axisSelectionMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.axis_x:
                        currentAxisIndex = X_AXIS_INDEX;
                        break;
                    case R.id.axis_y:
                        currentAxisIndex = Y_AXIS_INDEX;
                        break;
                    case R.id.axis_z:
                        currentAxisIndex = Z_AXIS_INDEX;
                        break;
                    default:
                        throw new IllegalArgumentException("there are only 3 axes");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this);
    }

    private RadCartesianChartView createChart(Iterable<SeismicDataPoint> dataPoints) {
        RadCartesianChartView chart = new RadCartesianChartView(this);
        LinearAxis vAxis = new LinearAxis(this);

        // The maximum value of the accelerometer is 20 and the minimum -20, so give a bonus 10 to the vertical axis.
        vAxis.setMaximum(30);
        vAxis.setMinimum(-30);

        CategoricalAxis hAxis = new CategoricalAxis(this);
        hAxis.setShowLabels(false);

        DataPointBinding categoryBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object o) throws IllegalArgumentException {
                return ((SeismicDataPoint) o).x;
            }
        };

        DataPointBinding valueBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object o) throws IllegalArgumentException {
                return ((SeismicDataPoint) o).y;
            }
        };

        LineSeries series = new LineSeries(this);
        series.setCategoryBinding(categoryBinding);
        series.setValueBinding(valueBinding);
        series.setData(dataPoints);

        CartesianChartGrid grid = new CartesianChartGrid(this);

        chart.setGrid(grid);
        chart.setVerticalAxis(vAxis);

        chart.setHorizontalAxis(hAxis);
        chart.getSeries().add(series);
        //chart.getBehaviors().add(panZoom);
        chart.setPalette(ChartPalettes.dark());
        chart.setEmptyContent("");

        // Customize chart elements after adding them to the chart to override the application of the palette.
        hAxis.setTickColor(Color.TRANSPARENT);

        grid.setMajorYLinesRenderMode(GridLineRenderMode.INNER_AND_LAST);
        grid.setMajorYLineDashArray(
                new float[]{
                        this.needle.typedValueToPixels(TypedValue.COMPLEX_UNIT_DIP, 10),
                        this.needle.typedValueToPixels(TypedValue.COMPLEX_UNIT_DIP, 4)
                }
        );
        vAxis.setLineColor(Color.TRANSPARENT);
        //hAxis.setLineColor(Color.TRANSPARENT);
        return chart;
    }

    private void stop() {
        this.stopped = true;
        this.chartContainer.removeAllViews();
        this.chart = createChart(this.allSeismicActivity);
        this.chartContainer.addView(this.chart);
    }
}
