package com.telerik.examples;

//import android.app.ActionBar;

import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.TelerikActivityHelper;
import com.telerik.examples.common.TrackedApplication;
import com.telerik.examples.common.contracts.TrackedActivity;
import com.telerik.examples.common.contracts.TransitionHandler;
import com.telerik.examples.common.fragments.AboutFragment;
import com.telerik.examples.common.fragments.ControlsFragment;
import com.telerik.examples.common.fragments.ExampleGroupListFragment;
import com.telerik.examples.common.fragments.FavoritesFragment;
import com.telerik.examples.common.fragments.MainActivityExamplesFragment;
import com.telerik.examples.common.fragments.NavigationDrawerFragment;
import com.telerik.examples.primitives.TipsPresenter;
import com.telerik.examples.viewmodels.ExampleGroup;

import java.util.HashMap;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ActionBar.OnNavigationListener, TransitionHandler, SpinnerAdapter, TrackedActivity, FragmentManager.OnBackStackChangedListener {

    private ColorDrawable currentBgColor;

    private int redFrom;
    private int redTo;

    private int greenFrom;
    private int greenTo;

    private int blueFrom;
    private int blueTo;

    private ActionBar actionBar;
    private ExamplesApplicationContext app;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private TipsPresenter tipsPresenter;
    private int lastNavigationItemIndex = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TelerikActivityHelper.updateActivityTaskDescription(this);
        Resources resources = getResources();
        ColorDrawable bgColorPrimary = new ColorDrawable(resources.getColor(R.color.primary_title_background));
        ColorDrawable bgColorSecondary = new ColorDrawable(resources.getColor(R.color.secondary_title_background));
        currentBgColor = bgColorPrimary;
        setContentView(R.layout.activity_main);
        Toolbar tb = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(tb);
        tb.setTitleTextColor(Color.WHITE);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setBackgroundDrawable(currentBgColor);
        }

        redFrom = Color.red(bgColorPrimary.getColor());
        redTo = Color.red(bgColorSecondary.getColor());

        greenFrom = Color.green(bgColorPrimary.getColor());
        greenTo = Color.green(bgColorSecondary.getColor());

        blueFrom = Color.blue(bgColorPrimary.getColor());
        blueTo = Color.blue(bgColorSecondary.getColor());

        app = (ExamplesApplicationContext) this.getApplicationContext();


        this.tipsPresenter = Util.getLayoutPart(this, R.id.tipsPresenter, TipsPresenter.class);

        this.setupNavigationDrawer(savedInstanceState);
        this.setupActionBar();

        // Prevents the drawer from being opened at the time of the first launch.
        Util.getLayoutPart(this, R.id.drawer_layout, DrawerLayout.class).closeDrawer(Gravity.LEFT);
        this.getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (savedInstanceState == null) {
            this.loadSectionFromIntent(this.getIntent(), false);
            this.app.trackScreenOpened(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.invalidateActionbar();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackStackChanged() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        manageTipsPresenter(currentFragment);
        if (currentFragment instanceof NavigationDrawerFragment.SectionInfoProvider) {
            mNavigationDrawerFragment.updateSelectedSection(((NavigationDrawerFragment.SectionInfoProvider) currentFragment).getSectionName());
        }
        invalidateActionbar();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("spinner_selection", this.lastNavigationItemIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.lastNavigationItemIndex = savedInstanceState.getInt("spinner_selection", this.lastNavigationItemIndex);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment instanceof ControlsFragment) {
            this.invalidateActionbar();
            this.actionBar.setSelectedNavigationItem(this.lastNavigationItemIndex);
        }
    }

    @Override
    public void onNavigationDrawerOpened() {
        this.app.trackEvent(TrackedApplication.HOME_SCREEN, TrackedApplication.EVENT_DRAWER_OPENED);
    }

    @Override
    public void onNavigationDrawerClosed() {
        this.app.trackEvent(TrackedApplication.HOME_SCREEN, TrackedApplication.EVENT_DRAWER_CLOSED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment instanceof ExampleGroupListFragment) {
            ExampleGroupListFragment typedFragment = (ExampleGroupListFragment) currentFragment;
            MenuItem item = menu.findItem(R.id.action_toggle_list_view);
            if (typedFragment.hasData()) {
                item.setVisible(true);
                if (typedFragment.currentMode() == ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE) {
                    item.setIcon(R.drawable.example_group_toggle_layout_pressed);
                } else {
                    item.setIcon(R.drawable.example_group_toggle_layout_normal);
                }
            } else {
                item.setVisible(false);
            }
        } else {
            menu.findItem(R.id.action_toggle_list_view).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_toggle_list_view) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (currentFragment instanceof ExampleGroupListFragment) {
                ExampleGroupListFragment typedFragment = (ExampleGroupListFragment) currentFragment;
                if (typedFragment.currentMode() == ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE) {
                    typedFragment.setViewMode(ExampleGroupListFragment.EXAMPLE_GROUP_GRID_MODE);
                    item.setIcon(R.drawable.example_group_toggle_layout_normal);
                    app.trackEvent(TrackedApplication.HOME_SCREEN, TrackedApplication.EVENT_LIST_LAYOUT_CHANGED);
                } else {
                    typedFragment.setViewMode(ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE);
                    item.setIcon(R.drawable.example_group_toggle_layout_pressed);
                    app.trackEvent(TrackedApplication.HOME_SCREEN, TrackedApplication.EVENT_LIST_LAYOUT_CHANGED);
                }

                typedFragment.refreshFilters();

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        //THIS FUNCTIONALITY WILL BE USED IN THE NEXT VERSION OF THE EXAMPLES.
        Fragment controlsFragment = this.getSupportFragmentManager().findFragmentById(R.id.container);

        if (!(controlsFragment instanceof ControlsFragment)) {
            return false;
        }
        ControlsFragment typedFragment = (ControlsFragment) controlsFragment;
        this.lastNavigationItemIndex = itemPosition;
        if (itemPosition == 0) {
            typedFragment.showAll();
            app.trackEvent(TrackedApplication.HOME_SCREEN, TrackedApplication.EVENT_ALL_CONTROLS);
            return true;

        } else if (itemPosition == 1) {
            typedFragment.showHighlighted();
            app.trackEvent(TrackedApplication.HOME_SCREEN, TrackedApplication.EVENT_HIGHLIGHTED_CONTROLS);
            return true;
        }
        return false;
    }

    @Override
    public void onNavigationDrawerControlSelected(ExampleGroup control) {
        this.app.openExample(this, control);
    }

    @Override
    public void onNavigationDrawerSectionSelected(String section) {
        this.addFragmentForSection(section, true);
    }

    private void loadSectionFromIntent(Intent intent, boolean addToBackStack) {
        if (intent.hasExtra(ExamplesApplicationContext.INTENT_SECTION_ID)) {
            String selectedSection = intent.getStringExtra(ExamplesApplicationContext.INTENT_SECTION_ID);
            if (selectedSection == null) {
                selectedSection = NavigationDrawerFragment.NAV_DRAWER_SECTION_HOME;
            }
            this.addFragmentForSection(selectedSection, addToBackStack);
        }
    }

    private void addFragmentForSection(String section, boolean addToBackStack) {
        Fragment newFragment = this.getSectionFragment(section);

        if (newFragment == null) {
            if (section.equalsIgnoreCase(NavigationDrawerFragment.NAV_DRAWER_SECTION_SETTINGS)) {
                this.app.showSettings(this);
            }
            return;
        }

        this.manageTipsPresenter(newFragment);

        if (newFragment instanceof ControlsFragment) {
            ControlsFragment typedFragment = (ControlsFragment) newFragment;
            if (this.lastNavigationItemIndex == 0) {
                typedFragment.showAll();
            } else if (this.lastNavigationItemIndex == 1) {
                typedFragment.showHighlighted();
            }
        }

        this.app.loadFragment(this, newFragment, R.id.container, addToBackStack);
        if (newFragment instanceof FavoritesFragment) {
            this.app.trackEvent(TrackedApplication.HOME_SCREEN, TrackedApplication.EVENT_SHOW_FAVOURITES);
        }else if (newFragment instanceof AboutFragment){
            this.app.trackEvent(TrackedApplication.HOME_SCREEN, TrackedApplication.EVENT_SHOW_ABOUT);
        }
        this.invalidateOptionsMenu();
    }

    private void manageTipsPresenter(Fragment newFragment) {
        if (!(newFragment instanceof ControlsFragment)) {
            if (this.tipsPresenter.getVisibility() != View.VISIBLE) {
                if (!this.app.getTipLearned()) {
                    this.tipsPresenter.cancelShow();
                }
            } else {
                this.tipsPresenter.hide();
            }
        } else if (!this.app.getTipLearned()) {
            this.tipsPresenter.scheduleShow();
        }
    }

    private Fragment getSectionFragment(String section) {
        Fragment newFragment = null;

        if (section.equalsIgnoreCase(NavigationDrawerFragment.NAV_DRAWER_SECTION_HOME)) {
            newFragment = new MainActivityExamplesFragment();
        } else if (section.equalsIgnoreCase(NavigationDrawerFragment.NAV_DRAWER_SECTION_FAVORITES)) {
            newFragment = new FavoritesFragment();
        } else if (section.equalsIgnoreCase(NavigationDrawerFragment.NAV_DRAWER_SECTION_ABOUT)) {
            newFragment = new AboutFragment();
        }

        return newFragment;
    }

    @Override
    public void updateTransition(float step) {
        Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(currentFragment instanceof AboutFragment))
            currentBgColor.setColor(calculateCurrentColor(step));
    }

    public void invalidateActionbar() {
        invalidateActionbarTitle();
        invalidateBackground();
        invalidateOptionsMenu();
    }

    private void setupActionBar() {
        this.actionBar.setListNavigationCallbacks(this, this);

    }

    private void setupNavigationDrawer(Bundle savedInstanceState) {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState == null) {
            if (!this.getIntent().hasExtra(ExamplesApplicationContext.INTENT_SECTION_ID)) {
                String selectedSection = mNavigationDrawerFragment.selectedSection() == null ? NavigationDrawerFragment.NAV_DRAWER_SECTION_HOME : mNavigationDrawerFragment.selectedSection();
                this.addFragmentForSection(selectedSection, false);
            }
        } else {
            Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.container);
            this.manageTipsPresenter(currentFragment);
        }
    }

    private void invalidateActionbarTitle() {
        Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment instanceof AboutFragment) {
            this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            this.actionBar.setTitle(R.string.aboutString);
        } else if (currentFragment instanceof FavoritesFragment) {
            this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            this.actionBar.setTitle(R.string.favoritesStringPascalCase);
        } else {
            this.actionBar.setTitle(null);
            this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            this.actionBar.setSelectedNavigationItem(this.lastNavigationItemIndex);
        }
    }


    private void invalidateBackground() {
        Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment instanceof AboutFragment || mNavigationDrawerFragment.isDrawerOpen())
            currentBgColor.setColor(secondaryBgColor());
        else
            currentBgColor.setColor(primaryBgColor());
    }

    private int primaryBgColor() {
        return Color.rgb(redFrom, greenFrom, blueFrom);
    }

    private int secondaryBgColor() {
        return Color.rgb(redTo, greenTo, blueTo);
    }

    private int calculateCurrentColor(float step) {
        return Color.rgb(
                calculateCurrentStep(redFrom, redTo, step),
                calculateCurrentStep(greenFrom, greenTo, step),
                calculateCurrentStep(blueFrom, blueTo, step)
        );
    }

    private int calculateCurrentStep(int from, int to, float step) {
        int max = Math.max(from, to);
        int min = Math.min(from, to);
        int calculatedStep = ((int) ((max - min) * step));
        if (from > to)
            return from - calculatedStep;
        else
            return from + calculatedStep;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        FrameLayout root = (FrameLayout) View.inflate(app, R.layout.actionbar_list_item, null);
        TextView text = (TextView) root.findViewById(R.id.actionBarTextView);
        text.setText(this.getItem(position).toString());
        return root;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return getResources().getString(R.string.allControlsStringPascalCase);
        } else if (position == 1) {
            return getResources().getString(R.string.actionbar_section_highlighted);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView root = (TextView) View.inflate(app, R.layout.actionbar_spinner_main_item, null);
        root.setText(this.getItem(position).toString());
        return root;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String getScreenName() {
        return TrackedApplication.HOME_SCREEN;
    }

    @Override
    public HashMap<String, Object> getAdditionalParameters() {
        return null;
    }
}
