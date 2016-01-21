using System;
using System.Collections.Generic;
using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Calendar;
using Android.Content;
using Android.Widget;
using Com.Telerik.Widget.Calendar.Events.Read;
using Java.Util;
using Android.Content.PM;
using Android;
using Android.Support.V4.Content;
using Android.Provider;

namespace Samples
{
	public class ReadEventsFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public static readonly int MyPermissionsRequestReadCalendar = 12;
		private static readonly String PrefsName = "telerik_samples_java_preferences";
		private static readonly String PermissionRejectedKey = "telerik_samples_calendar_rejected";
		private EventReadAdapter adapter;
		private bool permissionWasRequested = false;

		private LinearLayout settingsLayout;
		private LinearLayout readEventsLayout;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_calendar_events_read, container, false);
			settingsLayout = (LinearLayout)rootView.FindViewById(Resource.Id.go_settings_layout);
			readEventsLayout = (LinearLayout)rootView.FindViewById(Resource.Id.read_events_layout);

			RadCalendarView calendarView = new RadCalendarView(Activity);

			adapter = new EventReadAdapter(calendarView);
			calendarView.EventAdapter = adapter;

			Button settingsButton = (Button)rootView.FindViewById(Resource.Id.go_settings_button);
			settingsButton.Click += (object sender, EventArgs e) => {
				GoToSettings();
			};

			Button readEventsButton = (Button)rootView.FindViewById(Resource.Id.read_events_button);
			readEventsButton.Click += (object sender, EventArgs e) => {
				TryReadEvents();
			};
			InitLayoutVisibility();
			rootView.AddView(calendarView);

			return rootView;
		}

		public String Title() {
			return "Read events";
		}

		public override void OnRequestPermissionsResult (int requestCode, string[] permissions, Permission[] grantResults)
		{
			if (requestCode == MyPermissionsRequestReadCalendar) {
				if(grantResults != null && grantResults[0] == Permission.Granted) {
					OnPermissionGranted();
				} else {
					bool showRationale = ShouldShowRequestPermissionRationale(Manifest.Permission.ReadCalendar);
					if (!showRationale) {
						SwitchButtonLayout(true);
						SavePermissionWasRejected();
					} else {
						Toast.MakeText(Context, "events can't be loaded without the proper permission", ToastLength.Short).Show();
					}
				}
			}
		}

		private void TryReadEvents() {
			if (ContextCompat.CheckSelfPermission(Activity,
				Manifest.Permission.ReadCalendar)
				!= Permission.Granted) {
				RequestPermissions(new String[]{Manifest.Permission.ReadCalendar},
					MyPermissionsRequestReadCalendar);
			} else {
				ReadEvents();
			}
		}

		// This method should be called only when a READ_CALENDAR permission is granted.
		// You can see 3 different customization option by uncommenting the corresponding lines.
		// Only one of the options must be uncommented at a time.
		private void ReadEvents() {

			// 1. Simply read all events from the device's default calendar
			adapter.ReadEventsAsync();

			// 2. Read the events from all calendars:
			//        EventReadAdapter.GetAllCalendarsAsync (Activity, new ResultListener(adapter));

			//3. Read events for a specified range:
			//          Calendar calendar = Calendar.Instance;
			//			long start = calendar.TimeInMillis;
			//			calendar.Add (CalendarField.Date, 7);
			//			long end = calendar.TimeInMillis;
			//
			//			EventQueryToken token = adapter.EventsQueryToken;
			//			token.SetRange (start, end);
			//
			//			adapter.ReadEventsAsync ();

			readEventsLayout.Visibility = ViewStates.Gone;
			settingsLayout.Visibility = ViewStates.Gone;

			Toast.MakeText(Context, "events loaded", ToastLength.Short).Show();
		}

		private void CheckFirstTimePermissions() {
			
			ISharedPreferences preferences = Context.GetSharedPreferences(PrefsName, FileCreationMode.Private);
			permissionWasRequested = preferences.GetBoolean(PermissionRejectedKey, false);
		}

		private void SavePermissionWasRejected() {
			ISharedPreferences preferences = Context.GetSharedPreferences(PrefsName, FileCreationMode.Private);
			ISharedPreferencesEditor editor = preferences.Edit();
			editor.PutBoolean(PermissionRejectedKey, true);
			editor.Apply();
		}

		private void InitLayoutVisibility() {
			if(ContextCompat.CheckSelfPermission(Activity,
				Manifest.Permission.ReadCalendar)
				!= Permission.Granted) {
				CheckFirstTimePermissions();
				if (permissionWasRequested && !ShouldShowRequestPermissionRationale(Manifest.Permission.ReadCalendar)) {
					SwitchButtonLayout(true);
				}
			}
		}

		private void SwitchButtonLayout(bool showSettings) {
			settingsLayout.Visibility = showSettings ? ViewStates.Visible : ViewStates.Gone;
			readEventsLayout.Visibility = showSettings ? ViewStates.Gone : ViewStates.Visible;
		}

		private void OnPermissionGranted() {
			ReadEvents();
		}

		private void OnPermissionDenied() {
			Toast.MakeText(Activity, "events can't be loaded without the proper permission", ToastLength.Short).Show();
		}

		private void GoToSettings() {
			Intent intent = new Intent(Settings.ActionApplicationDetailsSettings);
			Android.Net.Uri uri = Android.Net.Uri.FromParts("package", Context.PackageName, null);
			intent.SetData(uri);
			StartActivityForResult(intent, MyPermissionsRequestReadCalendar);
		}

		public override void OnActivityResult (int requestCode, int resultCode, Intent data)
		{
			base.OnActivityResult (requestCode, resultCode, data);

			if(requestCode == MyPermissionsRequestReadCalendar) {
				if (ContextCompat.CheckSelfPermission(Activity,
					Manifest.Permission.ReadCalendar)
					!= Permission.Granted) {
					OnPermissionDenied();
				} else {
					OnPermissionGranted();
				}
			}
		}

		private class ResultListener : Java.Lang.Object, IGenericResultCallback
		{
			EventReadAdapter adapter;

			public ResultListener(EventReadAdapter adapter)
			{
				this.adapter = adapter;
			}

			public void OnResult(Java.Lang.Object result)
			{
				EventReadAdapter.CalendarInfo[] calendars = result.ToArray<EventReadAdapter.CalendarInfo> ();
				string[] calendarIDs = new string[calendars.Length];
				for (int i = 0; i < calendars.Length; i++) {
					calendarIDs [i] = calendars [i].Id;
				}

				adapter.EventsQueryToken = EventQueryToken.GetCalendarsById (calendarIDs);
				adapter.ReadEventsAsync ();
			}
		}
	}
}