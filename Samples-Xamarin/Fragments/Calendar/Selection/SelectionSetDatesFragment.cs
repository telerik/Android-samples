using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using System.Collections.Generic;
using Java.Util;
using System;

namespace Samples
{
	public class SelectionSetDatesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);
			IList<Java.Lang.Long> dates = new List<Java.Lang.Long> ();
			Calendar calendar = Calendar.GetInstance (Java.Util.TimeZone.Default);
			calendar.Set (CalendarField.DayOfMonth, 15);
			dates.Add ((Java.Lang.Long)calendar.TimeInMillis);
			calendar.Set (CalendarField.DayOfMonth, 17);
			dates.Add ((Java.Lang.Long)calendar.TimeInMillis);
			calendar.Set (CalendarField.DayOfMonth, 18);
			dates.Add ((Java.Lang.Long)calendar.TimeInMillis);
			calendar.Set (CalendarField.DayOfMonth, 22);
			dates.Add ((Java.Lang.Long)calendar.TimeInMillis);

			calendarView.SelectedDates = dates;

			return calendarView;
		}

		public String Title() {
			return "Set selected dates";
		}
	}
}