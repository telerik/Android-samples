package fragments.listview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.DeckOfCardsLayoutManager;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SlideLayoutManager;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewSlideFragment extends Fragment implements ExampleFragment {

    RadListView listView;
    SlideLayoutManager slideLayoutManager;
    int orientation = OrientationHelper.HORIZONTAL;

    public ListViewSlideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_view_slide, container, false);
        listView = (RadListView)rootView.findViewById(R.id.listView);

        CountryAdapter adapter = new CountryAdapter(getData());
        listView.setAdapter(adapter);

        slideLayoutManager = new SlideLayoutManager(getActivity());

        listView.setLayoutManager(slideLayoutManager);

        final Button orientationBtn = (Button)rootView.findViewById(R.id.orientationBtn);
        orientationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orientation == OrientationHelper.VERTICAL) {
                    orientation = OrientationHelper.HORIZONTAL;
                } else {
                    orientation = OrientationHelper.VERTICAL;
                }
                slideLayoutManager = new SlideLayoutManager(getActivity(), orientation);
                listView.setLayoutManager(slideLayoutManager);
            }
        });

        Button transitionBtn = (Button)rootView.findViewById(R.id.transitionBtn);
        transitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slideLayoutManager.getTransitionMode() == SlideLayoutManager.Transition.SLIDE_AWAY) {
                    slideLayoutManager.setTransitionMode(SlideLayoutManager.Transition.SLIDE_OVER);
                } else {
                    slideLayoutManager.setTransitionMode(SlideLayoutManager.Transition.SLIDE_AWAY);
                }
            }
        });

        ToggleButton spacingBtn = (ToggleButton)rootView.findViewById(R.id.spacingBtn);
        spacingBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    slideLayoutManager.setItemSpacing(50);
                } else {
                    slideLayoutManager.setItemSpacing(0);
                }
            }
        });

        ToggleButton showPreviousButton = (ToggleButton)rootView.findViewById(R.id.showPrevBtn);
        showPreviousButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    slideLayoutManager.setPreviousItemPreview(100);
                } else {
                    slideLayoutManager.setPreviousItemPreview(0);
                }
            }
        });

        ToggleButton showNextButton = (ToggleButton)rootView.findViewById(R.id.showNextBtn);
        showNextButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    slideLayoutManager.setNextItemPreview(100);
                } else {
                    slideLayoutManager.setNextItemPreview(0);
                }
            }
        });

        Button previousBtn = (Button)rootView.findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLayoutManager.scrollToPrevious();
            }
        });
        Button nextBtn = (Button)rootView.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLayoutManager.scrollToNext();
            }
        });

        return rootView;
    }

    @Override
    public String title() {
        return "Slide Layout";
    }

    class Country {
        public String name;
        public String capital;
        public int population;
    }

    class CountryViewHolder extends ListViewHolder {

        public TextView txtName;
        public TextView txtCapital;
        public TextView txtPopulation;

        public CountryViewHolder(View itemView) {
            super(itemView);

            this.txtName = (TextView) itemView.findViewById(R.id.name);
            this.txtCapital = (TextView) itemView.findViewById(R.id.capital);
            this.txtPopulation = (TextView) itemView.findViewById(R.id.population);
        }
    }

    class CountryAdapter extends ListViewAdapter {
        public CountryAdapter(List items) {
            super(items);
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_deck_item_layout, parent, false);
            return new CountryViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            CountryViewHolder vh = (CountryViewHolder) holder;
            Country item = (Country) getItem(position);

            vh.txtName.setText(item.name);
            vh.txtCapital.setText(String.format("Capital: %s", item.capital));
            vh.txtPopulation.setText(String.format("Population: %,d", item.population));
        }
    }

    private ArrayList<Country> getData() {
        ArrayList<Country> source = new ArrayList<>();

        Country sourceItem = new Country();
        sourceItem.name = "Austria";
        sourceItem.capital = "Vienna";
        sourceItem.population = 8507786;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Belgium";
        sourceItem.capital = "Brussels";
        sourceItem.population = 11203992;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Bulgaria";
        sourceItem.capital = "Sofia";
        sourceItem.population = 7245677;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Croatia";
        sourceItem.capital = "Zagreb";
        sourceItem.population = 4246700;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Cyprus";
        sourceItem.capital = "Nicosia";
        sourceItem.population = 858000;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Czech Republic";
        sourceItem.capital = "Prague";
        sourceItem.population = 10512419;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Denmark";
        sourceItem.capital = "Copenhagen";
        sourceItem.population = 5627235;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Estonia";
        sourceItem.capital = "Tallinn";
        sourceItem.population = 1315819;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Finland";
        sourceItem.capital = "Helsinki";
        sourceItem.population = 5451270;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "France";
        sourceItem.capital = "Paris";
        sourceItem.population = 65856609;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Germany";
        sourceItem.capital = "Berlin";
        sourceItem.population = 80780000;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Greece";
        sourceItem.capital = "Athens";
        sourceItem.population = 10992589;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Hungary";
        sourceItem.capital = "Budapest";
        sourceItem.population = 9879000;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Ireland";
        sourceItem.capital = "Dublin";
        sourceItem.population = 4604029;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Italy";
        sourceItem.capital = "Rome";
        sourceItem.population = 60782668;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Latvia";
        sourceItem.capital = "Riga";
        sourceItem.population = 2001468;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Lithuania";
        sourceItem.capital = "Vilnius";
        sourceItem.population = 2943472;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Luxembourg";
        sourceItem.capital = "Luxembourg";
        sourceItem.population = 549680;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Malta";
        sourceItem.capital = "Valletta";
        sourceItem.population = 425384;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "The Netherlands";
        sourceItem.capital = "Amsterdam";
        sourceItem.population = 16829289;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Poland";
        sourceItem.capital = "Warsaw";
        sourceItem.population = 38495659;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Portugal";
        sourceItem.capital = "Lisbon";
        sourceItem.population = 10427301;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Romania";
        sourceItem.capital = "Bucharest";
        sourceItem.population = 19942642;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Slovakia";
        sourceItem.capital = "Bratislava";
        sourceItem.population = 5415949;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Slovenia";
        sourceItem.capital = "Ljubljana";
        sourceItem.population = 2061085;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Spain";
        sourceItem.capital = "Madrid";
        sourceItem.population = 46507760;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "Sweden";
        sourceItem.capital = "Stockholm";
        sourceItem.population = 9644864;

        source.add(sourceItem);

        sourceItem = new Country();
        sourceItem.name = "United Kingdom";
        sourceItem.capital = "London";
        sourceItem.population = 64308261;

        source.add(sourceItem);

        return source;
    }

}
