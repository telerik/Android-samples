using System;
using Android.App;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Dataform.Engine;
using Com.Telerik.Widget.Dataform.Visualization;
using Com.Telerik.Widget.Dataform.Visualization.Core;
using Com.Telerik.Android.Common;

namespace Samples
{
	public class DataFormEditorsFragment : Android.Support.V4.App.Fragment, ExampleFragment, IFunction
	{
		RadDataForm dataForm;
		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup rootLayout = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_editors, container, false);

			dataForm = (RadDataForm)rootLayout.FindViewById(Resource.Id.data_form_editors);
			dataForm.Adapter.SetEditorProvider(this);

			dataForm.SetEntity (new Person ());

			return rootLayout;
		}

		public Java.Lang.Object Apply (Java.Lang.Object p0)
		{
			IEntityProperty property = (IEntityProperty)p0;
			if(property.Name().Equals("EmployeeType")) {
				return new CustomEditor(dataForm, property, this.Activity.FragmentManager);
			}

			return null;
		}

		public String Title()
		{
			return "Editors";
		}

		public class CustomEditor : EntityPropertyEditor, View.IOnClickListener, IPropertyChangedListener {
			private EmployeeType type;
			private Button editorButton;
			private FragmentManager fragmentManager;

			public CustomEditor(RadDataForm dataForm, IEntityProperty property, FragmentManager fragmentManager) : base(dataForm,
					dataForm.EditorsMainLayout,
					dataForm.EditorsHeaderLayout,
					Resource.Id.data_form_text_viewer_header,
					Resource.Layout.dataform_custom_editor,
					Resource.Id.custom_editor,
					dataForm.EditorsValidationLayout,
					property)
			{

				editorButton = (Button)EditorView;
				editorButton.SetOnClickListener(this);

				((TextView)HeaderView).Text = property.Header;
				this.fragmentManager = fragmentManager;
			}

			public override Java.Lang.Object Value()
			{
				return type.ToString();
			}

			protected override void ApplyEntityValueToEditor(Java.Lang.Object o)
			{
				if(o == null) {
					this.editorButton.Text = "Tap to select.";
					return;
				}

				this.editorButton.Text = o.ToString();
				//type = (EmployeeType)Enum.Parse(typeof(EmployeeType), o.ToString());
				type = (EmployeeType)Enum.Parse(type.GetType(), o.ToString());
			}

			public void OnClick(View v)
			{
				this.ShowEditorFragment(this.type);
			}

			private void ShowEditorFragment(EmployeeType initialValue)
			{
				CustomEditorFragment fragment = new CustomEditorFragment();
				fragment.AddPropertyChangedListener(this);
				fragment.Type = initialValue;
				fragment.Show(this.fragmentManager, "customEditor");
			}

			public void OnPropertyChanged(String s, Java.Lang.Object o)
			{
				if(o.ToString() == type.ToString()) {
					return;
				}

				ApplyEntityValueToEditor(o);

				// Remember to call value changed, otherwise the object being edited will not be updated.
				OnEditorValueChanged(o);
			}
		}
	}
}

