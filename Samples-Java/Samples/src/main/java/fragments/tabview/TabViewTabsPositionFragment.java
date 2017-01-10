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

    public void onTopPosition(View view) {
        this.tabView.setTabsPosition(com.telerik.android.primitives.widget.tabview.TabsPosition.TOP);
    }

    public void onBottomPosition(View view) {
        this.tabView.setTabsPosition(com.telerik.android.primitives.widget.tabview.TabsPosition.BOTTOM);
    }

    public void onLeftPosition(View view) {
        this.tabView.setTabsPosition(com.telerik.android.primitives.widget.tabview.TabsPosition.LEFT);
    }

    public void onRightPosition(View view) {
        this.tabView.setTabsPosition(com.telerik.android.primitives.widget.tabview.TabsPosition.RIGHT);
    }

    public void onLeftAlignment(View view) {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.LEFT);
    }

    public void onRightAlignment(View view) {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.RIGHT);
    }

    public void onTopAlignment(View view) {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.TOP);
    }

    public void onBottomAlignment(View view) {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.BOTTOM);
    }

    public void onCenterAlignment(View view) {
        this.tabView.getTabStrip().setTabsAlignment(TabsAlignment.CENTER);
    }

    public void onStretchAlignment(View view) {
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
            this.onTopPosition(v);
        }

        if(v.getId() == R.id.leftPosition) {
this.onLeftPosition(v);
        }

        if(v.getId() == R.id.rightPosition) {
this.onRightPosition(v);
        }

        if(v.getId() == R.id.bottomPosition) {
this.onBottomPosition(v);
        }

        if(v.getId() == R.id.topAlignment) {
this.onTopAlignment(v);
        }

        if(v.getId() == R.id.leftAlignment) {
this.onLeftAlignment(v);
        }

        if(v.getId() == R.id.bottomAlignment) {
this.onBottomAlignment(v);
        }

        if(v.getId() == R.id.rightAlignment) {
this.onRightAlignment(v);
        }

        if(v.getId() == R.id.centerAlignment) {
this.onCenterAlignment(v);
        }

        if(v.getId() == R.id.stretchAlignment) {
this.onStretchAlignment(v);
        }
    }
}
