package fragments.series;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.telerik.android.sdk.R;
import com.telerik.widget.chart.engine.databinding.FieldNameDataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.CandlestickSeries;

import java.util.ArrayList;
import java.util.Random;

import activities.ExampleFragment;

/**
 * Created by ginev on 11/19/2014.
 */
public class CandleStickSeriesFragment extends Fragment implements ExampleFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.fragment_candle_stick_series, container, false);
        rootView.addView(this.createChart());
        return rootView;
    }

    private RadCartesianChartView createChart() {
        RadCartesianChartView chart = new RadCartesianChartView(this.getActivity()); // context is an instance of Context.

        CandlestickSeries series = new CandlestickSeries();
        series.setCategoryBinding(new FieldNameDataPointBinding("category"));
        series.setOpenBinding(new FieldNameDataPointBinding("open"));
        series.setHighBinding(new FieldNameDataPointBinding("high"));
        series.setLowBinding(new FieldNameDataPointBinding("low"));
        series.setCloseBinding(new FieldNameDataPointBinding("close"));

        series.setData(this.getData());
        chart.getSeries().add(series);

        CategoricalAxis horizontalAxis = new CategoricalAxis();
        chart.setHorizontalAxis(horizontalAxis);

        LinearAxis verticalAxis = new LinearAxis();
        chart.setVerticalAxis(verticalAxis);



        return chart;
    }

    private ArrayList<OhlcData> getData() {
        ArrayList<OhlcData> data = new ArrayList<OhlcData>();
        int size = 10;
        Random r = new Random();

        for (int i = 1; i <= size; ++i) {
            OhlcData ohlc = new OhlcData();
            ohlc.category = Integer.toString(i);
            ohlc.high = r.nextInt(100);
            if (ohlc.high < 2) {
                ohlc.high = 2;
            }

            ohlc.low = r.nextInt(ohlc.high - 1);
            ohlc.open = ohlc.low + r.nextInt(ohlc.high - ohlc.low);
            ohlc.close = ohlc.low + r.nextInt(ohlc.high - ohlc.low);

            data.add(ohlc);
        }

        return data;
    }

    @Override
    public String title() {
        return "Candle stick series";
    }

    class OhlcData {
        public String category;
        public int open;
        public int high;
        public int low;
        public int close;
    }
}
