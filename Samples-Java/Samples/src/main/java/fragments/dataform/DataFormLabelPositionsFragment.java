package fragments.dataform;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.engine.LabelPosition;
import com.telerik.widget.dataform.visualization.DataFormLinearLayoutManager;
import com.telerik.widget.dataform.visualization.RadDataForm;

import activities.ExampleFragment;

public class DataFormLabelPositionsFragment extends Fragment implements ExampleFragment {
    RadDataForm dataForm;
    Person person;

    @Override
    public String title() {
        return "Label Positions";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layoutRoot = (ViewGroup)inflater.inflate(R.layout.fragment_dataform_label_positions, container, false);

        dataForm = (RadDataForm)layoutRoot.findViewById(R.id.dataForm);
        person = new Person();
        dataForm.setEntity(person);
        dataForm.setLayoutManager(new DataFormLinearLayoutManager(getContext()));

        // >> data-form-label-positions
        dataForm.setLabelPosition(LabelPosition.LEFT);
        // << data-form-label-positions

        return layoutRoot;
    }
}
