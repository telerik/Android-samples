
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
using Com.Telerik.Widget.Chart.Engine.Axes.Common;
using Com.Telerik.Widget.Chart.Visualization.Behaviors;
using Java.Util;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class SelectionBehaviorFragment : Android.Support.V4.App.Fragment, ExampleFragment
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
			horizontalAxis.LabelFitMode = AxisLabelFitMode.MultiLine;

			barSeries.VerticalAxis = verticalAxis;
			barSeries.HorizontalAxis = horizontalAxis;

			//Bind series to data
			barSeries.Data = this.getData();

			//Add series to chart
			chart.Series.Add(barSeries);

			ChartSelectionBehavior sb = new ChartSelectionBehavior();
			sb.DataPointsSelectionMode = ChartSelectionMode.Single;

			chart.Behaviors.Add(sb);

			return chart;
		}

		private ArrayList getData(){

			ArrayList result = new ArrayList(8);

			for (int i = 0; i < 15; i++){
				DataEntity entity = new DataEntity();
				entity.value = ChartExamples.randomIntValues[i];
				entity.category = "Item " + i;
				result.Add(entity);
			}

			return result;
		}

		public String Title() {
			return "Selection";
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

