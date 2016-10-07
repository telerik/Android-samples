using System;
using Com.Telerik.Widget.Dataform.Visualization.Annotations;
using Com.Telerik.Widget.Dataform.Visualization.Editors;

namespace Samples
{
	public class Employee : Java.Lang.Object
	{
		// >> data-form-image-labels
		[DataFormProperty(Label = "", Hint = "name", Index = 0,
						  ImageResource = Resource.Drawable.ic_dataform_guest)]
		public string Name
		{
			get;
			set;
		}
		// << data-form-image-labels

		[DataFormProperty(Label = "", Hint = "phone", Index = 1,
						  Editor = typeof(DataFormPhoneEditor),
						  ImageResource = Resource.Drawable.ic_dataform_phone)]
		public string Phone
		{
			get;
			set;
		}

		[DataFormProperty(Label = "", Hint = "birth date", Index = 2,
						  Editor = typeof(DataFormDateEditor),
						  ImageResource = Resource.Drawable.ic_dataform_calendar)]
		public long? BirthDate
		{
			get;
			set;
		}

		[DataFormProperty(Label = "", Hint = "employee id", Index = 3,
						  ImageResource = Resource.Drawable.ic_dataform_table)]
		public int? Id
		{
			get;
			set;
		}
	}
}

