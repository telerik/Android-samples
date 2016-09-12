using System;
using Android.Views;
using Com.Telerik.Android.Common;
using Com.Telerik.Widget.Dataform.Visualization;
using Android.OS;
using Android.Content;
using Android.Widget;

namespace Samples
{
	public class DataFormExpandableGroupsFragment : Android.Support.V4.App.Fragment, ExampleFragment, IFunction2
	{
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootLayout = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_grouping, null);

			RadDataForm dataForm = new RadDataForm(this.Activity);

			DataFormGroupLayoutManager groupManager = new DataFormGroupLayoutManager(this.Activity);
			groupManager.CreateGroup = this;

			dataForm.LayoutManager = groupManager;
			dataForm.SetEntity(new Person());

			rootLayout.AddView(dataForm);

			return rootLayout;
		}

		public Java.Lang.Object Apply(Java.Lang.Object context, Java.Lang.Object groupName) {
			ExpandableEditorGroup group = new ExpandableEditorGroup((Context)context, groupName.ToString());
			group.AddIsExpandedChangedListener (new MyIsExpandedChangedListener (this.Activity, group.Name ()));
			return group;
		}

		public string Title() {
			return "Data Form Expandable Groups";
		}
	}

	public class MyIsExpandedChangedListener : Java.Lang.Object, Com.Telerik.Widget.Dataform.Visualization.ExpandableEditorGroup.IIsExpandedChangedListener {
		
		Context context;
		String groupName;

		public MyIsExpandedChangedListener(Context context, String groupName) {
			this.context = context;
			this.groupName = groupName;
		}

		public void OnChanged (bool isExpanded)
		{
			String message = String.Format("{0} has been {1}", groupName, isExpanded ? "expanded" : "collapsed");
			Toast.MakeText(context, message, ToastLength.Short).Show();
		}
	}
}

