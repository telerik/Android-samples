package fragments.listview;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.WrapLayoutManager;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewWrapFragment extends Fragment implements ExampleFragment {

    RadListView listView;
    WrapLayoutManager wrapLayoutManager;
    int gravityCounter = 0;

    public ListViewWrapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_view_wrap, container, false);
        this.listView = (RadListView)rootView.findViewById(R.id.listView);

        wrapLayoutManager = new WrapLayoutManager(getActivity());
        this.listView.setLayoutManager(wrapLayoutManager);

        MyWrapAdapter myWrapAdapter = new MyWrapAdapter(getData());
        this.listView.setAdapter(myWrapAdapter);

        Button orientationBtn = (Button)rootView.findViewById(R.id.orientationBtn);
        orientationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wrapLayoutManager.canScrollHorizontally()) {
                    wrapLayoutManager = new WrapLayoutManager(getActivity(), OrientationHelper.VERTICAL);
                } else {
                    wrapLayoutManager = new WrapLayoutManager(getActivity(), OrientationHelper.HORIZONTAL);
                }
                listView.setLayoutManager(wrapLayoutManager);
            }
        });

        Button lineSpacingBtn = (Button)rootView.findViewById(R.id.lineSpacingBtn);
        lineSpacingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wrapLayoutManager.getLineSpacing() == 0) {
                    wrapLayoutManager.setLineSpacing(20);
                } else {
                    wrapLayoutManager.setLineSpacing(0);
                }
            }
        });

        Button minItemSpacingBtn = (Button)rootView.findViewById(R.id.minItemSpacingBtn);
        minItemSpacingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wrapLayoutManager.getMinimumItemSpacing() == 0) {
                    wrapLayoutManager.setMinimumItemSpacing(20);
                } else {
                    wrapLayoutManager.setMinimumItemSpacing(0);
                }
            }
        });

        Button gravityBtn = (Button)rootView.findViewById(R.id.gravityBtn);
        gravityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravityCounter++;
                if(gravityCounter > 6) {
                    gravityCounter = 1;
                }
                switch (gravityCounter) {
                    case 1: wrapLayoutManager.setGravity(Gravity.END | Gravity.BOTTOM);break;
                    case 2: wrapLayoutManager.setGravity(Gravity.END | Gravity.TOP);break;
                    case 3: wrapLayoutManager.setGravity(Gravity.START | Gravity.BOTTOM);break;
                    case 4: wrapLayoutManager.setGravity(Gravity.START | Gravity.TOP);break;
                    case 5: wrapLayoutManager.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);break;
                    case 6: wrapLayoutManager.setGravity(Gravity.FILL_HORIZONTAL | Gravity.FILL_VERTICAL);break;
                }
            }
        });

        return rootView;
    }

    @Override
    public String title() {
        return "Wrap Layout";
    }

    class MyWrapAdapter extends ListViewAdapter {
        public MyWrapAdapter(List items) {
            super(items);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);

            int id = (position % 5) + 1;
            switch (id) {
                case 1: holder.itemView.setBackgroundColor(Color.parseColor("#F44336"));break;
                case 2: holder.itemView.setBackgroundColor(Color.parseColor("#9C27B0"));break;
                case 3: holder.itemView.setBackgroundColor(Color.parseColor("#2196F3"));break;
                case 4: holder.itemView.setBackgroundColor(Color.parseColor("#00BCD4"));break;
                case 5: holder.itemView.setBackgroundColor(Color.parseColor("#CDDC39"));break;
            }
            int size = 200 + id*50;

            holder.itemView.setLayoutParams(new FrameLayout.LayoutParams(size, size));
        }
    }

    private List getData() {
        ArrayList list = new ArrayList();
        for(int i = 1; i <= 100; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

}
