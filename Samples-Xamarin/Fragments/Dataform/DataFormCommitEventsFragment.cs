
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Dataform.Visualization;
using Com.Telerik.Android.Common;
using Com.Telerik.Widget.Dataform.Engine;

namespace Samples
{
	public class DataFormCommitEventsFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		// >> data-form-commit-listener-implementation
		public class CommitListener : Java.Lang.Object, IEntityPropertyCommitListener
		{
			public void OnAfterCommit (IEntityProperty p0)
			{
				
			}

			public bool OnBeforeCommit (IEntityProperty p0)
			{
				// Return true to cancel the commit for the given property.
				if (p0.Name ().Equals ("Age")) {
					return true;
				}

				return false;
			}
		}
		// << data-form-commit-listener-implementation

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootLayout = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_commit_events, null);

			RadDataForm dataForm = new RadDataForm(this.Activity);

			// >> data-form-global-commit-listener
			dataForm.BeforeCommit += (object sender, Com.Telerik.Widget.Dataform.Engine.BeforeCommitEventArgs e) => {
				e.Handled = true;
			};

			dataForm.AfterCommit += (object sender, Com.Telerik.Widget.Dataform.Engine.AfterCommitEventArgs e) => {
			};
			// << data-form-global-commit-listener

			dataForm.SetEntity (new Person ());

			// >> data-form-local-commit-listener
			CommitListener commitListener = new CommitListener ();
			dataForm.GetExistingEditorForProperty ("Age").Property().AddCommitListener(commitListener);
			// << data-form-local-commit-listener

			rootLayout.AddView(dataForm);

			return rootLayout;
		}

		public String Title() {
			return "Commit Events";
		}
	}
}

