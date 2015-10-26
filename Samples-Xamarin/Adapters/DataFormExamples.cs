using System;
using Android.Widget;
using Java.Util;
using Android.Views;

namespace Samples
{
	public class DataFormExamples : Java.Lang.Object, ExamplesProvider
	{
		private HashMap dataFormExamples;

		public DataFormExamples(){
			this.dataFormExamples = this.getDataFormExamples();
		}

		public String ControlName() {
			return "Data Form";
		}

		public HashMap Examples(){
			return this.dataFormExamples;
		}

		private HashMap getDataFormExamples(){
			HashMap dataFormExamples = new HashMap();
			ArrayList result = new ArrayList();

			result.Add (new DataFormGettingStartedFragment());
			result.Add (new DataFormFeaturesFragment ());
			result.Add (new DataFormEditorsFragment ());

			dataFormExamples.Put ("Init", result);

			return dataFormExamples;
		}
	}
}

