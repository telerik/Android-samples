package com.telerik.examples.common.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.examples.R;

public class AboutFragment extends Fragment implements NavigationDrawerFragment.SectionInfoProvider {

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
        // Documentation tracking link. This will test how many developers view the documentation on their mobile device.
        final TextView documentationLinkTextView = (TextView) this.rootView.findViewById(R.id.documentationTrackingLink);
        documentationLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://docs.telerik.com/devtools/android/introduction?utm_medium=mobile-app&utm_source=AndroidDemoApp&utm_campaign=dt-android-examples-app&utm_content=documentation-link"));
                startActivity(browserIntent);
            }
        });
        // Product page tracking link. This will test how many developers browse the product page on their mobile device.
        final TextView productPageLinkTextView = (TextView) this.rootView.findViewById(R.id.productPageTrackingLink);
        productPageLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.telerik.com/android-ui?utm_medium=mobile-app&utm_source=AndroidDemoApp&utm_campaign=dt-android-examples-app&utm_content=product-link"));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public String getSectionName() {
        return NavigationDrawerFragment.NAV_DRAWER_SECTION_ABOUT;
    }
}
