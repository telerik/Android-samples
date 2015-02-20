package fragments.listview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewGettingStartedFragment extends Fragment implements ExampleFragment {

    private RadListView listView;


    public ListViewGettingStartedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view_example, container, false);
        this.listView = (RadListView)rootView.findViewById(R.id.listView);
        this.listView.setHeaderView(View.inflate(this.getActivity(), R.layout.example_list_view_header_layout, null));
        this.listView.setFooterView(View.inflate(this.getActivity(), R.layout.example_list_view_footer_layout, null));
        ArrayList<String> source = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            source.add("Item " + i);
        }
        this.listView.setAdapter(new MyListViewAdapter(source));
        return rootView;
    }

    @Override
    public String title() {
        return "Getting started";
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
            MyCustomViewHolder customVH = (MyCustomViewHolder)holder;
            customVH.txtItemText.setText(this.getItem(position).toString());
        }
    }

    class MyCustomViewHolder extends ListViewHolder{

        public TextView txtItemText;
        public MyCustomViewHolder(View itemView) {
            super(itemView);
            this.txtItemText = (TextView)itemView.findViewById(R.id.txtItemText);
        }
    }
}
