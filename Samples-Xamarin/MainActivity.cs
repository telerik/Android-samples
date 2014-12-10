using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;

namespace Samples
{
	[Activity (Label = "Samples", MainLauncher = true, Icon = "@drawable/icon")]
	public class MainActivity : Activity
	{
		private ListView listControls;

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Set our view from the "main" layout resource
			SetContentView (Resource.Layout.Main);

			this.listControls = (ListView) this.FindViewById(Resource.Id.listControls);
			ControlsAdapter currentAdapter = new ControlsAdapter (this, 0);
			this.listControls.Adapter = currentAdapter;
			this.listControls.ItemClick += (object sender, AdapterView.ItemClickEventArgs e) =>  {
				ControlActivity.currentProvider = (ExamplesProvider)currentAdapter.GetItem(e.Position);
				Intent examplesActivity = new Intent(this, typeof(ControlActivity));
				this.StartActivity(examplesActivity);
			};
		}
	}
}