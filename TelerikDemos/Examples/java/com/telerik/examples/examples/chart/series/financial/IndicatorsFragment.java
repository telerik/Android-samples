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
import com.telerik.widget.chart.visualization.behaviors.ChartPanAndZoomBehavior;
import com.telerik.widget.chart.visualization.behaviors.ChartPanZoomMode;
import com.telerik.widget.chart.visualization.behaviors.ChartTrackBallBehavior;
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
import java.util.List;

public class IndicatorsFragment extends ExampleFragmentBase {

    private boolean maChecked;
    private boolean exponentialMAChecked;
    private boolean modifiedExponentialMAChecked;
    private boolean bollingerBandsChecked;
    private AlertDialog alertDialog;
    private ArrayList<ChartSeries> currentActiveSeries;
    private Resources resources;
    private Context context;
    private int currentDataRange = -1;
    private int currentIndicator = 0;
    private DataPointBinding openBinding;
    private DataPointBinding highBinding;
    private DataPointBinding lowBinding;
    private DataPointBinding closeBinding;
    private DataPointBinding categoryBinding;
    private Iterable<FinancialDataClass> data;
    private byte labelsIntervalBase = 3;
    DateTimeCategoricalAxis mainChartHorizontalAxis;

    private RadCartesianChartView mainChart;
    private RadCartesianChartView volumeChart;
    private RadCartesianChartView indicatorsChart;

    public IndicatorsFragment() {
    }

    private Iterable<FinancialDataClass> data(int range) {
        if (this.currentDataRange != range) {
            if (range == 0) {
                this.data = ExampleDataProvider.indicatorsDataOneMonth(this.resources);
                this.mainChartHorizontalAxis.setMajorTickInterval(this.labelsIntervalBase);
            } else if (range == 1) {
                this.data = ExampleDataProvider.indicatorsDataSixMonths(this.resources);
                this.mainChartHorizontalAxis.setMajorTickInterval(this.labelsIntervalBase * 6);
            } else if (range == 2) {
                this.data = ExampleDataProvider.indicatorsDataOneYear(this.resources);
                this.mainChartHorizontalAxis.setMajorTickInterval(this.labelsIntervalBase * 12);
            } else if (range == 3) {
                this.data = ExampleDataProvider.indicatorsDataFiveYears(this.resources);
                this.mainChartHorizontalAxis.setMajorTickInterval(this.labelsIntervalBase * 12 * 5);
            }

            this.currentDataRange = range;
        }

        return this.data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_indicators, null);

        this.context = getActivity();
        this.resources = getResources();
        this.currentActiveSeries = new ArrayList<ChartSeries>();

        this.categoryBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((FinancialDataClass) instance).date;
            }
        };

        this.openBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((FinancialDataClass) instance).open;
            }
        };

        this.highBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((FinancialDataClass) instance).high;
            }
        };

        this.lowBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((FinancialDataClass) instance).low;
            }
        };

        this.closeBinding = new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((FinancialDataClass) instance).close;
            }
        };

        this.mainChart = (RadCartesianChartView) root.findViewById(R.id.chart_main);
        prepareMainChart(this.mainChart);

        this.volumeChart = (RadCartesianChartView) root.findViewById(R.id.chart_volume);
        prepareVolumeChart(this.volumeChart);

        this.indicatorsChart = (RadCartesianChartView) root.findViewById(R.id.chart_indicator);
        prepareIndicatorsChart(this.indicatorsChart);

        MultipleChartsPanAndZoomBehavior mainPanZoomBehavior = new MultipleChartsPanAndZoomBehavior();
        MultipleChartsPanAndZoomBehavior volumePanZoomBehavior = new MultipleChartsPanAndZoomBehavior();
        MultipleChartsPanAndZoomBehavior indicatorsPanZoomBehavior = new MultipleChartsPanAndZoomBehavior();

        this.mainChart.getBehaviors().add(mainPanZoomBehavior);
        this.volumeChart.getBehaviors().add(volumePanZoomBehavior);
        this.indicatorsChart.getBehaviors().add(indicatorsPanZoomBehavior);

        mainPanZoomBehavior.addBehavior(volumePanZoomBehavior);
        mainPanZoomBehavior.addBehavior(indicatorsPanZoomBehavior);

        volumePanZoomBehavior.addBehavior(mainPanZoomBehavior);
        volumePanZoomBehavior.addBehavior(indicatorsPanZoomBehavior);
        volumePanZoomBehavior.setZoomMode(ChartPanZoomMode.HORIZONTAL);
        volumePanZoomBehavior.setPanMode(ChartPanZoomMode.HORIZONTAL);

        indicatorsPanZoomBehavior.addBehavior(mainPanZoomBehavior);
        indicatorsPanZoomBehavior.addBehavior(volumePanZoomBehavior);
        indicatorsPanZoomBehavior.setZoomMode(ChartPanZoomMode.HORIZONTAL);
        indicatorsPanZoomBehavior.setPanMode(ChartPanZoomMode.HORIZONTAL);

        root.findViewById(R.id.range_selection_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(getResources().getString(R.string.indicators_range_dialog_title));
                builder.setSingleChoiceItems(getResources().getStringArray(R.array.indicators_range_options), currentDataRange, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        beginUpdate();
                        resetChartsPanZoom();
                        updateData(item);
                        endUpdate();
                        ((Button) getActivity().findViewById(R.id.range_selection_btn)).setText(getResources().getStringArray(R.array.indicators_range_button_text)[item]);
                        alertDialog.dismiss();
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        root.findViewById(R.id.technicials_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog alertDialog = new Dialog(context);

                alertDialog.setContentView(R.layout.indicators_custom_technicials_dialog);
                final ViewGroup g = (ViewGroup) alertDialog.findViewById(R.id.custom_dialog_view);
                ViewGroup dialogRoot = ((ViewGroup) g.getParent().getParent().getParent());
                dialogRoot.getChildAt(0).setVisibility(View.GONE);
                dialogRoot.getChildAt(1).setVisibility(View.GONE);

                g.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                g.findViewById(R.id.set_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        maChecked = ((CheckBox) g.findViewById(R.id.ma)).isChecked();
                        exponentialMAChecked = ((CheckBox) g.findViewById(R.id.exponential_ma)).isChecked();
                        modifiedExponentialMAChecked = ((CheckBox) g.findViewById(R.id.modified_exponential_ma)).isChecked();
                        bollingerBandsChecked = ((CheckBox) g.findViewById(R.id.bollinger)).isChecked();

                        int selectedIndicatorIndex = 0;
                        for (int i = 0, len = ((RadioGroup) g.findViewById(R.id.indicators_radio_group)).getChildCount(); i < len; i++)
                            if (((RadioButton) ((RadioGroup) g.findViewById(R.id.indicators_radio_group)).getChildAt(i)).isChecked()) {
                                selectedIndicatorIndex = i;
                                break;
                            }

                        beginUpdate();
                        resetChartsPanZoom();
                        updateMainChart(maChecked, exponentialMAChecked, modifiedExponentialMAChecked, bollingerBandsChecked);
                        updateIndicatorsChart(selectedIndicatorIndex);
                        updateData(currentDataRange);
                        endUpdate();
                        alertDialog.dismiss();
                    }
                });

                ((CheckBox) g.findViewById(R.id.ma)).setChecked(maChecked);
                ((CheckBox) g.findViewById(R.id.exponential_ma)).setChecked(exponentialMAChecked);
                ((CheckBox) g.findViewById(R.id.modified_exponential_ma)).setChecked(modifiedExponentialMAChecked);
                ((CheckBox) g.findViewById(R.id.bollinger)).setChecked(bollingerBandsChecked);

                ((RadioButton) ((RadioGroup) g.findViewById(R.id.indicators_radio_group)).getChildAt(currentIndicator)).setChecked(true);

                alertDialog.show();
            }
        });

        RadLegendView indicatorLegend = (RadLegendView) root.findViewById(R.id.indicators_chart_legend);
        indicatorLegend.setLegendProvider(this.indicatorsChart);

        RadLegendView mainLegend = (RadLegendView) root.findViewById(R.id.main_chart_legend);
        mainLegend.setLegendProvider(this.mainChart);

        updateData(1);

        return root;
    }

    private void resetChartsPanZoom() {
        this.mainChart.setZoom(1, 1);
        this.mainChart.setPanOffset(0, 0);

        this.volumeChart.setZoom(1, 1);
        this.volumeChart.setPanOffset(0, 0);

        this.indicatorsChart.setZoom(1, 1);
        this.indicatorsChart.setPanOffset(0, 0);
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

    private void updateIndicatorsChart(int selectedIndicatorIndex) {
        if (this.currentIndicator == selectedIndicatorIndex)
            return;

        clearSeries(this.indicatorsChart);

        switch (selectedIndicatorIndex) {
            case 0:
                addSeriesToChart(createMACDIndicator(), this.indicatorsChart);
                break;
            case 1:
                addSeriesToChart(createAverageTrueRangeIndicator(), this.indicatorsChart);
                break;
            case 2:
                addSeriesToChart(createRelativeStrengthIndexIndicator(), this.indicatorsChart);
                break;
            case 3:
                addSeriesToChart(createMomentumIndicator(), this.indicatorsChart);
                break;
            case 4:
                addSeriesToChart(createUltimateOscillator(), this.indicatorsChart);
                break;
            case 5:
                addSeriesToChart(createStochasticFastIndicator(), this.indicatorsChart);
                break;
            default:
                throw new IllegalArgumentException("unknown indicator");
        }

        this.currentIndicator = selectedIndicatorIndex;
    }

    private CartesianSeries createMACDIndicator() {
        MacdIndicator indicator = new MacdIndicator(context);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.lowBinding);
        indicator.setLongPeriod(12);
        indicator.setShortPeriod(9);
        indicator.setSignalPeriod(6);
        indicator.setLegendTitle("MACD (12, 9, 6)");

        return indicator;
    }

    private CartesianSeries createStochasticFastIndicator() {
        StochasticFastIndicator indicator = new StochasticFastIndicator(context);
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
        UltimateOscillatorIndicator indicator = new UltimateOscillatorIndicator(context);
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
        MomentumIndicator indicator = new MomentumIndicator(context);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setPeriod(5);
        indicator.setLegendTitle("Momentum (5)");

        return indicator;
    }

    private CartesianSeries createRelativeStrengthIndexIndicator() {
        RelativeStrengthIndexIndicator indicator = new RelativeStrengthIndexIndicator(context);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setPeriod(5);
        indicator.setLegendTitle("Relative Strength Index (5)");

        return indicator;
    }

    private CartesianSeries createAverageTrueRangeIndicator() {
        AverageTrueRangeIndicator indicator = new AverageTrueRangeIndicator(context);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setCloseBinding(this.closeBinding);
        indicator.setHighBinding(this.highBinding);
        indicator.setLowBinding(this.lowBinding);
        indicator.setPeriod(5);
        indicator.setLegendTitle("Average True Range (5)");

        return indicator;
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

    private BollingerBandsIndicator createBollingerBandsIndicator() {
        BollingerBandsIndicator indicator = new BollingerBandsIndicator(context);
        indicator.setPeriod(5);
        indicator.setStandardDeviations(2);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLegendTitle("Bollinger Bands (5, 2)");
        return indicator;
    }

    private ModifiedExponentialMovingAverageIndicator createModifiedExponentialMovingAverageIndicator() {
        ModifiedExponentialMovingAverageIndicator indicator = new ModifiedExponentialMovingAverageIndicator(context);
        indicator.setPeriod(5);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLegendTitle("Modified Exponential MA (5)");
        return indicator;
    }

    private ExponentialMovingAverageIndicator createExponentialMovingAverageIndicator() {
        ExponentialMovingAverageIndicator indicator = new ExponentialMovingAverageIndicator(context);
        indicator.setPeriod(5);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLegendTitle("Exponential MA (5)");
        return indicator;
    }

    private MovingAverageIndicator createMovingAverageIndicator() {
        MovingAverageIndicator indicator = new MovingAverageIndicator(context);
        indicator.setPeriod(5);
        indicator.setCategoryBinding(this.categoryBinding);
        indicator.setValueBinding(this.closeBinding);
        indicator.setLegendTitle("Moving Average (5)");
        return indicator;
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
        for (ChartSeries series : this.currentActiveSeries)
            series.setData(data(range));
    }

    private void prepareMainChart(RadCartesianChartView chart) {
        this.mainChartHorizontalAxis = new DateTimeCategoricalAxis(context);
        this.mainChartHorizontalAxis.setDateTimeFormat(new SimpleDateFormat("MM/dd"));
        this.mainChartHorizontalAxis.setDateTimeComponent(DateTimeComponent.DATE);

        LinearAxis vAxis = new LinearAxis(context);
        vAxis.getLabelRenderer().setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                return String.format("%s", Math.floor(((MajorTickModel) argument).value() / 100));
            }
        });

        OhlcSeries series = new OhlcSeries(context);
        series.setCategoryBinding(this.categoryBinding);
        series.setHighBinding(this.highBinding); // h
        series.setLowBinding(this.lowBinding); // l
        series.setOpenBinding(this.openBinding); // o
        series.setCloseBinding(this.closeBinding); // c
        series.setIsVisibleInLegend(false);

        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(this.mainChartHorizontalAxis);
        addSeriesToChart(series, chart);
        addSeriesToChart(createModifiedExponentialMovingAverageIndicator(), chart);
        this.modifiedExponentialMAChecked = true;
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
        DateTimeCategoricalAxis hAxis = new DateTimeCategoricalAxis(context);

        LinearAxis vAxis = new LinearAxis(context);
        vAxis.getLabelRenderer().setLabelValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                return String.format("%s", (int) (((MajorTickModel) argument).value() / 1000000));
            }
        });
        vAxis.setLabelInterval(3);

        BarSeries series = new BarSeries(context);
        series.setCategoryBinding(this.categoryBinding);
        series.setValueBinding(new DataPointBinding() {
            @Override
            public Object getValue(Object instance) throws IllegalArgumentException {
                return ((FinancialDataClass) instance).volume;
            }
        });

        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(hAxis);
        addSeriesToChart(series, chart);

        vAxis.setHorizontalLocation(AxisHorizontalLocation.RIGHT);
        hAxis.setShowLabels(false);
        hAxis.setTickColor(Color.TRANSPARENT);
    }

    private void prepareIndicatorsChart(RadCartesianChartView chart) {
        DateTimeCategoricalAxis hAxis = new DateTimeCategoricalAxis(context);

        LinearAxis vAxis = new LinearAxis(context);

        chart.setVerticalAxis(vAxis);
        chart.setHorizontalAxis(hAxis);
        addSeriesToChart(createMACDIndicator(), chart);

        hAxis.setPlotMode(AxisPlotMode.BETWEEN_TICKS);
        hAxis.setShowLabels(false);
        hAxis.setTickColor(Color.TRANSPARENT);
        vAxis.setHorizontalLocation(AxisHorizontalLocation.RIGHT);
        vAxis.setLabelInterval(3);
    }

    private class MultipleChartsPanAndZoomBehavior extends ChartPanAndZoomBehavior {
        private List<MultipleChartsPanAndZoomBehavior> behaviors = new ArrayList<MultipleChartsPanAndZoomBehavior>();

        public void addBehavior(MultipleChartsPanAndZoomBehavior chart) {
            this.behaviors.add(chart);
        }

        @Override
        public void setZoomToChart(double scale) {
            this.setZoomToChart(scale, 0);
        }

        public void setZoomToChart(double scale, int level) {
            if (level > 1)
                return;

            for (int i = 0, len = this.behaviors.size(); i < len; i++)
                this.behaviors.get(i).setZoomToChart(scale, level + 1);

            super.setZoomToChart(scale);
        }

        @Override
        public void setPanOffsetToChart(double offsetX, double offsetY) {
            this.setPanOffsetToChart(offsetX, offsetY, 0);
        }

        public void setPanOffsetToChart(double offsetX, double offsetY, int level) {
            if (level > 1)
                return;

            if (this.getPanMode() == ChartPanZoomMode.HORIZONTAL)
                offsetY = 0;

            for (int i = 0, len = this.behaviors.size(); i < len; i++)
                this.behaviors.get(i).setPanOffsetToChart(offsetX, offsetY, level + 1);

            super.setPanOffsetToChart(offsetX, offsetY);
        }
    }
}
