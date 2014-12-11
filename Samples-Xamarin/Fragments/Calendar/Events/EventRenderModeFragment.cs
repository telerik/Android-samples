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
	public class EventRenderModeFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);

			Calendar calendar = Java.Util.Calendar.Instance;
			long start = calendar.TimeInMillis;
			calendar.Add (CalendarField.Hour, 3);
			long end = calendar.TimeInMillis;
			Event newEvent = new Event ("Enjoy Life", start, end);

			IList<Event> events = new List<Event> ();
			events.Add (newEvent);

			calendarView.EventAdapter.Events = events;

			calendarView.EventAdapter.Renderer.EventRenderMode = EventRenderMode.Shape;

			return calendarView;
		}

		public String Title() {
			return "Event Render Mode";
		}
	}
}