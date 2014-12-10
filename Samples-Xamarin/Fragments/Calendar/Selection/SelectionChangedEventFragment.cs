using Com.Telerik.Widget.Calendar;
using Android.Views;
using Android.OS;
using System;
using System.Collections.Generic;
using Java.Util;
using Android.Widget;

namespace Samples
{
	public class SelectionChangedEventFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadCalendarView calendarView;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			calendarView = new RadCalendarView (Activity);
			calendarView.SelectionMode = CalendarSelectionMode.Multiple;
			calendarView.SelectedDatesChanged += delegate(object sender, RadCalendarView.SelectedDatesChangedEventArgs e) {
				OnSelectedDatesChanged(e.P0);
			};

			return calendarView;
		}

		public String Title() {
			return "Selection event";
		}

		public void OnSelectedDatesChanged (RadCalendarView.SelectionContext selectionContext)
		{
			IList<Java.Lang.Long> datesAdded = selectionContext.DatesAdded ();
			IList<Java.Lang.Long> datesRemoved = selectionContext.DatesRemoved ();
			IList<Java.Lang.Long> oldSelection = selectionContext.OldSelection ();
			IList<Java.Lang.Long> newSelection = selectionContext.NewSelection ();

			Calendar calendar = Calendar.GetInstance(Java.Util.TimeZone.Default);
			String log = "";
			log += "=======================================\n";
			log += "Dates added:\n";
			foreach (long date in datesAdded) {
				calendar.TimeInMillis = date;
				log += calendar.Time.ToString() + "\n";
			}
			log += datesAdded.Count + "\n";

			log += "------------------------------------";

			log += "\nDates removed:\n";
			foreach (long date in datesRemoved) {
				calendar.TimeInMillis = date;
				log += calendar.Time.ToString() + "\n";
			}
			log += datesRemoved.Count + "\n";

			log += "------------------------------------";

			log += "\nNew selection:\n";
			foreach (long date in newSelection) {
				calendar.TimeInMillis = date;
				log += calendar.Time.ToString() + "\n";
			}
			log += newSelection.Count + "\n";

			log += "------------------------------------";

			log += "\nOld selection:\n";
			foreach (long date in oldSelection) {
				calendar.TimeInMillis = date;
				log += calendar.Time.ToString() + "\n";
			}
			log += oldSelection.Count + "\n";

			Toast.MakeText (this.Activity, log, ToastLength.Long).Show ();
		}
	}
}