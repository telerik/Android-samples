using System;
using Android.Widget;
using Android.Views;
using Android.Content;

namespace Samples
{
	public class CustomAdapter : BaseAdapter 
	{
		private Context context;
		private String[] descriptions;

		public CustomAdapter(Context context, String[] descriptions) 
		{
			this.context = context;
			this.descriptions = descriptions;
		}

		public override int Count
		{
			get 
			{
				return Enum.GetValues (typeof(EmployeeType)).Length;
			}
		}

		public override Java.Lang.Object GetItem(int position) {
			return EmployeeType.Values ()[position];
		}

		public override long GetItemId(int position) {
			return EmployeeType.Values ()[position];
		}

		public override View GetView(int position, View convertView, ViewGroup parent) {
			View layoutRoot = LayoutInflater.From(this.context).Inflate(Resource.Layout.dataform_custom_editor_list_item, null);

			TextView title = (TextView)layoutRoot.FindViewById(Resource.Id.listItemTitle);
			title.Text = Enum.GetValues(typeof(EmployeeType)).GetValue(position).ToString();

			TextView desc = (TextView)layoutRoot.FindViewById(Resource.Id.listItemDescription);
			desc.Text = descriptions[position];

			return layoutRoot;
		}
	}
}

