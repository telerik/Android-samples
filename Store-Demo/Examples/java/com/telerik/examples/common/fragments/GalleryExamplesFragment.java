package com.telerik.examples.common.fragments;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.telerik.examples.R;
import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.primitives.ExampleViewPagerBase;
import com.telerik.examples.primitives.ScrollingTab;
import com.telerik.examples.primitives.ScrollingTabView;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;
import com.telerik.examples.viewmodels.ExampleSourceModel;
import com.telerik.examples.viewmodels.GalleryExample;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Locale;

public class GalleryExamplesFragment extends ExampleFragmentBase implements ActionBar.TabListener, ActionBar.OnNavigationListener {

    private ArrayList<Example> examples;
    private ExampleViewPagerBase viewPager;
    private ScrollingTabView tabView;
    protected ExamplesApplicationContext app;
    private ExampleGroup selectedControl;
    private Example selectedExample;
    private String exampleId;
    private String controlId;

    public GalleryExamplesFragment() {

    }

    public void setControlId(String id){
        this.controlId = id;
    }

    public void setExampleId(String id){
        this.exampleId = id;
    }

    @Override
    public void unloadExample() {
        super.unloadExample();
        this.getView().setWillNotDraw(true);
    }

    @Override
    public ExampleSourceModel getSourceCodeModel() {
        ExampleFragmentBase example = (ExampleFragmentBase) ((FragmentStatePagerAdapter) viewPager.getAdapter()).getItem(viewPager.getCurrentItem());
        return example.getSourceCodeModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = null;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            result = inflater.inflate(R.layout.fragment_examples, container, false);
            viewPager = (ExampleViewPagerBase) result.findViewById(R.id.pager);
            viewPager.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            result = inflater.inflate(R.layout.fragment_examples_horizontal, container, false);
            viewPager = (ExampleViewPagerBase) result.findViewById(R.id.pager);
            viewPager.setOrientation(LinearLayout.VERTICAL);
        }

        this.tabView = (ScrollingTabView) result.findViewById(R.id.scrollingTabView);
        this.app = (ExamplesApplicationContext) getActivity().getApplicationContext();

        if (savedInstanceState == null) {
            this.selectedControl = this.app.findControlById(this.controlId);
            this.selectedExample = this.app.findExampleById(this.selectedControl, this.exampleId);
        }else{
            this.selectedControl = this.app.findControlById(savedInstanceState.getString(ExamplesApplicationContext.INTENT_CONTROL_ID));
            this.selectedExample = this.app.findExampleById(this.selectedControl, savedInstanceState.getString(ExamplesApplicationContext.INTENT_EXAMPLE_ID));
        }

        this.examples = ((GalleryExample) this.selectedExample).getExamples();

        viewPager = (ExampleViewPagerBase) result.findViewById(R.id.pager);

        final ExamplesFragmentAdapter adapter = new ExamplesFragmentAdapter(((this.getActivity())).getSupportFragmentManager(), this.examples);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabView.setTabSelected(position);
            }
        });


        this.tabView.removeAllTabs();
        for (int i = 0; i < adapter.getCount(); i++) {
            ScrollingTab tab = new ScrollingTab(this.tabView);
            BitmapDrawable icon = (BitmapDrawable) getResources().getDrawable(adapter.getImage(i));
            icon.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            tab.setIcon(icon);
            tab.setTabListener(this);
            this.tabView.addTab(tab, false);
        }
        tabView.setTabSelected(0);
        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ExamplesApplicationContext.INTENT_CONTROL_ID, this.selectedControl.getFragmentName());
        outState.putString(ExamplesApplicationContext.INTENT_EXAMPLE_ID, this.selectedExample.getFragmentName());
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
    public void onExampleSuspended() {
        super.onExampleSuspended();
        ExampleFragmentBase current = ((ExamplesFragmentAdapter) this.viewPager.getAdapter()).currentFragment;
        if (current != null) {
            current.onExampleSuspended();
        }
    }

    @Override
    public void onExampleResumed() {
        super.onExampleResumed();

        ExampleFragmentBase current = ((ExamplesFragmentAdapter) this.viewPager.getAdapter()).currentFragment;
        if (current != null) {
            current.onExampleResumed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        viewPager.setCurrentItem(itemPosition);
        return true;
    }

    public class ExamplesFragmentAdapter extends FragmentStatePagerAdapter {
        ExampleFragmentBase currentFragment;
        private ArrayList<Example> examples;

        public ExamplesFragmentAdapter(FragmentManager fm, ArrayList<Example> examples) {
            super(fm);
            this.examples = examples;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);

            if (this.currentFragment != null) {
                this.currentFragment.onHidden();
            }

            this.currentFragment = (ExampleFragmentBase) object;
            this.currentFragment.onVisualized();
        }

        @Override
        public Fragment getItem(int position) {
            Example currentExample = this.examples.get(position);
            Fragment instance;
            try {
                Class<?> fragmentClass = Class.forName(currentExample.getFragmentName());
                Constructor<?> fragmentConstructor = fragmentClass.getConstructor();
                instance = (Fragment) fragmentConstructor.newInstance();
                return instance;
            } catch (Throwable e) {
                app.trackException(e);
                return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if (selectedExample instanceof GalleryExample) {
                return ((GalleryExample) selectedExample).getExamples().size();
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return ((GalleryExample) selectedExample).getExamples().get(position).getHeaderText().toUpperCase(l);
        }

        public int getImage(int position) {
            return app.getDrawableResource(((GalleryExample) selectedExample).getExamples().get(position).getImage());
        }
    }
}

