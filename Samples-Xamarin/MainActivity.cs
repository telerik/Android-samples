using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Android.Support.V7.App;

namespace Samples
{
	[Activity (Label = "@string/title_activity_main", MainLauncher = true, Icon = "@drawable/icon")]
	public class MainActivity : AppCompatActivity
	{
		private ListView listControls;

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			SetContentView (Resource.Layout.Main);

			this.listControls = (ListView) this.FindViewById(Resource.Id.listControls);
			this.listControls.Adapter = new ControlsAdapter (this, 0);
			this.listControls.ItemClick += (object sender, AdapterView.ItemClickEventArgs e) =>  {
				ControlActivity.selectedControl = (ExamplesProvider)listControls.Adapter.GetItem(e.Position);

				Intent examplesActivity = new Intent(this, typeof(ControlActivity));
				this.StartActivity(examplesActivity);
			};
		}
	}
}