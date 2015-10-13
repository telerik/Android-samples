package com.telerik.examples.examples.listview;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.RadListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewSlideItemFragment extends ExampleFragmentBase implements ListViewSlideFragment.OnDestinationSelectedListener {

    RadListView listView;
    ImageView image;
    ListViewSlideFragment.Destination destination;
    TextView titleView;
    TextView contentView;

    public ListViewSlideItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_view_slide_item, container, false);

        listView = (RadListView)rootView.findViewById(R.id.listView);

        if(savedInstanceState != null) {
            destination = savedInstanceState.getParcelable("currentAttraction");
        }

        if(destination == null) {
            return rootView;
        }

        ListViewAdapter adapter = new ListViewAdapter(destination.attractions);
        listView.setAdapter(adapter);

        View headerView = inflater.inflate(R.layout.listview_slideitem_header, listView, false);

        titleView = (TextView)headerView.findViewById(R.id.title);
        titleView.setText(destination.title);
        titleView.setTextColor(destination.color);

        contentView = (TextView)headerView.findViewById(R.id.content);
        contentView.setText(destination.info);

        TextView attractionsView = (TextView)headerView.findViewById(R.id.attractions);
        attractionsView.setTextColor(destination.color);

        listView.setHeaderView(headerView);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.getBackground().setColorFilter(destination.color, PorterDuff.Mode.SRC_IN);
        fab.setRippleColor(destination.color);

        View line = rootView.findViewById(R.id.line);
        line.setBackgroundColor(destination.color);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enquiry sent.", Toast.LENGTH_SHORT).show();
            }
        });

        image = (ImageView)rootView.findViewById(R.id.image);
        image.setImageResource(destination.src);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbar.setContentScrimColor(destination.color);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_arrow);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("currentAttraction", destination);
    }

    @Override
    public void onDestinationSelected(ListViewSlideFragment.Destination destination) {
        this.destination = destination;
    }
}
