
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using System.IO;
using Com.Telerik.Widget.Dataform.Visualization;
using Org.Json;
using Com.Telerik.Widget.Dataform.Engine;
using Android.Support.V4.App;

namespace Samples
{
	public class DataFormSchemaSetupFragment : Fragment, ExampleFragment
	{
		public string Title() {
			return "Json Schema setup";
		}

		public override void OnCreate (Bundle savedInstanceState)
		{
			base.OnCreate (savedInstanceState);

			// Create your fragment here
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ViewGroup layoutRoot = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_data_form_schema_setup, container, false);
			RadDataForm dataForm = (RadDataForm)layoutRoot.FindViewById(Resource.Id.data_form_schema);

			String json = LoadJSONFromAsset ("PersonExtended.json");
			try {
				JSONObject jsonObject = new JSONObject (json);
				dataForm.SetEntity(jsonObject);

				String schema = LoadJSONFromAsset("PersonSchema.json");
				JSONObject jsonSchema = new JSONObject(schema);
				DataFormMetadata metadata = new DataFormMetadata(jsonSchema);
				dataForm.Metadata = metadata;
			} catch(JSONException e) {
				Log.Error ("json", "error parsing json", e);
			}

			return layoutRoot;
		}

		private String LoadJSONFromAsset(String fileName)
		{
			String json = null;
			try
			{
				using (StreamReader sr = new StreamReader (Activity.Assets.Open (fileName)))
				{
					json = sr.ReadToEnd ();
				}
			}
			catch (IOException ex)
			{
				Log.Error("error", ex.StackTrace);
			}
			return json;
		}
	}
}

