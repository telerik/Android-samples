using System;
using System.Collections.Generic;
using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Android.Content;
using Android.Widget;
using Com.Telerik.Widget.Calendar.Events;
using Java.Util;


namespace Samples
{
	public class EventAllDayFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);

			Calendar calendar = Java.Util.Calendar.Instance;
			long start = calendar.TimeInMillis;
			calendar.Add (CalendarField.Hour, 3);
			long end = calendar.TimeInMillis;
			Event allDayEvent = new Event ("Enjoy Life", start, end);
			allDayEvent.AllDay = true;

			IList<Event> events = new List<Event> ();
			events.Add (allDayEvent);

			calendarView.EventAdapter.Events = events;

			return calendarView;
		}

		public String Title() {
			return "All day event";
		}
	}
}