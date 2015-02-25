package com.telerik.examples.common.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.examples.R;

/**
 * Instances of this class are fragments representing a single object in our collection.
 */
public class AppsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        return inflater.inflate(R.layout.fragment_apps, container, false);
    }
}