using System;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Dataform.Engine;

namespace Samples
{
	public class CustomEditorFragment : DialogFragment, AdapterView.IOnItemClickListener, INotifyPropertyChanged
	{
		public static readonly string[] Descriptions = new string[System.Enum.GetNames(typeof(EmployeeType)).Length];
		private EmployeeType type;
		private readonly NotifyPropertyChangedBase notifier = new NotifyPropertyChangedBase();
		
		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootLayout = inflater.Inflate(Resource.Layout.dataform_custom_editor_fragment, null);

			ListView mainList = (ListView) rootLayout.FindViewById(Resource.Id.dataform_custom_editor_list);
			mainList.Adapter = new CustomAdapter(this.Activity);
			mainList.OnItemClickListener = this;

			Descriptions[0] = "Develops software that is then sold to end users. Responsibilities include writing and debuggnig code, writing unit tests and documentation.";
			Descriptions[1] = "Manages a team of software developers. Sets goals and expectations and makes sure the team goals are aligned with the overall company goals.";
			Descriptions[2] = "Communicates with customers and helps them resolve technical issues. Escalates customer issues so that the developers can fix them when necessary.";
			Descriptions[3] = "Responsible for creating product awareness and enticing customers to buy the product by efficiently showcasing the strengths of the product.";

			return rootLayout;
		}

		public void OnItemClick(AdapterView parent, View view, int position, long id)
		{
			this.Type = (EmployeeType) position;
			this.Dismiss();
		}

		public void AddPropertyChangedListener(IPropertyChangedListener propertyChangeListener)
		{
			this.notifier.AddPropertyChangedListener(propertyChangeListener);
		}

		public void RemovePropertyChangedListener(IPropertyChangedListener propertyChangeListener)
		{
			this.notifier.RemovePropertyChangedListener(propertyChangeListener);
		}

		public EmployeeType Type
		{
			get { return this.type; }
			set
			{
				this.type = value;
				this.notifier.NotifyListeners("d", value.ToString());
			}
		}

		public class CustomAdapter : BaseAdapter
		{
			private Context context;

			public CustomAdapter(Context context)
			{
				this.context = context;
			}

			public override int Count
			{
				get
				{
					return System.Enum.GetNames(typeof(EmployeeType)).Length;
				}
			}

			public override Java.Lang.Object GetItem(int position)
			{
				return ((EmployeeType) position).ToString();
			}

			public override long GetItemId(int position)
			{
				return ((EmployeeType) position).GetHashCode();
			}

			public override View GetView(int position, View convertView, ViewGroup parent)
			{
				View layoutRoot = LayoutInflater.From(this.context).Inflate(Resource.Layout.dataform_custom_editor_list_item, null);

				TextView title = (TextView) layoutRoot.FindViewById(Resource.Id.listItemTitle);
				title.Text = ((EmployeeType)position).ToString();

				TextView desc = (TextView) layoutRoot.FindViewById(Resource.Id.listItemDescription);
				desc.Text = CustomEditorFragment.Descriptions[position];

				return layoutRoot;
			}
		}
	}
}