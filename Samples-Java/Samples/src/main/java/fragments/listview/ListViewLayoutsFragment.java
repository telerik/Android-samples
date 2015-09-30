package fragments.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.DeckOfCardsLayoutManager;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SlideLayoutManager;
import com.telerik.widget.list.WrapLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import activities.ExampleFragment;

/**
 * Created by ginev on 2/20/2015.
 */
public class ListViewLayoutsFragment extends Fragment implements ExampleFragment {

    private RadListView listView;


    public ListViewLayoutsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view_layouts, container, false);
        this.listView = (RadListView)rootView.findViewById(R.id.listView);

        final ArrayList<String> source = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            source.add(this.getRandomText());
        }
        this.listView.setAdapter(new MyListViewAdapter(source));

        Button btnLinear = (Button)rootView.findViewById(R.id.btnLinear);
        btnLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                listView.setLayoutManager(llm);
                listView.setAdapter(new MyListViewAdapter(source));
            }
        });
        Button btnStaggered = (Button)rootView.findViewById(R.id.btnStaggered);
        btnStaggered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                listView.setLayoutManager(slm);
                listView.setAdapter(new MyListViewAdapter(source));
            }
        });
        Button btnGrid = (Button)rootView.findViewById(R.id.btnGrid);
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridLayoutManager glm = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
                listView.setLayoutManager(glm);
                listView.setAdapter(new MyListViewAdapter(source));
            }
        });
        Button btnDeck = (Button)rootView.findViewById(R.id.btnDeck);
        btnDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeckOfCardsLayoutManager dclm = new DeckOfCardsLayoutManager(getActivity());
                listView.setLayoutManager(dclm);
                listView.setAdapter(new MyListViewAdapter(source));
            }
        });

        Button btnSlide = (Button)rootView.findViewById(R.id.btnSlide);
        btnSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideLayoutManager slm = new SlideLayoutManager(getActivity());
                listView.setLayoutManager(slm);
                listView.setAdapter(new MyListViewAdapter(source));
            }
        });

        Button btnWrap = (Button)rootView.findViewById(R.id.btnWrap);
        btnWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WrapLayoutManager wlm = new WrapLayoutManager(getActivity());
                listView.setLayoutManager(wlm);
                listView.setAdapter(new MyWrapAdapter(source));
            }
        });
        return rootView;
    }

    private String getRandomText(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        int wordCount = 5 + r.nextInt(30);

        for (int i = 0; i < wordCount; i++){
            sb.append("word ");
        }

        return sb.toString();
    }

    @Override
    public String title() {
        return "Layouts";
    }

    class MyListViewAdapter extends ListViewAdapter {

        public MyListViewAdapter(List items) {
            super(items);
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_item_layouts, parent, false);
            MyCustomViewHolder customHolder = new MyCustomViewHolder(itemView);
            return customHolder;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            MyCustomViewHolder customVH = (MyCustomViewHolder)holder;
            customVH.txtItemText.setText(this.getItem(position).toString());
        }
    }

    class MyWrapAdapter extends MyListViewAdapter {
        public MyWrapAdapter(List items) {
            super(items);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            int size = 200 + (position % 5) * 50;
            holder.itemView.setLayoutParams(new FrameLayout.LayoutParams(size, size));
        }
    }

    class MyCustomViewHolder extends ListViewHolder{

        public TextView txtItemText;
        public MyCustomViewHolder(View itemView) {
            super(itemView);
            this.txtItemText = (TextView)itemView.findViewById(R.id.txtItemText);
        }
    }
}
