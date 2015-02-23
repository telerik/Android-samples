
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
using Java.Lang;
using System.Collections;
using Android.Support.V4.App;

namespace Samples
{
	public class ListViewDataAutomaticLoadOnDemandFragment : Fragment, ExampleFragment, Com.Telerik.Widget.List.LoadOnDemandBehavior.ILoadOnDemandListener
	{
		private RadListView listView;
		private ArrayList source;
		private AsyncTask dataLoader;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_example, container, false);
			this.listView = (RadListView) rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();
			this.source = new ArrayList ();
			LoadOnDemandBehavior ldb = new LoadOnDemandBehavior();
			ldb.Mode = Com.Telerik.Widget.List.LoadOnDemandBehavior.LoadOnDemandMode.Automatic;

			ldb.AddListener(this);

			for (int i = 0; i < 50; i++) {
				source.Add("Item " + i);
			}

			listView.AddBehavior(ldb);

			this.listView.SetAdapter(new MyListViewAdapter(source));

			return rootView;
		}


		public void OnLoadStarted() {
			requestLoadData();
		}

		public void OnLoadFinished() {
		}

		private void requestLoadData() {
			this.dataLoader = new DataLoaderTask (this.listView);
			this.dataLoader.Execute ();
		}

	

		public System.String Title(){
			return "Automatic load on demand";
		}
	}

	class DataLoaderTask : AsyncTask
	{
		private RadListView listView;
		private int currentCount;

		public DataLoaderTask(RadListView owner){
			this.listView = owner;
			this.currentCount = ((MyListViewAdapter)owner.GetAdapter ()).ItemCount;
		}

		protected override Java.Lang.Object DoInBackground (params Java.Lang.Object[] @params)
		{
			Java.Util.ArrayList dataPage = getDataPage(10);
			try {
				// Simulate network delay
				Thread.Sleep((long) 1000);
			} catch (InterruptedException e) {
				e.PrintStackTrace();
			}

			return dataPage;
		}

		protected override void OnCancelled ()
		{
			base.OnCancelled ();

			this.Execute ();
		}

		protected override void OnPostExecute (Java.Lang.Object result)
		{
			base.OnPostExecute (result);

			Java.Util.ArrayList dataPage = (Java.Util.ArrayList) result;
			MyListViewAdapter adapter = (MyListViewAdapter)listView.GetAdapter();

			for (int i = 0; i < dataPage.Size(); i++) {
				adapter.Add(dataPage.Get(i));
			}
			
			adapter.NotifyLoadingFinished();
		}

		private Java.Util.ArrayList getDataPage(int pageSize) {
			Java.Util.ArrayList page = new Java.Util.ArrayList();
			for (int i = 0; i < pageSize; i++) {
				page.Add("Item " + (currentCount + i));
			}
			return page;
		}

	}
		
}

