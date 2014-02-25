package com.telerik.qsf.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.telerik.qsf.R;
import com.telerik.qsf.viewmodels.ExampleGroup;

import java.util.ArrayList;

public class NavigationDrawerControlsAdapter extends ArrayAdapter<ExampleGroup> {
    private final Context context;
    private ArrayList<ExampleGroup> controlsList;

    public NavigationDrawerControlsAdapter(Context context, ArrayList<ExampleGroup> controls) {
        super(context, R.layout.nav_drawer_controls_list_item, controls);
        this.context = context;
        this.controlsList = controls;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.nav_drawer_controls_list_item, parent, false);
        ExampleGroup control = controlsList.get(position);

        // set title
        TextView titleTextView = (TextView) rowView.findViewById(R.id.controlName);
        String title = control.getHeaderText();
        titleTextView.setText(title);
//        // set description
//        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.controlName);
//        descriptionTextView.setText(control.getDescriptionText());
//
//        // get control image
//        ImageView controlImage = (ImageView) rowView.findViewById(R.id.controlImage);
//        // get badge image
//        ImageView badgeImage = (ImageView) rowView.findViewById(R.id.badgeImage);
//
//        Boolean isHighlighted = control.getIsHighlighted();
//        Boolean isNew = control.getIsNew();
//
//        if (isHighlighted) {
//            if (control.isExample) {
//                controlImage.setImageResource(R.drawable.example_logo);
//                // set badge based on the parent control
//                badgeImage.setImageResource(R.drawable.badge_control);
//            } else {
//                // means it is a control
//                // ToDo: Set the appropriate image based on the control
//                // controlImage.setImageResource(R.drawable.chart);
//                if (isNew) {
//                    badgeImage.setVisibility(View.VISIBLE);
//                    badgeImage.setImageResource(R.drawable.badge_new);
//                } else {
//                    badgeImage.setVisibility(View.INVISIBLE);
//                }
//            }
//        } else {
//            // means it is a control
//            // ToDo: Set the appropriate image based on the control
//            // controlImage.setImageResource(R.drawable.chart);
//            if (isNew) {
//                badgeImage.setVisibility(View.VISIBLE);
//                badgeImage.setImageResource(R.drawable.badge_new);
//            } else {
//                badgeImage.setVisibility(View.INVISIBLE);
//            }
//        }

        return rowView;
    }
}