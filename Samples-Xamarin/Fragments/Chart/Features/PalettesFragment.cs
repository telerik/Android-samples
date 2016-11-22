
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
using Com.Telerik.Widget.Chart.Engine.Series.Combination;
using Com.Telerik.Widget.Palettes;
using Android.Graphics;
using Com.Telerik.Widget.Chart.Visualization.CartesianChart.Series.Categorical;
using Java.Util;
using Com.Telerik.Widget.Chart.Engine.Databinding;

namespace Samples
{
	public class PalettesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ViewGroup rootView = (ViewGroup) inflater.Inflate(Resource.Layout.fragment_chart_example, container, false);
			rootView.AddView(this.createChart());
			return rootView;
		}

		private RadCartesianChartView createChart() {
			//Create the Chart View
			RadCartesianChartView chart = new RadCartesianChartView(this.Activity);

			LinearAxis verticalAxis = new LinearAxis();
			//The values in the linear axis will not have values after the decimal point.
			verticalAxis.LabelFormat = "%.0f";
			CategoricalAxis horizontalAxis = new CategoricalAxis();
			chart.VerticalAxis = verticalAxis;
			chart.HorizontalAxis = horizontalAxis;


			for (int i = 0; i < 3; i++) {
				//Create the bar series and attach axes and value bindings.
				AreaSeries areaSeries = new AreaSeries();

				//We want to stack the different area series.
				areaSeries.CombineMode = ChartSeriesCombineMode.Stack;

				areaSeries.ValueBinding = new ValueBinding();
				areaSeries.CategoryBinding = new CategoryBinding();

				//Bind series to data
				areaSeries.Data = this.getData();

				//Add series to chart
				chart.Series.Add(areaSeries);
			}

			ChartPalette defaultPaletteClone = new ChartPalette(chart.Palette);

			// We acquire the palette entry containing the settings for the third series in the chart setup.
			PaletteEntry areaSeriesEntry = defaultPaletteClone.GetEntry(ChartPalette.AreaFamily, 2);

			//We set the fill color of the third series to be cyan.
			areaSeriesEntry.Fill = Color.Cyan;

			//We reset the chart palette by applying the new one.
			chart.Palette = defaultPaletteClone;

			return chart;
		}


		public String Title() {
			return "Palettes";
		}

		private ArrayList getData() {

			ArrayList result = new ArrayList(8);

			for (int i = 0; i < 8; i++) {
				DataEntity entity = new DataEntity();
				entity.value = ChartExamples.randomIntValues[i];
				entity.category = "Item " + i;
				result.Add(entity);
			}

			return result;
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

