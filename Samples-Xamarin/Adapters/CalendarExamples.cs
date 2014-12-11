using System;
using Java.Util;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Android.Common;

namespace Samples
{
	public class CalendarExamples : Java.Lang.Object, ExamplesProvider {

		private HashMap calendarExamples;

		public CalendarExamples(){
			this.calendarExamples = this.getCalendarExamples();
		}

		public String ControlName() {
			return "Calendar";
		}

		public HashMap Examples(){
			return this.calendarExamples;
		}

		private HashMap getCalendarExamples(){
			HashMap calendarExamples = new HashMap();
			ArrayList result = new ArrayList();

			result.Add (new InitCodeFragment());
			result.Add (new InitXmlFragment());

			calendarExamples.Put ("Init", result);

			result = new ArrayList ();

			result.Add (new SelectionModesFragment());
			result.Add (new SelectionChangedListenerFragment());
			result.Add (new SelectionChangedEventFragment());
			result.Add (new SelectionSetDatesFragment());
			result.Add (new SelectionSetRangeFragment());
			result.Add (new SelectionDisabledDatesFragment());

			calendarExamples.Put ("Selection", result);

			result = new ArrayList ();

			result.Add (new DisplayDateSetFragment());
			result.Add (new DisplayDateChangeListenerFragment());
			result.Add (new DisplayDateChangeEventFragment());
			result.Add (new DisplayDateMinMaxValuesFragment());

			calendarExamples.Put ("Display Date", result);

			result = new ArrayList ();

			result.Add (new DisplayModeFragment());
			result.Add (new DisplayModeChangeListenerFragment());

			calendarExamples.Put ("Display Mode", result);

			result = new ArrayList ();

			result.Add (new ScrollingModesFragment());
			result.Add (new ScrollingFlingSpeed());

			calendarExamples.Put ("Scrolling", result);

			return calendarExamples;
		}
	}
}

