package fragments.dataform;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.telerik.android.common.Procedure;
import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.visualization.DataFormLinearLayoutManager;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.dataform.visualization.core.EntityPropertyViewer;

import activities.ExampleFragment;

public class DataFormEditorStylesFragment extends Fragment implements ExampleFragment {

    RadDataForm dataForm;
    Person person;

    @Override
    public String title() {
        return "Editor Customizations";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layoutRoot = (ViewGroup)inflater.inflate(R.layout.fragment_dataform_editor_styles, container, false);

        dataForm = (RadDataForm)layoutRoot.findViewById(R.id.dataForm);
        person = new Person();
        dataForm.setEntity(person);
        dataForm.setLayoutManager(new DataFormLinearLayoutManager(getContext()));

        // >> data-form-customizations-editor-styles
        dataForm.setEditorCustomizations(new Procedure<EntityPropertyViewer>() {
            @Override
            public void apply(EntityPropertyViewer entityPropertyViewer) {
                switch (entityPropertyViewer.property().name()) {
                    case "Name":
                        ((TextView)entityPropertyViewer.getHeaderView()).setTextColor(Color.BLUE);
                        ((EditText)entityPropertyViewer.getEditorView()).setTextColor(Color.BLUE);
                        break;
                    case "Age":
                        entityPropertyViewer.rootLayout().setBackgroundColor(Color.CYAN);
                        break;
                    case "BirthDate":
                        entityPropertyViewer.getHeaderView().setBackgroundColor(Color.RED);
                        entityPropertyViewer.getEditorView().setBackgroundColor(Color.parseColor("#AAFF4444"));
                        break;
                }
            }
        });
        // << data-form-customizations-editor-styles

        return layoutRoot;
    }
}
