package com.telerik.qsf.common;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.telerik.qsf.R;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public class ExampleGroupActivity extends FragmentActivity implements ActionBar.TabListener {
    private ViewPager viewPager;
    protected App app;
    LinkedHashMap<Class, String> sections = new LinkedHashMap<Class, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_example);
        this.app = (App) this.getApplicationContext();

        this.sections.put(ExampleGroupAllExamplesFragment.class, "All");
        this.sections.put(ExampleGroupFavoriteExamplesFragment.class, "Favorites");

        final ActionBar actionBar = this.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager = (ViewPager) findViewById(R.id.pager);
        ExampleGroupFragmentAdapter tabsAdapter = new ExampleGroupFragmentAdapter(getSupportFragmentManager(), sections);
        viewPager.setAdapter(tabsAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        actionBar.removeAllTabs();
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < tabsAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabsAdapter.getText(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class ExampleGroupFragmentAdapter extends FragmentStatePagerAdapter {
        private LinkedHashMap<Class, String> classes;

        public ExampleGroupFragmentAdapter(FragmentManager fm, LinkedHashMap<Class, String> classes) {
            super(fm);
            this.classes = classes;
        }

        @Override
        public Fragment getItem(int position) {
            Class current = (new ArrayList<Class>(this.classes.keySet())).get(position);
            Fragment instance = null;
            try {
                Constructor fragmentConstructor = current.getConstructor();
                instance = (Fragment) fragmentConstructor.newInstance();
            } finally {
                return instance;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return this.classes.size();
        }

        public String getText(int position) {
            Locale l = Locale.getDefault();
            String title = (new ArrayList<String>(this.classes.values())).get(position);
            return title.toUpperCase(l);
        }
    }
}
