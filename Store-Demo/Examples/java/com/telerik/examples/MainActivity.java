package com.telerik.examples;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.TrackedApplication;
import com.telerik.examples.common.contracts.TrackedActivity;
import com.telerik.examples.common.contracts.TransitionHandler;
import com.telerik.examples.common.fragments.AboutFragment;
import com.telerik.examples.common.fragments.ControlsFragment;
import com.telerik.examples.common.fragments.ExampleGroupListFragment;
import com.telerik.examples.common.fragments.FavoritesFragment;
import com.telerik.examples.common.fragments.NavigationDrawerFragment;
import com.telerik.examples.primitives.TipsPresenter;
import com.telerik.examples.viewmodels.ExampleGroup;

import java.util.HashMap;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ActionBar.OnNavigationListener, TransitionHandler, SpinnerAdapter, TrackedActivity, FragmentManager.OnBackStackChangedListener {

    private ColorDrawable currentBgColor;

    private HashMap<Class<?>, Fragment> fragmentsCache = new HashMap<Class<?>, Fragment>();

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

        Resources resources = getResources();
        ColorDrawable bgColorPrimary = new ColorDrawable(resources.getColor(R.color.primary_title_background));
        ColorDrawable bgColorSecondary = new ColorDrawable(resources.getColor(R.color.secondary_title_background));
        currentBgColor = bgColorPrimary;

        actionBar = getActionBar();

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

        setContentView(R.layout.activity_main);
        this.tipsPresenter = Util.getLayoutPart(this, R.id.tipsPresenter, TipsPresenter.class);

        setupNavigationDrawer(savedInstanceState);
        setupActionBar();

        // Prevents the drawer from being opened at the time of the first launch.
        Util.getLayoutPart(this, R.id.drawer_layout, DrawerLayout.class).closeDrawer(Gravity.LEFT);
        this.getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        manageTipsPresenter(currentFragment);
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
            this.actionBar.setSelectedNavigationItem(this.lastNavigationItemIndex);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("section")) {
            int selectedSection = intent.getIntExtra("section", 0);
            Fragment newFragment = this.getSectionFragment(selectedSection);
            this.app.loadFragment(this, newFragment, R.id.container, true);
            this.manageTipsPresenter(newFragment);
        }
    }

    @Override
    public void onNavigationDrawerOpened() {
        this.invalidateActionbarTitle();
        this.app.trackFeature(TrackedApplication.HOME_CATEGORY, TrackedApplication.DRAWER_OPENED);
    }

    @Override
    public void onNavigationDrawerClosed() {
        this.invalidateActionbarTitle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        boolean isAbout = currentFragment instanceof AboutFragment;
        menu.findItem(R.id.action_about).setEnabled(!isAbout).setVisible(!isAbout);
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
        if (id == R.id.action_about) {
            mNavigationDrawerFragment.selectNavigationDrawerSection(2);
            return true;
        } else if (id == R.id.action_toggle_list_view) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (currentFragment instanceof ExampleGroupListFragment) {
                ExampleGroupListFragment typedFragment = (ExampleGroupListFragment) currentFragment;
                if (typedFragment.currentMode() == ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE) {
                    typedFragment.setViewMode(ExampleGroupListFragment.EXAMPLE_GROUP_GRID_MODE);
                    item.setIcon(R.drawable.example_group_toggle_layout_normal);
                    app.trackFeature(TrackedApplication.HOME_CATEGORY, TrackedApplication.ACTION_BAR_LIST_LAYOUT_TOGGLED + ": grid");
                } else {
                    typedFragment.setViewMode(ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE);
                    item.setIcon(R.drawable.example_group_toggle_layout_pressed);
                    app.trackFeature(TrackedApplication.HOME_CATEGORY, TrackedApplication.ACTION_BAR_LIST_LAYOUT_TOGGLED + ": list");
                }

                typedFragment.refreshFilters();

                return true;
            }
        } else if (id == R.id.action_settings) {
            this.app.showSettings(this);
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        //THIS FUNCTIONALITY WILL BE USED IN THE NEXT VERSION OF THE EXAMPLES.
        ControlsFragment controlsFragment = (ControlsFragment) this.getSupportFragmentManager().findFragmentById(R.id.container);

        if (controlsFragment == null) {
            return false;
        }

        this.lastNavigationItemIndex = itemPosition;
        if (itemPosition == 0) {
            controlsFragment.showAll();
            app.trackFeature(TrackedApplication.HOME_CATEGORY, TrackedApplication.ACTION_BAR_SPINNER_ITEM_SELECTED + ": all");
            return true;

        } else if (itemPosition == 1) {
            controlsFragment.showHighlighted();
            app.trackFeature(TrackedApplication.HOME_CATEGORY, TrackedApplication.ACTION_BAR_SPINNER_ITEM_SELECTED + ": highlighted");
            return true;
        }
        return false;
    }

    @Override
    public void onNavigationDrawerControlSelected(ExampleGroup control) {
        this.app.openExample(this, control);
        app.trackFeature(TrackedApplication.HOME_CATEGORY, TrackedApplication.DRAWER_CONTROL_SELECTED + ": " + control.getFragmentName());
    }

    @Override
    public void onNavigationDrawerSectionSelected(int position) {
        Fragment newFragment = this.getSectionFragment(position);

        if (newFragment == null) {
            return;
        }

        this.manageTipsPresenter(newFragment);

        this.app.loadFragment(this, newFragment, R.id.container, true);
        this.invalidateOptionsMenu();
        app.trackFeature(TrackedApplication.HOME_CATEGORY, TrackedApplication.DRAWER_SECTION_SELECTED + ": " + newFragment.getClass().getSimpleName());
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

    private Fragment getSectionFragment(int section) {
        Fragment newFragment;
        switch (section) {
            case 0:
                if (this.fragmentsCache.containsKey(ControlsFragment.class)) {
                    newFragment = this.fragmentsCache.get(ControlsFragment.class);
                    ((ControlsFragment) newFragment).refreshFilters();
                } else {
                    newFragment = new ControlsFragment();
                    this.fragmentsCache.put(ControlsFragment.class, newFragment);
                }
                break;
            case 1:
                if (this.fragmentsCache.containsKey(FavoritesFragment.class)) {
                    newFragment = this.fragmentsCache.get(FavoritesFragment.class);
                } else {
                    newFragment = new FavoritesFragment();
                    this.fragmentsCache.put(FavoritesFragment.class, newFragment);
                }
                break;
            case 2:
                if (this.fragmentsCache.containsKey(AboutFragment.class)) {
                    newFragment = this.fragmentsCache.get(AboutFragment.class);
                } else {
                    newFragment = new AboutFragment();
                    this.fragmentsCache.put(AboutFragment.class, newFragment);
                }
                break;
            default:
                return null;
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
        this.invalidateActionbar();
    }

    private void setupNavigationDrawer(Bundle savedInstanceState) {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState == null) {
            int selectedSection = mNavigationDrawerFragment.selectedSection() == -1 ? 0 : mNavigationDrawerFragment.selectedSection();
            Fragment newFragment = this.getSectionFragment(selectedSection);
            this.app.loadFragment(this, newFragment, R.id.container, false);
            this.manageTipsPresenter(newFragment);
        } else {
            Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.container);
            this.manageTipsPresenter(currentFragment);
        }
    }

    private void invalidateActionbarTitle() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            this.actionBar.setTitle(R.string.appName);
            this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        } else {
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
        TextView root = (TextView) View.inflate(app, R.layout.actionbar_list_item, null);
        root.setText(this.getItem(position).toString());
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
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String getCategoryName() {
        return TrackedApplication.HOME_CATEGORY;
    }
}
