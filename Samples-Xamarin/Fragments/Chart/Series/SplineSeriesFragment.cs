
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
using Com.Telerik.Widget.Chart.Engine.Databinding;
using Java.Util;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Axes;

namespace Samples
{
		
	public class SplineSeriesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadCartesianChartView createChart(){
			//Create the Chart View
			RadCartesianChartView chart = new RadCartesianChartView(this.Activity);

			//Create the spline area series and attach axes and value bindings.
			SplineSeries splineSeries = new SplineSeries();

			splineSeries.ValueBinding = new ValueBinding();
			splineSeries.CategoryBinding = new CategoryBinding();

			LinearAxis verticalAxis = new LinearAxis();
			//The values in the linear axis will not have values after the decimal point.
			verticalAxis.LabelFormat = "%.0f";
			CategoricalAxis horizontalAxis = new CategoricalAxis();
			splineSeries.VerticalAxis = verticalAxis;
			splineSeries.HorizontalAxis = horizontalAxis;

			//Bind series to data
			splineSeries.Data = this.getData();

			//Add series to chart
			chart.Series.Add(splineSeries);
			return chart;
		}

		private ArrayList getData(){
			Java.Util.Random numberGenerator = new Java.Util.Random();
			ArrayList result = new ArrayList(8);

			for (int i = 0; i < 8; i++){
				DataEntity entity = new DataEntity();
				entity.value = numberGenerator.NextInt(10) + 1;
				entity.category = "Item " + i;
				result.Add(entity);
			}

			return result;
		}

		public String Title() {
			return "Spline series";
		}


		public class CategoryBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				DataEntity entity = (DataEntity)p0;
				return entity.category;
			}

		}

		public class ValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				DataEntity entity = (DataEntity)p0;
				return entity.value;
			}

		}

		public class DataEntity : Java.Lang.Object {
			public String category;
			public int value;
		}
	}
}

