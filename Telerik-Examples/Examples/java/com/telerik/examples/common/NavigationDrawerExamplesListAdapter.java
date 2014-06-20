package com.telerik.examples.common;

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

    public NavigationDrawerExamplesListAdapter(Context context, int resource) {
        super(context, resource);
        this.app = (ExamplesApplicationContext) context.getApplicationContext();
        this.controls = this.app.getControlExamples();
    }

    @Override
    public int getCount() {
        return this.controls.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) this.app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = inflater.inflate(R.layout.navigation_drawer_control_item, null);
        ImageView controlBadge = (ImageView) rootView.findViewById(R.id.controlBadge);
        TextView controlName = (TextView) rootView.findViewById(R.id.controlName);

        ExampleGroup control = (ExampleGroup) this.controls.get(position);

        controlName.setText(control.getHeaderText());
        controlBadge.setImageResource(app.getDrawableResource(control.getDrawerIcon()));

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
