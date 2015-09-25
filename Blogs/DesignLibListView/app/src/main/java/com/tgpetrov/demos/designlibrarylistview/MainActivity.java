package com.tgpetrov.demos.designlibrarylistview;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.telerik.android.common.Function;
import com.telerik.widget.list.ListViewDataSourceAdapter;
import com.telerik.widget.list.RadListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadListView listView = (RadListView)findViewById(R.id.myList);
        ListViewDataSourceAdapter adapter = new ListViewDataSourceAdapter(getData());
        adapter.addGroupDescriptor(new Function<Object, Object>() {
            @Override
            public Object apply(Object object) {
                Entity entity = (Entity)object;
                return entity.category;
            }
        });
        listView.setAdapter(adapter);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbar.setTitle(getString(R.string.page_title));
    }

    private List getData() {
        List<Entity> items = new ArrayList<>();
        items.add(new Entity("Chart", "Essential Chart Series"));
        items.add(new Entity("Chart", "Smooth Interaction"));
        items.add(new Entity("Chart", "Support for Multiple Axes"));
        items.add(new Entity("Chart", "Financial Series and Financial Indicators"));
        items.add(new Entity("Chart", "Deferred Zooming"));
        items.add(new Entity("Chart", "Outer and Inner Labels"));
        items.add(new Entity("Chart", "Selection"));
        items.add(new Entity("Chart", "Tooltip and TrackBall"));
        items.add(new Entity("Chart", "Annotations"));
        items.add(new Entity("ListView", "Layout Modes"));
        items.add(new Entity("ListView", "Deck of Cards"));
        items.add(new Entity("ListView", "Swipes"));
        items.add(new Entity("ListView", "Selection"));
        items.add(new Entity("ListView", "Items Reorder"));
        items.add(new Entity("ListView", "Pull to Refresh"));
        items.add(new Entity("DataForm", "Default and Custom Editors"));
        items.add(new Entity("DataForm", "Validation with Feedback"));
        items.add(new Entity("DataForm", "Read-Only Mode"));
        items.add(new Entity("DataForm", "Commit Modes"));
        items.add(new Entity("DataForm", "Various Layouts Support"));
        items.add(new Entity("Calendar", "Week, Month and Year Views"));
        items.add(new Entity("Calendar", "Single, Multiple and Range Selection"));
        items.add(new Entity("Calendar", "Special and Disabled Dates"));
        items.add(new Entity("Calendar", "Inline Events display mode"));
        items.add(new Entity("Calendar", "Localization"));
        items.add(new Entity("SideDrawer", "Transitions"));
        items.add(new Entity("SideDrawer", "Layers"));
        items.add(new Entity("SideDrawer", "Toolbar integration"));
        items.add(new Entity("DataSource", "Grouping"));
        items.add(new Entity("DataSource", "Sorting"));
        items.add(new Entity("DataSource", "Filtering"));
        return items;
    }

    public class Entity {
        public String category;
        public String name;

        public Entity(String category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
