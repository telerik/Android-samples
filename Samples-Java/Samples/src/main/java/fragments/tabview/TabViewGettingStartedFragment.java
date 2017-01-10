package fragments.tabview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.android.primitives.widget.tabstrip.Tab;
import com.telerik.android.primitives.widget.tabview.RadTabView;
import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class TabViewGettingStartedFragment extends Fragment implements ExampleFragment {
    RadTabView tabView;

    @Override
    public String title() {
        return "Getting Started";
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view_getting_started, null);

        tabView = Util.getLayoutPart(view, R.id.tabView, RadTabView.class);

        for(int i = 1; i <= 3; i++) {
            Tab tab = new Tab("Tab " + Integer.toString(i));
            TextView contentView = new TextView(this.getContext());
            contentView.setText("Content view for tab " + Integer.toString(i));
            contentView.setGravity(Gravity.CENTER);
            tab.setContentView(contentView);

            tabView.getTabs().add(tab);
        }

        return view;
    }
}
