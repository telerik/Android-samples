package com.telerik.examples;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.examples.viewmodels.ExampleSourceModel;

public class ExampleSourceAdapter extends BaseAdapter {
    private ExampleSourceModel model;
    private LayoutInflater inflater;
    private Context context;

    public ExampleSourceAdapter(ExampleSourceModel model, Context context) {
        this.model = model;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.model.getDependencies().size();
    }

    @Override
    public Object getItem(int position) {
        return this.getFileName(position);
    }

    @Override
    public long getItemId(int position) {
        return this.getFileName(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = this.inflater.inflate(R.layout.view_code_list_item, null);
        TextView textView = (TextView) listItemView.findViewById(R.id.view_code_item_text);
        textView.setText(this.getFileName(position));

        ImageView tickMark = (ImageView) listItemView.findViewById(R.id.view_code_image);
        if (position == this.model.getCurrentIndex() && !(parent instanceof Spinner)) {
            tickMark.setImageDrawable(context.getResources().getDrawable(R.drawable.viewcode_check));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        }

        if (parent instanceof Spinner) {
            textView.setTextSize(18);
            textView.setTextColor(Color.WHITE);
        } else {
            int padding = (int) Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 15);
            textView.setPadding(padding, 0, 0, 0);
        }

        return listItemView;
    }

    private String getFileName(int position) {
        return this.model.getDependencies().get(position);
    }
}
