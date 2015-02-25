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
public class TemplatesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        //Bundle args = getArguments();
        //((TextView) rootView.findViewById(android.R.id.text1)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
        return inflater.inflate(R.layout.fragment_templates, container, false);
    }
}