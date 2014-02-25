package com.telerik.qsf.examples.chart.annotations;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.telerik.common.Function;
import com.telerik.common.ObservableCollection;
import com.telerik.qsf.R;
import com.telerik.qsf.common.DataClass;
import com.telerik.widget.chart.engine.axes.common.AxisHorizontalLocation;
import com.telerik.widget.chart.engine.dataPoints.RangeDataPoint;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.engine.decorations.annotations.custom.CustomAnnotationRenderer;
import com.telerik.widget.chart.engine.elementTree.ChartNode;
import com.telerik.widget.chart.engine.math.RadRect;
import com.telerik.widget.chart.engine.math.RadSize;
import com.telerik.widget.chart.visualization.annotations.cartesian.CartesianCustomAnnotation;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineRenderMode;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.RangeBarSeries;
import com.telerik.widget.chart.visualization.common.renderers.ChartLabelRenderer;

import java.util.ArrayList;

public class WeatherData extends Fragment implements RadioGroup.OnCheckedChangeListener {
    RadCartesianChartView chart;
    Context context;
    RadioGroup buttons;
    View contentView;
    private ArrayList<DataClass> fridayData;
    private ArrayList<DataClass> thursdayData;
    private ArrayList<DataClass> wednesdayData;
    private ArrayList<DataClass> tuesdayData;
    private ArrayList<DataClass> mondayData;
    private TextView title;

    public WeatherData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = this.getActivity();
        // Inflate the layout for this fragment
        this.contentView = inflater.inflate(R.layout.fragment_annotations_watherdata, container, false);
        this.chart = (RadCartesianChartView) this.contentView.findViewById(R.id.chart);
        this.title = (TextView) this.contentView.findViewById(R.id.annotationsTitle);
        title.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        this.prepareChart();

        this.buttons = (RadioGroup) this.contentView.findViewById(R.id.annotationsRadioGroup);
        this.buttons.setOnCheckedChangeListener(this);

        this.buttons.check(R.id.monday);

        return this.contentView;
    }

    private void prepareChart() {
        Resources res = this.getResources();
        CategoricalAxis horizontalAxis = new CategoricalAxis(this.context);
        horizontalAxis.setLabelMargin(20);
        chart.setHorizontalAxis(horizontalAxis);
        horizontalAxis.setLineThickness(res.getDimension(R.dimen.onedp));
        horizontalAxis.setLineColor(0xff66B3D4);

        LinearAxis verticalAxis = new LinearAxis(this.context);
        verticalAxis.setMinimum(-30);
        verticalAxis.setMaximum(10);
        verticalAxis.setMajorStep(10);
        verticalAxis.setShowLabels(false);
        verticalAxis.setHorizontalLocation(AxisHorizontalLocation.RIGHT);
        chart.setVerticalAxis(verticalAxis);
        verticalAxis.setTickColor(Color.TRANSPARENT);
        verticalAxis.setLineColor(Color.TRANSPARENT);

        RangeBarSeries barSeries = new RangeBarSeries(this.context);
        barSeries.setShowLabels(true);

        barSeries.setHighBinding(new GenericDataPointBinding<DataClass, Double>(new Function<DataClass, Double>() {
            @Override
            public Double apply(DataClass argument) {
                return argument.value;
            }
        }));

        barSeries.setLowBinding(new GenericDataPointBinding<DataClass, Double>(new Function<DataClass, Double>() {
            @Override
            public Double apply(DataClass argument) {
                return argument.value2;
            }
        }));

        barSeries.setCategoryBinding(new GenericDataPointBinding<DataClass, String>(new Function<DataClass, String>() {
            @Override
            public String apply(DataClass argument) {
                return argument.category;
            }
        }));

        LabelRenderer seriesLabelRenderer = new LabelRenderer(res);
        barSeries.setLabelRenderer(seriesLabelRenderer);
        this.chart.getSeries().add(barSeries);
        barSeries.setStrokeWidth(this.getResources().getDimension(R.dimen.onedp));
        barSeries.setFillColor(0xff3E3E3E);
        barSeries.setStrokeColor(Color.WHITE);

        CartesianChartGrid grid = new CartesianChartGrid(this.context);
        chart.setGrid(grid);

        grid.setMajorYLinesRenderMode(GridLineRenderMode.INNER_AND_LAST);
        grid.setLineThickness(res.getDimension(R.dimen.onedp));
        grid.setLineColor(Color.WHITE);
        grid.setMajorLinesVisibility(GridLineVisibility.Y);
        grid.setStripLinesVisibility(GridLineVisibility.Y);
        ObservableCollection<Paint> yBrushes = grid.getYStripeBrushes();
        yBrushes.clear();

        Paint paint = new Paint();
        paint.setColor(0xb27ECCED);
        yBrushes.add(paint);

        paint = new Paint();
        paint.setColor(0xb286EBD7);
        yBrushes.add(paint);

        paint = new Paint();
        paint.setColor(0xb29DF5B7);
        yBrushes.add(paint);

        paint = new Paint();
        paint.setColor(0xb2C2EB95);
        yBrushes.add(paint);

        chart.setChartPadding(0, 0, 0, 100);

        AnnotationRenderer renderer = new AnnotationRenderer();
        for (int i = -20; i <= 10; i += 10) {
            CartesianCustomAnnotation degreeAnnotation = new CartesianCustomAnnotation(this.context);
            degreeAnnotation.setContentRenderer(renderer);
            degreeAnnotation.setContent(Integer.toString(i) + "\u00b0");
            degreeAnnotation.setHorizontalAxis(horizontalAxis);
            degreeAnnotation.setVerticalAxis(verticalAxis);
            degreeAnnotation.setHorizontalValue("22:00");
            degreeAnnotation.setVerticalValue((double) i);
            chart.getAnnotations().add(degreeAnnotation);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton button = (RadioButton)this.contentView.findViewById(checkedId);
        if(!button.isChecked()) {
            return;
        }

        this.chart.getSeries().get(0).setData(this.prepareData(checkedId));

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.buttons.setVisibility(View.GONE);
            title.setText(this.getTitleText());
        } else {
            title.setVisibility(View.GONE);
        }
    }

    private Iterable<DataClass> prepareData(int checkedId) {
        switch (checkedId) {
            case R.id.monday:
                return this.getMondayData();
            case R.id.tuesday:
                return this.getTuesdayData();
            case R.id.wednesday:
                return this.getWednesdayData();
            case R.id.thursday:
                return this.getThursdayData();
            case R.id.friday:
                return this.getFridayData();
        }

        return null;
    }

    public ArrayList<DataClass> getFridayData() {
        if (this.fridayData != null) {
            return fridayData;
        }

        this.fridayData = new ArrayList<DataClass>();
        this.fridayData.add(new DataClass("8:00", -5.0, -10.0));
        this.fridayData.add(new DataClass("10:00", -4.0, -9.0));
        this.fridayData.add(new DataClass("12:00", -3.0, -8.0));
        this.fridayData.add(new DataClass("14:00", -2.0, -7.0));
        this.fridayData.add(new DataClass("16:00", -5.0, -10.0));
        this.fridayData.add(new DataClass("18:00", -6.0, -12.0));
        this.fridayData.add(new DataClass("20:00", -6.0, -11.0));
        this.fridayData.add(new DataClass("22:00", -9.0, -15.0));

        return this.fridayData;
    }

    public ArrayList<DataClass> getThursdayData() {
        if (this.thursdayData != null) {
            return thursdayData;
        }

        this.thursdayData = new ArrayList<DataClass>();
        this.thursdayData.add(new DataClass("8:00", -3.0, -10.0));
        this.thursdayData.add(new DataClass("10:00", -3.0, -8.0));
        this.thursdayData.add(new DataClass("12:00", -2.0, -5.0));
        this.thursdayData.add(new DataClass("14:00", -1.0, -7.0));
        this.thursdayData.add(new DataClass("16:00", 0.0, -5.0));
        this.thursdayData.add(new DataClass("18:00", -3.0, -8.0));
        this.thursdayData.add(new DataClass("20:00", -6.0, -12.0));
        this.thursdayData.add(new DataClass("22:00", -10.0, -17.0));

        return this.thursdayData;
    }

    public ArrayList<DataClass> getWednesdayData() {
        if (this.wednesdayData != null) {
            return wednesdayData;
        }

        this.wednesdayData = new ArrayList<DataClass>();
        this.wednesdayData.add(new DataClass("8:00", -2.0, -10.0));
        this.wednesdayData.add(new DataClass("10:00", -2.0, -7.0));
        this.wednesdayData.add(new DataClass("12:00", -1.0, -5.0));
        this.wednesdayData.add(new DataClass("14:00", 1.0, -7.0));
        this.wednesdayData.add(new DataClass("16:00", 1.0, -5.0));
        this.wednesdayData.add(new DataClass("18:00", -2.0, -6.0));
        this.wednesdayData.add(new DataClass("20:00", -5.0, -9.0));
        this.wednesdayData.add(new DataClass("22:00", -8.0, -13.0));

        return this.wednesdayData;
    }

    public ArrayList<DataClass> getTuesdayData() {
        if (this.tuesdayData != null) {
            return tuesdayData;
        }

        this.tuesdayData = new ArrayList<DataClass>();
        this.tuesdayData.add(new DataClass("8:00", -2.0, -11.0));
        this.tuesdayData.add(new DataClass("10:00", 2.0, -9.0));
        this.tuesdayData.add(new DataClass("12:00", 3.0, -7.0));
        this.tuesdayData.add(new DataClass("14:00", -1.0, -10.0));
        this.tuesdayData.add(new DataClass("16:00", -4.0, -15.0));
        this.tuesdayData.add(new DataClass("18:00", -9.0, -18.0));
        this.tuesdayData.add(new DataClass("20:00", -11.0, -20.0));
        this.tuesdayData.add(new DataClass("22:00", -12.0, -22.0));

        return this.tuesdayData;
    }

    public ArrayList<DataClass> getMondayData() {
        if (this.mondayData != null) {
            return mondayData;
        }

        this.mondayData = new ArrayList<DataClass>();
        this.mondayData.add(new DataClass("8:00", -15.0, -22.0));
        this.mondayData.add(new DataClass("10:00", -12.0, -19.0));
        this.mondayData.add(new DataClass("12:00", -11.0, -18.0));
        this.mondayData.add(new DataClass("14:00", -10.0, -17.0));
        this.mondayData.add(new DataClass("16:00", -12.0, -19.0));
        this.mondayData.add(new DataClass("18:00", -15.0, -22.0));
        this.mondayData.add(new DataClass("20:00", -19.0, -26.0));
        this.mondayData.add(new DataClass("22:00", -20.0, -26.0));

        return this.mondayData;
    }

    public String getTitleText() {
        String result = "";
        switch (this.buttons.getCheckedRadioButtonId()) {
            case R.id.monday:
                result = "monday's";
                break;
            case R.id.tuesday:
                result = "tuesday's";
                break;
            case R.id.wednesday:
                result = "wednesday's";
                break;
            case R.id.thursday:
                result = "thursday's";
                break;
            case R.id.friday:
                result = "friday's";
                break;
        }
        return (result + " weather").toUpperCase();
    }

    private class AnnotationRenderer implements CustomAnnotationRenderer {
        Paint contentPaint = new Paint();

        public AnnotationRenderer() {
            contentPaint.setTextSize(getResources().getDimension(R.dimen.customAnnotationSize));
            contentPaint.setColor(0xffffffff);
            contentPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        }

        @Override
        public RadSize measureContent(Object content) {
            if (content == null) {
                return RadSize.getEmpty();
            }

            String text = content.toString();
            Rect textBounds = new Rect();
            contentPaint.getTextBounds(text, 0, text.length(), textBounds);

            return new RadSize(textBounds.width(), textBounds.height());
        }

        @Override
        public void render(Object content, RadRect layoutSlot, Canvas canvas, Paint paint) {
            if (content == null) {
                return;
            }

            String text = content.toString();
            canvas.drawText(
                    text, (float) layoutSlot.x - (float) (layoutSlot.width / 2.0),
                    (float) layoutSlot.getBottom(), contentPaint);
        }
    }

    private class LabelRenderer implements ChartLabelRenderer {
        private String labelFormat = "%d";
        private TextPaint paint = new TextPaint();
        private Paint stroke = new Paint();
        private Paint highFill = new Paint();
        private Paint lowFill = new Paint();
        private float labelMargin = 10.0f;
        private float labelPadding = 20.0f;

        public LabelRenderer(Resources resources) {
            this.stroke.setStyle(Paint.Style.STROKE);
            this.stroke.setColor(Color.WHITE);
            this.stroke.setStrokeWidth(resources.getDimension(R.dimen.onedp));

            this.highFill.setColor(0xffF5413F);
            this.lowFill.setColor(0xff4FB6E7);
            this.paint.setTextSize(resources.getDimension(R.dimen.twelveSp));
            this.paint.setColor(Color.WHITE);
            this.labelMargin = resources.getDimension(R.dimen.threedp);
            this.labelPadding = resources.getDimension(R.dimen.fivedp);
        }

        @Override
        public void renderLabel(Canvas canvas, ChartNode relatedLabelNode) {
            RangeDataPoint dataPoint = (RangeDataPoint) relatedLabelNode;
            RadRect dataPointSlot = dataPoint.getLayoutSlot();
            Double val = dataPoint.getHigh();
            String labelText = String.format(this.labelFormat, val.intValue());

            StaticLayout textInfo = this.createTextInfo(labelText, dataPoint);
            this.renderTopLabel(canvas, dataPointSlot, labelText, textInfo);

            val = dataPoint.getLow();
            labelText = String.format(this.labelFormat, val.intValue());
            textInfo = this.createTextInfo(labelText, dataPoint);
            this.renderBottomLabel(canvas, dataPointSlot, labelText, textInfo);
        }

        private StaticLayout createTextInfo(String labelText, RangeDataPoint dataPoint) {
            return new StaticLayout(labelText,
                    0,
                    labelText.length(),
                    this.paint,
                    Math.round((float) dataPoint.getLayoutSlot().width),
                    Layout.Alignment.ALIGN_CENTER,
                    1.0f,
                    1.0f,
                    false);
        }

        private void renderTopLabel(Canvas canvas, RadRect dataPointSlot, String labelText, StaticLayout textBounds) {
            RectF labelBounds = new RectF();
            float height = textBounds.getHeight() + this.labelPadding * 2;
            float top = (float) dataPointSlot.y - this.labelMargin - height;
            labelBounds.set((float) dataPointSlot.x,
                    top,
                    (float) dataPointSlot.getRight(),
                    top + height);

            canvas.drawRect(labelBounds.left, labelBounds.top, labelBounds.right, labelBounds.bottom, this.highFill);
            canvas.drawRect(labelBounds.left, labelBounds.top, labelBounds.right, labelBounds.bottom, this.stroke);
            canvas.drawText(labelText, (float) dataPointSlot.x + (float) (dataPointSlot.width / 2.0) - textBounds.getLineWidth(0) / 2.0f, labelBounds.centerY() + textBounds.getLineBottom(0) - textBounds.getLineBaseline(0), paint);
        }

        private void renderBottomLabel(Canvas canvas, RadRect dataPointSlot, String labelText, StaticLayout textBounds) {
            RectF labelBounds = new RectF();
            float height = textBounds.getHeight() + this.labelPadding * 2;
            float top = (float) dataPointSlot.getBottom() + this.labelMargin;
            labelBounds.set((float) dataPointSlot.x,
                    top,
                    (float) dataPointSlot.getRight(),
                    top + height);

            canvas.drawRect(labelBounds.left, labelBounds.top, labelBounds.right, labelBounds.bottom, this.lowFill);
            canvas.drawRect(labelBounds.left, labelBounds.top, labelBounds.right, labelBounds.bottom, this.stroke);
            canvas.drawText(labelText, (float) dataPointSlot.x + (float) (dataPointSlot.width / 2.0) - textBounds.getLineWidth(0) / 2.0f, labelBounds.centerY() + textBounds.getLineBottom(0) - textBounds.getLineBaseline(0), paint);
        }

        @Override
        public Function<Object, String> getLabelValueToStringConverter() {
            return null;
        }

        @Override
        public void setLabelValueToStringConverter(Function<Object, String> converter) {
        }

        @Override
        public void setLabelColor(int color) {
            this.paint.setColor(color);
        }

        @Override
        public int getLabelColor() {
            return 0;
        }

        @Override
        public float getLabelSize() {
            return 0;
        }

        @Override
        public void setLabelSize(float value) {

        }

        @Override
        public Typeface getLabelFont() {
            return null;
        }

        @Override
        public void setLabelFont(Typeface value) {

        }

        @Override
        public int getLabelFontStyle() {
            return 0;
        }

        @Override
        public void setLabelFontStyle(int value) {

        }

        @Override
        public String getLabelFormat() {
            return this.labelFormat;
        }

        @Override
        public void setLabelFormat(String format) {
            this.labelFormat = format;
        }

        public void setLabelMargin(float labelMargin) {
            this.labelMargin = labelMargin;
        }

        public float getLabelMargin() {
            return labelMargin;
        }

        public float getLabelPadding() {
            return labelPadding;
        }

        public void setLabelPadding(float padding) {
            this.labelPadding = padding;
        }
    }
}
