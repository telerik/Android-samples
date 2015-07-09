package fragments.dataform;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.dataform.engine.NotifyPropertyChanged;
import com.telerik.widget.dataform.engine.NotifyPropertyChangedBase;
import com.telerik.widget.dataform.engine.PropertyChangedListener;

public class CustomEditorFragment extends DialogFragment implements AdapterView.OnItemClickListener, NotifyPropertyChanged {
    private EmployeeType type;
    String[] descriptions = new String[EmployeeType.values().length];
    NotifyPropertyChangedBase notifier = new NotifyPropertyChangedBase();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.dataform_custom_editor_fragment, null);

        ListView mainList = (ListView)rootLayout.findViewById(R.id.dataform_custom_editor_list);
        mainList.setAdapter(new CustomAdapter(this.getActivity()));
        mainList.setOnItemClickListener(this);

        descriptions[0] = "Develops software that is then sold to end users. Responsibilities include writing and debuggnig code, writing unit tests and documentation.";
        descriptions[1] = "Manages a team of software developers. Sets goals and expectations and makes sure the team goals are aligned with the overall company goals.";
        descriptions[2] = "Communicates with customers and helps them resolve technical issues. Escalates customer issues so that the developers can fix them when necessary.";
        descriptions[3] = "Responsible for creating product awareness and enticing customers to buy the product by efficiently showcasing the strengths of the product.";

        return rootLayout;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType value) {
        this.type = value;

        notifier.notifyListeners("Type", value);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.setType(EmployeeType.values()[position]);

        this.dismiss();
    }

    @Override
    public void addPropertyChangedListener(PropertyChangedListener propertyChangedListener) {
        notifier.addPropertyChangedListener(propertyChangedListener);
    }

    @Override
    public void removePropertyChangedListener(PropertyChangedListener propertyChangedListener) {
        notifier.removePropertyChangedListener(propertyChangedListener);
    }

    public class CustomAdapter extends BaseAdapter {
        private Context context;

        public CustomAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return EmployeeType.values().length;
        }

        @Override
        public Object getItem(int position) {
            return EmployeeType.values()[position];
        }

        @Override
        public long getItemId(int position) {
            return EmployeeType.values()[position].hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layoutRoot = LayoutInflater.from(this.context).inflate(R.layout.dataform_custom_editor_list_item, null);

            TextView title = (TextView)layoutRoot.findViewById(R.id.listItemTitle);
            title.setText(EmployeeType.values()[position].toString());

            TextView desc = (TextView)layoutRoot.findViewById(R.id.listItemDescription);
            desc.setText(descriptions[position]);

            return layoutRoot;
        }
    }
}
