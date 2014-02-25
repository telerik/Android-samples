package com.telerik.qsf.common;

import android.app.Activity;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected MainActivity mainActivity;
    private final String fragmentTitle;

    public BaseFragment(String title) {
        fragmentTitle = title;
    }

    public String title() {
        return fragmentTitle;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (MainActivity) activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity.invalidateActionbarTitle();
        mainActivity.invalidateLogo();
    }
}
