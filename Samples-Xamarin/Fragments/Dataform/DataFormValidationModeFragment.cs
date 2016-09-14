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
using Com.Telerik.Widget.Dataform.Visualization.Core;
using Com.Telerik.Widget.Dataform.Engine;
using Com.Telerik.Widget.Dataform.Visualization.Editors;
using Com.Telerik.Widget.Dataform.Visualization.Editors.Adapters;

namespace Samples
{
	public class DataFormValidationModeFragment : Android.Support.V4.App.Fragment, ExampleFragment, AdapterView.IOnItemSelectedListener, View.IOnClickListener
	{
		RadDataForm dataForm;
		Button validateButton;

		public String Title() {
			return "Validation Modes";
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootLayout = (ViewGroup) inflater.Inflate(Resource.Layout.fragment_dataform_validation_mode, container, false);

			Spinner validationModeSpinner = (Spinner) rootLayout.FindViewById(Resource.Id.data_form_validation_mode_spinner);
			validationModeSpinner.OnItemSelectedListener = this;
			validationModeSpinner.Adapter = new ArrayAdapter(this.Activity, Android.Resource.Layout.SimpleSpinnerItem, new ValidationMode[]{ValidationMode.Immediate, ValidationMode.OnLostFocus, ValidationMode.Manual});

			dataForm = (RadDataForm)rootLayout.FindViewById(Resource.Id.data_form_validation_mode);

			dataForm.LayoutManager = new DataFormLinearLayoutManager(this.Activity);
			dataForm.CommitMode = CommitMode.Manual;
			dataForm.ValidationMode = ValidationMode.Immediate;

			Person joe = new Person();
			joe.Name = "Joe";
			joe.Mail = "joe@mailservice.com";
			dataForm.SetEntity (joe);

			validateButton = (Button)rootLayout.FindViewById(Resource.Id.data_form_validate_button);
			validateButton.SetOnClickListener(this);

			return rootLayout;
		}

		public void OnItemSelected(AdapterView parent, View view, int position, long id) {
			dataForm.ValidationMode = ValidationMode.Values()[position];

			// Manual validation
			if(position == 2) {
				validateButton.Visibility = ViewStates.Visible;
			} else {
				validateButton.Visibility = ViewStates.Invisible;
			}
		}

		public void OnNothingSelected(AdapterView parent) {

		}

		public void OnClick(View v) {
			dataForm.ValidateChanges();
		}
	}
}
