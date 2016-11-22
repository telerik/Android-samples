using System;
using Java.Util;

namespace Samples
{
	public class ChartExamples : Java.Lang.Object, ExamplesProvider {

		private LinkedHashMap chartExamples;
        public static double[] randomDoubleValues = new double[] { 52.00d, 60.00d, 77.00d, 50.00d, 56.00d, 75.00d, 85.00d, 15.00d, 25.00d, 5.00d, 10.00d, 20.00d, 30.00d, 40.00d, 45.00d, 60.00d, 80.00d, 50.00d, 35.00d, 70.00d, 77.00d};
        public static int[] randomIntValues = new int[] { 70, 60, 30, 40, 50, 20, 5, 10, 15, 20, 25, 35, 55, 65, 75, 85, 80, 10, 15, 20, 30, 45, 60, 80, 50, 55, 70, 60, 30, 40, 50, 20, 5, 10, 15, 20, 25, 35, 55, 65, 75, 85, 80, 10, 15, 20, 30, 45, 60, 80, 50, 55, 70, 60, 30, 40, 50, 20, 5, 10, 15, 20, 25, 35, 55, 65, 75, 85, 80, 10, 15, 20, 30, 45, 60, 80, 50, 55, 70, 60, 30, 40, 50, 20, 5, 10, 15, 20, 25, 35, 55, 65, 75, 85, 80, 10, 15, 20, 30, 45, 60, 80, 50, 55 };

        public ChartExamples(){

			this.chartExamples = this.getChartExamples();
		}


		public String ControlName() {
			return "Chart";
		}


		public LinkedHashMap Examples(){
			return this.chartExamples;
		}

		private LinkedHashMap getChartExamples(){
			LinkedHashMap chartExamples = new LinkedHashMap();

			ArrayList result = new ArrayList();

			result.Add(new AreaSeriesFragment());
			result.Add(new LineSeriesFragment());
			result.Add(new CandleStickSeriesFragment());
			result.Add(new DoughnutSeriesFragment());
			result.Add(new HorizontalBarSeriesFragment());
			result.Add(new IndicatorSeriesFragment());
			result.Add(new OhlcSeriesFragment());
			result.Add(new PieSeriesFragment());
			result.Add(new ScatterBubbleSeriesFragment());
			result.Add(new ScatterPointSeriesFragment());
			result.Add(new SplineAreaSeriesFragment());
			result.Add(new SplineSeriesFragment());
			result.Add(new StackAreaSeriesFragment());
			result.Add(new StackBarSeriesFragment());
			result.Add(new StackSplineAreaSeriesFragment());
			result.Add(new VerticalBarSeriesFragment());

			chartExamples.Put("Series", result);

			result = new ArrayList();

			result.Add(new ChartLegendFragment());
			result.Add(new GridFeatureFragment());
			result.Add(new PalettesFragment());

			chartExamples.Put("Features", result);

			result = new ArrayList();

			result.Add(new PanAndZoomFragment());
			result.Add(new SelectionBehaviorFragment());
			result.Add(new TooltipBehaviorFragment());
			result.Add(new TrackballBehaviorFragment());

			chartExamples.Put("Behaviors", result);

			result = new ArrayList();

			result.Add(new DateTimeContinuousAxisFragment());
			result.Add(new MultipleAxesFragment());

			chartExamples.Put("Axes", result);

			result = new ArrayList();

			result.Add(new GridLineAnnotationFragment());
			result.Add(new PlotBandAnnotationFragment());

			chartExamples.Put("Annotations", result);

			return chartExamples;
		}
	}
}

