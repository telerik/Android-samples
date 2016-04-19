
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
using Com.Telerik.Widget.Dataform.Engine;

namespace Samples
{
	public class DataFormLabelPositionsFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadDataForm dataForm;
		Person person;

		public String Title() {
			return "Label Positions";
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup layoutRoot = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_label_positions, container, false);

			dataForm = (RadDataForm)layoutRoot.FindViewById(Resource.Id.dataForm);
			person = new Person();
			dataForm.SetEntity(person);
			dataForm.LayoutManager = (new DataFormLinearLayoutManager(Activity));

			// >> data-form-label-positions
			dataForm.LabelPosition = LabelPosition.Left;
			// << data-form-label-positions

			return layoutRoot;
		}
	}
}

