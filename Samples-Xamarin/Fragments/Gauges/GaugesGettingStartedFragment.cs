
using System;
using Android.OS;
using Android.Views;
using Com.Telerik.Widget.Gauge;
using Com.Telerik.Widget.Scales;
using Android.Graphics;
using Com.Telerik.Widget.Indicators;

namespace Samples
{
	public class GaugesGettingStartedFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Create your fragment here
		}

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_gauge_getting_started, container, false);

			// >> radial-gauge-load
			RadRadialGaugeView gauge = (RadRadialGaugeView)rootView.FindViewById(Resource.Id.radial_gauge);
			// << radial-gauge-load

			// >> radial-scale-setup
			GaugeRadialScale scale = new GaugeRadialScale(this.Context);
			scale.Minimum = 0;
			scale.Maximum = 6;
			scale.MajorTicksCount = 7;
			scale.MinorTicksCount = 9;
			scale.LabelsCount = 7;
			scale.LineVisible = false;
			scale.Radius = 0.95f;
            scale.TicksOffset = 0;
            // << radial-scale-setup

            // >> radial-indicators-setup
            int[] colors = new int[] {
				Color.Rgb(221,221,221),
				Color.Rgb(157,202,86),
				Color.Rgb(240,196,77),
				Color.Rgb(226,118,51),
				Color.Rgb(167,1,14)
			};

			float rangeWidth = scale.Maximum / colors.Length;
			float start = 0;
			foreach (var color in colors)
			{
				GaugeRadialBarIndicator indicator = new GaugeRadialBarIndicator(this.Context);
				indicator.Minimum = start;
				indicator.Maximum = start + rangeWidth;
				indicator.FillColor = color;
				scale.AddIndicator(indicator);
				start += rangeWidth;
			}

			GaugeRadialNeedle needle = new GaugeRadialNeedle(this.Context);
			needle.Value = 2;
			scale.AddIndicator(needle);
			// << radial-indicators-setup

			// >> add-radial-scale
			gauge.AddScale(scale);
			// << add-radial-scale

			return rootView;
		}

		public String Title()
		{
			return "Getting started";
		}
	}
}
