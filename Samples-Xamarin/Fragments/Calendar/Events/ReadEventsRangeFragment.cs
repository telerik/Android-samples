using System;
using System.Collections.Generic;
using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Android.Content;
using Android.Widget;
using Com.Telerik.Widget.Calendar.Events.Read;
using Java.Util;

namespace Samples
{
	public class ReadEventsRangeFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);

			EventReadAdapter adapter = new EventReadAdapter (calendarView);
			calendarView.EventAdapter = adapter;

			Calendar calendar = Calendar.Instance;
			long start = calendar.TimeInMillis;
			calendar.Add (CalendarField.Date, 7);
			long end = calendar.TimeInMillis;

			EventQueryToken token = adapter.EventsQueryToken;
			token.SetRange (start, end);

			adapter.ReadEventsAsync ();

			return calendarView;
		}

		public String Title() {
			return "Read events range";
		}
	}
}