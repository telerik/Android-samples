using Com.Telerik.Widget.Calendar;
using Android.Views;
using Android.OS;
using System;

namespace Samples
{
	public class InitXmlFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_calendar_init_xml, container, false);

			RadCalendarView calendarView = (RadCalendarView)rootView.FindViewById(Resource.Id.calendar);
			// customize calendar here

			return rootView;
		}

		public String Title() {
			return "Init from xml";
		}
	}
}