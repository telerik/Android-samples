
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
using Android.Support.V7.Widget;
using System.Collections;
using Android.Support.V4.App;

namespace Samples
{
	public class ListViewLayoutsFragment : Fragment, ExampleFragment
	{
		private RadListView listView;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_layouts, container, false);
			this.listView = (RadListView)rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			Button btnLinear = (Button)rootView.FindViewById(Resource.Id.btnLinear);
			btnLinear.Click += (object sender, EventArgs e) => {
				listView.SetLayoutManager(new LinearLayoutManager(this.Activity));
			};

			Button btnStaggered = (Button)rootView.FindViewById(Resource.Id.btnStaggered);
			btnStaggered.Click += (object sender, EventArgs e) => {
				StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.Vertical);
				listView.SetLayoutManager(slm);
			};

			Button btnGrid = (Button)rootView.FindViewById(Resource.Id.btnGrid);
			btnGrid.Click += (object sender, EventArgs e) => {
				GridLayoutManager glm = new GridLayoutManager(this.Activity, 3, GridLayoutManager.Vertical, false);
				listView.SetLayoutManager(glm);
			};
		

			ArrayList source = new ArrayList();
			for (int i = 0; i < 50; i++) {
				source.Add(this.getRandomText());
			}
			this.listView.SetAdapter(new LayoutsListViewAdapter(source));
			return rootView;
		}

		private String getRandomText(){
			Random r = new Random();
			StringBuilder sb = new StringBuilder();

			int wordCount = 10 + r.Next(30);

			for (int i = 0; i < wordCount; i++){
				sb.Append("word ");
			}

			return sb.ToString();
		}

		public String Title(){
			return "Layouts";
		}
	}

	class LayoutsListViewAdapter : ListViewAdapter {

		public LayoutsListViewAdapter(IList items) : base(items){

		}

		public override RecyclerView.ViewHolder OnCreateViewHolder (ViewGroup p0, int p1)
		{
			LayoutInflater inflater = LayoutInflater.From(p0.Context);
			View itemView = inflater.Inflate(Resource.Layout.example_list_view_item_layouts, p0, false);
			LayoutsViewHolder customHolder = new LayoutsViewHolder(itemView);
			return customHolder;
		}

		public override void onBindListViewHolder (ListViewHolder p0, int p1)
		{
			LayoutsViewHolder customVH = (LayoutsViewHolder)p0;
			customVH.txtItemText.Text = this.GetItem(p1).ToString();
		}
	}

	class LayoutsViewHolder : ListViewHolder{

		public TextView txtItemText;
		public LayoutsViewHolder(View itemView) : base(itemView){
			this.txtItemText = (TextView)itemView.FindViewById(Resource.Id.txtItemText);
		}
	}
}

