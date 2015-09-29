
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
using Com.Telerik.Widget.List;
using Android.Support.V7.Widget;
using System.Collections;
using Android;
using Android.Support.V4.App;

namespace Samples
{
	public class ListViewSlideFragment : Fragment, ExampleFragment
	{
		RadListView listView;
		SlideLayoutManager slideLayoutManager;
		int orientation = OrientationHelper.Horizontal;

		public override void OnCreate (Bundle savedInstanceState)
		{
			base.OnCreate (savedInstanceState);

			// Create your fragment here
		}

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView =  inflater.Inflate(Resource.Layout.fragment_list_view_slide, container, false);
			listView = (RadListView)rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			CountryAdapter adapter = new CountryAdapter(GetData());
			listView.SetAdapter(adapter);

			slideLayoutManager = new SlideLayoutManager(Activity);
			listView.SetLayoutManager(slideLayoutManager);

			Button orientationBtn = (Button)rootView.FindViewById (Resource.Id.orientationBtn);
			orientationBtn.Click += (object sender, EventArgs e) => {
				if(orientation == OrientationHelper.Vertical) {
					orientation = OrientationHelper.Horizontal;
				} else {
					orientation = OrientationHelper.Vertical;
				}
				slideLayoutManager = new SlideLayoutManager(Activity, orientation);
				listView.SetLayoutManager(slideLayoutManager);
			};

			Button transitionBtn = (Button)rootView.FindViewById(Resource.Id.transitionBtn);
			transitionBtn.Click += (object sender, EventArgs e) => {
				if(slideLayoutManager.TransitionMode == SlideLayoutManager.Transition.SlideAway) {
					slideLayoutManager.TransitionMode = SlideLayoutManager.Transition.SlideOver;
				} else {
					slideLayoutManager.TransitionMode = SlideLayoutManager.Transition.SlideAway;
				}
			};

			ToggleButton spacingBtn = (ToggleButton)rootView.FindViewById(Resource.Id.spacingBtn);
			spacingBtn.CheckedChange += (object sender, CompoundButton.CheckedChangeEventArgs e) => {
				if(e.IsChecked) {
					slideLayoutManager.ItemSpacing = 50;
				} else {
					slideLayoutManager.ItemSpacing = 0;
				}
			};

			ToggleButton showPreviousButton = (ToggleButton)rootView.FindViewById(Resource.Id.showPrevBtn);
			showPreviousButton.CheckedChange += (object sender, CompoundButton.CheckedChangeEventArgs e) => {
				if(e.IsChecked) {
					slideLayoutManager.PreviousItemPreview = 100;
				} else {
					slideLayoutManager.PreviousItemPreview = 0;
				}
			};

			ToggleButton showNextButton = (ToggleButton)rootView.FindViewById(Resource.Id.showNextBtn);
			showNextButton.CheckedChange += (object sender, CompoundButton.CheckedChangeEventArgs e) => {
				if(e.IsChecked) {
					slideLayoutManager.NextItemPreview = 100;
				} else {
					slideLayoutManager.NextItemPreview = 0;
				}
			};

			Button previousBtn = (Button)rootView.FindViewById(Resource.Id.previousBtn);
			previousBtn.Click += (object sender, EventArgs e) => {
				slideLayoutManager.ScrollToPrevious();
			};

			Button nextBtn = (Button)rootView.FindViewById(Resource.Id.nextBtn);
			nextBtn.Click += (object sender, EventArgs e) => {
				slideLayoutManager.ScrollToNext();
			};

			return rootView;
		}

		public String Title(){
			return "Slide Layout";
		}

		private ArrayList GetData() {
			ArrayList source = new ArrayList();

			Country sourceItem = new Country();
			sourceItem.name = "Austria";
			sourceItem.capital = "Vienna";
			sourceItem.population = 8507786;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Belgium";
			sourceItem.capital = "Brussels";
			sourceItem.population = 11203992;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Bulgaria";
			sourceItem.capital = "Sofia";
			sourceItem.population = 7245677;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Croatia";
			sourceItem.capital = "Zagreb";
			sourceItem.population = 4246700;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Cyprus";
			sourceItem.capital = "Nicosia";
			sourceItem.population = 858000;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Czech Republic";
			sourceItem.capital = "Prague";
			sourceItem.population = 10512419;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Denmark";
			sourceItem.capital = "Copenhagen";
			sourceItem.population = 5627235;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Estonia";
			sourceItem.capital = "Tallinn";
			sourceItem.population = 1315819;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Finland";
			sourceItem.capital = "Helsinki";
			sourceItem.population = 5451270;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "France";
			sourceItem.capital = "Paris";
			sourceItem.population = 65856609;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Germany";
			sourceItem.capital = "Berlin";
			sourceItem.population = 80780000;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Greece";
			sourceItem.capital = "Athens";
			sourceItem.population = 10992589;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Hungary";
			sourceItem.capital = "Budapest";
			sourceItem.population = 9879000;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Ireland";
			sourceItem.capital = "Dublin";
			sourceItem.population = 4604029;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Italy";
			sourceItem.capital = "Rome";
			sourceItem.population = 60782668;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Latvia";
			sourceItem.capital = "Riga";
			sourceItem.population = 2001468;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Lithuania";
			sourceItem.capital = "Vilnius";
			sourceItem.population = 2943472;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Luxembourg";
			sourceItem.capital = "Luxembourg";
			sourceItem.population = 549680;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Malta";
			sourceItem.capital = "Valletta";
			sourceItem.population = 425384;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "The Netherlands";
			sourceItem.capital = "Amsterdam";
			sourceItem.population = 16829289;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Poland";
			sourceItem.capital = "Warsaw";
			sourceItem.population = 38495659;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Portugal";
			sourceItem.capital = "Lisbon";
			sourceItem.population = 10427301;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Romania";
			sourceItem.capital = "Bucharest";
			sourceItem.population = 19942642;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Slovakia";
			sourceItem.capital = "Bratislava";
			sourceItem.population = 5415949;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Slovenia";
			sourceItem.capital = "Ljubljana";
			sourceItem.population = 2061085;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Spain";
			sourceItem.capital = "Madrid";
			sourceItem.population = 46507760;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "Sweden";
			sourceItem.capital = "Stockholm";
			sourceItem.population = 9644864;

			source.Add(sourceItem);

			sourceItem = new Country();
			sourceItem.name = "United Kingdom";
			sourceItem.capital = "London";
			sourceItem.population = 64308261;

			source.Add(sourceItem);

			return source;
		}
	}
}

