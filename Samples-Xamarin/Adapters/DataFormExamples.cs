using System;
using Android.Widget;
using Java.Util;
using Android.Views;

namespace Samples
{
	public class DataFormExamples : Java.Lang.Object, ExamplesProvider
	{
		private LinkedHashMap dataFormExamples;

		public DataFormExamples(){
			this.dataFormExamples = this.getDataFormExamples();
		}

		public String ControlName() {
			return "Data Form";
		}

		public LinkedHashMap Examples(){
			return this.dataFormExamples;
		}

		private LinkedHashMap getDataFormExamples(){
			LinkedHashMap dataFormExamples = new LinkedHashMap();
			ArrayList result = new ArrayList();

			result.Add (new DataFormGettingStartedFragment());
			result.Add (new DataFormFeaturesFragment ());
			result.Add (new DataFormEditorsFragment ());
			result.Add (new DataFormValidationFragment ());
			result.Add (new DataFormGroupLayoutFragment ());
			result.Add (new DataFormPlaceholderLayoutFragment ());
			result.Add (new DataFormValidationBehaviorFragment ());
			result.Add (new DataFormValidationModeFragment ());
			result.Add (new DataFormJsonEditFragment ());
			result.Add (new DataFormSchemaSetupFragment ());

			result.Add (new DataFormCommitEventsFragment());

			result.Add (new DataFormEditorStylesFragment());
			result.Add (new DataFormLabelPositionsFragment());

			dataFormExamples.Put ("Features", result);

			return dataFormExamples;
		}
	}
}

