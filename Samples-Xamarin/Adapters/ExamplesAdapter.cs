using System;
using Android.Widget;
using Java.Util;
using Android.Views;

namespace Samples
{
	public class ExamplesAdapter : BaseExpandableListAdapter {

		private Java.Lang.Object[] keys;

		private HashMap source;

		public ExamplesAdapter(HashMap source){
			this.source = source;

			Java.Lang.Object[] k = new Java.Lang.Object[source.Size ()];
			int counter = 0;
			foreach (Java.Lang.Object o in this.source.KeySet()) {
				k [counter++] = o;
			}

			this.keys = k;
		}

		public override int GroupCount {
			get {
				return this.keys.Length;
			}
		}


		public override int GetChildrenCount(int groupPosition) {
			return ((ArrayList)this.source.Get(this.keys[groupPosition])).Size();
		}


		public override Java.Lang.Object GetGroup(int groupPosition) {
			return this.keys[groupPosition];
		}


		public override Java.Lang.Object GetChild(int groupPosition, int childPosition) {
			return ((ArrayList)this.source.Get(this.keys[groupPosition])).Get(childPosition);
		}


		public override long GetGroupId(int groupPosition) {
			return this.keys[groupPosition].GetHashCode();
		}


		public override long GetChildId(int groupPosition, int childPosition) {
			return ((ArrayList)this.source.Get(this.keys[groupPosition])).Get(childPosition).GetHashCode();
		}


		public override bool HasStableIds {
			get {
				return true;
			}
		}


		public override View GetGroupView(int groupPosition, bool isExpanded, View convertView, ViewGroup parent) {
			View container = View.Inflate(parent.Context, Resource.Layout.list_item_group, null);
			TextView txtGroupName = (TextView)container.FindViewById(Resource.Id.txtFeatureName);
			txtGroupName.Text = this.GetGroup(groupPosition).ToString();
			return container;
		}


		public override View GetChildView(int groupPosition, int childPosition, bool isLastChild, View convertView, ViewGroup parent) {
			View container = View.Inflate(parent.Context, Resource.Layout.list_item_child, null);
			TextView txtGroupName = (TextView)container.FindViewById(Resource.Id.txtExampleName);
			ExampleFragment child = (ExampleFragment)this.GetChild(groupPosition, childPosition);
			txtGroupName.Text = child.Title();
			return container;
		}


		public override bool IsChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}

