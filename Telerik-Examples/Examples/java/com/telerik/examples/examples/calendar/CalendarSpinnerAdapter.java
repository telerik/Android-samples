package com.telerik.examples.examples.calendar;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.telerik.examples.R;

public class CalendarSpinnerAdapter implements SpinnerAdapter {

    private final Context context;
    private String[] spinnerOptions;

    public CalendarSpinnerAdapter(String[] options, Context context) {
        this.context = context;
        this.spinnerOptions = options;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView spinnerItem = (TextView) View.inflate(this.context, R.layout.calendar_range_spinner_item, null);
        spinnerItem.setText(spinnerOptions[position]);
        return spinnerItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView spinnerItem = (TextView) View.inflate(this.context, R.layout.calendar_range_spinner_header, null);
        spinnerItem.setText(spinnerOptions[position]);
        return spinnerItem;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return this.spinnerOptions.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
