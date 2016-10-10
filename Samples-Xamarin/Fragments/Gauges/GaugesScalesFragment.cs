using System;
using Android.Views;
using Com.Telerik.Widget.Gauge;
using Com.Telerik.Widget.Scales;
using Android.Graphics;
using Com.Telerik.Widget.Indicators;

namespace Samples
{
	public class GaugesScalesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public GaugesScalesFragment()
		{
		}

		public override Android.Views.View OnCreateView(Android.Views.LayoutInflater inflater, Android.Views.ViewGroup container, Android.OS.Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_gauge_scales, container, false);
			RadRadialGaugeView gauge = (RadRadialGaugeView)rootView.FindViewById(Resource.Id.radial_gauge);

			// >> radial-scale-instantiate
			GaugeRadialScale scale1 = new GaugeRadialScale(Activity);
			scale1.Minimum = 34;
			scale1.Maximum = 40;
			// << radial-scale-instantiate

			// >> radial-scale-config
			scale1.Radius = 0.6f;
			scale1.StrokeWidth = 2;
			// << radial-scale-config

			// >> radial-scale-config-ticks-labels
			scale1.LabelsColor = Color.Gray;
			scale1.LabelsCount = 7;
			scale1.MajorTicksCount = 7;
			scale1.LabelsPaint.TextSize = 30;
			// << radial-scale-config-ticks-labels

			// >> radial-scale-config2
			GaugeRadialScale scale2 = new GaugeRadialScale(Activity);
			scale2.StrokeWidth = 2;
			scale2.Radius = 0.7f;
			scale2.SetRange(93.2f, 104);
			scale2.TicksLayoutMode = GaugeScaleTicksLayoutMode.Outer;
			scale2.MajorTicksCount = 7;
			scale2.MinorTicksCount = 20;
			scale2.LabelsCount = 7;
			scale2.LabelsLayoutMode = GaugeScaleLabelsLayoutMode.Outer;
			scale2.LabelsPaint.TextSize = 30;
			scale2.LabelsColor = Color.Gray;
			// << radial-scale-config2

			// >> add-scale-to-gauge
			gauge.AddScale(scale1);
			gauge.AddScale(scale2);
			// << add-scale-to-gauge

			// >> add-indicators-to-scale
			GaugeRadialNeedle needle = new GaugeRadialNeedle(Activity);
			needle.Value = 36.5f;
			needle.Length = 0.5f;
			needle.TopWidth = 8;
			needle.BottomWidth = 8;
			scale1.AddIndicator(needle);
			scale1.AddIndicator(GetIndicator(34, 36, Color.Blue));
			scale1.AddIndicator(GetIndicator(36.05f, 40, Color.Red));
			// << add-indicators-to-scale

			// >> gauge-set-title
			gauge.Title.Text = "celsius";
			gauge.Subtitle.Text = "fahrenheit";
			gauge.TitleVerticalOffset = 90;
			// << gauge-set-title

			return rootView;
		}

		// >> add-bar-indicator-helper
		private GaugeIndicator GetIndicator(float min, float max, int color)
		{
			GaugeRadialBarIndicator indicator = new GaugeRadialBarIndicator(Activity);
			indicator.Minimum = min;
			indicator.Maximum = max;
			indicator.FillColor = color;
			indicator.Location = 0.69f;
			indicator.BarWidth = 0.08f;
			return indicator;
		}
		// << add-bar-indicator-helper

		public string Title()
		{
			return "Scales";
		}
	}
}
