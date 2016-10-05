
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Java.Util;

namespace Samples
{
	public class AutoCompleteExmaples : Java.Lang.Object, ExamplesProvider
	{
		private LinkedHashMap autocompleteExamples;

		public AutoCompleteExmaples()
		{
			this.autocompleteExamples = this.GetAutoCompleteExamples();
		}

		public String ControlName()
		{
			return "AutoCompleteTextView Beta";
		}

		private LinkedHashMap GetAutoCompleteExamples()
		{
			LinkedHashMap examples = new LinkedHashMap();
			ArrayList result = new ArrayList();

			result.Add(new AutoCompleteGettingStartedFragment());
			result.Add(new AutoCompleteTokenLayoutsFragment());
			result.Add(new AutoCompleteCustomizationFragment());
			examples.Put("Init", result);

			return examples;
		}

		LinkedHashMap ExamplesProvider.Examples()
		{
			return this.autocompleteExamples;
		}
	}
}
