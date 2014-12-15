
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
using Android.Support.V4.App;

namespace Samples
{
	[Activity (Label = "ExampleActivity")]			
	public class ExampleActivity : FragmentActivity
	{
		internal static Android.Support.V4.App.Fragment selectedExampleFragment = null;

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			this.SetContentView(Resource.Layout.activity_example);
			Android.Support.V4.App.FragmentManager fm = this.SupportFragmentManager;
			Android.Support.V4.App.FragmentTransaction ft = fm.BeginTransaction();
			ft.Replace(Resource.Id.container, selectedExampleFragment);
			ft.Commit();
			this.Title = ((ExampleFragment)selectedExampleFragment).Title();
			// Create your application here
		}
	}
}

