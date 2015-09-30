
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
using Android.Support.V7.Widget;
using Android.Support.V4.App;

namespace Samples
{
	public class ListViewWrapFragment : Fragment, ExampleFragment
	{
		private RadListView listView;
		private WrapLayoutManager wrapLayoutManager;
		private int gravityCounter = 0;

		public override void OnCreate (Bundle savedInstanceState)
		{
			base.OnCreate (savedInstanceState);

			// Create your fragment here
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView =  inflater.Inflate(Resource.Layout.fragment_list_view_wrap, container, false);

			this.listView = (RadListView)rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			wrapLayoutManager = new WrapLayoutManager(Activity);
			this.listView.SetLayoutManager(wrapLayoutManager);

			MyWrapLayoutAdapter myWrapAdapter = new MyWrapLayoutAdapter(GetData());
			this.listView.SetAdapter(myWrapAdapter);

			Button orientationBtn = (Button)rootView.FindViewById (Resource.Id.orientationBtn);
			orientationBtn.Click += (object sender, EventArgs e) => {
				if(wrapLayoutManager.CanScrollHorizontally()) {
					wrapLayoutManager = new WrapLayoutManager(Activity, OrientationHelper.Vertical);
				} else {
					wrapLayoutManager = new WrapLayoutManager(Activity, OrientationHelper.Horizontal);
				}
				listView.SetLayoutManager(wrapLayoutManager);
			};

			Button lineSpacingBtn = (Button)rootView.FindViewById (Resource.Id.lineSpacingBtn);
			lineSpacingBtn.Click += (object sender, EventArgs e) => {
				if(wrapLayoutManager.LineSpacing == 0) {
					wrapLayoutManager.LineSpacing = 20;
				} else {
					wrapLayoutManager.LineSpacing = 0;
				}
			};

			Button minItemSpacingBtn = (Button)rootView.FindViewById (Resource.Id.minItemSpacingBtn);
			minItemSpacingBtn.Click += (object sender, EventArgs e) => {
				if(wrapLayoutManager.MinimumItemSpacing == 0) {
					wrapLayoutManager.MinimumItemSpacing = 20;
				} else {
					wrapLayoutManager.MinimumItemSpacing = 0;
				}
			};

			Button gravityBtn = (Button)rootView.FindViewById (Resource.Id.gravityBtn);
			gravityBtn.Click += (object sender, EventArgs e) => {
				gravityCounter++;
				if(gravityCounter > 6) {
					gravityCounter = 1;
				}
				switch (gravityCounter) {
				case 1: wrapLayoutManager.Gravity = (int)(GravityFlags.End | GravityFlags.Bottom); break;
				case 2: wrapLayoutManager.Gravity = (int)(GravityFlags.End | GravityFlags.Top); break;
				case 3: wrapLayoutManager.Gravity = (int)(GravityFlags.Start | GravityFlags.Bottom); break;
				case 4: wrapLayoutManager.Gravity = (int)(GravityFlags.Start | GravityFlags.Top); break;
				case 5: wrapLayoutManager.Gravity = (int)(GravityFlags.CenterHorizontal | GravityFlags.CenterVertical); break;
				case 6: wrapLayoutManager.Gravity = (int)(GravityFlags.FillHorizontal | GravityFlags.FillVertical); break;
				}
			};

			return rootView;
		}

		public String Title(){
			return "Wrap Layout";
		}

		class MyWrapLayoutAdapter : ListViewAdapter {
			public MyWrapLayoutAdapter(IList items):base(items){
			}

			public override void OnBindListViewHolder(ListViewHolder holder, int position) {
				base.OnBindViewHolder(holder, position);

				int id = (position % 5) + 1;
				switch (id) {
					case 1: holder.ItemView.SetBackgroundColor(Android.Graphics.Color.ParseColor("#F44336"));break;
					case 2: holder.ItemView.SetBackgroundColor(Android.Graphics.Color.ParseColor("#9C27B0"));break;
					case 3: holder.ItemView.SetBackgroundColor(Android.Graphics.Color.ParseColor("#2196F3"));break;
					case 4: holder.ItemView.SetBackgroundColor(Android.Graphics.Color.ParseColor("#00BCD4"));break;
					case 5: holder.ItemView.SetBackgroundColor(Android.Graphics.Color.ParseColor("#CDDC39"));break;
				}
				int size = 200 + id*50;

				holder.ItemView.LayoutParameters = new FrameLayout.LayoutParams(size, size);
			}
		}

		private ArrayList GetData() {
			ArrayList list = new ArrayList();
			for(int i = 1; i <= 100; i++) {
				list.Add(i.ToString());
			}
			return list;
		}
	}
}

