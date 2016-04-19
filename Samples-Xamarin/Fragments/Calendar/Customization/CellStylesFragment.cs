
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
using Android.Graphics;

namespace Samples
{
	public class CellStylesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public String Title() {
			return "Cell Styles";
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			RadCalendarView calendarView = new RadCalendarView(Activity);

			// >> calendar-customizations-cell-styles-days
			CalendarDayCellFilter weekendCellFilter = new CalendarDayCellFilter();
			weekendCellFilter.IsWeekend = new Java.Lang.Boolean(true);
			CalendarDayCellStyle weekendCellStyle = new CalendarDayCellStyle();
			weekendCellStyle.Filter = weekendCellFilter;
			weekendCellStyle.TextColor = new Java.Lang.Integer(Color.Red.ToArgb());
			calendarView.AddDayCellStyle(weekendCellStyle);

			CalendarDayCellFilter todayCellFilter = new CalendarDayCellFilter();
			todayCellFilter.IsToday = new Java.Lang.Boolean(true);
			CalendarDayCellStyle todayCellStyle = new CalendarDayCellStyle();
			todayCellStyle.Filter = todayCellFilter;
			todayCellStyle.BorderColor = new Java.Lang.Integer(Color.Green.ToArgb());
			float widthInDp = 4;
			float widthInPixels = (int) TypedValue.ApplyDimension(ComplexUnitType.Dip,
				widthInDp, Resources.DisplayMetrics);
			todayCellStyle.BorderWidth = new Java.Lang.Float(widthInPixels);
			calendarView.AddDayCellStyle(todayCellStyle);
			// << calendar-customizations-cell-styles-days

			// >> calendar-customizations-cell-styles-months
			CalendarMonthCellFilter monthCellTitleFilter = new CalendarMonthCellFilter();
			monthCellTitleFilter.TextIsMonthName = new Java.Lang.Boolean(true);
			CalendarMonthCellStyle monthCellTitleStyle = new CalendarMonthCellStyle();
			monthCellTitleStyle.Filter = monthCellTitleFilter;
			monthCellTitleStyle.TextColor = new Java.Lang.Integer(Color.Blue.ToArgb());
			calendarView.AddMonthCellStyle(monthCellTitleStyle);
			// << calendar-customizations-cell-styles-months

			return calendarView;
		}
	}
}

