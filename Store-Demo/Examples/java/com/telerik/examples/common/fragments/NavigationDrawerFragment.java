package com.telerik.examples.common.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.telerik.examples.ExampleGroupActivity;
import com.telerik.examples.R;
import com.telerik.examples.common.NavigationDrawerExamplesListAdapter;
import com.telerik.examples.common.contracts.TransitionHandler;
import com.telerik.examples.primitives.ExamplesGridView;
import com.telerik.examples.viewmodels.ExampleGroup;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    public static final String NAV_DRAWER_SECTION_HOME = "Home";
    public static final String NAV_DRAWER_SECTION_ABOUT = "About";
    public static final String NAV_DRAWER_SECTION_SETTINGS = "Settings";
    public static final String NAV_DRAWER_SECTION_FAVORITES = "Favorites";

    /**
     * Handler for updating the background of the actionbar according to the drawer's level.
     */
    private TransitionHandler bgTransitionHandler;

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ExamplesGridView headerList;
    private ExamplesGridView controlsList;
    private ExamplesGridView footerList;
    private View mFragmentContainerView;
    private ArrayAdapter<String> headerListAdapter;
    private ArrayAdapter<String> footerListAdapter;


    private String mCurrentNavigationDrawerSection = null;

    private ActionBar actionBar;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bgTransitionHandler = (TransitionHandler) getActivity();

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (savedInstanceState != null) {
            mCurrentNavigationDrawerSection = savedInstanceState.getString(STATE_SELECTED_POSITION);
        }else{
            mCurrentNavigationDrawerSection = !(this.getActivity() instanceof ExampleGroupActivity) ? getResources().getString(R.string.homeString) : null;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.

        this.headerListAdapter = new SectionArrayAdapter(
                this.getActivity(),
                R.layout.drawer_list_item_container,
                R.id.drawerItemTextView,
                new String[]{
                        getString(R.string.homeString),
                        getString(R.string.favoritesStringPascalCase)
                }
        );

        this.headerList.setAdapter(this.headerListAdapter);

        this.footerListAdapter = new SectionArrayAdapter(
                this.getActivity(),
                R.layout.drawer_list_item_container,
                R.id.drawerItemTextView,
                new String[]{
                        getString(R.string.settingsString),
                        getString(R.string.aboutString)
                }

        );

        this.footerList.setAdapter(this.footerListAdapter);

        int selectedPosition = this.headerListAdapter.getPosition(mCurrentNavigationDrawerSection);
        if (selectedPosition == -1) {
            selectedPosition = this.footerListAdapter.getPosition(mCurrentNavigationDrawerSection);
            this.footerList.setItemChecked(selectedPosition, true);
        } else {
            this.headerList.setItemChecked(selectedPosition, true);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBarActivity hostingActivity = (ActionBarActivity)this.getActivity();
        FragmentManager supportFrMngr = hostingActivity.getSupportFragmentManager();
        android.support.v4.app.Fragment currentFragment = supportFrMngr.findFragmentById(R.id.container);
        if (currentFragment instanceof SectionInfoProvider){
            String currentSection = ((SectionInfoProvider)currentFragment).getSectionName();
            this.updateSelectedSection(currentSection);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mLayoutRoot = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        this.headerList = (ExamplesGridView) mLayoutRoot.findViewById(R.id.headerListView);
        this.headerList.expandWholeHeight = true;
        this.controlsList = (ExamplesGridView) mLayoutRoot.findViewById(R.id.controlsList);
        this.controlsList.expandWholeHeight = true;
        this.footerList = (ExamplesGridView) mLayoutRoot.findViewById(R.id.footerList);
        this.footerList.expandWholeHeight = true;
        this.footerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectNavigationDrawerSection((String) parent.getItemAtPosition(position));
            }
        });
        this.controlsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectNavigationDrawerControl(position);
            }
        });

        this.headerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectNavigationDrawerSection((String) parent.getItemAtPosition(position));
            }
        });

        this.controlsList.setAdapter(new NavigationDrawerExamplesListAdapter(this.getActivity(), 0));

        return mLayoutRoot;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void closeDrawer(){
        if (this.mDrawerLayout != null){
            this.mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        this.actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        if (this.actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.navigation_drawer_hamburger);
        }
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.navigation_drawer_hamburger,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                bgTransitionHandler.updateTransition(slideOffset);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (mCallbacks != null) {
                    mCallbacks.onNavigationDrawerClosed();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (mCallbacks != null) {
                    mCallbacks.onNavigationDrawerOpened();
                }
            }
        };

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public String selectedSection() {
        return this.mCurrentNavigationDrawerSection;
    }

    public void updateSelectedSection(String section){
        this.mCurrentNavigationDrawerSection = section;
        this.refreshListControlSelection(this.headerList);
        this.refreshListControlSelection(this.footerList);
    }

    private void selectNavigationDrawerControl(int position) {
        this.updateSelectedSection(null);
        ExampleGroup control = (ExampleGroup) this.controlsList.getItemAtPosition(position);
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerControlSelected(control);
        }
    }

    private void selectNavigationDrawerSection(String section) {
        if (this.mCurrentNavigationDrawerSection != null &&
                this.mCurrentNavigationDrawerSection.equalsIgnoreCase(section)){
            return;
        }
        this.updateSelectedSection(section);

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerSectionSelected(section);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_SELECTED_POSITION, mCurrentNavigationDrawerSection);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void refreshListControlSelection(GridView listControl) {
        for (int i = 0; i < listControl.getChildCount(); i++) {
            View currentView = listControl.getChildAt(i);
            initContainerSelectionState(currentView);
        }
    }

    private void initContainerSelectionState(View container) {
        TextView txtItemText = (TextView) container.findViewById(R.id.drawerItemTextView);

        if (mCurrentNavigationDrawerSection != null &&
                (!mCurrentNavigationDrawerSection.equals(getResources().getString(R.string.settingsString)) && container.getTag().equals(mCurrentNavigationDrawerSection))) {
            txtItemText.setTextColor(this.getActivity().getResources().getColor(R.color.telerikGreen));
        } else {
            txtItemText.setTextColor(this.getActivity().getResources().getColor(R.color.black));
        }
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer sections list is selected.
         */
        void onNavigationDrawerSectionSelected(String section);

        void onNavigationDrawerControlSelected(ExampleGroup control);

        void onNavigationDrawerOpened();

        void onNavigationDrawerClosed();
    }

    class SectionArrayAdapter extends ArrayAdapter<String> {
        public SectionArrayAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rootView = super.getView(position, convertView, parent);

            rootView.setTag(this.getItem(position));

            initContainerSelectionState(rootView);

            return rootView;
        }
    }

    public interface SectionInfoProvider{
        String getSectionName();
    }
}
