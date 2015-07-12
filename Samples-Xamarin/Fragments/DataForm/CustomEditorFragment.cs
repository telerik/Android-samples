using Android.Views;
using Android.OS;
using System;
using System.Collections.Generic;
using Java.Util;
using Android.Widget;
using Com.Telerik.Android.Primitives.Widget.Sidedrawer;
using Com.Telerik.Android.Primitives.Widget.Sidedrawer.Transitions;
using Com.Telerik.Widget.Dataform.Engine;

namespace Samples
{
	public class CustomEditorFragment : Android.Support.V4.App.DialogFragment, AdapterView.IOnItemClickListener, INotifyPropertyChanged
	{
		private EmployeeType type;
		private String[] descriptions = new String[Enum.GetValues(typeof(EmployeeType)).Length];
		private NotifyPropertyChangedBase notifier = new NotifyPropertyChangedBase();

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			descriptions[0] = "Develops software that is then sold to end users. Responsibilities include writing and debuggnig code, writing unit tests and documentation.";
			descriptions[1] = "Manages a team of software developers. Sets goals and expectations and makes sure the team goals are aligned with the overall company goals.";
			descriptions[2] = "Communicates with customers and helps them resolve technical issues. Escalates customer issues so that the developers can fix them when necessary.";
			descriptions[3] = "Responsible for creating product awareness and enticing customers to buy the product by efficiently showcasing the strengths of the product.";

			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_custom_editor, null);

			ListView mainList = (ListView)rootView.FindViewById(Resource.Id.dataform_custom_editor_list);
			mainList.Adapter = new CustomAdapter(this.Activity, descriptions);
			mainList.OnItemClickListener = this;

			return rootView;
		}

		public String Title() 
		{
			return "Editors";
		}

		public void OnItemClick(AdapterView parent, View view, int position, long id) 
		{
			this.Type = (EmployeeType)Enum.GetValues(typeof(EmployeeType)).GetValue(position);

			this.Dismiss();
		}

		public EmployeeType Type 
		{
			get 
			{
				return type;
			}
			set 
			{
			}
		}

		public void setType(EmployeeType value) 
		{
			this.type = value;
			notifier.NotifyListeners("Type", value);
		}

		public void AddPropertyChangedListener(IPropertyChangedListener propertyChangedListener) {
			notifier.AddPropertyChangedListener(propertyChangedListener);
		}

		public void RemovePropertyChangedListener(IPropertyChangedListener propertyChangedListener) {
			notifier.RemovePropertyChangedListener(propertyChangedListener);
		}
	}
}