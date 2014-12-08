
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
using Java.Util;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Axes;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Com.Telerik.Widget.Chart.Engine.Axes.Common;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class MultipleAxesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadCartesianChartView createChart(){
			RadCartesianChartView chart = new RadCartesianChartView(this.Activity);

			CategoricalAxis horizontalAxis = new CategoricalAxis();
			chart.HorizontalAxis = horizontalAxis;

			LinearAxis vertical1 = new LinearAxis();
			vertical1.LabelFormat = "%.0f";

			LinearAxis vertical2 = new LinearAxis();
			vertical2.LabelFormat = "%.0f";

			BarSeries series1 = new BarSeries();

			ArrayList data = this.getData();

			series1.ValueBinding = new ValueBinding1();
			series1.CategoryBinding = new CategoryBinding();
			series1.VerticalAxis = vertical1;
			series1.Data = data;

			chart.Series.Add(series1);

			LineSeries series2 = new LineSeries();

			series2.ValueBinding = new ValueBinding2();
			series2.CategoryBinding = new CategoryBinding();
			series2.VerticalAxis = (vertical2);
			vertical2.HorizontalLocation = AxisHorizontalLocation.Right;
			series2.Data = data;

			chart.Series.Add(series2);

			return chart;
		}

		private ArrayList getData(){
			Java.Util.Random numberGenerator = new Java.Util.Random();
			ArrayList result = new ArrayList(8);

			for (int i = 0; i < 8; i++){
				DataEntity entity = new DataEntity();
				entity.value1 = numberGenerator.NextInt(10) + 1;
				entity.value2 = numberGenerator.NextInt(10) + 1;
				entity.category = "Item " + i;
				result.Add(entity);
			}

			return result;
		}

		public String Title() {
			return "Multiple Axes";
		}

		public class CategoryBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				DataEntity entity = (DataEntity)p0;
				return entity.category;
			}

		}

		public class ValueBinding1 : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				DataEntity entity = (DataEntity)p0;
				return entity.value1;
			}

		}

		public class ValueBinding2 : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				DataEntity entity = (DataEntity)p0;
				return entity.value2;
			}

		}

		public class DataEntity : Java.Lang.Object {
			public String category;
			public int value1;
			public int value2;
		}
	}
}

