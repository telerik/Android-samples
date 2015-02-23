using Com.Telerik.Widget.Calendar;
using Android.Views;
using Android.OS;
using System;
using System.Collections.Generic;
using Java.Util;
using Android.Widget;
using Com.Telerik.Android.Primitives.Widget.Sidedrawer;

namespace Samples
{
	public class DrawerInitialSetupFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			SideDrawer drawer = new SideDrawer (Activity);

			drawer.DrawerContent = inflater.Inflate (Resource.Layout.drawer_content, null);
			drawer.MainContent = inflater.Inflate (Resource.Layout.drawer_main_content, null);

			return drawer;
		}

		public String Title() {
			return "Initial setup";
		}
	}
}