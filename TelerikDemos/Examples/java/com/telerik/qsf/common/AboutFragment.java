package com.telerik.qsf.common;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.qsf.R;

public class AboutFragment extends BaseFragment {

    private final static String title = "About";

    private static AboutFragment instance;
    private App app;
    View rootView;
    private String descriptionText;
    private TextView descriptionTextView;

    public AboutFragment() {
        super(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_about, container, false);
        return this.rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.invalidateBackground();
        this.app = (App) mainActivity.getApplicationContext();
        String description = "Telerik UI Examples shows scenarios developers can achieve using Telerik UI for Android - <b>a fully customizable native development toolset for building enterprise and consumer apps.";
        this.descriptionTextView = (TextView) this.rootView.findViewById(R.id.descriptionTextView);
        this.descriptionTextView.setText(Html.fromHtml(description));
        // INSERT ANALYTICS LOGIC HERE
    }

    public static AboutFragment newInstance() {
        if (instance == null)
            instance = new AboutFragment();

        return instance;
    }
}
