
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
	public class ListViewCollapsibleFragment : Fragment, ExampleFragment
	{
		RadListView listView;

		public String Title(){
			return "Collapsible Groups";
		}

		public override void OnCreate (Bundle savedInstanceState)
		{
			base.OnCreate (savedInstanceState);

			// Create your fragment here
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_collapsible, container, false);
			this.listView = (RadListView)rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			CollapsibleGroupsBehavior collapsibleGroupsBehavior = new CollapsibleGroupsBehavior ();
			this.listView.AddBehavior (collapsibleGroupsBehavior);

			ListViewDataSourceAdapter dataListViewAdapter = new ListViewDataSourceAdapter (GetData ());
			dataListViewAdapter.AddGroupDescriptor (new MyGroupDescriptor ());
			this.listView.SetAdapter (dataListViewAdapter);

			return rootView;
		}

		private ArrayList GetData() {
			ArrayList source = new System.Collections.ArrayList ();

			ShoppingListItem sourceItem = new ShoppingListItem ();
			sourceItem.category = "drinks";
			sourceItem.name = "tonic water";
			sourceItem.price = 3;

			source.Add (sourceItem);

			sourceItem = new ShoppingListItem ();
			sourceItem.category = "drinks";
			sourceItem.name = "coffee";
			sourceItem.price = 2;

			source.Add (sourceItem);

			sourceItem = new ShoppingListItem ();
			sourceItem.category = "drinks";
			sourceItem.name = "whiskey";
			sourceItem.price = 4;

			sourceItem = new ShoppingListItem ();
			sourceItem.category = "drinks";
			sourceItem.name = "cappuccino";
			sourceItem.price = 2;

			sourceItem = new ShoppingListItem ();
			sourceItem.category = "drinks";
			sourceItem.name = "lemon juice";
			sourceItem.price = 2;

			sourceItem = new ShoppingListItem ();
			sourceItem.category = "drinks";
			sourceItem.name = "vodka";
			sourceItem.price = 3;

			source.Add (sourceItem);

			sourceItem = new ShoppingListItem ();
			sourceItem.category = "drinks";
			sourceItem.name = "soda";
			sourceItem.price = 2;

			source.Add (sourceItem);

			sourceItem = new ShoppingListItem ();
			sourceItem.category = "drinks";
			sourceItem.name = "mineral water";
			sourceItem.price = 2;

			source.Add (sourceItem);

			sourceItem = new ShoppingListItem ();
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
				return currentItem.category;
			}
		}

		class ShoppingListItem : Java.Lang.Object
		{
			public System.String category;
			public int price;
			public System.String name;

			public override string ToString ()
			{
				return name;
			}
		}
	}
}

