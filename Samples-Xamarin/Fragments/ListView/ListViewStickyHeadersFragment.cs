
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
	public class ListViewStickyHeadersFragment : Fragment, ExampleFragment
	{
		RadListView listView;

		public override void OnCreate(Bundle savedInstanceState)
		{
			base.OnCreate(savedInstanceState);

			// Create your fragment here
		}

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_sticky_headers, container, false);
			this.listView = (RadListView) rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			StickyHeaderBehavior stickyHeaders = new StickyHeaderBehavior();
			this.listView.AddBehavior(stickyHeaders);

			MyDataListViewAdapter dataListViewAdapter = new MyDataListViewAdapter(GetData());
			dataListViewAdapter.AddGroupDescriptor(new MyGroupDescriptor());
			this.listView.SetAdapter(dataListViewAdapter);

			return rootView;
		}

		public String Title()
		{
			return "Sticky Headers";
		}

		private ArrayList GetData()
		{
			ArrayList source = new System.Collections.ArrayList();

			ShoppingListItem sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "tonic water";
			sourceItem.price = 3;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "coffee";
			sourceItem.price = 2;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "whiskey";
			sourceItem.price = 4;

			sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "cappuccino";
			sourceItem.price = 2;

			sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "lemon juice";
			sourceItem.price = 2;

			sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "vodka";
			sourceItem.price = 3;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "soda";
			sourceItem.price = 2;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "mineral water";
			sourceItem.price = 2;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "drinks";
			sourceItem.name = "orange fresh";
			sourceItem.price = 5;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "snacks";
			sourceItem.name = "potato chips";
			sourceItem.price = 2;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "snacks";
			sourceItem.name = "tortilla chips";
			sourceItem.price = 3;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "snacks";
			sourceItem.name = "crackers";
			sourceItem.price = 3;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "snacks";
			sourceItem.name = "tuna sandwich";
			sourceItem.price = 3;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "household";
			sourceItem.name = "washing powder";
			sourceItem.price = 5;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "household";
			sourceItem.name = "shower gel";
			sourceItem.price = 4;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "household";
			sourceItem.name = "shampoo";
			sourceItem.price = 4;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "household";
			sourceItem.name = "soap";
			sourceItem.price = 2;

			source.Add(sourceItem);

			sourceItem = new ShoppingListItem();
			sourceItem.category = "household";
			sourceItem.name = "towel";
			sourceItem.price = 3;

			source.Add(sourceItem);

			return source;
		}

		class MyGroupDescriptor : Java.Lang.Object, Com.Telerik.Android.Common.IFunction
		{
			public Java.Lang.Object Apply(Java.Lang.Object o1)
			{
				ShoppingListItem currentItem = (ShoppingListItem) o1;
				// Show only items that belong to the drinks category
				return currentItem.category;
			}
		}

		class MyDataListViewAdapter : ListViewDataSourceAdapter
		{
			public MyDataListViewAdapter(System.Collections.IList items) : base(items)
			{
			}


			public override void OnBindGroupViewHolder(ListViewHolder holder, Java.Lang.Object groupKey)
			{
				GroupItemViewHolder vh = (GroupItemViewHolder) holder;
				vh.txtGroupHeader.Text = groupKey.ToString().ToUpper();
			}


			public override void OnBindItemViewHolder(ListViewHolder holder, Java.Lang.Object entity)
			{
				ItemViewHolder vh = (ItemViewHolder) holder;
				ShoppingListItem item = (ShoppingListItem) entity;
				vh.txtItemCategory.Text = Java.Lang.String.Format("Category: %s", item.category);
				vh.txtItemName.Text = item.name;
				vh.txtItemPrice.Text = Java.Lang.String.Format("Price: $%d", item.price);
			}


			public override ListViewHolder OnCreateGroupViewHolder(ViewGroup parent, int viewType)
			{
				LayoutInflater inflater = LayoutInflater.From(parent.Context);
				View itemView = inflater.Inflate(Resource.Layout.example_list_view_group_header, parent, false);
				GroupItemViewHolder customHolder = new GroupItemViewHolder(itemView);
				return customHolder;
			}


			public override ListViewHolder OnCreateItemViewHolder(ViewGroup parent, int viewType)
			{
				LayoutInflater inflater = LayoutInflater.From(parent.Context);
				View itemView = inflater.Inflate(Resource.Layout.example_list_view_data_operations_item_layout, parent, false);
				ItemViewHolder customHolder = new ItemViewHolder(itemView);
				return customHolder;
			}
		}

		class ItemViewHolder : ListViewHolder
		{

			public TextView txtItemName;
			public TextView txtItemPrice;
			public TextView txtItemCategory;

			public ItemViewHolder(View itemView) : base(itemView)
			{

				this.txtItemName = (TextView) itemView.FindViewById(Resource.Id.txtItemName);
				this.txtItemPrice = (TextView) itemView.FindViewById(Resource.Id.txtItemPrice);
				this.txtItemCategory = (TextView) itemView.FindViewById(Resource.Id.txtItemCategory);
			}
		}

		class GroupItemViewHolder : ListViewHolder
		{

			public TextView txtGroupHeader;

			public GroupItemViewHolder(View itemView) : base(itemView)
			{

				this.txtGroupHeader = (TextView) itemView.FindViewById(Resource.Id.txtGroupHeader);
			}
		}

		class ShoppingListItem : Java.Lang.Object
		{
			public System.String category;
			public int price;
			public System.String name;
		}
	}
}

