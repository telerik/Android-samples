package com.telerik.qsf.common;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.telerik.qsf.R;
import com.telerik.qsf.viewmodels.Example;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Locale;

public class ExamplesActivity extends FragmentActivity implements ActionBar.TabListener, ActionBar.OnNavigationListener {

    private final static String EXAMPLES_CATEGORY_NAME = "Examples";
    private final static String FAVOURITE_CATEGORY_ADDED = "Favourites Added";
    private final static String FAVOURITE_CATEGORY_REMOVED = "Favourites Removed";
    private final static String EXAMPLE_INFO_CATEGORY = "Example info";

    private ViewPager viewPager;
    protected App app;
    private ArrayList<Example> examples;

    public ExamplesActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examples);
        this.app = (App) this.getApplicationContext();
        this.examples = app.selectedGroup().getExamples();
        final ActionBar actionBar = this.getActionBar();

        // workaround for adding padding to the left of the actionbar title
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff252939));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        String title = app.selectedGroup().getHeaderText();
        actionBar.setTitle(title);
        app.trackFeature(EXAMPLES_CATEGORY_NAME, title);

        viewPager = (ViewPager) findViewById(R.id.pager);
        ExamplesFragmentAdapter adapter = new ExamplesFragmentAdapter(this.getSupportFragmentManager(), this.examples);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        if (adapter.getCount() == 1) {
            // Don't show tabs if there is only one example.
            return;
        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            CustomArrayAdapter spinnerAdapter = new CustomArrayAdapter(this, R.layout.spinner_selected_list_item, R.id.spinnerTextView, R.layout.spinner_dropdown_list_item, this.examples);
            spinnerAdapter.setDropDownViewResource(R.layout.spinner_selected_list_item);
            actionBar.setListNavigationCallbacks(spinnerAdapter, this);
        } else {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.removeAllTabs();
            for (int i = 0; i < adapter.getCount(); i++) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setIcon(getResources().getDrawable(adapter.getImage(i)))
                                .setTabListener(this));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.app.isExampleGroupInFavorites(this.app.selectedGroup())) {
            getMenuInflater().inflate(R.menu.remove_from_favorites, menu);
        } else {
            getMenuInflater().inflate(R.menu.add_to_favorites, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        final String fragmentName = this.app.selectedGroup().getFragmentName();
        final String fragmentNameToTrack = app.extractClassName(fragmentName, 3);

        if (id == R.id.action_add_to_favorites) {
            this.app.getFavorites().add(fragmentName);
            this.app.trackFeature(FAVOURITE_CATEGORY_ADDED, fragmentNameToTrack);
        } else if (id == R.id.action_remove_from_favorites) {
            this.app.getFavorites().remove(fragmentName);
            this.app.trackFeature(FAVOURITE_CATEGORY_REMOVED, fragmentNameToTrack);
        } else if (id == R.id.action_view_example_info) {
            this.app.trackFeature(EXAMPLE_INFO_CATEGORY, fragmentNameToTrack);
            Intent intent = new Intent(this, ExampleInfoActivity.class);
            startActivity(intent);
        }

        if (id == android.R.id.home) {
            this.finish();
        }

        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        viewPager.setCurrentItem(itemPosition);
        return true;
    }

    public class ExamplesFragmentAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Example> examples;

        public ExamplesFragmentAdapter(FragmentManager fm, ArrayList<Example> examples) {
            super(fm);
            this.examples = examples;
        }

        @Override
        public Fragment getItem(int position) {
            Example currentExample = this.examples.get(position);
            Fragment instance = null;
            try {
                Class fragmentClass = Class.forName(currentExample.getFragmentName());
                Constructor fragmentConstructor = fragmentClass.getConstructor();
                instance = (Fragment) fragmentConstructor.newInstance();
            } catch (Throwable e) {
                app.trackException(e);
            } finally {
                return instance;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return app.selectedGroup().getExamples().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return app.selectedGroup().getExamples().get(position).getHeaderText().toUpperCase(l);
        }

        public int getImage(int position) {
            return app.selectedGroup().getExamples().get(position).getImage();
        }
    }

    public class CustomArrayAdapter extends ArrayAdapter<Example> {

        Context context;
        int selectedLayoutId, dropDownLayoutId;
        ArrayList<Example> data = null;

        public CustomArrayAdapter(Context context, int selectedLayoutResourceId, int textViewResourceId, int dropDownLayoutResourceId, ArrayList<Example> data) {
            super(context, selectedLayoutResourceId, textViewResourceId, data);
            this.selectedLayoutId = selectedLayoutResourceId;
            this.dropDownLayoutId = dropDownLayoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ImageView holder = null;
            Example example = this.data.get(position);
            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(selectedLayoutId, parent, false);
            }
            holder = (ImageView) row.findViewById(R.id.spinnerImageView);

            holder.setImageResource(example.getImage());

            return row;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ImageView holder = null;
            Example example = this.data.get(position);
            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(dropDownLayoutId, parent, false);
            }
            holder = (ImageView) row.findViewById(R.id.spinnerImageView);

            holder.setImageResource(example.getImage());

            return row;
        }
    }
}

