package fragments.dataform;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.engine.EntityProperty;
import com.telerik.widget.dataform.engine.PropertyChangedListener;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.dataform.visualization.core.EntityPropertyEditor;

import activities.ExampleFragment;

public class DataFormEditorsFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Editors";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootLayout = (ViewGroup)inflater.inflate(R.layout.fragment_dataform_editors, null);

        RadDataForm dataForm = new RadDataForm(this.getActivity());
        dataForm.getAdapter().setEditorProvider(new Function<EntityProperty, EntityPropertyEditor>() {
            @Override
            public EntityPropertyEditor apply(EntityProperty property) {
                if(property.name().equals("EmployeeType")) {
                    return new CustomEditor(getActivity(), property);
                }

                return null;
            }
        });

        dataForm.setEntity(new Person());

        rootLayout.addView(dataForm);

        return rootLayout;
    }

    public class CustomEditor extends EntityPropertyEditor implements View.OnClickListener, PropertyChangedListener {
        private EmployeeType type;
        private Button editorButton;

        public CustomEditor(Context context, EntityProperty property) {
            super(context, R.layout.dataform_custom_editor, R.id.custom_editor_header, R.id.custom_editor, R.id.custom_editor_validation_view, property);

            editorButton = (Button)editorView;
            editorButton.setOnClickListener(this);

            ((TextView)headerView).setText(property.getHeader());
        }

        @Override
        public Object value() {
            return type;
        }

        @Override
        protected void applyEntityValueToEditor(Object o) {
            if(o == null) {
                this.editorButton.setText("Tap to select.");
                return;
            }

            this.editorButton.setText(o.toString());
            type = (EmployeeType)o;
        }

        @Override
        public void onClick(View v) {
            this.showEditorFragment(this.type);
        }

        private void showEditorFragment(EmployeeType initialValue) {
            CustomEditorFragment fragment = new CustomEditorFragment();
            fragment.addPropertyChangedListener(this);
            fragment.setType(initialValue);
            fragment.show(getActivity().getSupportFragmentManager(), "customEditor");
        }

        @Override
        protected boolean supportsType(Class type) {
            // Defining which types this editor supports is required.
            return type == EmployeeType.class;
        }

        @Override
        public void onPropertyChanged(String s, Object o) {
            if(o == type) {
                return;
            }

            applyEntityValueToEditor(o);

            // Remember to call value changed, otherwise the object being edited will not be updated.
            onEditorValueChanged(o);
        }
    }
}
