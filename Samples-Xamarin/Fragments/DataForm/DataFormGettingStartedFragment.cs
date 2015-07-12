using System;
using Android.Support.V4.App;
using Android.Views;
using Android.OS;
using Com.Telerik.Widget.Dataform.Visualization;

namespace Samples
{
	public class DataFormGettingStartedFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootLayout = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_getting_started, null);

			RadDataForm dataForm = new RadDataForm(this.Activity);
			dataForm.Entity = new Person();

			rootLayout.AddView(dataForm);

			return rootLayout;
		}

		public string Title() 
		{
			return "Getting Started";
		}
	}
}
