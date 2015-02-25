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
	public class ReadEventsOptionsFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);

			EventReadAdapter adapter = new EventReadAdapter (calendarView);
			calendarView.EventAdapter = adapter;
			EventReadAdapter.GetAllCalendarsAsync (Activity, new ResultListener(adapter));

			return calendarView;
		}

		private class ResultListener : Java.Lang.Object, IGenericResultCallback
		{
			EventReadAdapter adapter;

			public ResultListener(EventReadAdapter adapter)
			{
				this.adapter = adapter;
			}

			public void OnResult(Java.Lang.Object result)
			{
				EventReadAdapter.CalendarInfo[] calendars = result.ToArray<EventReadAdapter.CalendarInfo> ();
				string[] calendarIDs = new string[calendars.Length];
				for (int i = 0; i < calendars.Length; i++) {
					calendarIDs [i] = calendars [i].Id;
				}

				adapter.EventsQueryToken = EventQueryToken.GetCalendarsById (calendarIDs);
				adapter.ReadEventsAsync ();
			}
		}

		public String Title() {
			return "Read events options";
		}
	}
}