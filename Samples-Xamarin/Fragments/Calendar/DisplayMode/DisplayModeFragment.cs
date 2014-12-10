using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using System;

namespace Samples
{
	public class DisplayModeFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);
			/* 
			 * Setting the display mode with `false` for animation. This way the change
			 * will not be visible at the initial state of the calendar.
			 */
			calendarView.ChangeDisplayMode(CalendarDisplayMode.Week, false); 

			return calendarView;
		}

		public String Title() {
			return "Display Modes";
		}
	}
}