package fragments.listview;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.android.sdk.R;
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
public class ListViewStickyHeadersFragment extends Fragment implements ExampleFragment {

    private RadListView listView;

    public ListViewStickyHeadersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view_sticky_headers, container, false);
        this.listView = (RadListView) rootView.findViewById(R.id.listView);

        StickyHeaderBehavior stickyHeaderBehavior = new StickyHeaderBehavior();
        this.listView.addBehavior(stickyHeaderBehavior);

        MyListViewAdapter myListViewAdapter = new MyListViewAdapter(getData());
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

    @Override
    public String title() {
        return "Sticky Headers";
    }


    class MyListViewAdapter extends ListViewDataSourceAdapter {

        public MyListViewAdapter(List items) {
            super(items);
        }

        @Override
        public void onBindGroupViewHolder(ListViewHolder holder, Object groupKey) {
            GroupItemViewHolder vh = (GroupItemViewHolder) holder;
            vh.txtGroupHeader.setText(groupKey.toString().toUpperCase());
        }

        @Override
        public void onBindItemViewHolder(ListViewHolder holder, Object entity) {
            ItemViewHolder vh = (ItemViewHolder) holder;
            ShoppingListItem item = (ShoppingListItem) entity;
            vh.txtItemCategory.setText(String.format("Category: %s", item.category));
            vh.txtItemName.setText(item.name);
            vh.txtItemPrice.setText(String.format("Price: $%d", item.price));
        }

        @Override
        public ListViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_group_header, parent, false);
            GroupItemViewHolder customHolder = new GroupItemViewHolder(itemView);
            return customHolder;
        }

        @Override
        public ListViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_data_operations_item_layout, parent, false);
            ItemViewHolder customHolder = new ItemViewHolder(itemView);
            return customHolder;
        }
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

    class ItemViewHolder extends ListViewHolder {

        public TextView txtItemName;
        public TextView txtItemPrice;
        public TextView txtItemCategory;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.txtItemName = (TextView) itemView.findViewById(R.id.txtItemName);
            this.txtItemPrice = (TextView) itemView.findViewById(R.id.txtItemPrice);
            this.txtItemCategory = (TextView) itemView.findViewById(R.id.txtItemCategory);
        }
    }

    class GroupItemViewHolder extends ListViewHolder {

        public TextView txtGroupHeader;

        public GroupItemViewHolder(View itemView) {
            super(itemView);
            this.txtGroupHeader = (TextView) itemView.findViewById(R.id.txtGroupHeader);
        }
    }

    class ShoppingListItem {
        public String category;
        public int price;
        public String name;
    }
}
