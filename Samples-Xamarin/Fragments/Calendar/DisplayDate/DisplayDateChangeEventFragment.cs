using Com.Telerik.Widget.Calendar;
using Android.Views;
using Android.OS;
using System;
using Java.Util;
using Android.Widget;

namespace Samples
{
	public class DisplayDateChangeEventFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadCalendarView calendarView;
		Calendar calendar = Java.Util.Calendar.Instance;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			calendarView = new RadCalendarView (Activity);
			calendarView.DisplayDateChanged += delegate(object sender, RadCalendarView.DisplayDateChangedEventArgs e) {
				OnDisplayDateChanged(e.P0, e.P1);
			};

			return calendarView;
		}

		public String Title() {
			return "Display Date Change Event";
		}

		public void OnDisplayDateChanged (long oldValue, long newValue)
		{
			calendar.TimeInMillis = newValue;
			Toast.MakeText (this.Activity, calendar.Time.ToString (), ToastLength.Long).Show ();
		}
	}
}