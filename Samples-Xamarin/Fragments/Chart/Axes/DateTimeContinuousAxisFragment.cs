
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
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Axes;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class DateTimeContinuousAxisFragment : Android.Support.V4.App.Fragment, ExampleFragment
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

			BarSeries series = new BarSeries();

			DateTimeContinuousAxis axis = new DateTimeContinuousAxis();
			axis.LabelFormat = "MM/dd";
			series.HorizontalAxis = axis;

			LinearAxis verticalAxis = new LinearAxis();
			verticalAxis.LabelFormat = "%.0f";
			series.VerticalAxis = verticalAxis;

			series.ValueBinding = new ValueBinding();
			series.CategoryBinding = new CategoryBinding();

			series.Data = this.getData();

			chart.Series.Add(series);

			return chart;
		}

		private ArrayList getData(){
			Java.Util.Random numberGenerator = new Java.Util.Random();
			ArrayList result = new ArrayList(8);
			int startingMonth =0;
			for (int i = 0; i < 8; i++){

				DataEntity entity = new DataEntity();
				entity.value = numberGenerator.NextInt(10) + 1;
				Calendar date = Calendar.Instance;
				date.Set(CalendarField.Month, startingMonth++);
				entity.date = date;
				if (i == 2 || i == 6){
					continue;
				}
				result.Add(entity);
			}

			return result;
		}

		public String Title() {
			return "Date Time Continuous";
		}


		public class CategoryBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				DataEntity entity = (DataEntity)p0;
				return entity.date;
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
			public Calendar date;
			public int value;
		}
	}
}

