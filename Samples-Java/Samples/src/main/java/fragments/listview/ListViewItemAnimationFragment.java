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
import com.telerik.widget.list.FadeItemAnimator;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.ScaleItemAnimator;
import com.telerik.widget.list.SlideItemAnimator;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ginev on 2/20/2015.
 */
public class ListViewItemAnimationFragment extends Fragment implements ExampleFragment {

    private RadListView listView;
    private Button btnAddItem;
    private Button btnRemoveItem;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view_item_animations, container, false);
        this.listView = (RadListView) rootView.findViewById(R.id.listView);

        ArrayList<String> source = new ArrayList<>();
        source.add("Item 1");
        source.add("Item 2");
        source.add("Item 3");
        source.add("Item 4");
        source.add("Item 5");
        this.listView.setAdapter(new MyListViewAdapter(source));

        Button setFade = (Button)rootView.findViewById(R.id.btnSetFade);
        setFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setItemAnimator(new FadeItemAnimator());
            }
        });
        Button setSlide = (Button)rootView.findViewById(R.id.btnSetSlide);
        setSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setItemAnimator(new SlideItemAnimator());
            }
        });
        Button setScale = (Button)rootView.findViewById(R.id.btnSetScale);
        setScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setItemAnimator(new ScaleItemAnimator());
            }
        });

        this.btnAddItem = (Button)rootView.findViewById(R.id.btnAddItem);

        this.btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyListViewAdapter adapter = (MyListViewAdapter)listView.getAdapter();
                adapter.add(0, "Item " + adapter.getItemCount());
            }
        });
        this.btnRemoveItem = (Button)rootView.findViewById(R.id.btnRemoveItem);
        this.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyListViewAdapter adapter = (MyListViewAdapter)listView.getAdapter();
                if (adapter.getItemCount() > 0) {
                    adapter.remove(0);
                }
            }
        });
        return rootView;
    }

    @Override
    public String title() {
        return "Item animations";
    }

    class MyListViewAdapter extends ListViewAdapter {

        public MyListViewAdapter(List items) {
            super(items);
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_item_animations_layout, parent, false);
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
