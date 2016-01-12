using System;
using Java.Util;
using Com.Telerik.Android.Common;

namespace Samples
{
	public class SideDrawerExamples : Java.Lang.Object, ExamplesProvider {

		private LinkedHashMap sideDrawerExamples;

		public SideDrawerExamples(){
			this.sideDrawerExamples = this.getSideDrawerExamples();
		}

		public String ControlName() {
			return "Side Drawer";
		}

		public LinkedHashMap Examples(){
			return this.sideDrawerExamples;
		}

		private LinkedHashMap getSideDrawerExamples(){
			LinkedHashMap sideDrawerExamples = new LinkedHashMap();
			ArrayList result = new ArrayList();

			result.Add (new DrawerInitialSetupFragment());

			sideDrawerExamples.Put ("Init", result);

			return sideDrawerExamples;
		}
	}
}

