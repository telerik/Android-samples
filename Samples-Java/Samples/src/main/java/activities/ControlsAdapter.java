package activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.telerik.android.sdk.R;

import java.util.ArrayList;

public class ControlsAdapter extends ArrayAdapter {

    private ArrayList<ExamplesProvider> source = new ArrayList<ExamplesProvider>();

    public ControlsAdapter(Context context, int resource) {
        super(context, resource);

        this.source.add(new CalendarExamples());
        this.source.add(new ChartExamples());
        this.source.add(new DataFormExamples());
        this.source.add(new ListViewExamples());
        this.source.add(new SideDrawerExamples());
        this.source.add(new AutoCompleteExamples());
        this.source.add(new GaugeExamples());
    }

    @Override
    public int getCount() {
        return this.source.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.list_item_control, null);
        }

        ExamplesProvider currentProvider = (ExamplesProvider) this.getItem(position);
        TextView controlName = (TextView) convertView.findViewById(R.id.txtControlName);
        controlName.setText(currentProvider.controlName());
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return this.source.get(position);
    }
}
