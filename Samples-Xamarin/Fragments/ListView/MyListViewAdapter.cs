using System;
using Com.Telerik.Widget.List;
using Android.Widget;
using Android.Views;
using System.Collections;

namespace Samples
{
	public class MyListViewAdapter : ListViewDataSourceAdapter
	{
		public MyListViewAdapter(IList items) : base(items){
		}

		public override Android.Support.V7.Widget.RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.From(parent.Context);
			View itemView = inflater.Inflate(Resource.Layout.example_list_view_item_layout, parent, false);
			MyCustomViewHolder customHolder = new MyCustomViewHolder(itemView);
			return customHolder;
		}


//		public override void OnBindViewHolder (Android.Support.V7.Widget.RecyclerView.ViewHolder p0, int p1)
//		{
//			MyCustomViewHolder customVH = (MyCustomViewHolder) p0;
//			customVH.txtItemText.Text = this.GetItem(p1).ToString();
//		}

		public override void OnBindItemViewHolder (ListViewHolder p0, Java.Lang.Object p1)
		{
			MyCustomViewHolder customVH = (MyCustomViewHolder) p0;
			customVH.txtItemText.Text = p1.ToString();
		}
	}

	class MyCustomViewHolder : ListViewHolder {

		public TextView txtItemText;

		public MyCustomViewHolder(View itemView) : base(itemView) {
			this.txtItemText = (TextView) itemView.FindViewById(Resource.Id.txtItemText);
		}
	}
}

