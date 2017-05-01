
using System;
using System.Collections.Generic;

using Android.App;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Java.Util;
using Com.Telerik.Widget.Calendar.Events;
using Android.Views;
using Android.Graphics;
using Android.Content;

namespace Samples
{
	[Activity(Label = "EventRenderCustomFragment")]
	public class EventRenderCustomFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView(Activity);

			Calendar calendar = Calendar.Instance;
			long start = calendar.TimeInMillis;
			calendar.Add(CalendarField.Hour, 3);
			long end = calendar.TimeInMillis;
			Event newEvent1 = new Event("Event 1", start, end);
			newEvent1.EventColor = Color.Cyan;
			Event newEvent2 = new Event("Event 2", start, end);
			newEvent2.EventColor = Color.Magenta;
			Event newEvent3 = new Event("Event 3", start, end);
			newEvent3.EventColor = Color.Red;
			Event newEvent4 = new Event("Event 4", start, end);
			newEvent4.EventColor = Color.Green;
			Event newEvent5 = new Event("Event 5", start, end);
			newEvent5.EventColor = Color.Blue;

			IList<Event> events = new List<Event>();
			events.Add(newEvent1);
			events.Add(newEvent2);
			events.Add(newEvent3);
			events.Add(newEvent4);
			events.Add(newEvent5);

			calendarView.EventAdapter.Events = events;

			// >> calendar-custom-event-renderer-init
			MyEventRenderer eventRenderer = new MyEventRenderer(Context);
			calendarView.EventAdapter.Renderer = eventRenderer;
			// << calendar-custom-event-renderer-init

			calendarView.SelectionMode = CalendarSelectionMode.Single;
			calendarView.EventsDisplayMode = EventsDisplayMode.Inline;

			return calendarView;
		}

		public String Title()
		{
			return "Event render mode Custom";
		}
	}

	// >> calendar-custom-event-renderer
	public class MyEventRenderer : EventRenderer
	{

		int shapeSpacing = 25;
		int shapeRadius = 10;
		Paint paint;


		public MyEventRenderer(Context context)
				: base(context)
		{

			paint = new Paint();
			paint.AntiAlias = true;
		}

		public override void RenderEvents(Canvas canvas, CalendarDayCell cell)
		{
			int startX = cell.Left + shapeSpacing;
			int startY = cell.Top + shapeSpacing;

			Rect drawTextRect = new Rect();
			if (cell.Text != null)
			{
				String text = cell.Text;
				cell.TextPaint.GetTextBounds(text, 0, text.Length, drawTextRect);
			}

			int x = startX;
			int y = startY;

			int spacingForDate = drawTextRect.Width();

			for (int i = 0; i < cell.Events.Count; i++)
			{
				Event e = cell.Events[i];
				paint.Color = new Color(e.EventColor);
				canvas.DrawCircle(x, y, shapeRadius, paint);
				x += shapeSpacing;
				if (x > cell.Right - spacingForDate - shapeSpacing)
				{
					x = startX;
					y += shapeSpacing;
				}
			}
		}
		// << calendar-custom-event-renderer
	}
}
