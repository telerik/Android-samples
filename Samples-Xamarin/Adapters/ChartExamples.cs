using System;
using Java.Util;

namespace Samples
{
	public class ChartExamples : Java.Lang.Object, ExamplesProvider {

		private HashMap chartExamples;

		public ChartExamples(){

			this.chartExamples = this.getChartExamples();
		}


		public String ControlName() {
			return "Chart";
		}


		public HashMap Examples(){
			return this.chartExamples;
		}

		private HashMap getChartExamples(){
			HashMap chartExamples = new HashMap();

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

