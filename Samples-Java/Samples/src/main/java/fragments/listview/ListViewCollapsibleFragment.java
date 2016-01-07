package fragments.listview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.android.sdk.R;
import com.telerik.widget.list.CollapsibleGroupsBehavior;
import com.telerik.widget.list.ListViewDataSourceAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.StickyHeaderBehavior;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewCollapsibleFragment extends Fragment implements ExampleFragment {

    private RadListView listView;

    @Override
    public String title() {
        return "Collapsible Groups";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list_view_collapsible, container, false);
        this.listView = (RadListView) rootView.findViewById(R.id.listView);

        CollapsibleGroupsBehavior collapsibleGroupsBehavior = new CollapsibleGroupsBehavior();
        this.listView.addBehavior(collapsibleGroupsBehavior);

        ListViewDataSourceAdapter myListViewAdapter = new ListViewDataSourceAdapter(getData());
        myListViewAdapter.addGroupDescriptor(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                ShoppingListItem item = (ShoppingListItem) o;
                return item.category;
            }
        });

        this.listView.setAdapter(myListViewAdapter);

        return rootView;
    }

    private ArrayList<ShoppingListItem> getData() {
        ArrayList<ShoppingListItem> source = new ArrayList<>();

        ShoppingListItem sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "tonic water";
        sourceItem.price = 3;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "coffee";
        sourceItem.price = 2;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "whiskey";
        sourceItem.price = 4;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "cappuccino";
        sourceItem.price = 2;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "lemon juice";
        sourceItem.price = 2;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "vodka";
        sourceItem.price = 3;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "soda";
        sourceItem.price = 2;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "mineral water";
        sourceItem.price = 2;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "drinks";
        sourceItem.name = "orange fresh";
        sourceItem.price = 5;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "snacks";
        sourceItem.name = "potato chips";
        sourceItem.price = 2;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "snacks";
        sourceItem.name = "tortilla chips";
        sourceItem.price = 3;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "snacks";
        sourceItem.name = "crackers";
        sourceItem.price = 3;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "snacks";
        sourceItem.name = "tuna sandwich";
        sourceItem.price = 3;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "household";
        sourceItem.name = "washing powder";
        sourceItem.price = 5;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "household";
        sourceItem.name = "shower gel";
        sourceItem.price = 4;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "household";
        sourceItem.name = "shampoo";
        sourceItem.price = 4;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "household";
        sourceItem.name = "soap";
        sourceItem.price = 2;

        source.add(sourceItem);

        sourceItem = new ShoppingListItem();
        sourceItem.category = "household";
        sourceItem.name = "towel";
        sourceItem.price = 3;

        source.add(sourceItem);

        return source;
    }

    class ShoppingListItem {
        public String category;
        public int price;
        public String name;

        @Override
        public String toString() {
            return name;
        }
    }
}
