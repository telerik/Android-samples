using System;
using System.Collections;
using System.Collections.Generic;
using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Telerik.Android.Primitives.Widget.Sidedrawer;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Widget.Calendar.Events;
using Java.Text;
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

            this.calendarView.MultiDayView.EventViewTapListener = new EventViewTapListener(Context);
            this.calendarView.MultiDayView.AddTimeSlotTappedListener(new TimeSlotTappedListener(Context));

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
            container.Orientation = Orientation.Vertical;
            container.LayoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WrapContent, LinearLayout.LayoutParams.MatchParent);

            container.SetBackgroundColor(Android.Graphics.Color.White);

            // VisibleDays configuration
            var visibleDaysLabel = new TextView(Activity);
            visibleDaysLabel.Text = "Set visible days:";

            var visibleDaysSpinnerArray = new System.Collections.Generic.List<int>
            {
                1, 2, 3, 4, 5, 6, 7
            };

            Spinner visibleDaysSpinner = this.CreateSpinner(visibleDaysSpinnerArray, 4, (sender, e) =>
            {
                this.calendarView.MultiDayView.VisibleDays = visibleDaysSpinnerArray[e.Position];
            });

            container.AddView(visibleDaysLabel);
            container.AddView(visibleDaysSpinner);


            // CurrentTimeIndicatorVisible configuration
            var currentTimeIndicatorVisibleLabel = new TextView(Activity);
            currentTimeIndicatorVisibleLabel.Text = "Set CurrentTimeIndicator visible:";

            var currentTimeIndicatorVisibleSpinnerArray = new System.Collections.Generic.List<bool>
            {
                true, false
            };
            Spinner currentTimeIndicatorVisibleSpinner = this.CreateSpinner(currentTimeIndicatorVisibleSpinnerArray, 0, (sender, e) =>
            {
                this.calendarView.MultiDayView.DayEventsViewStyle.CurrentTimeIndicatorVisible = currentTimeIndicatorVisibleSpinnerArray[e.Position];
            });

            container.AddView(currentTimeIndicatorVisibleLabel);
            container.AddView(currentTimeIndicatorVisibleSpinner);


            // CurrentTimeIndicatorWidth configuration
            var currentTimeIndicatorWidthLabel = new TextView(Activity);
            currentTimeIndicatorWidthLabel.Text = "Set CurrentTimeIndicator width:";

            var currentTimeIndicatorWidthSpinnerArray = new System.Collections.Generic.List<int>
            {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            };

            Spinner currentTimeIndicatorWidthSpinner = this.CreateSpinner(currentTimeIndicatorWidthSpinnerArray, 3, (sender, e) =>
            {
                this.calendarView.MultiDayView.DayEventsViewStyle.CurrentTimeIndicatorWidth = currentTimeIndicatorWidthSpinnerArray[e.Position];
            });

            container.AddView(currentTimeIndicatorWidthLabel);
            container.AddView(currentTimeIndicatorWidthSpinner);


            // CurrentTimeIndicatorRadius configuration
            var currentTimeIndicatorRadiusLabel = new TextView(Activity);
            currentTimeIndicatorRadiusLabel.Text = "Set CurrentTimeIndicator radius:";

            var currentTimeIndicatorRadiusSpinnerArray = new System.Collections.Generic.List<int>
            {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
            };

            Spinner currentTimeIndicatorRadiusSpinner = this.CreateSpinner(currentTimeIndicatorRadiusSpinnerArray, 3, (sender, e) =>
            {
                this.calendarView.MultiDayView.DayEventsViewStyle.CurrentTimeIndicatorRadius = currentTimeIndicatorRadiusSpinnerArray[e.Position];
            });

            container.AddView(currentTimeIndicatorRadiusLabel);
            container.AddView(currentTimeIndicatorRadiusSpinner);


            // CurrentTimeIndicatorColor configuration
            var currentTimeIndicatorColorLabel = new TextView(Activity);
            currentTimeIndicatorColorLabel.Text = "Set CurrentTimeIndicator color:";

            var currentTimeIndicatorColorSpinnerArray = new System.Collections.Generic.List<string>
            {
                "#" + Java.Lang.Integer.ToHexString(Color.Red).ToUpper().Substring(2),
                "#" + Java.Lang.Integer.ToHexString(Color.Yellow).ToUpper().Substring(2),
                "#" + Java.Lang.Integer.ToHexString(Color.Green).ToUpper().Substring(2),
                "#" + Java.Lang.Integer.ToHexString(Color.Blue).ToUpper().Substring(2),
            };

            Spinner currentTimeIndicatorColorSpinner = this.CreateSpinner(currentTimeIndicatorColorSpinnerArray, 0, (sender, e) =>
            {
                this.calendarView.MultiDayView.DayEventsViewStyle.CurrentTimeIndicatorColor = Color.ParseColor(currentTimeIndicatorColorSpinnerArray[e.Position]);
            });

            container.AddView(currentTimeIndicatorColorLabel);
            container.AddView(currentTimeIndicatorColorSpinner);


            // CurrentTimeIndicatorColor configuration
            var todayBackgroundColorLabel = new TextView(Activity);
            todayBackgroundColorLabel.Text = "Set today background color:";

            var todayBackgroundColorSpinnerArray = new System.Collections.Generic.List<string>
            {
                "#" + Java.Lang.Integer.ToHexString(Color.ParseColor("#F5F5F5")).ToUpper().Substring(2),
                "#" + Java.Lang.Integer.ToHexString(Color.Yellow).ToUpper().Substring(2),
                "#" + Java.Lang.Integer.ToHexString(Color.Green).ToUpper().Substring(2),
                "#" + Java.Lang.Integer.ToHexString(Color.White).ToUpper().Substring(2)
            };

            Spinner todayBackgroundColorSpinner = this.CreateSpinner(todayBackgroundColorSpinnerArray, 0, (sender, e) =>
            {
                this.calendarView.MultiDayView.DayEventsViewStyle.TodayBackgroundColor = Color.ParseColor(todayBackgroundColorSpinnerArray[e.Position]);
                this.calendarView.MultiDayView.AllDayEventsViewStyle.TodayBackgroundColor = Color.ParseColor(todayBackgroundColorSpinnerArray[e.Position]);
            });

            container.AddView(todayBackgroundColorLabel);
            container.AddView(todayBackgroundColorSpinner);

            return container;
        }

        private Spinner CreateSpinner(System.Collections.IList options, int initialSelection, Action<object, AdapterView.ItemSelectedEventArgs> action)
        {
            Spinner spinner = new Spinner(Activity);
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(Activity, Android.Resource.Layout.SimpleSpinnerDropDownItem, options);
            spinner.Adapter = spinnerArrayAdapter;

            spinner.SetSelection(initialSelection);
            spinner.ItemSelected += (object sender, AdapterView.ItemSelectedEventArgs e) =>
            {
                action.Invoke(sender, e);
            };

            return spinner;
        }

        public class EventViewTapListener : Java.Lang.Object, Com.Telerik.Widget.Calendar.Dayview.CalendarDayView.IEventViewTapListener
        {
            private Context context;

            public EventViewTapListener(Context context)
            {
                this.context = context;
            }

            public void OnEventViewTap(Event p0)
            {
                Toast.MakeText(this.context, String.Format("Еvent clicked: {0}", p0.Title), ToastLength.Short).Show();
            }
        }

        public class TimeSlotTappedListener : Java.Lang.Object, Com.Telerik.Widget.Calendar.Dayview.IDayViewTimeSlotTappedListener
        {
            private Context context;
            private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

            public TimeSlotTappedListener(Context context)
            {
                this.context = context;
            }

            public void OnTimeSlotTapped(long exactTime, long startTime, long endTime)
            {
                Calendar calendar = Calendar.Instance;
                calendar.TimeInMillis = startTime;

                string startTimeString = dateFormat.Format(calendar.Time);

                calendar.TimeInMillis = endTime;
                string endTimeString = dateFormat.Format(calendar.Time);

                Toast.MakeText(this.context, String.Format("TimeSlot clicked: {0} - {1}", startTimeString, endTimeString), ToastLength.Short).Show();
            }
        }
    }
}
