package fragments.tabview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class TabViewTabsPositionFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Tabs Position";
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view_position_fragment, null);

        return view;
    }
}
