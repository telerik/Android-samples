package fragments.tabview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.android.primitives.widget.tabstrip.Tab;
import com.telerik.android.primitives.widget.tabstrip.TabItemView;
import com.telerik.android.primitives.widget.tabstrip.TabStripOverflowLayout;
import com.telerik.android.primitives.widget.tabstrip.TabStripScrollLayout;
import com.telerik.android.primitives.widget.tabview.RadTabView;
import com.telerik.android.primitives.widget.tabview.TabViewChangeListener;
import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class TabViewLayoutsFragment extends Fragment implements ExampleFragment, Button.OnClickListener, TabViewChangeListener {
    RadTabView tabView;
    private Button addButton;
    private Button removeButton;
    private Button scrollButton;
    private Button overflowButton;
    private int tabCounter = 1;

    @Override
    public String title() {
        return "Tab Layouts";
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view_layouts_fragment, null);
        tabView = Util.getLayoutPart(view, R.id.tabView, RadTabView.class);
        this.addButton = Util.getLayoutPart(view, R.id.addTab, Button.class);
        this.addButton.setOnClickListener(this);

        this.removeButton = Util.getLayoutPart(view, R.id.removeTab, Button.class);
        this.removeButton.setOnClickListener(this);

        this.scrollButton = Util.getLayoutPart(view, R.id.scrollLayout, Button.class);
        this.scrollButton.setOnClickListener(this);

        this.overflowButton = Util.getLayoutPart(view, R.id.overflowLayout, Button.class);
        this.overflowButton.setOnClickListener(this);

        // >> tab-layout-maxtabs
        tabView.getTabStrip().getLayout().setMaxVisibleTabs(5);
        // << tab-layout-maxtabs

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == this.addButton) {
            this.onAddTab();
        } else if (v == this.removeButton) {
            this.onRemoveTab();
        } else if (v == this.scrollButton) {
            this.onScrollLayout();
        } else if (v == this.overflowButton) {
            this.onOverflowLayout();
        }
    }

    private void onAddTab() {
        this.tabView.getTabs().add(new Tab("Tab " + Integer.toString(this.tabCounter++)));
    }

    private void onRemoveTab() {
        int index = this.tabCounter - 2;
        if(index < 0) {
            return;
        }

        this.tabCounter--;
        this.tabView.getTabs().remove(index);
    }

    private void onScrollLayout() {
        // >> scroll-layout-instance
        this.tabView.getTabStrip().setTabStripLayout(new TabStripScrollLayout());
        // << scroll-layout-instance
    }

    private void onOverflowLayout() {
        // >> overflow-layout-instance
        this.tabView.getTabStrip().setTabStripLayout(new TabStripOverflowLayout());
        // << overflow-layout-instance
    }

    @Override
    public View getContentViewForTab(Tab tab) {
        TextView contentView = new TextView(this.getContext());
        contentView.setGravity(Gravity.CENTER);

        // Add exclamation mark for additional drama.
        contentView.setText(tab.getTitle() + " content view!");

        return contentView;
    }

    @Override
    public TabItemView getViewForTab(Tab tab) {
        return null;
    }

    @Override
    public boolean onSelectingTab(Tab tabToSelect) {
        return false;
    }

    @Override
    public void onTabSelected(Tab selectedTab, Tab deselectedTab) {

    }
}
