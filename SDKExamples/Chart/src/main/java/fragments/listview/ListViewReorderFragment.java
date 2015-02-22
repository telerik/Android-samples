package fragments.listview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.ItemReorderBehavior;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewReorderFragment extends Fragment implements ExampleFragment {

    private RadListView listView;

    public ListViewReorderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_view_example, container, false);

        this.listView = (RadListView) rootView.findViewById(R.id.listView);

        ItemReorderBehavior reorderBehavior = new ItemReorderBehavior();
        reorderBehavior.addListener(new ItemReorderBehavior.ItemReorderListener() {
            @Override
            public void onReorderStarted(int i) {
                //Called when the reorder operation is started.
            }

            @Override
            public void onReorderItem(int i, int i2) {
                //Called when the positions of two items are changes following a reorder operation.
            }

            @Override
            public void onReorderFinished() {
                //Called when the user has released the item being reordered.
            }
        });
        this.listView.addBehavior(reorderBehavior);

        ArrayList<String> source = new ArrayList<>();
        source.add("Item 1");
        source.add("Item 2");
        source.add("Item 3");
        source.add("Item 4");
        source.add("Item 5");

        this.listView.setAdapter(new MyListViewAdapter(source));

        return rootView;
    }

    @Override
    public String title() {
        return "Item reorder";
    }

    class MyListViewAdapter extends ListViewAdapter {

        public MyListViewAdapter(List items) {
            super(items);
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_item_reorder_layout, parent, false);
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
