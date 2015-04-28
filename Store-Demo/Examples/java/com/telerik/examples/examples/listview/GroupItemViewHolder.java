package com.telerik.examples.examples.listview;

import android.view.View;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.widget.list.ListViewHolder;

/**
 * Created by ginev on 2/17/2015.
 */
class GroupItemViewHolder extends ListViewHolder {

    public TextView txtGroupTitle;

    public GroupItemViewHolder(View itemView) {
        super(itemView);
        this.txtGroupTitle = (TextView) itemView.findViewById(R.id.txtGroupName);
    }
}
