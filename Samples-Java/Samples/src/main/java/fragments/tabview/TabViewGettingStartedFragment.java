package fragments.tabview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;

import activities.ExampleFragment;

public class TabViewGettingStartedFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Getting Started";
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view_getting_started, null);

        return view;
    }
}
