package com.telerik.examples.common.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.examples.R;

public class AboutFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_about, container, false);
        return this.rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String descriptionText = getResources().getString(R.string.activity_about);
        final TextView descriptionTextView = (TextView) this.rootView.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(Html.fromHtml(descriptionText));
    }
}
