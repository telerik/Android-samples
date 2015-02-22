using System;
using Java.Util;
using Com.Telerik.Widget.Calendar;
using Com.Telerik.Android.Common;

namespace Samples
{
	public class SideDrawerExamples : Java.Lang.Object, ExamplesProvider {

		private HashMap sideDrawerExamples;

		public SideDrawerExamples(){
			this.sideDrawerExamples = this.getCalendarExamples();
		}

		public String ControlName() {
			return "Side Drawer";
		}

		public HashMap Examples(){
			return this.sideDrawerExamples;
		}

		private HashMap getCalendarExamples(){
			HashMap calendarExamples = new HashMap();
			ArrayList result = new ArrayList();

			result.Add (new DrawerInitialSetupFragment());

			calendarExamples.Put ("Init", result);

			return calendarExamples;
		}
	}
}

