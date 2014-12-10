using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using System;

namespace Samples
{
	public class SelectionModesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);

			calendarView.SelectionMode = CalendarSelectionMode.Range;
			//calendarView.SelectionMode = CalendarSelectionMode.Single;
			//calendarView.SelectionMode = CalendarSelectionMode.Multiple;

			return calendarView;
		}

		public String Title() {
			return "Selection modes";
		}
	}
}