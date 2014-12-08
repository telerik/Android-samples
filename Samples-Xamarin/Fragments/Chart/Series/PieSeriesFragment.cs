
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
using Com.Telerik.Widget.Chart.Engine.Databinding;
using Com.Telerik.Widget.Chart.Visualization.PieChart;
using Java.Util;

namespace Samples
{
	public class PieSeriesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadPieChartView createChart() {

			RadPieChartView pieChart = new RadPieChartView(this.Activity);

			PieSeries pieSeries = new PieSeries();

			pieSeries.ValueBinding = new ValueBinding();
			pieSeries.ShowLabels = true;
			pieSeries.Data = this.getData();
			pieChart.Series.Add(pieSeries);

			return pieChart;
		}

		private ArrayList getData() {
			Java.Util.Random r = new Java.Util.Random();

			ArrayList result = new ArrayList();

			for (int i = 0; i < 8; i++) {
				DataEntity entity = new DataEntity();
				entity.value = r.NextInt(10) + 1;
				result.Add(entity);
			}

			return result;
		}


		public String Title() {
			return "Pie series";
		}

		public class ValueBinding : DataPointBinding{
			public override Java.Lang.Object GetValue (Java.Lang.Object p0)
			{
				DataEntity entity = (DataEntity)p0;
				return entity.value;
			}

		}

		public class DataEntity : Java.Lang.Object {
			public double value;
		}
	}
}

