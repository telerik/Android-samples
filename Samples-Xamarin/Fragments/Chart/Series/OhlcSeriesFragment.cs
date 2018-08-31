
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
using Java.Util;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Axes;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class OhlcSeriesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadCartesianChartView createChart() {
			RadCartesianChartView chart = new RadCartesianChartView(this.Activity); // context is an instance of Context.

			OhlcSeries series = new OhlcSeries();
			series.CategoryBinding = new CategoryBinding();
			series.OpenBinding = new OpenValueBinding();
			series.HighBinding = new HighValueBinding();
			series.LowBinding = new LowValueBinding();
			series.CloseBinding = new CloseValueBinding();

			series.Data = this.getData();
			chart.Series.Add(series);

			CategoricalAxis horizontalAxis = new CategoricalAxis();
			chart.HorizontalAxis = horizontalAxis;

			LinearAxis verticalAxis = new LinearAxis();
			chart.VerticalAxis = verticalAxis;



			return chart;
		}

		private ArrayList getData() {
			ArrayList data = new ArrayList();
			int size = 10;

			for (int i = 1; i <= size; ++i) {
				OhlcData ohlc = new OhlcData();
				ohlc.category = i.ToString();
				ohlc.high = ChartExamples.randomIntValues[i] + 20;

                ohlc.low = ohlc.high - 10;
                ohlc.open = ohlc.high - 7;
                ohlc.close = ohlc.high - 2;

                data.Add(ohlc);
			}

			return data;
		}


		public String Title() {
			return "Ohlc series";
		}

		public class CategoryBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				OhlcData entity = (OhlcData)p0;
				return entity.category;
			}

		}

		public class OpenValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				OhlcData entity = (OhlcData)p0;
				return entity.open;
			}

		}

		public class HighValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				OhlcData entity = (OhlcData)p0;
				return entity.high;
			}

		}

		public class LowValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				OhlcData entity = (OhlcData)p0;
				return entity.low;
			}

		}

		public class CloseValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				OhlcData entity = (OhlcData)p0;
				return entity.close;
			}

		}

		class OhlcData : Java.Lang.Object {
			public String category;
			public int open;
			public int high;
			public int low;
			public int close;
		}
	}
}

