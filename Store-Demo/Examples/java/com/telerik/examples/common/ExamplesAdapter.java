package com.telerik.examples.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleGroupListFragment;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;
import com.telerik.examples.viewmodels.GalleryExample;

import java.util.ArrayList;
import java.util.List;

public class ExamplesAdapter extends ArrayAdapter<Example> {

    public static final String FAVOURITES_FILTER_KEY = "favourites";
    public static final String ALL_FILTER_KEY = "all";
    public static final String HIGHLIGHTED_FILTER_KEY = "highlighted";
    public static final String NOTHING_FILTER_KEY = "nothing";
    public static final String HIGHLIGHTED_CONTROLS_FILTER_KEY = "highlighted_controls_only";
    public static final String HIGHLIGHTED_EXAMPLES_FILTER_KEY = "highlighted_examples_only";

    protected final ExamplesApplicationContext app;
    private final List<Example> source;
    private List<Example> filteredList;
    protected int listMode;
    protected final View.OnClickListener callBack;
    private ExamplesFilter filter;
    private boolean showControlBadge;

    public ExamplesAdapter(ExamplesApplicationContext context, List<Example> source, int listMode, View.OnClickListener callBack, boolean showControlBadge) {
        super(context, R.layout.example_list_item, source);
        this.callBack = callBack;
        this.listMode = listMode;
        this.showControlBadge = showControlBadge;
        this.app = context;
        this.filteredList = this.source = source;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView =
                this.listMode == ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE ?
                        inflater.inflate(R.layout.example_list_item, parent, false) :
                        inflater.inflate(R.layout.example_wrap_list_item, parent, false);
        rowView.setOnClickListener(this.callBack);
        this.bindView(rowView, this.filteredList.get(position));
        return rowView;
    }

    public int getCount() {
        return this.filteredList.size();
    }

    public void setListMode(int listMode) {
        if (this.listMode != listMode) {
            this.listMode = listMode;
            this.notifyDataSetChanged();
        }
    }

    public Example getItem(int position) {
        return this.filteredList.get(position);
    }

    public long getItemId(int position) {
        return this.filteredList.get(position).hashCode();
    }

    private void bindView(View rootView, Example example) {

        final TextView titleTextView = (TextView) rootView.findViewById(R.id.controlName);
        final String title = example.getHeaderText();
        titleTextView.setText(title);
        if (this.listMode == ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE) {
            // set description
            final TextView descriptionTextView = (TextView) rootView.findViewById(R.id.controlDescription);
            descriptionTextView.setText(example.getDescriptionText());
        }
        // get control image
        final ImageView controlImage = (ImageView) rootView.findViewById(R.id.controlImage);
        controlImage.setImageResource(app.getDrawableResource(example.getImage()));
        rootView.setTag(example);

        ImageButton showMenuBtn = (ImageButton) rootView.findViewById(R.id.showItemMenu);
        showMenuBtn.setOnClickListener(this.callBack);
        showMenuBtn.setTag(example);

        ImageView badgeView = (ImageView) rootView.findViewById(R.id.badgeImage);
        ImageView badgeOverlay = (ImageView) rootView.findViewById(R.id.badgeOverlay);

        if (!example.getIsNew() && showControlBadge && (!(example instanceof ExampleGroup) || (example instanceof GalleryExample))) {
            badgeOverlay.setVisibility(View.VISIBLE);
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setImageResource(R.drawable.rhomb_green);
            badgeOverlay.setImageResource(app.getDrawableResource(example.getParentControl().getLogoResource()));
        } else if (example.getIsNew()) {
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setImageResource(R.drawable.rhomb_new);
            badgeOverlay.setVisibility(View.INVISIBLE);
        } else {
            badgeOverlay.setVisibility(View.INVISIBLE);
            badgeView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public Filter getFilter() {
        if (this.filter == null) {
            this.filter = new ExamplesFilter();
        }
        return this.filter;
    }

    private class ExamplesFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Example> filteredResults = new ArrayList<Example>();
            if (constraint == ExamplesAdapter.NOTHING_FILTER_KEY) {
                results.count = 0;
                results.values = filteredResults;
            } else if (constraint != ExamplesAdapter.ALL_FILTER_KEY) {
                this.filterExamples(filteredResults, source, constraint);
                results.count = filteredResults.size();
                results.values = filteredResults;
            } else {
                results.count = source.size();
                results.values = source;
            }
            return results;
        }

        private void filterExamples(List<Example> result, List<Example> originalList, CharSequence filter) {
            for (Example example : originalList) {
                if (filter == ALL_FILTER_KEY ||
                        filter == FAVOURITES_FILTER_KEY && app.isExampleInFavourites(example) || (
                        example.getIsHighlighted() &&
                                (filter == HIGHLIGHTED_FILTER_KEY ||
                                        filter == HIGHLIGHTED_CONTROLS_FILTER_KEY && (example instanceof ExampleGroup && !(example instanceof GalleryExample)) ||
                                        filter == HIGHLIGHTED_EXAMPLES_FILTER_KEY && (!(example instanceof ExampleGroup) && !(example instanceof GalleryExample))))) {
                    result.add(example);
                }

                if (example instanceof ExampleGroup && !(example instanceof GalleryExample)) {
                    ExampleGroup group = (ExampleGroup) example;
                    this.filterExamples(result, group.getExamples(), filter);
                }
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Example>) results.values;
            notifyDataSetChanged();
        }
    }
}