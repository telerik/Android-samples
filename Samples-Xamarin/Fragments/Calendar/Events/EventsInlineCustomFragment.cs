using System;
using System.Collections.Generic;
using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Widget.Calendar.Events.Read;
using Java.Util;
using Com.Telerik.Widget.Calendar.Events;
using Android.Graphics;
using Android.Widget;
using Android.Content;

namespace Samples
{
	public class EventsInlineCustomFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView(Activity);

			// Setting the events display mode
			calendarView.SelectionMode = CalendarSelectionMode.Single;
			calendarView.EventsDisplayMode = EventsDisplayMode.Inline;

			// Creating some events
			List<Event> events = new List<Event>();
			Calendar calendar = Calendar.Instance;

			long start = calendar.TimeInMillis;
			calendar.Add(CalendarField.Hour, 1);
			long end = calendar.TimeInMillis;

			events.Add(new Event("Test1", start, end));

			calendar.Add(CalendarField.Date, 1);
			calendar.Add(CalendarField.Hour, -1);
			start = calendar.TimeInMillis;
			calendar.Add(CalendarField.Hour, 3);
			end = calendar.TimeInMillis;

			events.Add(new Event("Test2", start, end));

			calendarView.EventAdapter.Events = events;

			// >> calendar-custom-inline-events-adapter-init
			MyInlineEventsAdapter adapter = new MyInlineEventsAdapter(Context);
			calendarView.EventsManager().Adapter = adapter;
			// << calendar-custom-inline-events-adapter-init

			return calendarView;
		}

		public String Title()
		{
			return "Inline mode Custom";
		}
	}

	// >> calendar-custom-inline-events-adapter
	public class MyInlineEventsAdapter : ArrayAdapter
	{
		private LayoutInflater layoutInflater;

		public MyInlineEventsAdapter(Context context)
			: base(context, Resource.Layout.custom_inline_event_layout)
		{
			this.layoutInflater = LayoutInflater.From(context); ;
		}

		public override View GetView(int position, View convertView, ViewGroup parent)
		{
			View view = convertView;
			ViewHolder holder;

			if (view == null)
			{
				view = layoutInflater.Inflate(
					Resource.Layout.custom_inline_event_layout, parent, false);

				holder = new ViewHolder();
				holder.eventTitle = (TextView)view.FindViewById(Resource.Id.event_title);
				holder.eventTime = (TextView)view.FindViewById(Resource.Id.event_time);

				view.Tag = holder;
			}
			else {
				holder = (ViewHolder)view.Tag;
			}

			EventsManager.EventInfo eventInfo = (EventsManager.EventInfo)GetItem(position);
			Event event1 = eventInfo.OriginalEvent();
			holder.eventTitle.SetTextColor(new Color(event1.EventColor));
			holder.eventTitle.Text = event1.Title;
			String eventTime = String.Format("{0} - {1}", 
			                                 eventInfo.StartTimeFormatted(), eventInfo.EndTimeFormatted());
			holder.eventTime.Text = eventTime;

			return view;
		}

		class ViewHolder : Java.Lang.Object
		{
			public TextView eventTitle;
			public TextView eventTime;
		}
	}
	// << calendar-custom-inline-events-adapter
}

