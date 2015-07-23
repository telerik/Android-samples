package com.telerik.examples.examples.listview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.list.DeckOfCardsLayoutManager;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SwipeExecuteBehavior;

import java.util.ArrayList;
import java.util.List;

public class ListViewDeckOfCardsFragment extends ExampleFragmentBase {

    RadListView listView;
    RecipeAdapter adapter;
    ImageButton layoutButton;

    int imageLayoutHeight;
    int imageLayoutWidth;

    Typeface robotoSlab;
    Typeface robotoSlabLight;

    boolean isCurrentLayoutDeck = true;
    int current = -1;

    int size;
    int offset;
    int swipePart = 8;

    public ListViewDeckOfCardsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list_view_deck_of_cards, container, false);

        List allItems = null;

        if (savedInstanceState != null) {
            allItems = savedInstanceState.getParcelableArrayList("allItems");
            isCurrentLayoutDeck = savedInstanceState.getBoolean("isCurrentLayoutDeck");
            if(!isCurrentLayoutDeck) {
                current = savedInstanceState.getInt("currentItem", -1);
            }
        }

        listView = (RadListView)rootView.findViewById(R.id.listView);
        layoutButton = (ImageButton)rootView.findViewById(R.id.layoutButton);

        changeLayoutManager();
        initExampleStyles(rootView);

        if(allItems == null) {
            allItems = getData();
        }

        adapter = new RecipeAdapter(allItems);
        listView.setAdapter(adapter);

        final SwipeExecuteBehavior swipeExecuteBehavior = new SwipeExecuteBehavior();

        Drawable dislike = getResources().getDrawable(R.drawable.list_view_deck_dislike);
        swipeExecuteBehavior.addSwipeDrawable(-offset, dislike);

        Drawable like = getResources().getDrawable(R.drawable.list_view_deck_like);
        swipeExecuteBehavior.addSwipeDrawable(offset, like);

        swipeExecuteBehavior.addListener(new SwipeExecuteBehavior.SwipeExecuteListener() {
            @Override
            public void onSwipeStarted(int position) {
            }

            @Override
            public void onSwipeProgressChanged(int position, int currentOffset, View swipeContent) {
            }

            @Override
            public void onSwipeEnded(int position, int finalOffset) {
                int border = offset * (swipePart / 2);
                if(finalOffset > border || finalOffset < -border) {
                    adapter.remove(position);
                }
                swipeExecuteBehavior.endExecute();
            }

            @Override
            public void onExecuteFinished(int position) {
            }
        });
        swipeExecuteBehavior.setAutoDissolve(false);
        listView.addBehavior(swipeExecuteBehavior);

        if(!isCurrentLayoutDeck && current != -1) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    listView.scrollToPosition(current);
                }
            });
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listView == null) {
            return;
        }

        ArrayList<Recipe> allItems = getItems();
        outState.putParcelableArrayList("allItems", allItems);

        outState.putBoolean("isCurrentLayoutDeck", isCurrentLayoutDeck);
        if(!isCurrentLayoutDeck && allItems != null && allItems.size() > 0) {
            int currentItem = ((LinearLayoutManager)listView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            outState.putInt("currentItem", currentItem);
        }
    }

    private ArrayList<Recipe> getItems() {
        ArrayList<Recipe> allItems = new ArrayList<Recipe>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            allItems.add((Recipe) adapter.getItem(i));
        }
        return allItems;
    }

    private void initExampleStyles(View view) {
        robotoSlab = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlabRegular.ttf");
        robotoSlabLight = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlab300.ttf");

        TextView headerText = (TextView)view.findViewById(R.id.headerText);
        headerText.setTypeface(robotoSlabLight);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        offset = size.x / swipePart;

        layoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.INVISIBLE);
                isCurrentLayoutDeck = !isCurrentLayoutDeck;
                changeLayoutManager();
                List items = getItems();
                adapter = new RecipeAdapter(items);
                listView.setAdapter(adapter);

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        initListView();
                    }
                });
            }
        });

        Button reload = new Button(getActivity());
        reload.setBackgroundColor(Color.TRANSPARENT);
        reload.setTextColor(Color.WHITE);
        reload.setText("reload");
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayoutManager();

                List items = getData();
                adapter = new RecipeAdapter(items);
                listView.setAdapter(adapter);
            }
        });
        listView.setEmptyContent(reload);
    }

    private void changeLayoutManager() {
        if(isCurrentLayoutDeck) {
            DeckOfCardsLayoutManager deckOfCardsLayoutManager = new DeckOfCardsLayoutManager(getActivity(), OrientationHelper.VERTICAL, true);
            listView.setLayoutManager(deckOfCardsLayoutManager);
            layoutButton.setImageResource(R.drawable.ic_listview_expand);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
            listView.setLayoutManager(linearLayoutManager);
            layoutButton.setImageResource(R.drawable.ic_listview_collapse);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        if(v == null) {
            return;
        }
        v.post(new Runnable() {
            @Override
            public void run() {
                initListView();
            }
        });
    }

    private void initListView() {
        int height = listView.getMeasuredHeight();
        int width = listView.getMeasuredWidth();

        int translation = Math.round(this.getResources().getDimension(R.dimen.card_deck_translation));

        height -= 2 * translation;

        int min = Math.min(height, width);
        size = min;

        imageLayoutHeight = min + 2 * translation;
        imageLayoutWidth = min;

        offset = imageLayoutWidth / swipePart;

        adapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    class Recipe implements Parcelable {
        public String title;
        public String category;
        public int src;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(category);
            dest.writeInt(src);
        }

        public Recipe() {
        }

        public Recipe(Parcel source) {
            title = source.readString();
            category = source.readString();
            src = source.readInt();
        }

        public final Creator<Recipe> CREATOR = new Creator<Recipe>() {
            @Override
            public Recipe[] newArray(int size) {
                return new Recipe[size];
            }

            @Override
            public Recipe createFromParcel(Parcel source) {
                return new Recipe(source);
            }
        };
    }

    class RecipeViewHolder extends ListViewHolder {
        ImageView image;
        TextView name;
        TextView category;
        ViewGroup imageLayout;

        RecipeViewHolder(View itemView) {
            super(itemView);

            imageLayout = (ViewGroup)itemView.findViewById(R.id.imageLayout);
            image = (ImageView)itemView.findViewById(R.id.deck_layout_image);
            name = (TextView)itemView.findViewById(R.id.deck_layout_name);
            name.setTypeface(robotoSlab);
            category = (TextView)itemView.findViewById(R.id.deck_layout_category);
            category.setTypeface(null, Typeface.ITALIC);
        }
    }

    class RecipeAdapter extends ListViewAdapter {
        RecipeAdapter(List items) {
            super(items);
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.list_view_deck_item_layout, parent, false);
            return new RecipeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            RecipeViewHolder recipeViewHolder = (RecipeViewHolder) holder;
            updateLayoutParams(recipeViewHolder.imageLayout);

            Recipe recipe = (Recipe) getItem(position);
            recipeViewHolder.name.setText(recipe.title);
            recipeViewHolder.category.setText(recipe.category);
            recipeViewHolder.image.setImageResource(recipe.src);
        }

        @Override
        public ListViewHolder onCreateSwipeContentHolder(ViewGroup parent) {
            View itemView = new TextView(parent.getContext());
            itemView.setVisibility(View.INVISIBLE);
            return new ListViewHolder(itemView);
        }

        private void updateLayoutParams(View view) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            int topMargin = layoutParams.topMargin;
            int bottomMargin = layoutParams.bottomMargin;
            int leftMargin = layoutParams.leftMargin;
            int rightMargin = layoutParams.rightMargin;

            layoutParams.width = imageLayoutWidth - leftMargin - rightMargin;
            layoutParams.height = imageLayoutWidth - topMargin - bottomMargin;
        }
    }

    public ArrayList getData() {
        ArrayList list = new ArrayList();

        Recipe recipe1 = new Recipe();
        recipe1.title = "PORK FILLETS MADE WITH GENTLE HEAT TREATMENT";
        recipe1.category = "Meats";
        recipe1.src = R.drawable.listview_pic_1;
        Recipe recipe2 = new Recipe();
        recipe2.title = "AVOCADO SANDWICH WITH APPLES";
        recipe2.category = "Sandwiches";
        recipe2.src = R.drawable.listview_pic_2;

        Recipe recipe3 = new Recipe();
        recipe3.title = "GRILLED CHIPOTLE SALMON";
        recipe3.category = "Fish & Shellfish";
        recipe3.src = R.drawable.listview_pic_3;

        Recipe recipe4 = new Recipe();
        recipe4.title = "PORK LOINS WITH POTATOES AND SPICY DRESSING";
        recipe4.category = "Meats";
        recipe4.src = R.drawable.listview_pic_4;

        Recipe recipe5 = new Recipe();
        recipe5.title = "SALMON SANDWICH WITH PEA PUREE";
        recipe5.category = "Sandwiches";
        recipe5.src = R.drawable.listview_pic_5;

        Recipe recipe6 = new Recipe();
        recipe6.title = "SLOW-COOKER BARBECUE RIBS";
        recipe6.category = "Meats";
        recipe6.src = R.drawable.listview_pic_6;

        Recipe recipe7 = new Recipe();
        recipe7.title = "PORK STEAK WITH POTATOES AND VEGETABLES";
        recipe7.category = "Meats";
        recipe7.src = R.drawable.listview_pic_7;

        Recipe recipe8 = new Recipe();
        recipe8.title = "HONEY-GLAZED ROAST PORK";
        recipe8.category = "Meats";
        recipe8.src = R.drawable.listview_pic_8;

        Recipe recipe9 = new Recipe();
        recipe9.title = "BACON AND DILL BUTTER SANDWICH";
        recipe9.category = "Sandwiches";
        recipe9.src = R.drawable.listview_pic_9;

        Recipe recipe10 = new Recipe();
        recipe10.title = "SMOKED SALMON OPEN-TOPPED SANDWICH";
        recipe10.category = "Sandwiches";
        recipe10.src = R.drawable.listview_pic_10;

        list.add(recipe3);
        list.add(recipe5);
        list.add(recipe4);
        list.add(recipe10);
        list.add(recipe8);
        list.add(recipe6);
        list.add(recipe9);
        list.add(recipe7);
        list.add(recipe1);
        list.add(recipe2);

        return list;
    }
}
