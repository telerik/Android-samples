package fragments.dataform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.visualization.RadDataForm;

import activities.ExampleFragment;

public class DataFormGettingStartedFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Getting Started";
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootLayout = (ViewGroup)inflater.inflate(R.layout.fragment_dataform_getting_started, null);

        RadDataForm dataForm = new RadDataForm(this.getActivity());
        dataForm.setEntity(new Person());

        rootLayout.addView(dataForm);

        return rootLayout;
    }
}
