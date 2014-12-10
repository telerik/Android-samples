using System;
using System.Collections.Generic;
using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Android.Content;
using Android.Widget;


namespace Samples
{
	public class DisplayModeChangeListenerFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView (Activity);
			calendarView.SetOnDisplayModeChangedListener (new DisplayModeChangeListener(this.Activity));

			return calendarView;
		}

		public String Title() {
			return "Display Mode'Change Listener";
		}

		private class DisplayModeChangeListener : Java.Lang.Object, Com.Telerik.Widget.Calendar.RadCalendarView.IOnDisplayModeChangedListener
		{
			private Context context;

			public DisplayModeChangeListener(Context context) 
			{
				this.context = context;
			}

			public void OnDisplayModeChanged (CalendarDisplayMode oldValue, CalendarDisplayMode newValue)
			{
				string msg = "";
				if (newValue == CalendarDisplayMode.Year) {
					msg = "Year mode";
				} else if (newValue == CalendarDisplayMode.Month) {
					msg = "Month mode";
				}

				Toast.MakeText (this.context, msg, ToastLength.Long).Show ();
			}
		}
	}
}