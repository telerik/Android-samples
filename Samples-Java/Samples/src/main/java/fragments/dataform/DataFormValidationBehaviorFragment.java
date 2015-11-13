package fragments.dataform;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.engine.NonEmptyValidator;
import com.telerik.widget.dataform.engine.ValidationInfo;
import com.telerik.widget.dataform.visualization.DataFormLinearLayoutManager;
import com.telerik.widget.dataform.visualization.DataFormValidationViewBehavior;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.dataform.visualization.ValidationAnimationBehavior;
import com.telerik.widget.dataform.visualization.core.EntityPropertyEditor;

import activities.ExampleFragment;

public class DataFormValidationBehaviorFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Validation Behavior";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootLayout = (ViewGroup)inflater.inflate(R.layout.fragment_dataform_grouping, null);

        RadDataForm dataForm = new RadDataForm(this.getActivity());
        dataForm.setLayoutManager(new DataFormLinearLayoutManager(this.getActivity()));
        Person person = new Person();
        person.setName("Joe");
        dataForm.setEntity(person);

        EntityPropertyEditor nameEditor = (EntityPropertyEditor) dataForm.getExistingEditorForProperty("Name");
        nameEditor.property().setValidator(new NonEmptyValidator());
        nameEditor.setValidationViewBehavior(new ValidationAnimationBehavior(this.getActivity()));

        EntityPropertyEditor mailEditor = (EntityPropertyEditor) dataForm.getExistingEditorForProperty("Mail");
        mailEditor.setValidationViewBehavior(new BlinkValidationBehavior(this.getActivity()));

        rootLayout.addView(dataForm);

        return rootLayout;
    }

    public class BlinkValidationBehavior extends DataFormValidationViewBehavior {
        public BlinkValidationBehavior(Context context) {
            super(context);
        }

        @Override
        protected void showNegativeFeedback(ValidationInfo info) {
            super.showNegativeFeedback(info);

            final View editorView = this.editor.rootLayout();

            ViewCompat.animate(editorView).alpha(0).setDuration(50).withEndAction(new Runnable() {
                @Override
                public void run() {
                    ViewCompat.animate(editorView).alpha(1).setDuration(50).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            ViewCompat.animate(editorView).alpha(0).setDuration(50).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    ViewCompat.animate(editorView).alpha(1).setDuration(50);
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
