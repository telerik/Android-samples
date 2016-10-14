using System;
using Java.Util;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Android.Common;

namespace Samples
{
	public class CalendarExamples : Java.Lang.Object, ExamplesProvider {

		private LinkedHashMap calendarExamples;

		public CalendarExamples(){
			this.calendarExamples = this.GetCalendarExamples();
		}

		public String ControlName() {
			return "Calendar";
		}

		public LinkedHashMap Examples(){
			return this.calendarExamples;
		}

		private LinkedHashMap GetCalendarExamples(){
			LinkedHashMap calendarExamples = new LinkedHashMap();
			ArrayList result = new ArrayList();

			result.Add (new InitCodeFragment());
			result.Add (new InitXmlFragment());

			calendarExamples.Put ("Init", result);

			result = new ArrayList ();

			result.Add (new DisplayDateSetFragment());
			result.Add (new DisplayDateChangeListenerFragment());
			result.Add (new DisplayDateMinMaxValuesFragment());
			//result.Add (new DisplayDateChangeEventFragment());

			calendarExamples.Put ("Display Date", result);

			result = new ArrayList ();

			result.Add (new DisplayModeFragment());
			result.Add (new DisplayModeChangeListenerFragment());

			calendarExamples.Put ("Display Mode", result);

			result = new ArrayList ();
			result.Add(new CellStylesFragment());
			result.Add(new CustomizationRuleFragment());
			result.Add(new DateToColorFragment());

			calendarExamples.Put("Customizations", result);

			result = new ArrayList ();

			result.Add (new EventFragment());
			result.Add (new EventAllDayFragment());
			result.Add (new EventRenderModeFragment());
			result.Add (new ReadEventsFragment());
			//result.Add (new ReadEventsOptionsFragment());
			result.Add (new EventsInlineDisplayModeFragment());
			result.Add (new EventsPopupDisplayModeFragment());
			result.Add(new EventsInlineCustomFragment());

			calendarExamples.Put ("Events", result);

			result = new ArrayList ();

			result.Add (new ScrollingDirectionFragment());
			result.Add (new ScrollingFlingSpeedFragment());
			result.Add (new ScrollingFrictionFragment());
			result.Add (new ScrollingModesFragment());
			result.Add (new ScrollingProgramatticallyFragment());
			result.Add (new ScrollingSnapFragment());

			calendarExamples.Put ("Scrolling", result);

			result = new ArrayList ();

			result.Add (new SelectionChangedListenerFragment());
			//result.Add (new SelectionChangedEventFragment());
			result.Add (new SelectionDecoratorsFragment());
			result.Add (new SelectionDisabledDatesFragment());
			result.Add (new SelectionModesFragment());
			result.Add (new SelectionSetDatesFragment());
			result.Add (new SelectionSetRangeFragment());

			calendarExamples.Put ("Selection", result);

			return calendarExamples;
		}
	}
}

