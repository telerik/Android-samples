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
import com.telerik.android.primitives.widget.tabstrip.TabsAlignment;
import com.telerik.android.primitives.widget.tabview.RadTabView;
import com.telerik.android.primitives.widget.tabview.TabViewChangeListener;
import com.telerik.android.primitives.widget.tabview.TabsPosition;
import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class TabViewTabsPositionFragment extends Fragment implements ExampleFragment, TabViewChangeListener, Button.OnClickListener {
    RadTabView tabView;

    @Override
    public String title() {
        return "Tabs Position";
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view_position_fragment, null);
        tabView = Util.getLayoutPart(view, R.id.tabView, RadTabView.class);
        tabView.addChangeListener(this);

        tabView.getTabs().add(new Tab("Tab 1"));
        tabView.getTabs().add(new Tab("Tab 2"));
        tabView.getTabs().add(new Tab("Tab 3"));
        tabView.getTabs().add(new Tab("Tab 4"));
        tabView.getTabs().add(new Tab("Tab 5"));
        tabView.getTabs().add(new Tab("Tab 6"));
        tabView.getTabs().add(new Tab("Tab 7"));
        tabView.getTabs().add(new Tab("Tab 8"));

        int[] ids = new int[] { R.id.leftPosition, R.id.rightPosition, R.id.topPosition, R.id.bottomPosition,
                R.id.leftAlignment, R.id.rightAlignment, R.id.topAlignment, R.id.bottomAlignment, R.id.centerAlignment, R.id.stretchAlignment };

        for (int id : ids) {
            Button btn = Util.getLayoutPart(view, id, Button.class);
            btn.setOnClickListener(this);
        }

        return view;
    }

    public void onTopPosition() {
        // >> tabview-tabs-position
        this.tabView.setTabsPosition(TabsPosition.TOP);
        // << tabview-tabs-position
    }

    public void onBottomPosition() {
        this.tabView.setTabsPosition(TabsPosition.BOTTOM);
    }

    public void onLeftPosition() {
        this.tabView.setTabsPosition(TabsPosition.LEFT);
    }

    public void onRightPosition() {
        this.tabView.setTabsPosition(TabsPosition.RIGHT);
    }

    public void onLeftAlignment() {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.LEFT);
    }

    public void onRightAlignment() {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.RIGHT);
    }

    public void onTopAlignment() {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.TOP);
    }

    public void onBottomAlignment() {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.BOTTOM);
    }

    public void onCenterAlignment() {
        // >> tabview-tabs-alignment
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.CENTER);
        // << tabview-tabs-alignment
    }

    public void onStretchAlignment() {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.STRETCH);
    }

    @Override
    public View getContentViewForTab(Tab tab) {
        TextView contentView = new TextView(this.getContext());
        contentView.setText(tab.getTitle() + " content view.");
        contentView.setGravity(Gravity.CENTER);

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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.topPosition) {
            this.onTopPosition();
        }

        if(v.getId() == R.id.leftPosition) {
            this.onLeftPosition();
        }

        if(v.getId() == R.id.rightPosition) {
            this.onRightPosition();
        }

        if(v.getId() == R.id.bottomPosition) {
            this.onBottomPosition();
        }

        if(v.getId() == R.id.topAlignment) {
            this.onTopAlignment();
        }

        if(v.getId() == R.id.leftAlignment) {
            this.onLeftAlignment();
        }

        if(v.getId() == R.id.bottomAlignment) {
            this.onBottomAlignment();
        }

        if(v.getId() == R.id.rightAlignment) {
            this.onRightAlignment();
        }

        if(v.getId() == R.id.centerAlignment) {
            this.onCenterAlignment();
        }

        if(v.getId() == R.id.stretchAlignment) {
            this.onStretchAlignment();
        }
    }
}
