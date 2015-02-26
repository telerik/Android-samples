package fragments.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.LoadOnDemandBehavior;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SelectionBehavior;
import com.telerik.widget.list.SwipeRefreshBehavior;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ginev on 2/20/2015.
 */
public class ListViewSelectionFragment extends Fragment implements ExampleFragment {

    private RadListView listView;
    private ArrayList<String> source = new ArrayList<>();
    private Button btnManual;
    private Button btnAutomatic;

    public ListViewSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view_example, container, false);
        this.listView = (RadListView) rootView.findViewById(R.id.listView);

        final SelectionBehavior sb = new SelectionBehavior();

        for (int i = 0; i < 50; i++) {
            source.add("Item " + i);
        }

        this.listView.addBehavior(sb);

        this.listView.setAdapter(new MyListViewAdapter(source));

        return rootView;
    }

    private ArrayList<String> getDataPage(int pageSize) {
        ArrayList<String> page = new ArrayList<>();
        int sourceSize = source.size();
        for (int i = 0; i < pageSize; i++) {
            page.add("Item " + (sourceSize + i));
        }
        return page;
    }

    @Override
    public String title() {
        return "Selection";
    }

    class MyListViewAdapter extends ListViewAdapter {

        public MyListViewAdapter(List items) {
            super(items);
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_item_layout, parent, false);
            MyCustomViewHolder customHolder = new MyCustomViewHolder(itemView);
            return customHolder;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            MyCustomViewHolder customVH = (MyCustomViewHolder) holder;
            customVH.txtItemText.setText(this.getItem(position).toString());
        }
    }

    class MyCustomViewHolder extends ListViewHolder {

        public TextView txtItemText;

        public MyCustomViewHolder(View itemView) {
            super(itemView);
            this.txtItemText = (TextView) itemView.findViewById(R.id.txtItemText);
        }
    }
}
