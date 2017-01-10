package fragments.tabview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class TabViewLayoutsFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Tab Layouts";
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view_layouts_fragment, null);

        return view;
    }
}
