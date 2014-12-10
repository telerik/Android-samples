using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Java.Util;
using System;

namespace Samples
{
	public class SelectionSetRangeFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);
			Calendar calendar = Calendar.GetInstance (Java.Util.TimeZone.Default);

			calendar.Set (CalendarField.DayOfMonth, 15);
			long start = calendar.TimeInMillis;
			calendar.Set (CalendarField.DayOfMonth, 22);
			long end = calendar.TimeInMillis;

			calendarView.SelectionMode = CalendarSelectionMode.Range;
			calendarView.SelectedRange = new DateRange (start, end);

			return calendarView;
		}

		public String Title() {
			return "Selection set range";
		}
	}
}