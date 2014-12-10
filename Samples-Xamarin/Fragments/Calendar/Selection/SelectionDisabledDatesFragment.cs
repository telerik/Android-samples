using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using System;
using Com.Telerik.Android.Common;
using Java.Util;
using Android.Runtime;

namespace Samples
{
	public class SelectionDisabledDatesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);
			calendarView.CustomizationRule = new DisabledDatesRule (calendarView.Calendar);

			return calendarView;
		}

		public String Title() {
			return "Selection disabled dates";
		}

		private class DisabledDatesRule : Java.Lang.Object, IProcedure
		{
			private readonly Calendar calendar;

			public DisabledDatesRule(Calendar calendar)
			{
				this.calendar = calendar;
			}

			public void Apply (Java.Lang.Object calendarCell)
			{
				CalendarDayCell dayCell = calendarCell.JavaCast<CalendarDayCell> ();
				if (dayCell.CellType != CalendarCellType.Date) {
					return;
				}

				this.calendar.TimeInMillis = dayCell.Date;
				
				if (calendar.Get (CalendarField.DayOfWeek) == Calendar.Saturday) {
					dayCell.Selectable = false;
				} else {
					dayCell.Selectable = true;
				}
			}
		}
	}
}