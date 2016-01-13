
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
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Axes;
using Com.Telerik.Widget.Chart.Visualization.Annotations.Cartesian;
using Android.Graphics;
using Java.Util;
using Com.Telerik.Widget.Chart.Engine.Databinding;
using Com.Telerik.Widget.Chart.Visualization.Annotations;

namespace Samples
{
	public class GridLineAnnotationFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadCartesianChartView createChart(){
			//Create the Chart View
			RadCartesianChartView chart = new RadCartesianChartView(this.Activity);

			//Create the bar series and attach axes and value bindings.
			BarSeries barSeries = new BarSeries();

			barSeries.ValueBinding  = new ValueBinding();
			barSeries.CategoryBinding = new CategoryBinding();

			LinearAxis verticalAxis = new LinearAxis();
			//The values in the linear axis will not have values after the decimal point.
			verticalAxis.LabelFormat = "%.0f";
			CategoricalAxis horizontalAxis = new CategoricalAxis();
			barSeries.VerticalAxis = verticalAxis;
			barSeries.HorizontalAxis = horizontalAxis;

			//Bind series to data
			barSeries.Data  = this.getData();

			//Add series to chart
			chart.Series.Add(barSeries);

			CartesianGridLineAnnotation annotation = new CartesianGridLineAnnotation(verticalAxis, 3);
			chart.Annotations.Add(annotation);
			annotation.LabelHorizontalAlignment = HorizontalAlignment.Left;
			annotation.StrokeColor = Color.Argb(255, 235, 100, 32);
			annotation.StrokeWidth = 4;
			annotation.ZIndex = 1001;
			annotation.Label = "This is Grid Line annotation";

			return chart;
		}

		private ArrayList getData(){
			ArrayList result = new ArrayList(8);

			for (int i = 0; i < 8; i++){
				DataEntity entity = new DataEntity();
				entity.value = i + 1;
				entity.category = "Item " + i;
				result.Add(entity);
			}

			return result;
		}

		public String Title() {
			return "Grid Line annotations";
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

		public class DataEntity : Java.Lang.Object{
			public String category;
			public int value;
		}
	}
}

