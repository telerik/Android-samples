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
			return "Display Date Change Listener";
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
				Toast.MakeText (this.context, calendar.Time.ToString (), ToastLength.Long).Show ();
			}
		}
	}
}