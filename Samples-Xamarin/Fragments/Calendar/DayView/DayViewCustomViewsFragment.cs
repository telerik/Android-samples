using System;
using System.Collections.Generic;
using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Widget.Calendar.Dayview;
using Com.Telerik.Widget.Calendar.Events;

namespace Samples
{
	public class DayViewCustomViewsFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public String Title()
		{
			return "DayView Custom Adapter";
		}

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView(Activity);

			calendarView.SelectionMode = CalendarSelectionMode.Single;
			calendarView.DisplayMode = CalendarDisplayMode.Day;

			List<Event> events = CalendarEventsHelper.GenerateEvents();
			calendarView.EventAdapter.Events = events;

			calendarView.DayView.EventDayViewAdapter = new MyDayViewEventCustomAdapter(Context);

			return calendarView;
		}
	}

	public class MyDayViewEventCustomAdapter : EventDayViewAdapter
	{
		private Context context;

		public MyDayViewEventCustomAdapter(Context context)
			:base(context)
		{
			this.context = context;
		}

		public override View GetView(Event e, long date)
		{
			DayEventViewBase eventView;
			if (e.AllDay)
			{
				eventView = new MyAllDayView(this.context, e);
			}
			else {
				eventView = new MyDayView(this.context);
				eventView.Event = e;
			}
			return eventView;
		}
	}

	public class MyAllDayView : AllDayEventView
	{
		public MyAllDayView(Context context, Event e)
			:base(context)
		{
			this.Event = e;
			this.CornerRadius = 0;
			this.TitleView().SetAllCaps(true);
			this.TitleView().SetTextSize(Android.Util.ComplexUnitType.Sp, 15);
			this.TitleView().SetTypeface(Typeface.Default, TypefaceStyle.Italic);
		}
	}

	public class MyDayView : DayEventView
	{
		private TextView title;
		private TextView details;
		private View line;
		private ViewGroup mainContent;

		public MyDayView(Context context)
			: base(context)
		{
			this.Orientation = Orientation.Horizontal;
		}

		public override TextView TitleView()
		{
			return this.title;
		}

		public override TextView DetailsView()
		{
			return this.details;
		}

		protected override void Init(Context p0)
		{
			Inflate(p0, Resource.Layout.example_calendar_dayview_custom, this);

			this.line = FindViewById(Resource.Id.line);
			this.mainContent = (ViewGroup)FindViewById(Resource.Id.main_content);
			this.title = (TextView)FindViewById(Resource.Id.event_title);
			this.details = (TextView)FindViewById(Resource.Id.event_details);
		}

		protected override void UpdateBackground()
		{
			int eventColor = Event.EventColor;
			Color mainColor = new Color(eventColor);
			Color accentColor = CalendarEventsHelper.GetAlternativeColor(mainColor);

			mainContent.SetBackgroundColor(mainColor);
			line.SetBackgroundColor(accentColor);
		}
	}
}
