
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Graphics.Drawables;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Autocomplete;
using Newtonsoft.Json;

namespace Samples
{
	public class AutoCompleteCustomizationFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		private RadAutoCompleteTextView autocomplete;
		private AutoCompleteAdapter adapter;

		public string Title()
		{
			return "Customization";
		}

		public override Android.Views.View OnCreateView(Android.Views.LayoutInflater inflater, Android.Views.ViewGroup container, Android.OS.Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_autocomplete_customization, container, false);
			this.autocomplete = (RadAutoCompleteTextView)rootView.FindViewById(Resource.Id.autocomplete);

			List<FeedObject> objects = this.loadDataFromJson();

			// >> autocomplete-suggest-mode-xamarin
			this.autocomplete.SuggestMode = SuggestMode.Suggest;
			// << autocomplete-suggest-mode-xamarin

			this.autocomplete.DisplayMode = DisplayMode.Tokens;

			this.adapter = new AutoCompleteAdapter(this.Context, this.GetTokenObjects(objects), Java.Lang.Integer.ValueOf(Resource.Layout.suggestion_item_layout));
			this.adapter.CompletionMode = CompletionMode.StartsWith;
			this.autocomplete.Adapter = this.adapter;

			Display display = this.Activity.WindowManager.DefaultDisplay;
			int height = display.Height;
			this.autocomplete.SuggestionViewHeight = height / 3;

			Drawable img = Resources.GetDrawable(Resource.Drawable.search);
			this.autocomplete.SetAutocompleteIcon(img);

			return rootView;
		}

		private List<TokenModel> GetTokenObjects(List<FeedObject> initialObjects)
		{
			List<TokenModel> feedData = new List<TokenModel>();
			for (int i = 0; i < initialObjects.Count; i++)
			{
				int dr = Resources.GetIdentifier(initialObjects[i].Flag, "drawable", "com.telerik.android.sdk.xamarin");
				Drawable drawable = Resources.GetDrawable(dr);
				TokenModel token = new TokenModel(initialObjects[i].Country, drawable);
				feedData.Add(token);
			}

			return feedData;
		}

		private List<FeedObject> loadDataFromJson()
		{
			String json = LoadJSONFromAsset("countries.json");
			List<FeedObject> feed = JsonConvert.DeserializeObject<List<FeedObject>>(json);
			return feed;
		}

		private String LoadJSONFromAsset(String fileName)
		{
			String json = null;
			try
			{
				using (StreamReader sr = new StreamReader(Activity.Assets.Open(fileName)))
				{
					json = sr.ReadToEnd();
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
