using System;
using Android.Graphics;
using Android.Views;
using Android.Views.Animations;
using Android.Widget;
using Com.Telerik.Widget.Gauge;
using Com.Telerik.Widget.Indicators;
using Com.Telerik.Widget.Scales;

namespace Samples
{
	public class GaugesAnimationsFragment: Android.Support.V4.App.Fragment, ExampleFragment
	{
		GaugeRadialNeedle needle;

		public GaugesAnimationsFragment()
		{
		}

		public override View OnCreateView(Android.Views.LayoutInflater inflater, Android.Views.ViewGroup container, Android.OS.Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_gauge_animations, container, false);
			RadRadialGaugeView gauge = (RadRadialGaugeView)rootView.FindViewById(Resource.Id.radial_gauge);
			SetupGauge(gauge);

			RadioButton btn60 = (RadioButton)rootView.FindViewById(Resource.Id.radio_60);
			btn60.Click += (sender, e) =>
			{
				needle.Value = 60;
			};

			RadioButton btn80 = (RadioButton)rootView.FindViewById(Resource.Id.radio_80);
			btn80.Click += (sender, e) =>
			{
				needle.Value = 80;
			};

			RadioButton btn120 = (RadioButton)rootView.FindViewById(Resource.Id.radio_120);
			btn120.Click += (sender, e) =>
			{
				needle.Value = 120;
			};

			RadioButton btn160 = (RadioButton)rootView.FindViewById(Resource.Id.radio_160);
			btn160.Click += (sender, e) =>
			{
				needle.Value = 160;
			};

			return rootView;
		}

		public override void OnStart()
		{
			base.OnStart();
			needle.Value = 60;
		}

		private void SetupGauge(RadGaugeView gauge)
		{
			gauge.Subtitle.Text = "km/h";
			gauge.SubtitleVerticalOffset = 20;

			GaugeRadialScale scale = new GaugeRadialScale(Activity);
			scale.SetRange(0, 180);
			scale.Radius = 0.98f;
			scale.LabelsCount = 10;
			scale.MajorTicksCount = 19;
			scale.MinorTicksCount = 1;
			scale.LabelsOffset = scale.LabelsOffset + 20;
			scale.TicksOffset = 0.1f;
			scale.MajorTicksStrokePaint.StrokeWidth = 2;
			scale.MajorTicksStrokeColor = Color.Rgb(132, 132, 132);
			scale.LineVisible = false;
			gauge.AddScale(scale);

			// >> gauge-indicators-needle
			needle = new GaugeRadialNeedle(Activity);
			needle.Length = 0.8f;
			needle.BottomWidth = 8;
			needle.TopWidth = 8;
			// << gauge-indicators-needle

			// >> gauge-animations-turn-on
			needle.Animated = true;
			needle.AnimationDuration = 500;
			// << gauge-animations-turn-on

			// >> gauge-animations-timing-func
			needle.Interpolator = new AccelerateDecelerateInterpolator();
			// << gauge-animations-timing-func

			scale.AddIndicator(needle);

			scale.AddIndicator(GetIndicator(0, 60, Color.Rgb(132, 132, 132)));
			scale.AddIndicator(GetIndicator(61, 120, Color.Rgb(54, 54, 54)));
			scale.AddIndicator(GetIndicator(121, 180, Color.Rgb(198, 85, 90)));
		}

		private GaugeRadialBarIndicator GetIndicator(float min, float max, int color)
		{
			GaugeRadialBarIndicator indicator = new GaugeRadialBarIndicator(Activity);
			indicator.Minimum = min;
			indicator.Maximum = max;
			indicator.FillColor = color;
			indicator.BarWidth = 0.02f;
			return indicator;
		}

		public string Title()
		{
			return "Animations";
		}
	}
}
