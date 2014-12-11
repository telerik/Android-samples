using System;
using System.Collections.Generic;
using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Android.Content;
using Android.Widget;


namespace Samples
{
	public class ScrollingFlingSpeed : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);

			calendarView.ScrollMode = ScrollMode.Combo;
			calendarView.AnimationsManager.FlingSpeed = 20;

			return calendarView;
		}

		public String Title() {
			return "Scrolling Fling Speed";
		}
	}
}