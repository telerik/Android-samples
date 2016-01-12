package activities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.telerik.android.sdk.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExamplesAdapter extends BaseExpandableListAdapter {

    private Object[] keys;

    private LinkedHashMap<String, ArrayList<ExampleFragment>> source;

    public ExamplesAdapter(LinkedHashMap<String, ArrayList<ExampleFragment>> source){
        this.source = source;
        this.keys = this.source.keySet().toArray();
    }

    @Override
    public int getGroupCount() {
        return this.source.keySet().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.source.get(this.keys[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.keys[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.source.get(this.keys[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return this.keys[groupPosition].hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.source.get(this.keys[groupPosition]).get(childPosition).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View container = View.inflate(parent.getContext(), R.layout.list_item_group, null);
        TextView txtGroupName = (TextView)container.findViewById(R.id.txtFeatureName);
        txtGroupName.setText(String.valueOf(this.getGroup(groupPosition)));
        return container;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View container = View.inflate(parent.getContext(), R.layout.list_item_child, null);
        TextView txtGroupName = (TextView)container.findViewById(R.id.txtExampleName);
        ExampleFragment child = (ExampleFragment)this.getChild(groupPosition, childPosition);
        txtGroupName.setText(child.title());
        return container;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
