using System;
using System.Collections.Generic;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Telerik.Android.Primitives.Widget.Sidedrawer;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Widget.Calendar.Events;
using Java.Util;

namespace Samples
{
    public class MultiDayViewConfigurationFragment : Android.Support.V4.App.Fragment, ExampleFragment
    {
        private System.Random random = new System.Random();
        private RadCalendarView calendarView;

        public String Title()
        {
            return "MultiDayView Configuration";
        }

        public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            this.calendarView = new RadCalendarView(Activity);

            this.calendarView.SelectionMode = CalendarSelectionMode.Single;
            this.calendarView.DisplayMode = CalendarDisplayMode.MultiDay;

            var events = GenerateClendarEvents();
            this.calendarView.EventAdapter.Events = events;

            RadSideDrawer drawer = new RadSideDrawer(Activity);

            drawer.MainContent = calendarView;
            drawer.DrawerContent = this.CreateConfigurationMenu();

            return drawer;
        }

        private IList<Event> GenerateClendarEvents()
        {
            var events = new List<Event>();

            for (int i = 0; i < 30; i++)
            {
                Calendar calendar = Calendar.Instance;
                calendar.Add(CalendarField.DayOfMonth, i);
                calendar.Set(CalendarField.HourOfDay, i % 22);
                calendar.Set(CalendarField.Minute, 0);


                long start = calendar.TimeInMillis;
                calendar.Add(CalendarField.Hour, 3);
                long end = calendar.TimeInMillis;
                Event calendarEvent = new Event("Event " + i, start, end);
                events.Add(calendarEvent);

                // create allday events
                for (int j = 0; j < 4; j++)
                {
                    Event calendarAllDayEvent1 = new Event("AllDay E " + j, start, end);
                    calendarAllDayEvent1.AllDay = true;
                    events.Add(calendarAllDayEvent1);
                }
            }

            return events;
        }

        private View CreateConfigurationMenu()
        {
            LinearLayout container = new LinearLayout(Activity);
            container.LayoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WrapContent, LinearLayout.LayoutParams.MatchParent);

            container.SetBackgroundColor(Android.Graphics.Color.White);

            var label = new TextView(Activity);
            label.Text = "Set visible days:";

            var spinnerArray = new System.Collections.Generic.List<int>();
            spinnerArray.Add(1);
            spinnerArray.Add(2);
            spinnerArray.Add(3);
            spinnerArray.Add(4);
            spinnerArray.Add(6);
            spinnerArray.Add(7);

            Spinner spinner = new Spinner(Activity);
            ArrayAdapter<int> spinnerArrayAdapter = new ArrayAdapter<int>(Activity, Android.Resource.Layout.SimpleSpinnerDropDownItem, spinnerArray);
            spinner.Adapter = spinnerArrayAdapter;

            spinner.SetSelection(2);
            spinner.ItemSelected += (object sender, AdapterView.ItemSelectedEventArgs e) => 
            {
                this.calendarView.MultiDayView.VisibleDays = spinnerArray[e.Position];
            };

            container.AddView(label);
            container.AddView(spinner);

            return container;
        }
    }
}
