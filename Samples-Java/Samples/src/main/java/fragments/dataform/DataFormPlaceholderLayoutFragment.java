package fragments.dataform;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Function2;
import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.visualization.DataFormGroupLayoutManager;
import com.telerik.widget.dataform.visualization.DataFormPlaceholderLayoutManager;
import com.telerik.widget.dataform.visualization.EditorGroup;
import com.telerik.widget.dataform.visualization.RadDataForm;

import activities.ExampleFragment;

public class DataFormPlaceholderLayoutFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Placeholder Layout";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootLayout = (ViewGroup)inflater.inflate(R.layout.fragment_dataform_grouping, null);

        RadDataForm dataForm = new RadDataForm(this.getActivity());
        dataForm.setFillViewport(true);
        dataForm.setLayoutManager(new DataFormPlaceholderLayoutManager(this.getActivity(), R.layout.dataform_placeholder_layout));
        dataForm.setEntity(new Person());

        rootLayout.addView(dataForm);

        return rootLayout;
    }
}
