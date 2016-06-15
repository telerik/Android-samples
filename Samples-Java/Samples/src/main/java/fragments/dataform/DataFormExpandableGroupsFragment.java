package fragments.dataform;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.telerik.android.common.Function2;
import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.visualization.DataFormGroupLayoutManager;
import com.telerik.widget.dataform.visualization.EditorGroup;
import com.telerik.widget.dataform.visualization.ExpandableEditorGroup;
import com.telerik.widget.dataform.visualization.RadDataForm;

import activities.ExampleFragment;

public class DataFormExpandableGroupsFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Data Form Expandable Groups";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_dataform_grouping, container, false);

        RadDataForm dataForm = (RadDataForm)rootLayout.findViewById(R.id.data_form_grouping);

        DataFormGroupLayoutManager groupManager = new DataFormGroupLayoutManager(this.getActivity());
        groupManager.setCreateGroup(new Function2<Context, String, EditorGroup>() {
            @Override
            public EditorGroup apply(Context context, String groupName) {
                final ExpandableEditorGroup group = new ExpandableEditorGroup(context, groupName);
                group.addIsExpandedChangedListener(new ExpandableEditorGroup.IsExpandedChangedListener() {
                    @Override
                    public void onChanged(boolean isExpanded) {
                        String message = String.format("%s has been %s", group.name(), isExpanded ? "expanded" : "collapsed");
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
                return group;
            }
        });

        dataForm.setLayoutManager(groupManager);
        dataForm.setEntity(new Person());

        return rootLayout;
    }
}
