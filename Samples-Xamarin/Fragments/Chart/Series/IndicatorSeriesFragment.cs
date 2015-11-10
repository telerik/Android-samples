
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Axes;
using Java.Text;
using Com.Telerik.Widget.Chart.Engine.Axes.Common;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Indicators;
using Java.Util;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class IndicatorSeriesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadCartesianChartView createChart() {
			RadCartesianChartView chart = new RadCartesianChartView(this.Activity);

			DateTimeCategoricalAxis horizontalAxis = new DateTimeCategoricalAxis();
			horizontalAxis.DateTimeFormat = new SimpleDateFormat("MM/dd");
			horizontalAxis.DateTimeComponent = DateTimeComponent.Date;

			LinearAxis verticalAxis = new LinearAxis();

			DataPointBinding categoryBinding = new CategoryBinding();
			DataPointBinding openBinding = new OpenValueBinding();
			DataPointBinding highBinding = new HighValueBinding();
			DataPointBinding lowBinding = new LowValueBinding();
			DataPointBinding closeBinding = new CloseValueBinding();

			ArrayList data = this.getData();

			CandlestickSeries series = new CandlestickSeries();
			series.CategoryBinding = categoryBinding;
			series.OpenBinding = openBinding;
			series.HighBinding = highBinding;
			series.LowBinding = lowBinding;
			series.CloseBinding = closeBinding;
			series.Data = data;

			BollingerBandsIndicator bollingerBands = new BollingerBandsIndicator();
			bollingerBands.CategoryBinding = categoryBinding;
			bollingerBands.ValueBinding = closeBinding;
			bollingerBands.Period = 5;
			bollingerBands.StandardDeviations = 2;
			bollingerBands.Data = data;

			MovingAverageIndicator movingAverage = new MovingAverageIndicator();
			movingAverage.CategoryBinding = categoryBinding;
			movingAverage.ValueBinding = closeBinding;
			movingAverage.Period = 5;
			movingAverage.Data = data;

			chart.VerticalAxis = verticalAxis;
			chart.HorizontalAxis = horizontalAxis;
			chart.Series.Add(series);
			chart.Series.Add(bollingerBands);
			chart.Series.Add(movingAverage);

			// irrelevant
			horizontalAxis.LabelFitMode = AxisLabelFitMode.Rotate;

			return chart;
		}

		private ArrayList getData() {
			ArrayList data = new ArrayList();
			int size = 10;
			Java.Util.Random r = new Java.Util.Random();


			int month = 1;
			for (int i = 1; i <= size; ++i) {
				FinancialDataClass ohlc = new FinancialDataClass();
				//ohlc.category = Integer.toString(i);
				ohlc.high = 150 + r.NextInt(50);

				ohlc.low = ohlc.high - (r.NextInt(20) + 10);
				ohlc.open = ohlc.low + r.NextInt((int)ohlc.high - (int)ohlc.low);
				ohlc.close = ohlc.low + r.NextInt((int)ohlc.high - (int)ohlc.low);
				Calendar date = Calendar.Instance;
				date.Set(CalendarField.Month, month++);
				ohlc.date = date;

				data.Add(ohlc);
			}

			return data;
		}

		public String Title() {
			return "Indicator series";
		}

		public class CategoryBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				FinancialDataClass entity = (FinancialDataClass)p0;
				return entity.date;
			}

		}

		public class OpenValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				FinancialDataClass entity = (FinancialDataClass)p0;
				return entity.open;
			}

		}

		public class HighValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				FinancialDataClass entity = (FinancialDataClass)p0;
				return entity.high;
			}

		}

		public class LowValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				FinancialDataClass entity = (FinancialDataClass)p0;
				return entity.low;
			}

		}

		public class CloseValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				FinancialDataClass entity = (FinancialDataClass)p0;
				return entity.close;
			}

		}


		public class FinancialDataClass : Java.Lang.Object {

			public float open;
			public float high;
			public float low;
			public float close;
			public Calendar date;
		}
	}
}

