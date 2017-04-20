using System;
using System.Collections.Generic;
using Android.Content;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Widget.Calendar.Events;

namespace Samples
{
	public class DayViewGettingStartedFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public String Title()
		{
			return "DayView Getting Started";
		}

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView(Activity);

			calendarView.SelectionMode = CalendarSelectionMode.Single;
			calendarView.DisplayMode = CalendarDisplayMode.Day;

			List<Event> events = CalendarEventsHelper.GenerateEvents();
			calendarView.EventAdapter.Events = events;

			calendarView.DayView.EventViewTapListener = new MyEventViewTapListener(Context);

			return calendarView;
		}

		public class MyEventViewTapListener : Java.Lang.Object, Com.Telerik.Widget.Calendar.Dayview.CalendarDayView.IEventViewTapListener
		{
			private Context context;

			public MyEventViewTapListener(Context context)
			{
				this.context = context;
			}

			public void OnEventViewTap(Event p0)
			{
				Toast.MakeText(this.context, String.Format("You selected: {0}", p0.Title), ToastLength.Short).Show();
			}
		}
	}
}
