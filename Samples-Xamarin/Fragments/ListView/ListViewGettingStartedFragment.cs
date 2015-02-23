
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.List;
using System.Collections;
using Android.Support.V4.App;



namespace Samples
{
	public class ListViewGettingStartedFragment : Fragment, ExampleFragment
	{
		private RadListView listView;

		public override void OnCreate (Bundle savedInstanceState)
		{
			base.OnCreate (savedInstanceState);

			// Create your fragment here
		}



		public ListViewGettingStartedFragment() {
			// Required empty public constructor
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_example, container, false);
			this.listView = (RadListView)rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();
			this.listView.HeaderView = View.Inflate(this.Activity, Resource.Layout.example_list_view_header_layout, null);
			this.listView.FooterView = View.Inflate(this.Activity, Resource.Layout.example_list_view_footer_layout, null);
			ArrayList source = new ArrayList();
			for (int i = 0; i < 50; i++) {
				source.Add("Item " + i);
			}
			this.listView.SetAdapter(new MyListViewAdapter(source));
			return rootView;
		}


		public String Title(){
			return "Getting started";
		}

	}
}


