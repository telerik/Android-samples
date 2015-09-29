using System;
using Android.Widget;
using Android.Content;
using Android.Views;
using Java.Util;

namespace Samples
{
	public class ControlsAdapter : ArrayAdapter {

		private ArrayList source = new ArrayList();

		public ControlsAdapter(Context context, int resource) : base(context, resource) {

			this.source.Add(new ChartExamples());
			this.source.Add (new CalendarExamples ());
			this.source.Add (new ListViewExamples ());
			this.source.Add (new SideDrawerExamples ());

		}


		public override int Count {
			get {
				return this.source.Size();
			}
		}


		public override View GetView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.Inflate(parent.Context, Resource.Layout.list_item_control, null);
			}

			ExamplesProvider currentProvider = (ExamplesProvider) this.GetItem(position);
			TextView controlName = (TextView) convertView.FindViewById(Resource.Id.txtControlName);
			controlName.Text = currentProvider.ControlName();
			return convertView;
		}


		public override Java.Lang.Object GetItem(int position) {
			return this.source.Get(position);
		}
	}
}

