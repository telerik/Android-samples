
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
using DataFormEntities;

namespace Samples
{
	public class DataFormValidationFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup layoutRoot = (ViewGroup) inflater.Inflate(Resource.Layout.fragment_dataform_validation, null);

			RadDataForm dataForm = new RadDataForm(this.Activity);
			dataForm.Entity = new XamarinEntity(new Person());

			dataForm.GetExistingEditorForProperty ("Name").Property ().Validator = new NonEmptyValidator();

			layoutRoot.AddView(dataForm);

			return layoutRoot;
		}

		public string Title() {
			return "Validation";
		}
	}
}
