
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
	public class ListViewSwipeToRefreshFragment : Fragment, ExampleFragment, SwipeRefreshBehavior.ISwipeRefreshListener
	{

		private SwipeRefreshBehavior srb;
		private RadListView listView;
		private ArrayList source = new ArrayList();

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{ 
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_example, container, false);
	
			this.listView = (RadListView) rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			for (int i = 0; i < 10; i++) {
				source.Add("Item " + (source.Count + i));
			}

			this.listView.SetAdapter(new MyListViewAdapter(source));

			srb = new SwipeRefreshBehavior();
			srb.AddListener(this);
			this.listView.AddBehavior(srb);

			return rootView;

		}

		public String Title(){
			return "Swipe to refresh";
		}

		public void OnRefreshRequested ()
		{
			MyListViewAdapter currentAdapter = (MyListViewAdapter)listView.GetAdapter();
			Java.Util.ArrayList dataPage = getDataPage(10);

			for (int i = 0; i < dataPage.Size(); i++) {
				currentAdapter.Add(0, dataPage.Get(i));
			}

			srb.EndRefresh(false);
		}

		private Java.Util.ArrayList getDataPage(int pageSize) {
			Java.Util.ArrayList page = new Java.Util.ArrayList();
			for (int i = 0; i < pageSize; i++) {
				page.Add("Item " + source.Count);
			}
			return page;
		}
	}
}

