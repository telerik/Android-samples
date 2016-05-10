
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Telerik.Android.Common;
using Java.Util;
using Android.Graphics;
using Com.Telerik.Widget.Calendar;

namespace Samples
{
	public class DateToColorFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public String Title() {
			return "Date to Color";
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView(Activity);

			// >> calendar-customizations-date-to-color
			Calendar calendar = Calendar.GetInstance(Java.Util.TimeZone.Default);
			calendarView.DateToColor = new DateToColorExample ();
			// ...
			// >> (hide)
			return calendarView;
		}

		// << (hide)
		class DateToColorExample : Java.Lang.Object, IFunction
		{
			private Java.Util.Calendar calendar = Java.Util.Calendar.Instance;

			public Java.Lang.Object Apply (Java.Lang.Object timeInMillis)
			{
				calendar.TimeInMillis = (long)timeInMillis;
				if(calendar.Get(CalendarField.DayOfWeek) == Calendar.Sunday) {
					return Color.Red.ToArgb();
				} 
				return null;
			}
		}
		// << calendar-customizations-date-to-color
	}
}

