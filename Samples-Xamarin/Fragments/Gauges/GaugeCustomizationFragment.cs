using System;
using Android.Graphics;
using Android.Views;
using Com.Telerik.Widget.Gauge;
using Com.Telerik.Widget.Indicators;
using Com.Telerik.Widget.Scales;

namespace Samples
{
	public class GaugeCustomizationFragment: Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadRadialGaugeView gauge;
		public GaugeCustomizationFragment()
		{
		}

		public override Android.Views.View OnCreateView(Android.Views.LayoutInflater inflater, Android.Views.ViewGroup container, Android.OS.Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_gauge_customization, container, false);
			gauge = (RadRadialGaugeView)rootView.FindViewById(Resource.Id.radial_gauge);

			GaugeRadialScale scale = new GaugeRadialScale(Activity);
			scale.StartAngle = 0;
			scale.SweepAngle = 360;
			scale.LineVisible = false;
			scale.SetRange(0, 100);
			scale.TicksVisible = false;
			scale.LabelsVisible = false;

			// >> gauges-indicators-bars
			int[] transparentColors = new int[] {
				Color.Argb(100,224,151,36),
				Color.Argb(100,196,241,57),
				Color.Argb(100,132,235,247)
		};

			int[] colors = new int[] {
				Color.Argb(255,224,151,36),
				Color.Argb(255,196,241,57),
				Color.Argb(255,132,235,247)
		};

			for (int i = 0; i < 3; i++)
			{
				GaugeRadialBarIndicator trnspIndicator = new GaugeRadialBarIndicator(Activity);
				trnspIndicator.Minimum = 0;
				trnspIndicator.Maximum = 100;
				trnspIndicator.FillColor = transparentColors[i];
				trnspIndicator.BarWidth = 0.2f;
				trnspIndicator.Location = 0.5f + i * 0.25f;
				scale.AddIndicator(trnspIndicator);

				GaugeRadialBarIndicator indicator = new GaugeRadialBarIndicator(Activity);
				indicator.Minimum = 0;
				Random r = new Random();
				indicator.Maximum = r.Next(100);
				indicator.Animated = true;
				indicator.AnimationDuration = 500;
				indicator.BarWidth = 0.2f;
				indicator.Location = 0.5f + i * 0.25f;
				indicator.FillColor = colors[i];
				indicator.Cap = GaugeBarIndicatorCapMode.Round;
				scale.AddIndicator(indicator);
			}
			// << gauges-indicators-bars
			gauge.AddScale(scale);
			return rootView;
		}

		public override void OnResume()
		{
			base.OnResume();
			gauge.AnimateGauge();
		}

		public string Title()
		{
			return "Customization";
		}
	}
}
