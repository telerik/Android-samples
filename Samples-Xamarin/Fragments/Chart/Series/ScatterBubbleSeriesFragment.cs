
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
using Java.Util;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Scatter;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class ScatterBubbleSeriesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadCartesianChartView createChart(){
			//Create the Chart View
			RadCartesianChartView chart = new RadCartesianChartView(this.Activity);

			//Create the scatter bubble series and attach axes and value bindings.
			ScatterBubbleSeries scatterBubbleSeries = new ScatterBubbleSeries();
			scatterBubbleSeries.BubbleScale = 1000;


			scatterBubbleSeries.XValueBinding = new XValueBinding();
			scatterBubbleSeries.YValueBinding = new YValueBinding();
			scatterBubbleSeries.BubbleSizeBinding = new AreaValueBinding();

			LinearAxis verticalAxis = new LinearAxis();
			//The values in the linear axis will not have values after the decimal point.
			verticalAxis.LabelFormat = "%.2f";
			LinearAxis horizontalAxis = new LinearAxis();
			horizontalAxis.LabelFormat = "%.2f";
			scatterBubbleSeries.VerticalAxis = verticalAxis;
			scatterBubbleSeries.HorizontalAxis = horizontalAxis;

			//Bind series to data
			scatterBubbleSeries.Data = this.getData();

			//Add series to chart
			chart.Series.Add(scatterBubbleSeries);
			return chart;
		}

		private ArrayList getData(){
			Java.Util.Random numberGenerator = new Java.Util.Random();
			ArrayList result = new ArrayList(8);

			for (int i = 0; i < 20; i++){
				ScatterDataEntity entity = new ScatterDataEntity();
				entity.value1 = numberGenerator.NextDouble() * 10;
				entity.value2 = numberGenerator.NextDouble() * 10;
				entity.bubbleArea = numberGenerator.NextInt(5) + 1;
				result.Add(entity);
			}

			return result;
		}

		public String Title() {
			return "Scatter bubble series";
		}

		public class XValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				ScatterDataEntity entity = (ScatterDataEntity)p0;
				return entity.value1;
			}

		}

		public class YValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				ScatterDataEntity entity = (ScatterDataEntity)p0;
				return entity.value2;
			}

		}

		public class AreaValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				ScatterDataEntity entity = (ScatterDataEntity)p0;
				return entity.bubbleArea;
			}

		}

		public class ScatterDataEntity : Java.Lang.Object{
			public double value1;
			public double value2;
			public int bubbleArea;
		}
	}
}

