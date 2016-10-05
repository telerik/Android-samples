
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.Autocomplete;

namespace Samples
{
	public class AutoCompleteTokenLayoutsFragment : Android.Support.V4.App.Fragment, ExampleFragment
	{
		private RadAutoCompleteTextView autocomplete;
		private AutoCompleteAdapter adapter;
		private List<String> data;

		public AutoCompleteTokenLayoutsFragment() : base()
		{
			this.data = new List<string>() { "Australia", "Albania","Austria", "Argentina", "Maldives","Bulgaria","Belgium","Cyprus","Italy","Japan",
										"Denmark","Finland","France","Germany","Greece","Hungary","Ireland",
										"Latvia","Luxembourg","Macedonia","Moldova","Monaco","Netherlands","Norway",
										"Poland","Romania","Russia","Sweden","Slovenia","Slovakia","Turkey","Ukraine",
										"Vatican City", "Chad", "China", "Chile" };
		}

		public string Title()
		{
			return "Tokens Layout";
		}

		public override Android.Views.View OnCreateView(Android.Views.LayoutInflater inflater, Android.Views.ViewGroup container, Android.OS.Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_autocomplete_token_layouts, container, false);
			this.autocomplete = (RadAutoCompleteTextView)rootView.FindViewById(Resource.Id.autocmp);

			this.autocomplete.SuggestMode = SuggestMode.Suggest;
			// >> autocomplete-display-mode-xamarin
			this.autocomplete.DisplayMode = DisplayMode.Tokens;
			// << autocomplete-display-mode-xamarin

			// >> autocomplete-layout-mode-xamarin
			this.autocomplete.TokensLayoutMode = LayoutMode.Horizontal;
			// << autocomplete-layout-mode-xamarin

			this.adapter = new AutoCompleteAdapter(this.Context, this.GetTokenObjects(), Java.Lang.Integer.ValueOf(Resource.Layout.suggestion_item_layout));

			// >> autocomplete-completion-mode-xamarin
			this.adapter.CompletionMode = CompletionMode.StartsWith;
			// << autocomplete-completion-mode-xamarin
			this.autocomplete.Adapter = this.adapter;

			Display display = this.Activity.WindowManager.DefaultDisplay;
			int height = display.Height;
			this.autocomplete.SuggestionViewHeight = height / 4;

			this.SetButtonActions(rootView);

			return rootView;
		}

		private List<TokenModel> GetTokenObjects()
		{
			List<TokenModel> feedData = new List<TokenModel>();
			for (int i = 0; i < this.data.Count; i++)
			{
				TokenModel token = new TokenModel(this.data[i], null);
				feedData.Add(token);
			}

			return feedData;
		}

		private void SetButtonActions(View rootView)
		{
			Button btnHorizontal = (Button)rootView.FindViewById(Resource.Id.horizontal_layout_btn);
			btnHorizontal.Click += (object sender, EventArgs e) =>
			{
				this.autocomplete.TokensLayoutMode = LayoutMode.Horizontal;
				this.autocomplete.ResetAutocomplete();
			};

			Button btnWrap = (Button)rootView.FindViewById(Resource.Id.wrap_layout_btn);
			btnWrap.Click += (object sender, EventArgs e) =>
			{
				this.autocomplete.TokensLayoutMode = LayoutMode.Wrap;
				this.autocomplete.ResetAutocomplete();
			};
		}
	}
}
