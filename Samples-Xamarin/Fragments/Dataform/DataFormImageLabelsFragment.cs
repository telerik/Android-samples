using System;
using Android.OS;
using Android.Views;
using Com.Telerik.Widget.Dataform.Visualization;

namespace Samples
{
	public class DataFormImageLabelsFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		RadDataForm dataForm;
		Employee employee;

		public String Title()
		{
			return "Image Labels";
		}

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup layoutRoot = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_dataform_image_labels, container, false);

			dataForm = (RadDataForm)layoutRoot.FindViewById(Resource.Id.dataForm);
			employee = new Employee();
			dataForm.SetEntity(employee);


			return layoutRoot;
		}
	}
}

