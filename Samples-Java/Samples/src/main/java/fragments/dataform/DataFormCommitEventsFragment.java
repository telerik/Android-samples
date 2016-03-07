package fragments.dataform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.engine.EntityProperty;
import com.telerik.widget.dataform.engine.EntityPropertyCommitListener;
import com.telerik.widget.dataform.visualization.RadDataForm;

import activities.ExampleFragment;

public class DataFormCommitEventsFragment extends Fragment implements ExampleFragment, EntityPropertyCommitListener {

    @Override
    public String title() {
        return "Commit Events";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootLayout = (ViewGroup)inflater.inflate(R.layout.fragment_dataform_commit, null);

        RadDataForm form = (RadDataForm) rootLayout.findViewById(R.id.data_form_commit);

        form.setEntity(new Person());

        // >> data-form-global-commit-listener
        // The commit listener will be notified before and after each property is committed.
        // The global data form commit listeners are only notified when the CommitMode is set to Manual.
        form.addCommitListener(this);
        // << data-form-global-commit-listener

        // >> data-form-local-commit-listener
        // Add a commit listener for a specific property. This way the listener will be notified whenever a
        // commit is attempted regardless of the data form commit mode.
        form.getExistingEditorForProperty("Age").property().addCommitListener(this);
        // << data-form-local-commit-listener

        return rootLayout;
    }

    // >> data-form-commit-listener-implementation
    @Override
    public boolean onBeforeCommit(EntityProperty entityProperty) {
        // To cancel the commit for a given property return true.
        if(entityProperty.name().equals("Age")) {
            return true;
        }

        return false;
    }

    @Override
    public void onAfterCommit(EntityProperty entityProperty) {
        // Do something after a property has been committed.
    }
    // << data-form-commit-listener-implementation
}
