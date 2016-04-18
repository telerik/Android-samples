package fragments.dataform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.visualization.DataFormLinearLayoutManager;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.dataform.visualization.core.CommitMode;
import com.telerik.widget.dataform.visualization.core.ValidationMode;
import com.telerik.widget.dataform.visualization.editors.adapters.EditorSpinnerAdapter;

import activities.ExampleFragment;

public class DataFormValidationModeFragment extends Fragment implements ExampleFragment, AdapterView.OnItemSelectedListener, View.OnClickListener {
    RadDataForm dataForm;
    Button validateButton;

    @Override
    public String title() {
        return "Validation Modes";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_dataform_validation_mode, null);

        Spinner validationModeSpinner = (Spinner) rootLayout.findViewById(R.id.data_form_validation_mode_spinner);
        validationModeSpinner.setOnItemSelectedListener(this);
        validationModeSpinner.setAdapter(new EditorSpinnerAdapter(this.getActivity(), R.layout.data_form_spinner_item, ValidationMode.values()));

        dataForm = new RadDataForm(this.getActivity());

        dataForm.setLayoutManager(new DataFormLinearLayoutManager(this.getActivity()));
        dataForm.setCommitMode(CommitMode.MANUAL);
        dataForm.setValidationMode(ValidationMode.IMMEDIATE);

        Person joe = new Person();
        joe.setName("Joe");
        joe.setMail("joe@mailservice.com");
        dataForm.setEntity(joe);

        validateButton = (Button)rootLayout.findViewById(R.id.data_form_validate_button);
        validateButton.setOnClickListener(this);

        rootLayout.addView(dataForm, 0);

        return rootLayout;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dataForm.setValidationMode(ValidationMode.values()[position]);

        // Manual validation
        if(position == 2) {
            validateButton.setVisibility(View.VISIBLE);
        } else {
            validateButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        dataForm.validateChanges();
    }
}
