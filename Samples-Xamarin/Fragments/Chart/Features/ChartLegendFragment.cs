
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
using Com.Telerik.Widget.Primitives.Legend;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Axes;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Java.Util;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class ChartLegendFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		private RadCartesianChartView chartView;
		private RadLegendView legendView;

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.Inflate(Resource.Layout.fragment_chart_legend_example, container, false);
			this.chartView = rootView.FindViewById(Resource.Id.chartView).JavaCast<RadCartesianChartView>();
			this.legendView = rootView.FindViewById(Resource.Id.legendView).JavaCast<RadLegendView>();

			this.prepareChart();

			this.legendView.LegendProvider = this.chartView;

			return rootView;
		}

		private void prepareChart() {
			CategoricalAxis horizontalAxis = new CategoricalAxis();
			this.chartView.HorizontalAxis = horizontalAxis;

			LinearAxis verticalAxis = new LinearAxis();
			verticalAxis.LabelFormat = "%.0f";
			this.chartView.VerticalAxis = verticalAxis;

			for (int i = 0; i < 5; i++){
				BarSeries series = new BarSeries();
				series.LegendTitle = "Series " + (i + 1);
				series.CategoryBinding = new CategoryBinding();
				series.ValueBinding = new ValueBinding();
				series.Data = this.getData();
				this.chartView.Series.Add(series);
			}
		}

		private ArrayList getData() {
			Java.Util.Random numberGenerator = new Java.Util.Random();
			ArrayList result = new ArrayList(8);

			for (int i = 0; i < 8; i++) {
				DataEntity entity = new DataEntity();
				entity.value = numberGenerator.NextInt(10) + 1;
				entity.category = "Item " + i;
				result.Add(entity);
			}

			return result;
		}

		public String Title() {
			return "Chart legend";
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

