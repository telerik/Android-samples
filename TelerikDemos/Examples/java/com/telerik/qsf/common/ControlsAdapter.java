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

public class ControlsAdapter extends ArrayAdapter<ExampleGroup> implements Filterable {
    private final Context context;
    private ArrayList<ExampleGroup> controlsList, originalControlsList;
    private Filter controlsFilter;

    public ControlsAdapter(Context context, ArrayList<ExampleGroup> controls) {
        super(context, R.layout.controls_list_item, controls);
        this.context = context;
        this.controlsList = controls;
        this.originalControlsList = controls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.controls_list_item, parent, false);
        ExampleGroup control = controlsList.get(position);
        // set title
        TextView titleTextView = (TextView) rowView.findViewById(R.id.controlName);
        String title = control.getHeaderText();
        titleTextView.setText(title);
        // set description
        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.controlDescription);
        descriptionTextView.setText(control.getDescriptionText());

        // get control image
        ImageView controlImage = (ImageView) rowView.findViewById(R.id.controlImage);
        controlImage.setImageResource(control.getImage());
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

    public void resetData() {
        controlsList = originalControlsList;
    }

    public int getCount() {
        return controlsList.size();
    }

    public ExampleGroup getItem(int position) {
        return controlsList.get(position);
    }

    public long getItemId(int position) {
        return controlsList.get(position).hashCode();
    }

    @Override
    public Filter getFilter() {
        if (controlsFilter == null)
            controlsFilter = new ControlsFilter();

        return controlsFilter;
    }

    public void clearFilter() {
        controlsFilter = null;
    }

    private class ControlsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == "all") {
                // No filter implemented we return all the list
                results.values = originalControlsList;
                results.count = originalControlsList.size();
            } else if (constraint == "highlighted") {
                // We perform filtering operation
                List<ExampleGroup> exampleGroups = new ArrayList<ExampleGroup>();

                for (ExampleGroup exampleGroup : controlsList) {
                    if (exampleGroup.getIsHighlighted()) {
                        exampleGroups.add(exampleGroup);
                    }
                    for (Example example : exampleGroup.getExamples()) {
                        if (example.getIsHighlighted()) {
                            ExampleGroup dummyExampleGroup = copyFromExample(example);
                            dummyExampleGroup.isExample = true;
                            exampleGroups.add(dummyExampleGroup);
                        }

                    }
                }

                results.values = exampleGroups;
                results.count = exampleGroups.size();

            }
            return results;
        }

        private ExampleGroup copyFromExample(Example example) {
            ExampleGroup exampleGroup = new ExampleGroup();
            exampleGroup.setHeaderText(example.getHeaderText());
            exampleGroup.setDescriptionText(example.getDescriptionText());
            exampleGroup.setIsHighlighted(example.getIsHighlighted());
            exampleGroup.setIsNew(example.getIsNew());
            return exampleGroup;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                controlsList = (ArrayList<ExampleGroup>) results.values;
                notifyDataSetChanged();
            }

        }
    }
}