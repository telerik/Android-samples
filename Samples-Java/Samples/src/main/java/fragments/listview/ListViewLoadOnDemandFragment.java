package fragments.listview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.LoadOnDemandBehavior;
import com.telerik.widget.list.RadListView;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ginev on 2/20/2015.
 */
public class ListViewLoadOnDemandFragment extends Fragment implements ExampleFragment {
    private RadListView listView;
    private ArrayList<String> source = new ArrayList<>();
    private AsyncTask dataLoader;
    private Button btnManual;
    private Button btnAutomatic;

    public ListViewLoadOnDemandFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view_on_demand, container, false);
        this.listView = (RadListView) rootView.findViewById(R.id.listView);

        final LoadOnDemandBehavior ldb = new LoadOnDemandBehavior();

        this.btnManual = (Button) rootView.findViewById(R.id.btnManual);
        this.btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ldb.setMode(LoadOnDemandBehavior.LoadOnDemandMode.MANUAL);
            }
        });
        this.btnAutomatic = (Button) rootView.findViewById(R.id.btnAutomatic);
        this.btnAutomatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ldb.setMode(LoadOnDemandBehavior.LoadOnDemandMode.AUTOMATIC);
            }
        });

        ldb.addListener(new LoadOnDemandBehavior.LoadOnDemandListener() {
            @Override
            public void onLoadStarted() {
                requestLoadData();
            }

            @Override
            public void onLoadFinished() {
            }
        });

        for (int i = 0; i < 50; i++) {
            source.add("Item " + i);
        }

        listView.addBehavior(ldb);

        this.listView.setAdapter(new MyListViewAdapter(source));

        return rootView;
    }

    private void requestLoadData() {
        if (this.dataLoader != null) {
            this.dataLoader.cancel(true);
        } else {
            this.dataLoader = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    ArrayList<String> dataPage = getDataPage(10);
                    try {
                        // Simulate network delay
                        Thread.sleep((long) 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return dataPage;
                }

                @Override
                protected void onCancelled() {
                    super.onCancelled();
                    dataLoader = null;
                    requestLoadData();
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);

                    ArrayList<String> dataPage = (ArrayList<String>) o;
                    MyListViewAdapter adapter = (MyListViewAdapter) listView.getAdapter();

                    for (int i = 0; i < dataPage.size(); i++) {
                        adapter.add(dataPage.get(i));
                    }

                    adapter.notifyLoadingFinished();
                    dataLoader = null;
                }
            };

            this.dataLoader.execute();
        }
    }

    private ArrayList<String> getDataPage(int pageSize) {
        ArrayList<String> page = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            page.add("Item " + (source.size() + i));
        }
        return page;
    }

    @Override
    public String title() {
        return "Load on demand";
    }

    class MyListViewAdapter extends ListViewAdapter {

        public MyListViewAdapter(List items) {
            super(items);
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_item_layout, parent, false);
            MyCustomViewHolder customHolder = new MyCustomViewHolder(itemView);
            return customHolder;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            MyCustomViewHolder customVH = (MyCustomViewHolder) holder;
            customVH.txtItemText.setText(this.getItem(position).toString());
        }
    }

    class MyCustomViewHolder extends ListViewHolder {

        public TextView txtItemText;

        public MyCustomViewHolder(View itemView) {
            super(itemView);
            this.txtItemText = (TextView) itemView.findViewById(R.id.txtItemText);
        }
    }
}
