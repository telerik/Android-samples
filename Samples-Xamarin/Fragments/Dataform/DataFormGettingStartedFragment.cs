
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
using DataFormEntities;

namespace Samples
{
	public class DataFormGettingStartedFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootLayout = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_getting_started, null);

			RadDataForm dataForm = new RadDataForm(this.Activity);
			dataForm.LayoutManager = new DataFormLinearLayoutManager (this.Activity);
			dataForm.Entity = new XamarinEntity (new Person ());

			rootLayout.AddView(dataForm);

			return rootLayout;
		}

		public String Title() {
			return "Getting Started";
		}
	}
}
