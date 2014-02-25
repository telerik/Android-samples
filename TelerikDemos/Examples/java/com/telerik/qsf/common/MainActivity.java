package com.telerik.qsf.common;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.telerik.qsf.R;
import com.telerik.qsf.viewmodels.ExampleGroup;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ActionBar.OnNavigationListener, TransitionHandler {

    private static final String DEFAULT_TITLE = "Telerik Examples";

    private ColorDrawable currentBgColor;

    private int redFrom;
    private int redTo;

    private int greenFrom;
    private int greenTo;

    private int blueFrom;
    private int blueTo;

    private ActionBar actionBar;
    private String currentActionbarTitle = DEFAULT_TITLE;
    private App app;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private final static String DRAWER_CLICKED_ITEM_CATEGORY_KEY = "Drawer item clicked";
    private BaseFragment activeFragment;

    private Drawable artificialPadding;
    private Drawable telerikLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getActionBar();
        ColorDrawable bgColorPrimary = new ColorDrawable(getResources().getColor(R.color.primary_title_background));
        ColorDrawable bgColorSecondary = new ColorDrawable(getResources().getColor(R.color.secondary_title_background));
        currentBgColor = bgColorPrimary;

        actionBar.setBackgroundDrawable(currentBgColor);

        redFrom = Color.red(bgColorPrimary.getColor());
        redTo = Color.red(bgColorSecondary.getColor());

        greenFrom = Color.green(bgColorPrimary.getColor());
        greenTo = Color.green(bgColorSecondary.getColor());

        blueFrom = Color.blue(bgColorPrimary.getColor());
        blueTo = Color.blue(bgColorSecondary.getColor());
        artificialPadding = getResources().getDrawable(R.drawable.artificial_padding);
        telerikLogo = getResources().getDrawable(R.drawable.telerik_logo);
        this.app = (App) this.getApplicationContext();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();

        this.app.savePreferences();
    }

    private void setupActionBar() {
        enableLogo(true);
        actionBar.setTitle(currentActionbarTitle);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    private void setupNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerSectionSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BaseFragment newFragment;
        switch (position) {
            case 0:
                newFragment = ControlsFragment.newInstance();
                break;
            case 1:
                newFragment = FavoritesFragment.newInstance();
                break;
            case 2:
                newFragment = AboutFragment.newInstance();
                break;
            default:
                newFragment = HomeFragment.newInstance();
        }

        this.activeFragment = newFragment;
        fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();
        this.app.trackFeature(DRAWER_CLICKED_ITEM_CATEGORY_KEY, ((Object) newFragment).getClass().getName());
    }

    @Override
    public void onNavigationDrawerExampleGroupSelected(int position) {
        this.app.selectExampleGroup(position);
    }

    @Override
    public void onNavigationDrawerOpened() {
        enableLogo(true);
        /*if (!(activeFragment instanceof AboutFragment))
        transitionalBackground.startTransition(BACKGROUND_TRANSITION_DURATION);*/
        setActionBarTitle(DEFAULT_TITLE);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    public void onNavigationDrawerClosed() {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        invalidateLogo();
        invalidateActionbarTitle();
        //supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!(this.activeFragment instanceof AboutFragment)) {
            getMenuInflater().inflate(R.menu.main, menu);
            setupActionBar();
            setupNavigationDrawer();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            mNavigationDrawerFragment.selectNavigationDrawerSection(2);
            enableLogo(false);
        }
        //supportInvalidateOptionsMenu();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
// THIS FUNCTIONALITY WILL BE USED IN THE NEXT VERSION OF THE QSF.
//        if (this.controlsFragment == null)
//            return false;
//
//        if (itemPosition == 0)
//            this.controlsFragment.showAll();
//        if (itemPosition == 1)
//            this.controlsFragment.showHighlighted();

        return true;
    }

    public void showExampleGroup(int position) {
        this.app.selectExampleGroup(position);

        Intent intent = new Intent(this, ExamplesActivity.class);
        startActivity(intent);
    }

    public void showExampleGroup(ExampleGroup exampleGroup) {
        this.app.selectExampleGroup(exampleGroup);

        Intent intent = new Intent(this, ExamplesActivity.class);
        startActivity(intent);
    }

    private void setActionBarTitle(String title) {
        if (title != null && !currentActionbarTitle.equals(title))
            currentActionbarTitle = title;
            actionBar.setTitle(currentActionbarTitle);
    }

    public void invalidateActionbarTitle() {
        setActionBarTitle(activeFragment.title());
    }

    private void enableLogo(boolean enable) {
        if (enable)
            actionBar.setLogo(telerikLogo);
        else
            actionBar.setLogo(artificialPadding);
    }

    public void invalidateLogo() {
        if (activeFragment instanceof ControlsFragment)
            enableLogo(true);
        else
            enableLogo(false);
    }

    @Override
    public void updateTransition(float step) {
        if (!(activeFragment instanceof AboutFragment))
            currentBgColor.setColor(calculateCurrentColor(step));
    }

    public void invalidateBackground() {
        if (activeFragment instanceof AboutFragment)
            currentBgColor.setColor(Color.rgb(redTo, greenTo, blueTo));
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
        int calculatedstep = ((int) ((max - min) * step));
        if (from > to)
            return from - calculatedstep;
        else
            return from + calculatedstep;
    }
}
