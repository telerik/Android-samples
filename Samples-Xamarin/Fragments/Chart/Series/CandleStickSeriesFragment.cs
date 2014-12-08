
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Axes;
using Java.Util;
using Java.Lang;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{

	public class CandleStickSeriesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadCartesianChartView createChart() {
			RadCartesianChartView chart = new RadCartesianChartView(this.Activity); // context is an instance of Context.

			CandlestickSeries series = new CandlestickSeries();
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
			Java.Util.Random r = new Java.Util.Random();

			for (int i = 1; i <= size; ++i) {
				OhlcData ohlc = new OhlcData();
				ohlc.category = i.ToString();
				ohlc.high = r.NextInt(100);
				if (ohlc.high < 2) {
					ohlc.high = 2;
				}

				ohlc.low = r.NextInt(ohlc.high - 1);
				ohlc.open = ohlc.low + r.NextInt(ohlc.high - ohlc.low);
				ohlc.close = ohlc.low + r.NextInt(ohlc.high - ohlc.low);

				data.Add(ohlc);
			}

			return data;
		}


		public System.String Title() {
			return "Candle stick series";
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
			public System.String category;
			public int open;
			public int high;
			public int low;
			public int close;
		}
	}
}

