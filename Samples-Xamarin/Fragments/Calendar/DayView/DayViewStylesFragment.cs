using System;
using System.Collections.Generic;
using Android.Graphics;
using Android.OS;
using Android.Views;
using Com.Telerik.Android.Common;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Widget.Calendar.Events;
using Java.Util;

namespace Samples
{
	public class DayViewStylesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public String Title()
		{
			return "DayView Styles";
		}

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView(Activity);

			calendarView.SelectionMode = CalendarSelectionMode.Single;
			calendarView.DisplayMode = CalendarDisplayMode.Day;

			List<Event> events = CalendarEventsHelper.GenerateEvents();
			calendarView.EventAdapter.Events = events;

			calendarView.DayView.AllDayEventsViewStyle.AllDayTextIsVisible = false;
			calendarView.DayView.AllDayEventsViewStyle.BackgroundColor = Color.ParseColor("#4FC3F7");
			calendarView.DayView.AllDayEventsViewStyle.MaxVisibleEventRows = 2.5f;

			int timeLabelSize = Util.GetSP(20);
			calendarView.DayView.DayEventsViewStyle.TimeLabelTextSize = timeLabelSize;
			calendarView.DayView.DayEventsViewStyle.TimeLabelColor = Color.ParseColor("#512DA8");
			calendarView.DayView.DayEventsViewStyle.TimeLabelFormat = new Java.Text.SimpleDateFormat("HH:mm", Locale.Us);

			int timeLinesSpacing = Util.GetDP(50);
			calendarView.DayView.DayEventsViewStyle.TimeLinesSpacing = timeLinesSpacing;
			calendarView.DayView.DayEventsViewStyle.TimeLinesInterval = 30 * 60 * 1000;
			calendarView.DayView.DayEventsViewStyle.TimeLinesVisible = false;
			calendarView.DayView.DayEventsViewStyle.EventsSpacing = Util.GetDP(2);
			calendarView.DayView.DayEventsViewStyle.BackgroundColor = Color.ParseColor("#F7F7F7");

			return calendarView;
		}
	}
}
