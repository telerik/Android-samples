package com.telerik.examples.common;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleGroupListFragment;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;

import java.util.List;

/**
 * Created by ginev on 1/8/2015.
 */
public class ControlExamplesAdapter extends ExamplesAdapter {

    public ControlExamplesAdapter(ExamplesApplicationContext context, List<Example> source, int listMode, View.OnClickListener callBack, boolean showControlBadge) {
        super(context, source, listMode, callBack, showControlBadge);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;

        if (this.listMode == ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE) {
            rootView = View.inflate(getContext(), R.layout.main_example_list_control_list_mode, null);
        } else if (this.listMode == ExampleGroupListFragment.EXAMPLE_GROUP_GRID_MODE) {
            rootView = View.inflate(getContext(), R.layout.main_example_list_control_wrap_mode, null);
        }

        rootView.setOnClickListener(this.callBack);

        Example dataItem = this.getItem(position);
        rootView.setTag(dataItem);
        ImageView controlIcon = (ImageView) rootView.findViewById(R.id.controlIcon);

        if (listMode == ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE) {
            TextView controlName = (TextView) rootView.findViewById(R.id.controlName);
            controlName.setText(dataItem.getHeaderText().toUpperCase());
            controlIcon.setImageResource(app.getDrawableResource(((ExampleGroup) dataItem).getLogoResource()));
        } else if (listMode == ExampleGroupListFragment.EXAMPLE_GROUP_GRID_MODE) {
            controlIcon.setImageResource(app.getDrawableResource(dataItem.getImage()));
        }

        if (dataItem.getIsNew()){
            rootView.setBackgroundResource(R.color.controlBackgroundHighlighted);
        }

        return rootView;
    }
}
