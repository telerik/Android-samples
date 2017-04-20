using System;
using System.Collections.Generic;
using Com.Telerik.Widget.Calendar.Events;
using Java.Util;
using Android.Graphics;

namespace Samples
{
	public static class CalendarEventsHelper
	{
		public static Color ColorLight1 = Color.ParseColor("#29B6F6");
		public static Color ColorDark1 = Color.ParseColor("#01579B");
		public static Color ColorLight2 = Color.ParseColor("#4DD0E1");
		public static Color ColorDark2 = Color.ParseColor("#00ACC1");
		public static Color ColorLight3 = Color.ParseColor("#66BB6A");
		public static Color ColorDark3 = Color.ParseColor("#2E7D32");
		public static Color ColorLight4 = Color.ParseColor("#B39DDB");
		public static Color ColorDark4 = Color.ParseColor("#311B92");
		public static Color ColorLight5 = Color.ParseColor("#64B5F6");
		public static Color ColorDark5 = Color.ParseColor("#0D47A1");
		public static Color ColorLight6 = Color.ParseColor("#26A69A");
		public static Color ColorDark6 = Color.ParseColor("#004D40");

		public static List<Event> GenerateEvents()
		{
			Calendar calendar = Calendar.Instance;

			long start = GetTime(calendar, 2, 0);
			long end = GetTime(calendar, 3, 0);

			Event newEvent1 = new Event("Event Title 1", start, end);
			newEvent1.Details = "Details 1";
			newEvent1.EventColor = ColorLight1;

			start = GetTime(calendar, 3, 0);
			end = GetTime(calendar, 4, 0);
			Event newEvent2 = new Event("Event Title 2", start, end);
			newEvent2.Details = "Details 2";
			newEvent2.EventColor = ColorLight2;

			start = GetTime(calendar, 4, 0);
			end = GetTime(calendar, 5, 0);
			Event newEvent3 = new Event("Event Title 3", start, end);
			newEvent3.Details = "Details 3";
			newEvent3.EventColor = ColorLight3;

			start = GetTime(calendar, 5, 0);
			end = GetTime(calendar, 6, 0);
			Event newEvent4 = new Event("Event Title 4", start, end);
			newEvent4.Details = "Details 4";
			newEvent4.EventColor = ColorLight4;

			start = GetTime(calendar, 6, 0);
			end = GetTime(calendar, 7, 0);
			Event newEvent5 = new Event("Event Title 5", start, end);
			newEvent5.Details = "Details 5";
			newEvent5.EventColor = ColorLight5;

			start = GetTime(calendar, 7, 0);
			end = GetTime(calendar, 8, 0);
			Event newEvent6 = new Event("Event Title 6", start, end);
			newEvent6.Details = "Details 6";
			newEvent6.EventColor = ColorLight6;

			start = GetTime(calendar, 14, 0);
			end = GetTime(calendar, 15, 30);
			Event newEvent7 = new Event("Event Title 7", start, end);
			newEvent7.Details = "Details 7";
			newEvent7.EventColor = ColorLight1;

			start = GetTime(calendar, 15, 0);
			end = GetTime(calendar, 17, 0);
			Event newEvent8 = new Event("Event Title 8", start, end);
			newEvent8.Details = "Details 8";
			newEvent8.EventColor = ColorLight2;

			start = GetTime(calendar, 16, 0);
			end = GetTime(calendar, 17, 30);
			Event newEvent9 = new Event("Event Title 9", start, end);
			newEvent9.Details = "Details 9";
			newEvent9.EventColor = ColorLight3;

			List<Event> events = new List<Event>();
			events.Add(newEvent1);
			events.Add(newEvent2);
			events.Add(newEvent3);
			events.Add(newEvent4);
			events.Add(newEvent5);
			events.Add(newEvent6);
			events.Add(newEvent7);
			events.Add(newEvent8);
			events.Add(newEvent9);

			Color allDayColor = Color.ParseColor("#B3E5FC");
			Event allDay1 = new Event("All Day 1", start, end);
			allDay1.AllDay = true;
			allDay1.EventColor = allDayColor;

			Event allDay2 = new Event("All Day 2", start, end);
			allDay2.AllDay = true;
			allDay2.EventColor = allDayColor;

			Event allDay3 = new Event("All Day 3", start, end);
			allDay3.AllDay = true;
			allDay3.EventColor = allDayColor;

			Event allDay4 = new Event("All Day 4", start, end);
			allDay4.AllDay = true;
			allDay4.EventColor = allDayColor;

			Event allDay5 = new Event("All Day 5", start, end);
			allDay5.AllDay = true;
			allDay5.EventColor = allDayColor;

			events.Add(allDay1);
			events.Add(allDay2);
			events.Add(allDay3);
			events.Add(allDay4);
			events.Add(allDay5);

			return events;
		}

		public static long GetTime(Calendar calendar, int hour, int minute)
		{
			calendar.Set(CalendarField.HourOfDay, hour);
			calendar.Set(CalendarField.Minute, minute);
			return calendar.TimeInMillis;
		}

		public static Color GetAlternativeColor(Color mainColor)
		{
			if (mainColor == CalendarEventsHelper.ColorLight1)
			{
				return CalendarEventsHelper.ColorDark1;
			}
			else if (mainColor == CalendarEventsHelper.ColorLight2)
			{
				return CalendarEventsHelper.ColorDark2;
			}
			else if (mainColor == CalendarEventsHelper.ColorLight3)
			{
				return CalendarEventsHelper.ColorDark3;
			}
			else if (mainColor == CalendarEventsHelper.ColorLight4)
			{
				return CalendarEventsHelper.ColorDark4;
			}
			else if (mainColor == CalendarEventsHelper.ColorLight5)
			{
				return CalendarEventsHelper.ColorDark5;
			}
			else
			{
				return CalendarEventsHelper.ColorDark6;
			}
		}
	}
}
