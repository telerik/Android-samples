package com.telerik.examples.common;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;

import java.util.List;

/**
 * Created by ginev on 17/04/2014.
 */
public class NavigationDrawerExamplesListAdapter extends ArrayAdapter<ExampleGroup> {
    private List<Example> controls;
    private ExamplesApplicationContext app;
    private ExampleGroup selectedControl;

    public NavigationDrawerExamplesListAdapter(Context context, int resource) {
        super(context, resource);
        this.app = (ExamplesApplicationContext) context.getApplicationContext();
        this.selectedControl = this.app.findControlById(((Activity) this.getContext()).getIntent().getStringExtra(ExamplesApplicationContext.INTENT_CONTROL_ID));
        this.controls = this.app.getControlExamples();
    }

    @Override
    public int getCount() {
        return this.controls.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) this.app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = inflater.inflate(R.layout.drawer_control_item_container, null);
        ImageView controlBadge = (ImageView) rootView.findViewById(R.id.controlBadge);
        TextView controlName = (TextView) rootView.findViewById(R.id.controlName);

        ExampleGroup control = (ExampleGroup) this.controls.get(position);

        controlName.setText(control.getHeaderText());
        controlBadge.setImageResource(app.getDrawableResource(control.getDrawerIcon()));

        if (this.selectedControl != null && this.selectedControl.equals(control)){
            controlName.setTextColor(this.getContext().getResources().getColor(R.color.telerikGreen));
        }else{
            controlName.setTextColor(this.getContext().getResources().getColor(R.color.black));
        }

        return rootView;
    }

    @Override
    public long getItemId(int position) {
        return this.controls.get(position).hashCode();
    }

    @Override
    public ExampleGroup getItem(int position) {
        return (ExampleGroup) this.controls.get(position);
    }
}
