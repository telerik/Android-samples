package com.telerik.qsf.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.qsf.R;
import com.telerik.qsf.viewmodels.Example;
import com.telerik.qsf.viewmodels.ExampleGroup;

import java.util.ArrayList;
import java.util.List;

public class ExamplesAdapter extends ArrayAdapter<Example> {
    private final Context context;
    private ArrayList<Example> examples;

    public ExamplesAdapter(Context context, ArrayList<Example> examples) {
        super(context, R.layout.controls_list_item, examples);
        this.context = context;
        this.examples = examples;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.example_list_item, parent, false);
        Example example = examples.get(position);
        // set title
        TextView titleTextView = (TextView) rowView.findViewById(R.id.controlName);
        String title = example.getHeaderText();
        titleTextView.setText(title);
        // set description
        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.controlDescription);
        descriptionTextView.setText(example.getDescriptionText());

        // get control image
        ImageView controlImage = (ImageView) rowView.findViewById(R.id.controlImage);
        controlImage.setImageResource(example.getImage());
        // get badge image
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

    public int getCount() {
        return examples.size();
    }

    public Example getItem(int position) {
        return examples.get(position);
    }

    public long getItemId(int position) {
        return examples.get(position).hashCode();
    }
}