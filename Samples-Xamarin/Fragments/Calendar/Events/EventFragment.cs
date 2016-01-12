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
	public class EventFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);

			Calendar calendar = Java.Util.Calendar.Instance;
			long start = calendar.TimeInMillis;
			calendar.Add (CalendarField.Hour, 3);
			long end = calendar.TimeInMillis;
			Event newEvent = new Event ("Enjoy Life", start, end);

			calendar.Add (CalendarField.Hour, 1);
			start = calendar.TimeInMillis;
			calendar.Add (CalendarField.Hour, 1);
			end = calendar.TimeInMillis;
			Event newEvent2 = new Event("Walk to work", start, end);
			newEvent2.EventColor = Android.Graphics.Color.Green;

			IList<Event> events = new List<Event> ();
			events.Add (newEvent);
			events.Add (newEvent2);

			calendarView.EventAdapter.Events = events;

			return calendarView;
		}

		public String Title() {
			return "Creating events";
		}
	}
}