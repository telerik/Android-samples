using Com.Telerik.Widget.Calendar;
using Android.Views;
using Android.OS;
using Java.Util;
using System;

namespace Samples
{
	public class DisplayDateSetFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);
			calendarView.DisplayDate = new GregorianCalendar (2022, Calendar.October, 1).TimeInMillis;

			return calendarView;
		}

		public String Title() {
			return "Set display date";
		}
	}
}