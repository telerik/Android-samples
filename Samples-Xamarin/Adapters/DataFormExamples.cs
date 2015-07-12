using System;
using Java.Util;
using Com.Telerik.Android.Common;

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
			result.Add (new DataFormFeaturesFragment());
			result.Add (new DataFormEditorsFragment());
			result.Add (new DataFormValidationFragment());
			dataFormExamples.Put ("Examples", result);

			return dataFormExamples;
		}
	}
}

