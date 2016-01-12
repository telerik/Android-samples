using Com.Telerik.Widget.Calendar;
using Android.Views;
using Android.OS;
using System;
using System.Collections.Generic;
using Java.Util;
using Android.Widget;
using Com.Telerik.Widget.Calendar.Decorations;

namespace Samples
{
	public class SelectionDecoratorsFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadCalendarView calendarView;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			calendarView = new RadCalendarView (Activity);
			calendarView.Adapter.DateTextPosition = CalendarElement.Center;
			calendarView.Adapter.SelectedCellTextColor = Android.Graphics.Color.White;
			calendarView.ShowGridLines = false;

			calendarView.SelectionMode = CalendarSelectionMode.Range;

			RangeDecorator decorator = new CircularRangeDecorator (calendarView);
			decorator.Scale = 1f;
			decorator.ShapeScale = .77f;
			decorator.Color = Android.Graphics.Color.Magenta;
			decorator.ShapeColor = Android.Graphics.Color.DarkGray;

			calendarView.CellDecorator = decorator;

			return calendarView;
		}

		public String Title() {
			return "Selection decorators";
		}
	}
}