using Com.Telerik.Widget.Calendar;
using Android.Views;
using Android.OS;
using System;

namespace Samples
{
	public class InitXmlFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadCalendarView calendarView;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_calendar_init_xml, container, false);

			calendarView = rootView.FindViewById(Resource.Id.calendar) as RadCalendarView;
			// add calendar logic here

			return rootView;
		}

		public String Title() {
			return "Init from xml";
		}
	}
}