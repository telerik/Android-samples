package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.chart.annotations.GridLineAnnotationFragment;
import fragments.chart.annotations.PlotBandAnnotationFragment;
import fragments.chart.axes.DateTimeContinuousAxisFragment;
import fragments.chart.axes.MultipleAxesFragment;
import fragments.chart.behaviors.PanAndZoomFragment;
import fragments.chart.behaviors.SelectionBehaviorFragment;
import fragments.chart.behaviors.TooltipBehaviorFragment;
import fragments.chart.behaviors.TrackBallBehaviorFragment;
import fragments.chart.features.ChartLegendFragment;
import fragments.chart.features.GridFeatureFragment;
import fragments.chart.features.PalettesFragment;
import fragments.chart.series.AreaSeriesFragment;
import fragments.chart.series.CandleStickSeriesFragment;
import fragments.chart.series.DoughnutSeriesFragment;
import fragments.chart.series.HorizontalBarSeriesFragment;
import fragments.chart.series.IndicatorSeriesFragment;
import fragments.chart.series.LineSeriesFragment;
import fragments.chart.series.OhlcSeriesFragment;
import fragments.chart.series.PieSeriesFragment;
import fragments.chart.series.ScatterBubbleSeriesFragment;
import fragments.chart.series.ScatterPointSeriesFragment;
import fragments.chart.series.SplineAreaSeriesFragment;
import fragments.chart.series.SplineSeriesFragment;
import fragments.chart.series.StackAreaSeriesFragment;
import fragments.chart.series.StackBarSeriesFragment;
import fragments.chart.series.StackSplineAreaSeriesFragment;
import fragments.chart.series.VerticalBarSeriesFragment;

public class ChartExamples implements ExamplesProvider {

    private LinkedHashMap<String, ArrayList<ExampleFragment>> chartExamples;

    public ChartExamples(){
        this.chartExamples = this.getChartExamples();
    }

    @Override
    public String controlName() {
        return "Chart";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples(){
        return this.chartExamples;
    }

    private LinkedHashMap<String, ArrayList<ExampleFragment>> getChartExamples(){
        LinkedHashMap<String, ArrayList<ExampleFragment>> chartExamples = new LinkedHashMap<String, ArrayList<ExampleFragment>>();

        ArrayList<ExampleFragment> result = new ArrayList<ExampleFragment>();

        result.add(new AreaSeriesFragment());
        result.add(new LineSeriesFragment());
        result.add(new CandleStickSeriesFragment());
        result.add(new DoughnutSeriesFragment());
        result.add(new HorizontalBarSeriesFragment());
        result.add(new IndicatorSeriesFragment());
        result.add(new OhlcSeriesFragment());
        result.add(new PieSeriesFragment());
        result.add(new ScatterBubbleSeriesFragment());
        result.add(new ScatterPointSeriesFragment());
        result.add(new SplineAreaSeriesFragment());
        result.add(new SplineSeriesFragment());
        result.add(new StackAreaSeriesFragment());
        result.add(new StackBarSeriesFragment());
        result.add(new StackSplineAreaSeriesFragment());
        result.add(new VerticalBarSeriesFragment());

        chartExamples.put("Series", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new ChartLegendFragment());
        result.add(new GridFeatureFragment());
        result.add(new PalettesFragment());

        chartExamples.put("Features", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new PanAndZoomFragment());
        result.add(new SelectionBehaviorFragment());
        result.add(new TooltipBehaviorFragment());
        result.add(new TrackBallBehaviorFragment());

        chartExamples.put("Behaviors", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new DateTimeContinuousAxisFragment());
        result.add(new MultipleAxesFragment());

        chartExamples.put("Axes", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new GridLineAnnotationFragment());
        result.add(new PlotBandAnnotationFragment());

        chartExamples.put("Annotations", result);

        return chartExamples;
    }
}
