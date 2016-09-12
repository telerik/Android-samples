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

public class DataFormGroupLayoutFragment extends Fragment implements ExampleFragment {
    @Override
    public String title() {
        return "Data Form Groups";
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
                // Developers can create a special group layout for any given group name.
                if(groupName.equals("Group 2")) {
                    EditorGroup group = new EditorGroup(context, groupName, R.layout.dataform_custom_group);
                    // Each group can have a specific layout manager, be it a table layout, a linear layout, a placeholder layout or even something completely custom.
                    group.setLayoutManager(new DataFormPlaceholderLayoutManager(context, R.layout.dataform_group_placeholder_layout));
                    return group;
                }

                return null;
            }
        });

        dataForm.setLayoutManager(groupManager);
        dataForm.setEntity(new Person());

        return rootLayout;
    }
}
