package fragments.series;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.telerik.manual.tests.R;
import com.telerik.widget.chart.engine.axes.common.AxisLabelFitMode;
import com.telerik.widget.chart.engine.axes.common.DateTimeComponent;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeCategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.BollingerBandsIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.MovingAverageIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.CandlestickSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.OhlcSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by ginev on 11/19/2014.
 */
public class IndicatorSeriesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.fragment_indicator_series, container, false);
        rootView.addView(this.createChart());
        return rootView;
    }

    private RadCartesianChartView createChart() {
        RadCartesianChartView chart = new RadCartesianChartView(this.getActivity());

        DateTimeCategoricalAxis horizontalAxis = new DateTimeCategoricalAxis();
        horizontalAxis.setDateTimeFormat(new SimpleDateFormat("MM/dd"));
        horizontalAxis.setDateTimeComponent(DateTimeComponent.DATE);

        LinearAxis verticalAxis = new LinearAxis();

        DataPointBinding categoryBinding = new FieldNameDataPointBinding("date");
        DataPointBinding openBinding = new FieldNameDataPointBinding("open");
        DataPointBinding highBinding = new FieldNameDataPointBinding("high");
        DataPointBinding lowBinding = new FieldNameDataPointBinding("low");
        DataPointBinding closeBinding = new FieldNameDataPointBinding("close");

        ArrayList<FinancialDataClass> data = this.getData();

        CandlestickSeries series = new CandlestickSeries();
        series.setCategoryBinding(categoryBinding);
        series.setOpenBinding(openBinding);
        series.setHighBinding(highBinding);
        series.setLowBinding(lowBinding);
        series.setCloseBinding(closeBinding);
        series.setData(data);

        BollingerBandsIndicator bollingerBands = new BollingerBandsIndicator();
        bollingerBands.setCategoryBinding(categoryBinding);
        bollingerBands.setValueBinding(closeBinding);
        bollingerBands.setPeriod(5);
        bollingerBands.setStandardDeviations(2);
        bollingerBands.setData(data);

        MovingAverageIndicator movingAverage = new MovingAverageIndicator();
        movingAverage.setCategoryBinding(categoryBinding);
        movingAverage.setValueBinding(closeBinding);
        movingAverage.setPeriod(5);
        movingAverage.setData(data);

        chart.setVerticalAxis(verticalAxis);
        chart.setHorizontalAxis(horizontalAxis);
        chart.getSeries().add(series);
        chart.getSeries().add(bollingerBands);
        chart.getSeries().add(movingAverage);

        // irrelevant
        horizontalAxis.setLabelFitMode(AxisLabelFitMode.ROTATE);

        return chart;
    }

    private ArrayList<FinancialDataClass> getData() {
        ArrayList<FinancialDataClass> data = new ArrayList<FinancialDataClass>();
        int size = 10;
        Random r = new Random();


        int month = 1;
        for (int i = 1; i <= size; ++i) {
            FinancialDataClass ohlc = new FinancialDataClass();
            //ohlc.category = Integer.toString(i);
            ohlc.high = 150 + r.nextInt(50);

            ohlc.low = ohlc.high - (r.nextInt(20) + 10);
            ohlc.open = ohlc.low + r.nextInt((int)ohlc.high - (int)ohlc.low);
            ohlc.close = ohlc.low + r.nextInt((int)ohlc.high - (int)ohlc.low);
            Calendar date = Calendar.getInstance();
            date.set(Calendar.MONTH, month++);
            ohlc.date = date;

            data.add(ohlc);
        }

        return data;
    }

    public class FinancialDataClass {

        public float open;
        public float high;
        public float low;
        public float close;
        public Calendar date;
    }
}
