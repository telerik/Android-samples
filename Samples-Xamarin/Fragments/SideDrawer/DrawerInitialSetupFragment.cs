using Android.Views;
using Android.OS;
using System;
using System.Collections.Generic;
using Java.Util;
using Android.Widget;
using Com.Telerik.Android.Primitives.Widget.Sidedrawer;
using Com.Telerik.Android.Primitives.Widget.Sidedrawer.Transitions;

namespace Samples
{
	public class DrawerInitialSetupFragment : Android.Support.V4.App.Fragment, ExampleFragment, IDrawerChangeListener, AdapterView.IOnItemSelectedListener, CompoundButton.IOnCheckedChangeListener
	{
		List<DrawerTransitionBase> transitions = new List<DrawerTransitionBase>();
		RadSideDrawer drawer;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			transitions.Add (new SlideInOnTopTransition ());
			transitions.Add (new FallDownTransition());
			transitions.Add (new PushTransition());
			transitions.Add (new RevealTransition());
			transitions.Add (new ReverseSlideOutTransition());
			transitions.Add (new ScaleDownPusherTransition());
			transitions.Add (new ScaleUpTransition());
			transitions.Add (new SlideAlongTransition());
			ViewGroup rootView = (ViewGroup)inflater.Inflate(Resource.Layout.fragment_side_drawer_features, null);

			drawer = new RadSideDrawer (Activity);
			drawer.MainContent = this.LoadMainContent(inflater);
			drawer.DrawerContent = this.LoadDrawerContent(inflater);

			drawer.AddChangeListener (this);

			rootView.AddView (drawer);

			return rootView;
		}

		private View LoadMainContent(LayoutInflater inflater) {
			View result = inflater.Inflate(Resource.Layout.side_drawer_features_main_content, null);
			Spinner locationSpinner = (Spinner)result.FindViewById(Resource.Id.drawerLocationSpinner);
			ArrayAdapter<DrawerLocation> locationAdapter = new ArrayAdapter<DrawerLocation>(this.Activity, Android.Resource.Layout.SimpleListItem1, DrawerLocation.Values());
			locationSpinner.Adapter = locationAdapter;
			locationSpinner.OnItemSelectedListener = this;
			Spinner transitionsSpinner = (Spinner)result.FindViewById(Resource.Id.drawerTransitionsSpinner);

			ArrayAdapter<DrawerTransitionBase> transitionsAdapter = new ArrayAdapter<DrawerTransitionBase>(this.Activity, Android.Resource.Layout.SimpleListItem1, transitions);
			transitionsSpinner.Adapter = transitionsAdapter;
			transitionsSpinner.OnItemSelectedListener = this;
			return result;
		}

		private View LoadDrawerContent(LayoutInflater inflater) {
			View result = inflater.Inflate(Resource.Layout.side_drawer_features_drawer_content, null);

			ToggleButton tapOutside = (ToggleButton)result.FindViewById(Resource.Id.drawerTapOutsideButton);
			tapOutside.SetOnCheckedChangeListener (this);

			ToggleButton lockButton = (ToggleButton)result.FindViewById(Resource.Id.lockDrawerButton);
			lockButton.SetOnCheckedChangeListener (this);

			return result;
		}

		public String Title() {
			return "Features";
		}

		public void OnCheckedChanged (CompoundButton buttonView, bool isChecked)
		{
			if (buttonView.Id == Resource.Id.lockDrawerButton) {
				this.drawer.IsLocked = isChecked;
			}

			if (buttonView.Id == Resource.Id.drawerTapOutsideButton) {
				this.drawer.TapOutsideToClose = isChecked;
			}
		}

		public void OnItemSelected (AdapterView parent, View view, int position, long id)
		{
			if (parent.Id == Resource.Id.drawerLocationSpinner) 
			{
				this.drawer.DrawerLocation = DrawerLocation.Values() [position];
			}

			if (parent.Id == Resource.Id.drawerTransitionsSpinner) 
			{
				this.drawer.DrawerTransition = this.transitions[position];
			}

		}

		public void OnNothingSelected (AdapterView parent)
		{

		}

		public void OnDrawerClosed (RadSideDrawer p0)
		{

		}

		public bool OnDrawerClosing (RadSideDrawer p0)
		{
			return false;
		}

		public void OnDrawerOpened (RadSideDrawer p0)
		{

		}

		public bool OnDrawerOpening (RadSideDrawer p0)
		{
			return false;
		}
	}
}