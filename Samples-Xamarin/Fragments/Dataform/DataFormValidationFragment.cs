
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;

using Com.Telerik.Widget.Dataform.Visualization;
using Com.Telerik.Widget.Dataform.Engine;
using Com.Telerik.Widget.Dataform.Visualization.Editors;

namespace Samples
{
	public class DataFormValidationFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup layoutRoot = (ViewGroup) inflater.Inflate(Resource.Layout.fragment_dataform_validation, container, false);

			RadDataForm dataForm = (RadDataForm)layoutRoot.FindViewById(Resource.Id.data_form_validation);
			dataForm.SetEntity (new Person ());

			dataForm.GetExistingEditorForProperty ("Name").Property ().Validator = new NonEmptyValidator();

			return layoutRoot;
		}

		public string Title() {
			return "Validation";
		}
	}
}
