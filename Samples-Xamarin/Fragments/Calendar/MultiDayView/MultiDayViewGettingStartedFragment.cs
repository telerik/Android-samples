using System;
using Android.OS;
using Android.Views;
using Com.Telerik.Widget.Calendar;

namespace Samples
{
    public class MultiDayViewGettingStartedFragment : Android.Support.V4.App.Fragment, ExampleFragment
    {
        public String Title()
        {
            return "MultiDayView Getting Started";
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            RadCalendarView calendarView = new RadCalendarView(Activity);

            calendarView.SelectionMode = CalendarSelectionMode.Single;
            calendarView.DisplayMode = CalendarDisplayMode.MultiDay;

            var events = CalendarEventsHelper.GenerateEvents();
            calendarView.EventAdapter.Events = events;

            return calendarView;
        }
    }
}
