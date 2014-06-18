package com.telerik.examples.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;

import java.util.ArrayList;
import java.util.List;

public class ControlsAdapter extends ArrayAdapter<Example> implements Filterable {

    private ExamplesApplicationContext app;
    private final Context context;
    private final List<Example> originalControlsList;

    private List<Example> controlsList;
    private Filter controlsFilter;

    public ControlsAdapter(Context context, List<Example> controls) {
        super(context, R.layout.example_list_item, controls);
        this.context = context;
        this.app = (ExamplesApplicationContext) context.getApplicationContext();
        this.controlsList = controls;
        this.originalControlsList = controls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.example_list_item, parent, false);
        final Example control = controlsList.get(position);
        // set title
        final TextView titleTextView = (TextView) rowView.findViewById(R.id.controlName);
        titleTextView.setText(control.getHeaderText());
        // set description
        final TextView descriptionTextView = (TextView) rowView.findViewById(R.id.controlDescription);
        descriptionTextView.setText(control.getDescriptionText());

        // get control image
        final ImageView controlImage = (ImageView) rowView.findViewById(R.id.controlImage);

        controlImage.setImageResource(app.getDrawableResource(control.getImage()));
        return rowView;
    }

    public void resetData() {
        controlsList = originalControlsList;
    }

    public int getCount() {
        return controlsList.size();
    }

    public Example getItem(int position) {
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
            final FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == "all") {
                // No filter implemented we return all the list
                results.values = originalControlsList;
                results.count = originalControlsList.size();
            } else if (constraint == "highlighted") {
                // We perform filtering operation
                final List<Example> exampleGroups = new ArrayList<Example>();
                this.filterExamples(exampleGroups, originalControlsList, constraint);
                results.values = exampleGroups;
                results.count = exampleGroups.size();
            }
            return results;
        }

        private void filterExamples(List<Example> result, List<Example> originalList, CharSequence filter) {
            for (Example example : originalList) {
                if (example.getIsHighlighted()) {
                    result.add(example);
                }
                if (example instanceof ExampleGroup) {
                    ExampleGroup group = (ExampleGroup) example;
                    this.filterExamples(result, group.getExamples(), filter);
                }
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                controlsList = (List<Example>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}