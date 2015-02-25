package fragments.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.telerik.android.common.Function;
import com.telerik.android.common.Function2;
import com.telerik.android.sdk.R;
import com.telerik.widget.list.ListViewDataSourceAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ginev on 2/20/2015.
 */
public class ListViewDataOperationsFragment extends Fragment implements ExampleFragment {

    private RadListView listView;
    private ToggleButton btnSort;
    private ToggleButton btnGroup;
    private ToggleButton btnFilter;


    public ListViewDataOperationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view_data_operations, container, false);
        this.listView = (RadListView) rootView.findViewById(R.id.listView);
        this.btnSort = (ToggleButton) rootView.findViewById(R.id.btnSort);
        this.btnSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ListViewDataSourceAdapter dsa = (ListViewDataSourceAdapter) listView.getAdapter();

                if (isChecked) {
                    // Sort by price
                    dsa.addSortDescriptor(new Function2<Object, Object, Integer>() {
                        @Override
                        public Integer apply(Object o, Object o2) {
                            ShoppingListItem item1 = (ShoppingListItem) o;
                            ShoppingListItem item2 = (ShoppingListItem) o2;
                            return item1.price > item2.price ? 1 : item1.price == item2.price ? 0 : -1;
                        }
                    });
                } else {
                    dsa.clearSortDescriptors();
                }
            }
        });
        this.btnFilter = (ToggleButton) rootView.findViewById(R.id.btnFilter);
        this.btnFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ListViewDataSourceAdapter dsa = (ListViewDataSourceAdapter) listView.getAdapter();
                if (isChecked) {
                    dsa.addFilterDescriptor(new Function<Object, Boolean>() {
                        @Override
                        public Boolean apply(Object o) {
                            ShoppingListItem currentItem = (ShoppingListItem) o;
                            // Show only items that belong to the drinks category
                            return currentItem.category.equalsIgnoreCase("drinks");
                        }
                    });
                } else {
                    dsa.clearFilterDescriptors();
                }
            }
        });
        this.btnGroup = (ToggleButton) rootView.findViewById(R.id.btnGroup);
        this.btnGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ListViewDataSourceAdapter dsa = (ListViewDataSourceAdapter) listView.getAdapter();
                if (isChecked) {
                    dsa.addGroupDescriptor(new Function<Object, Object>() {
                        @Override
                        public Object apply(Object o) {
                            ShoppingListItem item = (ShoppingListItem) o;
                            // Group by category
                            return item.category;
                        }
                    });
                } else {
                    dsa.clearGroupDescriptors();
                }
            }
        });

        this.listView.setAdapter(new MyListViewAdapter(this.getData()));

        return rootView;
    }

    @Override
    public String title() {
        return "Data operations";
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
        sourceItem.name = "soap";
        sourceItem.price = 2;


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
