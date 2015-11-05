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
using Com.Telerik.Widget.Dataform.Visualization.Editors;
using Com.Telerik.Widget.Dataform.Visualization.Core;
using Com.Telerik.Widget.Dataform.Engine;

namespace Samples
{
	public class DataFormFeaturesFragment : Android.Support.V4.App.Fragment, ExampleFragment, CompoundButton.IOnCheckedChangeListener, AdapterView.IOnItemSelectedListener, IPropertyChangedListener, View.IOnClickListener {
		RadDataForm dataForm;
		TextView personText;
		Person person = new Person();
		Button commitButton;

		public String Title() {
			return "Features";
		}

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ViewGroup layoutRoot = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_features, null);

			CheckBox readOnly = (CheckBox)layoutRoot.FindViewById(Resource.Id.readOnly);
			readOnly.SetOnCheckedChangeListener(this);

			dataForm = Android.Runtime.Extensions.JavaCast<RadDataForm>(layoutRoot.FindViewById(Resource.Id.dataForm));
			dataForm.LayoutManager = new DataFormLinearLayoutManager (this.Activity);

			person = new Person();
			person.AddPropertyChangedListener(this);

			dataForm.SetEntity (person);

			RangeValidator validator = (RangeValidator) dataForm.GetExistingEditorForProperty("Age").Property().Validator;
			validator.Max = 30;
			validator.Min = 18;

			Spinner commitModes = (Spinner)layoutRoot.FindViewById(Resource.Id.commitModeSpinner);
			commitModes.Adapter = new ArrayAdapter(this.Activity, Android.Resource.Layout.SimpleSpinnerItem, CommitMode.Values());
			commitModes.OnItemSelectedListener = this;

			personText = (TextView)layoutRoot.FindViewById(Resource.Id.personText);

			commitButton = (Button) layoutRoot.FindViewById(Resource.Id.manualCommit);
			commitButton.SetOnClickListener(this);

			return layoutRoot;
		}

		public void OnCheckedChanged(CompoundButton buttonView, bool isChecked) {
			dataForm.IsReadOnly = isChecked;
		}

		public void OnItemSelected(AdapterView parent, View view, int position, long id) {
			dataForm.CommitMode = CommitMode.Values()[position];

			if(dataForm.CommitMode == CommitMode.Manual) {
				commitButton.Visibility = ViewStates.Visible;
			} else {
				commitButton.Visibility = ViewStates.Gone;
			}
		}

		public void OnNothingSelected(AdapterView parent) {

		}

		public void OnPropertyChanged(String s, Java.Lang.Object o) {
			personText.Text = person.ToString();
		}

		public void OnClick(View v) {
			dataForm.CommitChanges();
		}
	}
}

