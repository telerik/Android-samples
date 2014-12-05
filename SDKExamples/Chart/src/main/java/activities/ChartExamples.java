package activities;

import com.telerik.widget.chart.visualization.cartesianChart.axes.DateTimeContinuousAxis;

import java.util.ArrayList;
import java.util.HashMap;

import fragments.annotations.GridLineAnnotationFragment;
import fragments.annotations.PlotBandAnnotationFragment;
import fragments.axes.DateTimeContinuousAxisFragment;
import fragments.axes.MultipleAxesFragment;
import fragments.behaviors.PanAndZoomFragment;
import fragments.behaviors.SelectionBehaviorFragment;
import fragments.behaviors.TooltipBehaviorFragment;
import fragments.behaviors.TrackBallBehaviorFragment;
import fragments.features.ChartLegendFragment;
import fragments.features.GridFeatureFragment;
import fragments.features.PalettesFragment;
import fragments.series.AreaSeriesFragment;
import fragments.series.CandleStickSeriesFragment;
import fragments.series.DoughnutSeriesFragment;
import fragments.series.HorizontalBarSeriesFragment;
import fragments.series.IndicatorSeriesFragment;
import fragments.series.LineSeriesFragment;
import fragments.series.OhlcSeriesFragment;
import fragments.series.PieSeriesFragment;
import fragments.series.ScatterBubbleSeriesFragment;
import fragments.series.ScatterPointSeriesFragment;
import fragments.series.SplineAreaSeriesFragment;
import fragments.series.SplineSeriesFragment;
import fragments.series.StackAreaSeriesFragment;
import fragments.series.StackBarSeriesFragment;
import fragments.series.StackSplineAreaSeriesFragment;
import fragments.series.VerticalBarSeriesFragment;

/**
 * Created by ginev on 12/5/2014.
 */
public class ChartExamples implements ExamplesProvider {

    private HashMap<String, ArrayList<ExampleFragment>> chartExamples;

    public ChartExamples(){
        this.chartExamples = this.getChartExamples();
    }

    @Override
    public String controlName() {
        return "Chart";
    }

    @Override
    public HashMap<String, ArrayList<ExampleFragment>> examples(){
        return this.chartExamples;
    }

    private HashMap<String, ArrayList<ExampleFragment>> getChartExamples(){
        HashMap<String, ArrayList<ExampleFragment>> chartExamples = new HashMap<String, ArrayList<ExampleFragment>>();

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
        result.add(new StackBarSeriesFragment());
        result.add(new StackSplineAreaSeriesFragment());
        result.add(new VerticalBarSeriesFragment());

        chartExamples.put("Series", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new ChartLegendFragment());
        result.add(new GridFeatureFragment());
        result.add(new PalettesFragment());

        chartExamples.put("Features", result);

        result.add(new PanAndZoomFragment());
        result.add(new SelectionBehaviorFragment());
        result.add(new TooltipBehaviorFragment());
        result.add(new TrackBallBehaviorFragment());

        chartExamples.put("Behaviors", result);

        result.add(new PanAndZoomFragment());
        result.add(new SelectionBehaviorFragment());
        result.add(new TooltipBehaviorFragment());
        result.add(new TrackBallBehaviorFragment());

        chartExamples.put("Behaviors", result);

        result.add(new DateTimeContinuousAxisFragment());
        result.add(new MultipleAxesFragment());

        chartExamples.put("Axes", result);

        result.add(new GridLineAnnotationFragment());
        result.add(new PlotBandAnnotationFragment());

        chartExamples.put("Annotations", result);

        return chartExamples;
    }
}
