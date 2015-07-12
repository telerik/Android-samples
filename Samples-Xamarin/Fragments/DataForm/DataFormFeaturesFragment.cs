
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

namespace Samples
{
	public class DataFormFeaturesFragment : Android.Support.V4.App.Fragment, ExampleFragment, CompoundButton.IOnCheckedChangeListener, AdapterView.IOnItemSelectedListener, IPropertyChangedListener
	{
		private RadDataForm dataForm;
		private TextView personText;
		private Person person = new Person();
		private Button commitButton;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup layoutRoot = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_features, null);

			CheckBox readOnly = (CheckBox)layoutRoot.FindViewById(Resource.Id.readOnly);
			readOnly.SetOnCheckedChangeListener(this);

			dataForm = new RadDataForm (Activity);
			layoutRoot.AddView (dataForm, 0);

			person = new Person();
			person.AddPropertyChangedListener(this);

			dataForm.Entity = person;

			//RangeValidator validator = (RangeValidator) dataForm.GetExistingEditorForProperty("Age").Property().Validator;
			//validator.Max = new Java.Lang.Integer(30);
			//validator.Min = new Java.Lang.Integer(18);

			Spinner commitModes = (Spinner)layoutRoot.FindViewById(Resource.Id.commitModeSpinner);
			commitModes.Adapter = new ArrayAdapter(Activity, Android.Resource.Layout.SimpleListItem1, CommitMode.Values());
			commitModes.OnItemSelectedListener = this;

			personText = (TextView)layoutRoot.FindViewById(Resource.Id.personText);

			commitButton = (Button) layoutRoot.FindViewById(Resource.Id.manualCommit);
			commitButton.Click += (object sender, EventArgs e) => dataForm.CommitChanges();

			return layoutRoot;
		}

		public string Title() 
		{
			return "Features";
		}

		public void OnCheckedChanged(CompoundButton buttonView, bool isChecked) {
			dataForm.IsReadOnly = isChecked;
		}

		public void OnItemSelected(AdapterView parent, View view, int position, long id) {
			dataForm.CommitMode = CommitMode.Values()[position];

			if(dataForm.CommitMode == CommitMode.Manual) {
				commitButton.Visibility = Android.Views.ViewStates.Visible;
			} else {
				commitButton.Visibility = Android.Views.ViewStates.Gone;
			}
		}

		public void OnNothingSelected(AdapterView parent) {

		}

		public void OnPropertyChanged(String s, Java.Lang.Object o) {
			personText.Text = person.ToString();
		}
	}
}

