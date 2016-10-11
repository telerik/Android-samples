package fragments.dataform;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.visualization.RadDataForm;

import activities.ExampleFragment;

public class DataFormImageLabelsFragment extends Fragment implements ExampleFragment {

    RadDataForm dataForm;
    Employee employee;

    @Override
    public String title() {
        return "Image Labels";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layoutRoot = (ViewGroup) inflater.inflate(R.layout.fragment_dataform_image_labels, container, false);

        dataForm = (RadDataForm) layoutRoot.findViewById(R.id.dataForm);
        employee = new Employee();
        dataForm.setEntity(employee);

        return layoutRoot;
    }
}
