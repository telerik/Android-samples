package com.telerik.examples.examples.chart.series.financial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.FinancialDataClass;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.examples.viewmodels.ExampleDataProvider;
import com.telerik.widget.chart.engine.axes.MajorTickModel;
import com.telerik.widget.chart.engine.axes.common.AxisHorizontalLocation;
import com.telerik.widget.chart.engine.axes.common.AxisPlotMode;
import com.telerik.widget.chart.engine.axes.common.DateTimeComponent;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.GenericDataPointBinding;
import com.telerik.widget.chart.visualization.behaviors.ChartPanAndZoomBehavior;
import com.telerik.widget.chart.visualization.behaviors.ChartTrackBallBehavior;
import com.telerik.widget.chart.visualization.behaviors.ChartZoomStrategy;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeCategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.AverageTrueRangeIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.BollingerBandsIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.ExponentialMovingAverageIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.MacdIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.ModifiedExponentialMovingAverageIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.MomentumIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.MovingAverageIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.RelativeStrengthIndexIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.StochasticFastIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.indicators.UltimateOscillatorIndicator;
import com.telerik.widget.chart.visualization.cartesianChart.series.CartesianSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.OhlcSeries;
import com.telerik.widget.chart.visualization.common.ChartSeries;
import com.telerik.widget.primitives.legend.RadLegendView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class IndicatorsFragment extends ExampleFragmentBase implements View.OnClickListener {

    private static final byte LABELS_INTERVAL_BASE = 3;
    private static final byte DATA_RANGE_ONE_MONTH = 0;
    private static final byte DATA_RANGE_SIX_MONTHS = 1;
    private static final byte DATA_RANGE_ONE_YEAR = 2;
    private static final byte DATA_RANGE_FIVE_YEARS = 3;

    private static final String DATA_RANGE_NAME = "IndicatorsDataRange";
    private static final String CURRENT_INDICATOR_NAME = "IndicatorsCurrentIndicator";

    private static final String MA_CHECKED_NAME = "IndicatorsMAChecked";
    private static final String EXPONENTIAL_MA_CHECKED_NAME = "IndicatorsExponentialMAChecked";
    private static final String MODIFIED_EXPONENTIAL_MA_CHECKED_NAME = "IndicatorsModifiedExponentialMAChecked";
    private static final String BOLLINGER_BANDS_CHECKED_NAME = "IndicatorsBollingerBandsChecked";

    private static final byte MACD_INDICATOR_INDEX = 0;
    private static final byte AVERAGE_TRUE_RANGE_INDICATOR_INDEX = 1;
    private static final byte RELATIVE_STRENGTH_INDICATOR_INDEX = 2;
    private static final byte MOMENTUM_INDICATOR_INDEX = 3;
    private static final byte ULTIMATE_OSCILLATOR_INDICATOR_INDEX = 4;
    private static final byte STOCHASTIC_FAST_INDICATOR_INDEX = 5;

    private boolean maChecked;
    private boolean exponentialMAChecked;
    private boolean modifiedExponentialMAChecked = true;
    private boolean bollingerBandsChecked;

    private int currentDataRange = DATA_RANGE_SIX_MONTHS;
    private int currentIndicator = MACD_INDICATOR_INDEX;

    private Button rangeSelectBtn;
    private Dialog alertDialog;
    private ArrayList<ChartSeries> currentActiveSeries;
    private Resources resources;
    private Context context;
    private DataPointBinding openBinding;
    private DataPointBinding highBinding;
    private DataPointBinding lowBinding;
    private DataPointBinding closeBinding;
    private DataPointBinding categoryBinding;
    private DateTimeCategoricalAxis mainChartHorizontalAxis;

    private RadCartesianChartView mainChart;
    private RadCartesianChartView volumeChart;
    private RadCartesianChartView indicatorsChart;

    public IndicatorsFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(BOLLINGER_BANDS_CHECKED_NAME, this.bollingerBandsChecked);
        outState.putBoolean(EXPONENTIAL_MA_CHECKED_NAME, this.exponentialMAChecked);
        outState.putBoolean(MA_CHECKED_NAME, this.maChecked);
        outState.putBoolean(MODIFIED_EXPONENTIAL_MA_CHECKED_NAME, this.modifiedExponentialMAChecked);

        outState.putInt(DATA_RANGE_NAME, this.currentDataRange);
        outState.putInt(CURRENT_INDICATOR_NAME, this.currentIndicator);

        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_indicators, null);

        this.rangeSelectBtn = (Button) root.findViewById(R.id.range_selection_btn);

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }

        this.context = getActivity();
        this.resources = getResources();
        this.currentActiveSeries = new ArrayList<>();

        this.initDataPointBindings();

        this.mainChart = (RadCartesianChartView) root.findViewById(R.id.chart_main);
        prepareMainChart(this.mainChart);

        this.volumeChart = (RadCartesianChartView) root.findViewById(R.id.chart_volume);
        prepareVolumeChart(this.volumeChart);

        this.indicatorsChart = (RadCartesianChartView) root.findViewById(R.id.chart_indicator);
        prepareIndicatorsChart(this.indicatorsChart);

        MultipleChartsPanAndZoomBehavior mainPanZoomBehavior = new MultipleChartsPanAndZoomBehavior(this.mainChart, this.volumeChart, this.indicatorsChart);
        this.mainChart.getBehaviors().add(mainPanZoomBehavior);

        root.findViewById(R.id.range_selection_btn).setOnClickListener(this);
        root.findViewById(R.id.technicals_btn).setOnClickListener(this);

        RadLegendView indicatorLegend = (RadLegendView) root.findViewById(R.id.indicators_chart_legend);
        indicatorLegend.setLegendProvider(this.indicatorsChart);

        RadLegendView mainLegend = (RadLegendView) root.findViewById(R.id.main_chart_legend);
        mainLegend.setLegendProvider(this.mainChart);

        updateExample(this.currentDataRange);

        return root;
    }

    private void initDataPointBindings() {
        this.categoryBinding = new GenericDataPointBinding<>(new Function<FinancialDataClass, Calendar>() {
            @Override
            public Calendar apply(FinancialDataClass argument) {
                return argument.date;
            }
        });

        this.openBinding = new GenericDataPointBinding<>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.open;
            }
        });

        this.highBinding = new GenericDataPointBinding<>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.high;
            }
        });

        this.lowBinding = new GenericDataPointBinding<>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.low;
            }
        });

        this.closeBinding = new GenericDataPointBinding<>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.close;
            }
        });
    }

    private void showRangeSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.indicators_range_dialog_title));
        builder.setSingleChoiceItems(getResources().getStringArray(R.array.indicators_range_options), currentDataRange, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                updateData(item);
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAlertDialog() {
        alertDialog = new Dialog(context);

        alertDialog.setContentView(R.layout.indicators_custom_technicials_dialog);
        ViewGroup customDialog = (ViewGroup) alertDialog.findViewById(R.id.custom_dialog_view);

        // Design workaround
        if(customDialog != null &&
                customDialog.getParent() != null &&
                customDialog.getParent().getParent() != null &&
                customDialog.getParent().getParent().getParent() != null) {
            ViewGroup dialogRoot = ((ViewGroup) customDialog.getParent().getParent().getParent());
            if(dialogRoot.getChildCount() > 1) {
                dialogRoot.getChildAt(0).setVisibility(View.GONE);
                dialogRoot.getChildAt(1).setVisibility(View.GONE);
            }
        }

        customDialog.findViewById(R.id.cancel_btn).setOnClickListener(this);

        customDialog.findViewById(R.id.set_btn).setOnClickListener(this);

        ((CheckBox) customDialog.findViewById(R.id.ma)).setChecked(maChecked);
        ((CheckBox) customDialog.findViewById(R.id.exponential_ma)).setChecked(exponentialMAChecked);
        ((CheckBox) customDialog.findViewById(R.id.modified_exponential_ma)).setChecked(modifiedExponentialMAChecked);
        ((CheckBox) customDialog.findViewById(R.id.bollinger)).setChecked(bollingerBandsChecked);

        ((RadioButton) ((RadioGroup) customDialog.findViewById(R.id.indicators_radio_group)).getChildAt(currentIndicator)).setChecked(true);

        alertDialog.show();
    }

    private void cancelTechnicals() {
        this.alertDialog.dismiss();
    }

    private void okTechnicals() {
        ViewGroup customDialog = (ViewGroup) alertDialog.findViewById(R.id.custom_dialog_view);
        maChecked = ((CheckBox) customDialog.findViewById(R.id.ma)).isChecked();
        exponentialMAChecked = ((CheckBox) customDialog.findViewById(R.id.exponential_ma)).isChecked();
        modifiedExponentialMAChecked = ((CheckBox) customDialog.findViewById(R.id.modified_exponential_ma)).isChecked();
        bollingerBandsChecked = ((CheckBox) customDialog.findViewById(R.id.bollinger)).isChecked();

        for (int i = 0, len = ((RadioGroup) customDialog.findViewById(R.id.indicators_radio_group)).getChildCount(); i < len; i++)
            if (((RadioButton) ((RadioGroup) customDialog.findViewById(R.id.indicators_radio_group)).getChildAt(i)).isChecked()) {
                currentIndicator = i;
                break;
            }

        updateExample(currentDataRange);
        alertDialog.dismiss();
    }

    private void updateExample(int dataRange) {
        beginUpdate();
        updateMainChart(this.maChecked, this.exponentialMAChecked, this.modifiedExponentialMAChecked, this.bollingerBandsChecked);
        updateIndicatorsChart(this.currentIndicator);
        updateData(dataRange);
        endUpdate();
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        this.bollingerBandsChecked = savedInstanceState.getBoolean(BOLLINGER_BANDS_CHECKED_NAME);
        this.exponentialMAChecked = savedInstanceState.getBoolean(EXPONENTIAL_MA_CHECKED_NAME);
        this.maChecked = savedInstanceState.getBoolean(MA_CHECKED_NAME);
        this.modifiedExponentialMAChecked = savedInstanceState.getBoolean(MODIFIED_EXPONENTIAL_MA_CHECKED_NAME);

        this.currentDataRange = savedInstanceState.getInt(DATA_RANGE_NAME);
        this.currentIndicator = savedInstanceState.getInt(CURRENT_INDICATOR_NAME);
    }

    private void prepareMainChart(RadCartesianChartView chart) {
        this.mainChartHorizontalAxis = new DateTimeCategoricalAxis();
        this.mainChartHorizontalAxis.setDateTimeFormat(new SimpleDateFormat("MM/dd"));
        this.mainChartHorizontalAxis.setDateTimeComponent(DateTimeComponent.DATE);

        LinearAxis vAxis = new LinearAxis();
        vAxis.getLabelRenderer().setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                return String.format("%.2f", ((MajorTickModel) argument).value() / 100.0);
            }
        });

        OhlcSeries series = new OhlcSeries();
        series.setCategoryBinding(this.categoryBinding);
        series.setHighBinding(this.highBinding);
        series.setLowBinding(this.lowBinding);
        series.setOpenBinding(this.openBinding);
        series.setCloseBinding(this.closeBinding);
        series.setIsVisibleInLegend(false);

        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(this.mainChartHorizontalAxis);
        addSeriesToChart(series, chart);
        vAxis.setHorizontalLocation(AxisHorizontalLocation.RIGHT);

        ChartTrackBallBehavior trackBallBehavior = new ChartTrackBallBehavior(context);
        trackBallBehavior.contentAdapter().setValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                return String.format("%.2f", ((Number) argument).doubleValue());
            }
        });

        final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        trackBallBehavior.contentAdapter().setCategoryToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                return dateFormat.format(((Calendar) argument).getTime());
            }
        });

        chart.getBehaviors().add(trackBallBehavior);
    }

    private void prepareVolumeChart(RadCartesianChartView chart) {
        DateTimeCategoricalAxis hAxis = new DateTimeCategoricalAxis();
        hAxis.setDateTimeComponent(DateTimeComponent.DATE);

        LinearAxis vAxis = new LinearAxis();
        vAxis.getLabelRenderer().setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                return String.format("%s", (int) (((MajorTickModel) argument).value() / 1000000));
            }
        });
        vAxis.setLabelInterval(3);

        BarSeries series = new BarSeries();
        series.setCategoryBinding(this.categoryBinding);
        series.setValueBinding(new GenericDataPointBinding<FinancialDataClass, Float>(new Function<FinancialDataClass, Float>() {
            @Override
            public Float apply(FinancialDataClass argument) {
                return argument.volume;
            }
        }));

        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(hAxis);
        addSeriesToChart(series, chart);

        vAxis.setHorizontalLocation(AxisHorizontalLocation.RIGHT);
        hAxis.setShowLabels(false);
        hAxis.setTickColor(Color.TRANSPARENT);
    }

    private void prepareIndicatorsChart(RadCartesianChartView chart) {
        DateTimeCategoricalAxis hAxis = new DateTimeCategoricalAxis();
        hAxis.setDateTimeComponent(DateTimeComponent.DATE);

        LinearAxis vAxis = new LinearAxis();

        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(hAxis);

        hAxis.setPlotMode(AxisPlotMode.BETWEEN_TICKS);
        hAxis.setShowLabels(false);
        hAxis.setTickColor(Color.TRANSPARENT);
        vAxis.setHorizontalLocation(AxisHorizontalLocation.RIGHT);
        vAxis.setLabelInterval(3);
    }

    private void clearSeries(RadCartesianChartView chart) {
        for (CartesianSeries series : chart.getSeries())
            this.currentActiveSeries.remove(series);
        chart.getSeries().clear();
    }

    private void addSeriesToChart(CartesianSeries series, RadCartesianChartView chart) {
        chart.getSeries().add(series);
        this.currentActiveSeries.add(series);
    }

    private void updateData(int range) {
        for (ChartSeries series : this.currentActiveSeries) {
            series.setData(data(range));
        }

        this.currentDataRange = range;
        this.rangeSelectBtn.setText(getResources().getStringArray(R.array.indicators_range_button_text)[range]);
    }

    private void updateMainChart(boolean maChecked, boolean exponentialMAChecked, boolean modifiedExponentialMAChecked, boolean bollingerBandsChecked) {
        CartesianSeries mainSeries = this.mainChart.getSeries().get(0);
        clearSeries(this.mainChart);
        addSeriesToChart(mainSeries, this.mainChart);

        if (maChecked) {
            addSeriesToChart(createMovingAverageIndicator(), this.mainChart);
        }

        if (exponentialMAChecked) {
            addSeriesToChart(createExponentialMovingAverageIndicator(), this.mainChart);
        }

        if (modifiedExponentialMAChecked) {
            addSeriesToChart(createModifiedExponentialMovingAverageIndicator(), this.mainChart);
        }

        if (bollingerBandsChecked) {
            addSeriesToChart(createBollingerBandsIndicator(), this.mainChart);
        }
    }

    private void updateIndicatorsChart(int selectedIndicatorIndex) {
        clearSeries(this.indicatorsChart);

        switch (selectedIndicatorIndex) {
            case MACD_INDICATOR_INDEX:
                addSeriesToChart(createMACDIndicator(), this.indicatorsChart);
                break;
            case AVERAGE_TRUE_RANGE_INDICATOR_INDEX:
                addSeriesToChart(createAverageTrueRangeIndicator(), this.indicatorsChart);
                break;
            case RELATIVE_STRENGTH_INDICATOR_INDEX:
                addSeriesToChart(createRelativeStrengthIndexIndicator(), this.indicatorsChart);
                break;
            case MOMENTUM_INDICATOR_INDEX:
                addSeriesToChart(createMomentumIndicator(), this.indicatorsChart);
                break;
            case ULTIMATE_OSCILLATOR_INDICATOR_INDEX:
                addSeriesToChart(createUltimateOscillator(), this.indicatorsChart);
                break;
            case STOCHASTIC_FAST_INDICATOR_INDEX:
                addSeriesToChart(createStochasticFastIndicator(), this.indicatorsChart);
                break;
            default:
                throw new IllegalArgumentException("unknown indicator type");
        }

        this.currentIndicator = selectedIndicatorIndex;
    }

    private Iterable<FinancialDataClass> data(int range) {
        Iterable<FinancialDataClass> data = null;
        if (range == DATA_RANGE_ONE_MONTH) {
            data = ExampleDataProvider.indicatorsDataOneMonth(this.resources);
            this.mainChartHorizontalAxis.setMajorTickInterval(LABELS_INTERVAL_BASE);
        } else if (range == DATA_RANGE_SIX_MONTHS) {
            data = ExampleDataProvider.indicatorsDataSixMonths(this.resources);
            this.mainChartHorizontalAxis.setMajorTickInterval(LABELS_INTERVAL_BASE * 6);
        } else if (range == DATA_RANGE_ONE_YEAR) {
            data = ExampleDataProvider.indicatorsDataOneYear(this.resources);
            this.mainChartHorizontalAxis.setMajorTickInterval(LABELS_INTERVAL_BASE * 12);
        } else if (range == DATA_RANGE_FIVE_YEARS) {
            data = ExampleDataProvider.indicatorsDataFiveYears(this.resources);
            this.mainChartHorizontalAxis.setMajorTickInterval(LABELS_INTERVAL_BASE * 12 * 5);
        }

        return data;
    }

    private void endUpdate() {
        this.mainChart.endUpdate();
        this.volumeChart.endUpdate();
        this.indicatorsChart.endUpdate();
    }

    private void beginUpdate() {
        this.mainChart.beginUpdate();
        this.volumeChart.beginUpdate();
        this.indicatorsChart.beginUpdate();
    }

    private CartesianSeries createMACDIndicator() {
        MacdIndicator indicator = new MacdIndicator();
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLongPeriod(12);
        indicator.setShortPeriod(9);
        indicator.setSignalPeriod(6);
        indicator.setLegendTitle("MACD (12, 9, 6)");

        return indicator;
    }

    private CartesianSeries createStochasticFastIndicator() {
        StochasticFastIndicator indicator = new StochasticFastIndicator();
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setCloseBinding(closeBinding);
        indicator.setHighBinding(highBinding);
        indicator.setLowBinding(lowBinding);
        indicator.setMainPeriod(8);
        indicator.setSignalPeriod(6);
        indicator.setLegendTitle("Stochastic Fast (8, 6)");

        return indicator;
    }

    private CartesianSeries createUltimateOscillator() {
        UltimateOscillatorIndicator indicator = new UltimateOscillatorIndicator();
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setCloseBinding(this.closeBinding);
        indicator.setHighBinding(this.highBinding);
        indicator.setLowBinding(this.lowBinding);
        indicator.setPeriod(2);
        indicator.setPeriod2(5);
        indicator.setPeriod3(8);
        indicator.setLegendTitle("Ultimate Oscillator (2, 5, 8)");

        return indicator;
    }

    private CartesianSeries createMomentumIndicator() {
        MomentumIndicator indicator = new MomentumIndicator();
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setPeriod(5);
        indicator.setLegendTitle("Momentum (5)");

        return indicator;
    }

    private CartesianSeries createRelativeStrengthIndexIndicator() {
        RelativeStrengthIndexIndicator indicator = new RelativeStrengthIndexIndicator();
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setPeriod(5);
        indicator.setLegendTitle("Relative Strength Index (5)");

        return indicator;
    }

    private CartesianSeries createAverageTrueRangeIndicator() {
        AverageTrueRangeIndicator indicator = new AverageTrueRangeIndicator();
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setCloseBinding(this.closeBinding);
        indicator.setHighBinding(this.highBinding);
        indicator.setLowBinding(this.lowBinding);
        indicator.setPeriod(5);
        indicator.setLegendTitle("Average True Range (5)");

        return indicator;
    }

    private BollingerBandsIndicator createBollingerBandsIndicator() {
        BollingerBandsIndicator indicator = new BollingerBandsIndicator();
        indicator.setPeriod(5);
        indicator.setStandardDeviations(2);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLegendTitle("Bollinger Bands (5, 2)");

        return indicator;
    }

    private ModifiedExponentialMovingAverageIndicator createModifiedExponentialMovingAverageIndicator() {
        ModifiedExponentialMovingAverageIndicator indicator = new ModifiedExponentialMovingAverageIndicator();
        indicator.setPeriod(5);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLegendTitle("Modified Exponential MA (5)");

        return indicator;
    }

    private ExponentialMovingAverageIndicator createExponentialMovingAverageIndicator() {
        ExponentialMovingAverageIndicator indicator = new ExponentialMovingAverageIndicator();
        indicator.setPeriod(5);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLegendTitle("Exponential MA (5)");

        return indicator;
    }

    private MovingAverageIndicator createMovingAverageIndicator() {
        MovingAverageIndicator indicator = new MovingAverageIndicator();
        indicator.setPeriod(5);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLegendTitle("Moving Average (5)");

        return indicator;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.technicals_btn) {
            this.showAlertDialog();
        } else if (v.getId() == R.id.range_selection_btn) {
            this.showRangeSelectionDialog();
        } else if (v.getId() == R.id.cancel_btn) {
            this.cancelTechnicals();
        } else if (v.getId() == R.id.set_btn) {
            this.okTechnicals();
        }
    }

    private class MultipleChartsPanAndZoomBehavior extends ChartPanAndZoomBehavior {
        private RadCartesianChartView[] charts;
        private RadCartesianChartView mainChart;

        public MultipleChartsPanAndZoomBehavior(RadCartesianChartView mainChart, RadCartesianChartView... charts) {
            this.mainChart = mainChart;
            this.charts = charts;
            this.setZoomStrategy(ChartZoomStrategy.DEFERRED);
        }

        @Override
        public void onPinchComplete() {
            super.onPinchComplete();

            for (RadCartesianChartView chart : this.charts) {
                chart.setZoom(this.mainChart.getZoomWidth(), 1);
                chart.setPanOffset(this.mainChart.getPanOffsetX(), 0);
            }
        }

        @Override
        public void setZoomToChart(double scaleX, double scaleY, double centerX, double centerY) {
            super.setZoomToChart(scaleX, scaleY, centerX, centerY);

            for (RadCartesianChartView chart : this.charts) {
                chart.setZoom(this.mainChart.getZoomWidth(), 1);
                chart.setPanOffset(this.mainChart.getPanOffsetX(), 0);
            }
        }

        @Override
        public void setPanOffsetToChart(double offsetX, double offsetY) {
            super.setPanOffsetToChart(offsetX, offsetY);
            for (RadCartesianChartView chart : this.charts) {
                chart.setPanOffset(this.mainChart.getPanOffsetX(), 0);
            }
        }
    }
}
