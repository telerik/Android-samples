using Com.Telerik.Widget.Calendar;
using Android.Views;
using Android.OS;
using System;
using Java.Util;
using Android.Content;
using Android.Widget;

namespace Samples
{
	public class DisplayDateChangeListenerFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadCalendarView calendarView;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			calendarView = new RadCalendarView (Activity);
			calendarView.SetOnDisplayDateChangedListener (new DateChangeListener(this.Activity));

			return calendarView;
		}

		public String Title() {
			return "Display date changed listener";
		}

		private class DateChangeListener : Java.Lang.Object, Com.Telerik.Widget.Calendar.RadCalendarView.IOnDisplayDateChangedListener
		{
			Calendar calendar = Java.Util.Calendar.Instance;
			Context context;

			public DateChangeListener(Context context)
			{
				this.context = context;
			}

			public void OnDisplayDateChanged (long oldValue, long newValue)
			{
				calendar.TimeInMillis = newValue;
				String value = String.Format("New display date: {0}-{1}-{2}", calendar.Get(CalendarField.Year), calendar.Get(CalendarField.Month) + 1, calendar.Get(CalendarField.DayOfMonth));
				Toast.MakeText (this.context, value, ToastLength.Long).Show ();
			}
		}
	}
}