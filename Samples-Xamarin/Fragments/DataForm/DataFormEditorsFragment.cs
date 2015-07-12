using System;
using Android.Support.V4.App;
using Android.Views;
using Com.Telerik.Widget.Dataform.Visualization;
using Com.Telerik.Android.Common;
using Com.Telerik.Widget.Dataform.Engine;
using Android.OS;

namespace Samples
{
	public class DataFormEditorsFragment : Android.Support.V4.App.Fragment, ExampleFragment, IFunction
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_custom_editor, null);

			RadDataForm dataForm = new RadDataForm (Activity);
			dataForm.Adapter.SetEditorProvider (this);

			dataForm.Entity = new Person ();

			rootView.AddView (dataForm);
			return rootView;
		}

		public Java.Lang.Object Apply(Java.Lang.Object obj)
		{
			EntityProperty property = (EntityProperty)obj;
			if (property.Name ().Equals ("EmployeeType")) 
			{
				return new CustomEditor (Activity, property);
			}

			return null;
		}

		public string Title() 
		{
			return "Editors";
		}
	}	
}

