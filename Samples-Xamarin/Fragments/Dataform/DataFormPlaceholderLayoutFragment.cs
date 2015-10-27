
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

using DataFormEntities;
using Com.Telerik.Widget.Dataform.Visualization;

namespace Samples
{
	public class DataFormPlaceholderLayoutFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		public String Title() {
			return "Placeholder Layout";
		}

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ViewGroup rootLayout = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_grouping, null);

			RadDataForm dataForm = new RadDataForm(this.Activity);
			dataForm.LayoutManager = new DataFormPlaceholderLayoutManager(this.Activity, Resource.Layout.dataform_placeholder_layout);
			dataForm.Entity = new XamarinEntity(new Person());

			rootLayout.AddView(dataForm);

			return rootLayout;
		}
	}
}
