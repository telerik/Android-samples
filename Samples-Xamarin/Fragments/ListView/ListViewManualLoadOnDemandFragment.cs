
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
	public class ListViewManualLoadOnDemandFragment : Fragment, ExampleFragment, LoadOnDemandBehavior.ILoadOnDemandListener
	{
		private RadListView listView;
		private ArrayList source;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_example, container, false);
			this.listView = (RadListView) rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();
			this.source = new ArrayList ();
			LoadOnDemandBehavior ldb = new LoadOnDemandBehavior();
			ldb.Mode = Com.Telerik.Widget.List.LoadOnDemandBehavior.LoadOnDemandMode.Manual;

			ldb.AddListener(this);

			for (int i = 0; i < 50; i++) {
				source.Add("Item " + i);
			}

			listView.AddBehavior(ldb);

			this.listView.SetAdapter(new MyListViewAdapter(source));

			return rootView;
		}


		public void OnLoadStarted() {
			MyListViewAdapter adapter = (MyListViewAdapter) listView.GetAdapter();
			Java.Util.ArrayList dataPage = getDataPage(10);
			for (int i = 0; i < dataPage.Size(); i++) {
				adapter.Add((Java.Lang.Object)dataPage.Get(i));
			}
			adapter.NotifyLoadingFinished();
		}

		public void OnLoadFinished() {
		}


		public System.String Title(){
			return "Manual load on demand";
		}

		private Java.Util.ArrayList getDataPage(int pageSize) {
			Java.Util.ArrayList page = new Java.Util.ArrayList();
			for (int i = 0; i < pageSize; i++) {
				page.Add("Item " + (((MyListViewAdapter)listView.GetAdapter()).ItemCount + i));
			}
			return page;
		}
	}

}

