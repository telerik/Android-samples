package com.telerik.qsf.common;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.telerik.qsf.R;

public class HomeFragment extends BaseFragment {

    private static HomeFragment instance;

    public HomeFragment() {
        super(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TabHost mTabHost = (TabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup();

        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

        HomeTabsAdapter mHomeTabsAdapter = new HomeTabsAdapter((FragmentActivity) getActivity(), mTabHost, mViewPager);

        mHomeTabsAdapter.addTab(mTabHost.newTabSpec("controls").setIndicator("CONTROLS"), ControlsFragment.class, null);
        mHomeTabsAdapter.addTab(mTabHost.newTabSpec("templates").setIndicator("TEMPLATES"), TemplatesFragment.class, null);
        mHomeTabsAdapter.addTab(mTabHost.newTabSpec("apps").setIndicator("APPS"), AppsFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        return rootView;
    }

    public static BaseFragment newInstance() {
        if (instance == null)
            instance = new HomeFragment();

        return instance;
    }
}
