
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

namespace Samples
{
	public class DataFormGettingStartedFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootLayout = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_getting_started, container, false);

			RadDataForm dataForm = (RadDataForm)rootLayout.FindViewById(Resource.Id.data_form_getting_started);

			dataForm.SetEntity(new Person());

			return rootLayout;
		}

		public String Title() {
			return "Getting Started";
		}
	}
}
