using System;
using System.Collections.Generic;
using Android.Views;

using Com.Telerik.Android.Common;
using Com.Telerik.Widget.Autocomplete;
using Java.IO;
using Java.Net;
using Org.Json;
using Java.Util;
using Java.Lang;
using Android.Widget;
using Android.Graphics;
using Android.Net;
using Android.Content;

namespace Samples
{
	public class AutoCompleteRemoteDataFragment : Android.Support.V4.App.Fragment, ExampleFragment, View.IOnClickListener
	{
		private RadAutoCompleteTextView autocomplete;
		private AutoCompleteAdapter adapter;
		private View exampleMain;
		private View connectionInfo;
		private Button refresh;

		public string Title()
		{
			return "Loading Remote Data";
		}

		public void OnClick(View v)
		{
			bool isConnectionAvailable = IsConnectionAvailable(Activity);
			this.UpdateConnectivity(isConnectionAvailable);
		}

		public override Android.Views.View OnCreateView(Android.Views.LayoutInflater inflater, Android.Views.ViewGroup container, Android.OS.Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_autocomplete_remote_data, container, false);

			this.autocomplete = (RadAutoCompleteTextView)rootView.FindViewById(Resource.Id.autocomplete);

			this.exampleMain = rootView.FindViewById(Resource.Id.exampleMainContainer);
			this.connectionInfo = rootView.FindViewById(Resource.Id.connectionInfoContainer);
			this.refresh = (Button)rootView.FindViewById(Resource.Id.retryButton);
			this.refresh.SetOnClickListener(this);

			bool isConnectionAvailable = IsConnectionAvailable(Activity);
			this.UpdateConnectivity(isConnectionAvailable);

			this.autocomplete.SuggestMode = SuggestMode.Suggest;
			this.autocomplete.DisplayMode = DisplayMode.Plain;
			this.autocomplete.AutocompleteHint = "Choose airport";
			// >> set-async-data-xamarin
			this.autocomplete.UsingAsyncData = true;
			this.adapter = new AutoCompleteAdapter(
				this.Context, new List<TokenModel>(), Integer.ValueOf(Resource.Layout.suggestion_item_layout));
			// << set-async-data-xamarin
			this.adapter.CompletionMode = new StartsWithRemote(this.autocomplete);
			this.autocomplete.Adapter = this.adapter;

			Display display = this.Activity.WindowManager.DefaultDisplay;
			int height = display.Height;
			this.autocomplete.SuggestionViewHeight = height / 4;

			return rootView;
		}

		private void UpdateConnectivity(bool isNetwork)
		{
			this.exampleMain.Visibility = isNetwork ? ViewStates.Visible : ViewStates.Gone;
			this.connectionInfo.Visibility = isNetwork ? ViewStates.Gone : ViewStates.Visible;
		}

		public static bool IsConnectionAvailable(Context context)
		{
			ConnectivityManager cm = (ConnectivityManager)context.GetSystemService(Context.ConnectivityService);

			NetworkInfo activeNetwork = cm.ActiveNetworkInfo;
			return activeNetwork != null &&
				activeNetwork.IsConnectedOrConnecting;
		}
	}

	// >> autocomplete-remote-full-xamarin
	// >> autocomplete-remote-completion-mode-xamarin
	public class StartsWithRemote : Java.Lang.Object, IFunction2Async
	{
		private RadAutoCompleteTextView autocomplete;

		public StartsWithRemote(RadAutoCompleteTextView autocomplete)
		{
			this.autocomplete = autocomplete;
		}

		public void Apply(Java.Lang.Object autoCompleteText,
						  Java.Lang.Object originalCollection, IProcedure finishedCallback)
		{
			IList list = originalCollection as IList;

			if (list == null)
			{
				FeedAutoCompleteTask task = new FeedAutoCompleteTask(
					finishedCallback, (string)autoCompleteText, this.autocomplete);
				task.Execute();
			}
		}
	}
	// << autocomplete-remote-completion-mode-xamarin

	class FeedAutoCompleteTask : Android.OS.AsyncTask<string, string, Java.Lang.Void>
	{
		JSONArray data;
		List<TokenModel> remoteData;
		private IProcedure remoteCallback;
		private string filter;
		private RadAutoCompleteTextView autocomplete;

		public FeedAutoCompleteTask(IProcedure callback, string filter, RadAutoCompleteTextView autocomplete)
		{
			this.remoteCallback = callback;
			this.filter = filter;
			this.autocomplete = autocomplete;
			this.data = new JSONArray();
		}

		// >> autocomplete-remote-do-in-background-xamarin
		protected override Java.Lang.Void RunInBackground(params string[] @params)
		{
			HttpURLConnection urlConnection = null;
			try
			{
				URL url = new URL
					("http://www.telerik.com/docs/default-source/ui-for-ios/airports.json?sfvrsn=2");

				urlConnection = (HttpURLConnection)url.OpenConnection();

				urlConnection.RequestMethod = "GET";
				urlConnection.UseCaches = false;
				urlConnection.AllowUserInteraction = false;
				urlConnection.Connect();
				HttpStatus status = urlConnection.ResponseCode;

				if (status.Equals(HttpStatus.Ok))
				{
					BufferedReader reader = new BufferedReader
						(new InputStreamReader(urlConnection.InputStream));
					char[] buffer = new char[1024];
					int n;
					Writer writer = new Java.IO.StringWriter();

					while ((n = reader.Read(buffer)) != -1)
					{
						writer.Write(buffer, 0, n);
					}

					string json = writer.ToString();

					try
					{
						JSONObject jObj = new JSONObject(json);
						data = jObj.GetJSONArray("airports");
					}
					catch (JSONException ex)
					{
						ex.PrintStackTrace();
					}
				}

			}
			catch (Java.IO.IOException e)
			{
				e.PrintStackTrace();
			}
			finally
			{
				if (urlConnection != null)
				{
					urlConnection.Disconnect();
				}
			}

			return null;
		}
		// << autocomplete-remote-do-in-background-xamarin

		// >> autocomplete-remote-on-post-execute-xamarin
		protected override void OnPostExecute(Java.Lang.Void result)
		{
			ArrayList filtered = new ArrayList();
			remoteData = GetTokenModelObjects(data);
			foreach (TokenModel item in remoteData)
			{
				string text = item.Text;
				if (text.ToLower().StartsWith(filter.ToLower()))
				{
					filtered.Add(item);
				}
			}
			remoteCallback.Apply(filtered);
			autocomplete.ResolveAfterFilter(autocomplete.TextField.Text.ToString(), true);
		}
		// << autocomplete-remote-on-post-execute-xamarin
		// << autocomplete-remote-full-xamarin

		private List<TokenModel> GetTokenModelObjects(JSONArray data)
		{

			List<TokenModel> feedData = new List<TokenModel>();
			JSONObject current = new JSONObject();

			for (int i = 0; i < data.Length(); i++)
			{
				string airport = "";

				try
				{
					current = data.GetJSONObject(i);
					string fullname = (string)current.Get("FIELD2");
					string abr = (string)current.Get("FIELD5");
					airport = fullname + "," + abr;

				}
				catch (JSONException ex)
				{
					ex.PrintStackTrace();
				}

				TokenModel token = new TokenModel(airport, null);
				feedData.Add(token);
			}

			return feedData;
		}
	}
}

