package com.telerik.examples.examples.panels;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScrollViewFragment extends ExampleFragmentBase {


    public ScrollViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_scroll_view, container, false);

        return root;
    }


}
