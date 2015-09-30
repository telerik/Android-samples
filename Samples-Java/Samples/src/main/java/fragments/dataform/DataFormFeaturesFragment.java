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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.engine.PropertyChangedListener;
import com.telerik.widget.dataform.engine.RangeValidator;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.dataform.visualization.core.CommitMode;

import activities.ExampleFragment;

public class DataFormFeaturesFragment extends Fragment implements ExampleFragment, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, PropertyChangedListener, View.OnClickListener {
    RadDataForm dataForm;
    TextView personText;
    Person person = new Person();
    Button commitButton;

    @Override
    public String title() {
        return "Features";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layoutRoot = (ViewGroup)inflater.inflate(R.layout.fragment_data_form_features, null);

        CheckBox readOnly = (CheckBox)layoutRoot.findViewById(R.id.readOnly);
        readOnly.setOnCheckedChangeListener(this);

        dataForm = (RadDataForm)layoutRoot.findViewById(R.id.dataForm);

        person = new Person();
        person.addPropertyChangedListener(this);

        dataForm.setEntity(person);

        RangeValidator validator = (RangeValidator) dataForm.getExistingEditorForProperty("Age").property().getValidator();
        validator.setMax(30);
        validator.setMin(18);

        Spinner commitModes = (Spinner)layoutRoot.findViewById(R.id.commitModeSpinner);
        commitModes.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, CommitMode.values()));
        commitModes.setOnItemSelectedListener(this);

        personText = (TextView)layoutRoot.findViewById(R.id.personText);

        commitButton = (Button) layoutRoot.findViewById(R.id.manualCommit);
        commitButton.setOnClickListener(this);

        return layoutRoot;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        dataForm.setIsReadOnly(isChecked);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dataForm.setCommitMode(CommitMode.values()[position]);

        if(dataForm.getCommitMode() == CommitMode.MANUAL) {
            commitButton.setVisibility(View.VISIBLE);
        } else {
            commitButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPropertyChanged(String s, Object o) {
        personText.setText(person.toString());
    }

    @Override
    public void onClick(View v) {
        dataForm.commitChanges();
    }
}
