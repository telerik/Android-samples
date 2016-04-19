
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
using Com.Telerik.Widget.Calendar;
using Java.Util;
using Android.Graphics;
using Com.Telerik.Android.Common;

namespace Samples
{
	public class CustomizationRuleFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public String Title() {
			return "Customization Rules";
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView(Activity);

			// >> calendar-customizations-customization-rule
			Calendar calendar = Calendar.GetInstance(Java.Util.TimeZone.Default);
			calendarView.CustomizationRule = new CustomizationRuleExample ();
			// << calendar-customizations-customization-rule

			return calendarView;
		}

		class CustomizationRuleExample : Java.Lang.Object, IProcedure
		{
			private Java.Util.Calendar calendar = Java.Util.Calendar.Instance;
			public void Apply (Java.Lang.Object p0)
			{
				CalendarCell calendarCell = p0.JavaCast<CalendarCell>();
				if (calendarCell.CellType != CalendarCellType.Date) {
					return;
				}
				calendar.TimeInMillis = calendarCell.Date;
				if (calendar.Get (Java.Util.CalendarField.DayOfMonth) == 21 &&
					calendar.Get (Java.Util.CalendarField.Month) == 
					Java.Util.Calendar.Instance.Get(Java.Util.CalendarField.Month)) 
				{
					calendarCell.SetBackgroundColor (
						Android.Graphics.Color.ParseColor("#FF00A1"),
						Android.Graphics.Color.ParseColor("#F988CF"));
				} 
				else 
				{
					calendarCell.SetBackgroundColor (
						Android.Graphics.Color.ParseColor("#FFFFFF"),
						Android.Graphics.Color.ParseColor("#FBFBFB"));
				}
			}
		}
	}
}

