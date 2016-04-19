
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
using Com.Telerik.Widget.Dataform.Visualization.Core;
using Android.Graphics;

namespace Samples
{
	public class DataFormEditorStylesFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadDataForm dataForm;
		Person person;

		public String Title() {
			return "Editor Customizations";
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup layoutRoot = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_editor_styles, container, false);

			dataForm = (RadDataForm)layoutRoot.FindViewById(Resource.Id.dataForm);
			person = new Person();
			dataForm.SetEntity(person);
			dataForm.LayoutManager = new DataFormLinearLayoutManager(Activity);

			// >> data-form-customizations-editor-styles
			dataForm.EditorCustomizations = new EditorCustomizationsExample ();
			// ...
			// >> (hide)

			return layoutRoot;
		}
	}

	// << (hide)
	class EditorCustomizationsExample : Java.Lang.Object, IProcedure {
		public void Apply (Java.Lang.Object p0)
		{
			EntityPropertyViewer entityPropertyViewer = (EntityPropertyViewer)p0;
			switch (entityPropertyViewer.Property().Name()) {
				case "Name":
					((TextView)entityPropertyViewer.HeaderView).SetTextColor(Color.Blue);
					((EditText)entityPropertyViewer.EditorView).SetTextColor(Color.Blue);
					break;
				case "Age":
					entityPropertyViewer.RootLayout().SetBackgroundColor(Color.Cyan);
					break;
				case "BirthDate":
					entityPropertyViewer.HeaderView.SetBackgroundColor(Color.Red);
					entityPropertyViewer.EditorView.SetBackgroundColor(Color.ParseColor("#AAFF4444"));
					break;
			}
		}
	}
	// << data-form-customizations-editor-styles
}

