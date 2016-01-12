
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.Support.V7.App;
using Android.Util;

namespace Samples
{
	[Activity (Label = "@string/title_activity_control")]			
	public class ControlActivity : AppCompatActivity
	{
		internal static ExamplesProvider selectedControl;
		private ExpandableListView expList;
		private static int IndicatorLeft = 40;
		private static int IndicatorRight = 8;

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			this.SetContentView(Resource.Layout.activity_control);

			if(SupportActionBar != null && selectedControl != null) {
				SupportActionBar.Title = selectedControl.ControlName();
			}

			this.expList = (ExpandableListView)this.FindViewById (Resource.Id.expListView);
			MoveIndicatorImage ();

			ExamplesAdapter ea = new ExamplesAdapter(selectedControl.Examples());
			this.expList.SetAdapter(ea);

			for (int i = 0; i < ea.GroupCount; i++ ) {
				expList.ExpandGroup(i);
			}

			this.expList.ChildClick += (object sender, ExpandableListView.ChildClickEventArgs e) => {
				ExampleActivity.selectedExampleFragment = (Android.Support.V4.App.Fragment)ea.GetChild(e.GroupPosition, e.ChildPosition);
				Intent exampleIntent = new Intent(this, typeof(ExampleActivity));
				this.StartActivity(exampleIntent);
				e.Handled = true;
			};
		}

		public override void OnWindowFocusChanged (bool hasFocus)
		{
			base.OnWindowFocusChanged (hasFocus);

			MoveIndicatorImage ();
		}

		private void MoveIndicatorImage() {
			DisplayMetrics metrics = new DisplayMetrics();
			WindowManager.DefaultDisplay.GetMetrics (metrics);

			int width = metrics.WidthPixels;
			int left = (int)(IndicatorLeft * metrics.Density + 0.5f);
			int right = (int)(IndicatorRight * metrics.Density + 0.5f);

			if (Android.OS.Build.VERSION.SdkInt >= Android.OS.BuildVersionCodes.JellyBeanMr2) {
				expList.SetIndicatorBoundsRelative (width - left, width - right);
			} else {
				expList.SetIndicatorBounds (width - left, width - right);
			}
		}
	}
}

