
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
using Com.Telerik.Widget.Chart.Engine.Axes.Common;
using Com.Telerik.Widget.Chart.Visualization.Behaviors;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class PanAndZoomFragment : Android.Support.V4.App.Fragment, ExampleFragment
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

			//Create the area series and attach axes and value bindings.
			AreaSeries areaSeries = new AreaSeries();

			areaSeries.ValueBinding  = new ValueBinding();
			areaSeries.CategoryBinding = new CategoryBinding();

			LinearAxis verticalAxis = new LinearAxis();
			//The values in the linear axis will not have values after the decimal point.
			verticalAxis.LabelFormat = "%.0f";
			CategoricalAxis horizontalAxis = new CategoricalAxis();
			horizontalAxis.LabelInterval = 10;
			horizontalAxis.LabelFitMode = AxisLabelFitMode.MultiLine;
			areaSeries.VerticalAxis = verticalAxis;
			areaSeries.HorizontalAxis = horizontalAxis;

			//Bind series to data
			areaSeries.Data = this.getData();

			//Add series to chart
			chart.Series.Add(areaSeries);

			ChartPanAndZoomBehavior pzBehavior = new ChartPanAndZoomBehavior();

			pzBehavior.PanMode = ChartPanZoomMode.Both;
			pzBehavior.ZoomMode = ChartPanZoomMode.Both;

			chart.Behaviors.Add(pzBehavior);

			return chart;
		}

		private ArrayList getData(){

			ArrayList result = new ArrayList(8);

			for (int i = 0; i < 80; i++){
				DataEntity entity = new DataEntity();  
				entity.value = ChartExamples.randomIntValues[i];
				entity.category = "Item " + i;
				result.Add(entity);
			}

			return result;
		}

		public String Title() {
			return "Pan and zoom";
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

