
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
	public class ListViewReorderFragment : Fragment, ExampleFragment, ItemReorderBehavior.IItemReorderListener
	{
		private RadListView listView;
	
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_example, container, false);

			this.listView = (RadListView) rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			ItemReorderBehavior reorderBehavior = new ItemReorderBehavior();
			reorderBehavior.AddListener (this);
		
			this.listView.AddBehavior(reorderBehavior);

			ArrayList source = new ArrayList();
			source.Add("Item 1");
			source.Add("Item 2");
			source.Add("Item 3");
			source.Add("Item 4");
			source.Add("Item 5");

			this.listView.SetAdapter(new ReorderListViewAdapter(source));

			return rootView;
		}

		public String Title(){
			return "Item reorder";
		}

		public void OnReorderFinished ()
		{

		}

		public void OnReorderItem (int p0, int p1)
		{

		}

		public void OnReorderStarted (int p0)
		{

		}

	}

	class ReorderListViewAdapter : ListViewDataSourceAdapter {

		public ReorderListViewAdapter(IList items) : base(items){

		}

		public override Android.Support.V7.Widget.RecyclerView.ViewHolder OnCreateViewHolder (ViewGroup p0, int p1)
		{
			LayoutInflater inflater = LayoutInflater.From(p0.Context);
			View itemView = inflater.Inflate(Resource.Layout.example_list_view_item_reorder_layout, p0, false);
			ReorderCustomViewHolder customHolder = new ReorderCustomViewHolder(itemView);
			return customHolder;
		}

		public override void OnBindItemViewHolder (ListViewHolder p0, Java.Lang.Object p1)
		{
			ReorderCustomViewHolder customVH = (ReorderCustomViewHolder) p0;
			customVH.txtItemText.Text = p1.ToString();
		}
	}

	class ReorderCustomViewHolder : ListViewHolder {

		public TextView txtItemText;

		public ReorderCustomViewHolder(View itemView) : base(itemView){
			this.txtItemText = (TextView) itemView.FindViewById(Resource.Id.txtItemText);
		}
	}
}

