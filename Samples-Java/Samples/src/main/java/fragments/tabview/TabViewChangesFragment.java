package fragments.tabview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.android.primitives.widget.tabstrip.Tab;
import com.telerik.android.primitives.widget.tabstrip.TabItemView;
import com.telerik.android.primitives.widget.tabview.RadTabView;
import com.telerik.android.primitives.widget.tabview.TabViewChangeListener;
import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class TabViewChangesFragment extends Fragment implements ExampleFragment, TabViewChangeListener {
    RadTabView tabView;

    @Override
    public String title() {
        return "TabView Changes";
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view_changes_fragment, null);
        tabView = Util.getLayoutPart(view, R.id.tabView, RadTabView.class);
        tabView.addChangeListener(this);

        for(int i = 1; i <= 3; i++) {
            Tab tab = new Tab("Tab " + Integer.toString(i));
            tabView.getTabs().add(tab);
        }
        return view;
    }

    @Override
    public View getContentViewForTab(Tab tab) {
        TextView contentView = new TextView(this.getContext());
        contentView.setText(tab.getTitle() + " content view");
        contentView.setGravity(Gravity.CENTER);

        return contentView;
    }

    @Override
    public TabItemView getViewForTab(Tab tab) {
        TabItemView tabItemView = new TabItemView(this.getContext());
        tabItemView.getTextView().setText(tab.getTitle());
        tabItemView.getTextView().setGravity(Gravity.CENTER);

        return tabItemView;
    }

    @Override
    public boolean onSelectingTab(Tab tabToSelect) {
        // Cancel selection of the middle tab.
        return tabToSelect == this.tabView.getTabs().get(1);
    }

    @Override
    public void onTabSelected(Tab selectedTab, Tab deselectedTab) {
        if(deselectedTab != null) {
            Log.d("TabView deselected: ", deselectedTab.getTitle());
        }
        Log.d("TabView selected: ", selectedTab.getTitle());
    }
}
