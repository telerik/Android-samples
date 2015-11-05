
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

using Com.Telerik.Widget.Dataform.Visualization;
using Com.Telerik.Android.Common;
using Com.Telerik.Widget.Dataform.Engine;

namespace Samples
{
	[Activity (Label = "DataFormGroupLayoutFragment")]			
	public class DataFormGroupLayoutFragment : Android.Support.V4.App.Fragment, ExampleFragment, IFunction2
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
			// Developers can create a special group layout for any given group name.
			if(groupName.Equals("Group 2")) {
				EditorGroup group = new EditorGroup((Context)context, groupName.ToString(), Resource.Layout.dataform_custom_group);
				// Each group can have a specific layout manager, be it a table layout, a linear layout, a placeholder layout or even something completely custom.
				group.LayoutManager = new DataFormPlaceholderLayoutManager((Context)context, Resource.Layout.dataform_group_placeholder_layout);
				return group;
			}

			return null;
		}

		public string Title() {
			return "Data Form Groups";
		}
	}
}

