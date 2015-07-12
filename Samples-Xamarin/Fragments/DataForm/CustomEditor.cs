using System;
using Com.Telerik.Widget.Dataform.Visualization.Core;
using Com.Telerik.Widget.Dataform.Engine;
using Android.Widget;
using Android.Content;
using Android.Support.V4.App;

namespace Samples
{
	public class CustomEditor : EntityPropertyEditor, IPropertyChangedListener {
		private EmployeeType type;
		private Button editorButton;

		public CustomEditor(Context context, EntityProperty property) : base(context, Resource.Layout.dataform_custom_editor, Resource.Id.custom_editor_header, Resource.Id.custom_editor, Resource.Id.custom_editor_validation_view, property)
		{
			editorButton = (Button)EditorView;
			editorButton.Click += (object sender, EventArgs e) => this.onClick();

			((TextView)HeaderView).Text = this.Property().Header;
		}

		public override Java.Lang.Object Value() {
			return type;
		}

		protected override void ApplyEntityValueToEditor(Java.Lang.Object o) {
			if(o == null) {
				this.editorButton.Text = "Tap to select.";
				return;
			}

			this.editorButton.Text = o.ToString();
			type = (EmployeeType)o;
		}

		public void onClick() {
			this.ShowEditorFragment(this.type);
		}

		private void ShowEditorFragment(EmployeeType initialValue) {
			CustomEditorFragment fragment = new CustomEditorFragment();
			fragment.AddPropertyChangedListener(this);
			fragment.Type = initialValue;
			FragmentActivity activity = (FragmentActivity)Context;
			fragment.Show(activity.SupportFragmentManager, "customEditor");
		}

		protected override bool SupportsType(Java.Lang.Class type) {
			// Defining which types this editor supports is required.
			return type.Equals(Java.Lang.Class.FromType(typeof(EmployeeType)));
		}

		public void OnPropertyChanged(string s, Java.Lang.Object o) {
			if(o == type) {
				return;
			}

			this.ApplyEntityValueToEditor (o);

			// Remember to call value changed, otherwise the object being edited will not be updated.
			this.OnEditorValueChanged(o);
		}
	}
}
	