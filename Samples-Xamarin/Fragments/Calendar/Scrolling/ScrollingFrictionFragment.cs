using System;
using System.Collections.Generic;
using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Android.Content;
using Android.Widget;


namespace Samples
{
	public class ScrollingFrictionFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);

			calendarView.ScrollMode = ScrollMode.Combo;

			/*
			 * The calendar will fling for quite a while.
			 * Setting this higher will make it stop faster. Default is 7.
			 */
			calendarView.AnimationsManager.Friction = .2;

			return calendarView;
		}

		public String Title() {
			return "Scrolling friction";
		}
	}
}