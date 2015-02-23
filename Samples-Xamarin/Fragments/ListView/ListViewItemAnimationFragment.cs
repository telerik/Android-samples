
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
	public class ListViewItemAnimationFragment : Fragment, ExampleFragment
	{
		private RadListView listView;
		private Button btnAddItem;
		private Button btnRemoveItem;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_item_animations, container, false);
			this.listView = (RadListView) rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			ArrayList source = new ArrayList();

			source.Add("Item 1");
			source.Add("Item 2");
			source.Add("Item 3");
			source.Add("Item 4");
			source.Add("Item 5");

			this.listView.SetAdapter(new AnimationsListViewAdapter(source));

			Button setFade = (Button)rootView.FindViewById(Resource.Id.btnSetFade);
			setFade.Click += (object sender, EventArgs e) => {
				listView.SetItemAnimator(new FadeItemAnimator());
			};
			Button setSlide = (Button)rootView.FindViewById(Resource.Id.btnSetSlide);
			setSlide.Click += (object sender, EventArgs e) => {
				listView.SetItemAnimator(new SlideItemAnimator());
			};

			Button setScale = (Button)rootView.FindViewById(Resource.Id.btnSetScale);
			setScale.Click += (object sender, EventArgs e) => {
				listView.SetItemAnimator(new ScaleItemAnimator());
			};

			this.btnAddItem = (Button)rootView.FindViewById(Resource.Id.btnAddItem);
			this.btnAddItem.Click += (object sender, EventArgs e) => {
				AnimationsListViewAdapter adapter = (AnimationsListViewAdapter)listView.GetAdapter();
				adapter.Add(0, "Item " + adapter.ItemCount);
			};

			this.btnRemoveItem = (Button)rootView.FindViewById(Resource.Id.btnRemoveItem);
			this.btnRemoveItem.Click += (object sender, EventArgs e) => {
				AnimationsListViewAdapter adapter = (AnimationsListViewAdapter)listView.GetAdapter();
				if (adapter.ItemCount > 0) {
					adapter.Remove(0);
				}
			};
			return rootView;
		}

		public String Title(){
			return "Item animations";
		}
	}

	class AnimationsListViewAdapter : ListViewAdapter {

		public AnimationsListViewAdapter(IList items) : base(items){

		}


		public override Android.Support.V7.Widget.RecyclerView.ViewHolder OnCreateViewHolder (ViewGroup p0, int p1)
		{
			LayoutInflater inflater = LayoutInflater.From(p0.Context);
			View itemView = inflater.Inflate(Resource.Layout.example_list_view_item_animations_layout, p0, false);
			MyCustomViewHolder customHolder = new MyCustomViewHolder(itemView);
			return customHolder;
		}
		public override void OnBindListViewHolder (ListViewHolder p0, int p1)
		{
			MyCustomViewHolder customVH = (MyCustomViewHolder)p0;
			customVH.txtItemText.Text = p1.ToString();
		}

	}

}

