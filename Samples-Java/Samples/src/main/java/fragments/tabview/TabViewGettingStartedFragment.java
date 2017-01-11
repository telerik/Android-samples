package fragments.tabview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.android.primitives.widget.tabstrip.Tab;
import com.telerik.android.primitives.widget.tabstrip.TabItemView;
import com.telerik.android.primitives.widget.tabview.RadTabView;
import com.telerik.android.primitives.widget.tabview.TabViewChangeListener;
import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class TabViewGettingStartedFragment extends Fragment implements ExampleFragment, TabViewChangeListener {
    @Override
    public String title() {
        return "Getting Started";
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.tab_view_getting_started, null);

        // >> tab-view-create-instance
        RadTabView tabView = new RadTabView(this.getContext());
        // << tab-view-create-instance

        // >> tab-view-add-tabs
        tabView.getTabs().add(new Tab("Tab 1"));
        tabView.getTabs().add(new Tab("Tab 2"));
        tabView.getTabs().add(new Tab("Tab 3"));
        // << tab-view-add-tabs

        tabView.addChangeListener(this);

        view.addView(tabView);
        return view;
    }

    // >> tab-view-listener-content-view
    @Override
    public View getContentViewForTab(Tab tab) {
        TextView contentView = new TextView(this.getContext());
        contentView.setText("Content view for " + tab.getTitle());
        contentView.setGravity(Gravity.CENTER);

        return contentView;
    }
    // << tab-view-listener-content-view

    @Override
    public TabItemView getViewForTab(Tab tab) {
        return null;
    }

    @Override
    public boolean onSelectingTab(Tab tab) {
        return false;
    }

    @Override
    public void onTabSelected(Tab tab, Tab tab1) {

    }
}
