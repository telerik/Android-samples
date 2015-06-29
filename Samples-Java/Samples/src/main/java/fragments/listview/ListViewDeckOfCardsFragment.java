package fragments.listview;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.DeckOfCardsLayoutManager;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.PerspectiveChangeInfo;
import com.telerik.widget.list.RadListView;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

public class ListViewDeckOfCardsFragment extends Fragment implements ExampleFragment {

    private RadListView listView;
    private DeckOfCardsLayoutManager deckOfCardsLayoutManager;
    private int orientation = OrientationHelper.VERTICAL;
    private boolean reverseLayout = false;

    public ListViewDeckOfCardsFragment() {
        // Required empty public constructor
    }

    @Override
    public String title() {
        return "Deck of Cards Layout";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_view_deck_of_cards, container, false);

        listView = (RadListView)rootView.findViewById(R.id.listView);

        CountryAdapter adapter = new CountryAdapter(getData());
        listView.setAdapter(adapter);

        deckOfCardsLayoutManager = new DeckOfCardsLayoutManager(getActivity());
        listView.setLayoutManager(deckOfCardsLayoutManager);

        final Button orientationBtn = (Button)rootView.findViewById(R.id.orientationBtn);
        orientationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orientation == OrientationHelper.VERTICAL) {
                    orientation = OrientationHelper.HORIZONTAL;
                } else {
                    orientation = OrientationHelper.VERTICAL;
                }
                deckOfCardsLayoutManager = new DeckOfCardsLayoutManager(getActivity(), orientation, reverseLayout);
                listView.setLayoutManager(deckOfCardsLayoutManager);
            }
        });

        Button reverseBtn = (Button)rootView.findViewById(R.id.reverseBtn);
        reverseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reverseLayout = !reverseLayout;
                deckOfCardsLayoutManager = new DeckOfCardsLayoutManager(getActivity(), orientation, reverseLayout);
                listView.setLayoutManager(deckOfCardsLayoutManager);
            }
        });

        Button countBtn = (Button)rootView.findViewById(R.id.countBtn);
        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deckOfCardsLayoutManager.getPerspectiveItemsCount() == 2) {
                    deckOfCardsLayoutManager.setPerspectiveItemsCount(5);
                } else {
                    deckOfCardsLayoutManager.setPerspectiveItemsCount(2);
                }
            }
        });

        Button perspectiveBtn = (Button)rootView.findViewById(R.id.perspectiveBtn);
        perspectiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deckOfCardsLayoutManager.perspective().getTranslateStart() != PerspectiveChangeInfo.DEFAULT_TRANSLATION) {
                    deckOfCardsLayoutManager.perspective().setTranslateStart(PerspectiveChangeInfo.DEFAULT_TRANSLATION);
                    deckOfCardsLayoutManager.perspective().setTranslateTop(PerspectiveChangeInfo.DEFAULT_TRANSLATION);
                    deckOfCardsLayoutManager.perspective().setTranslateEnd(PerspectiveChangeInfo.DEFAULT_TRANSLATION);
                } else {
                    deckOfCardsLayoutManager.perspective().setTranslateStart(-50);
                    deckOfCardsLayoutManager.perspective().setTranslateTop(-50);
                    deckOfCardsLayoutManager.perspective().setTranslateEnd(-50);
                }
            }
        });

        Button previousBtn = (Button)rootView.findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deckOfCardsLayoutManager.scrollToPrevious();
            }
        });
        Button nextBtn = (Button)rootView.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deckOfCardsLayoutManager.scrollToNext();
            }
        });

        return rootView;
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
