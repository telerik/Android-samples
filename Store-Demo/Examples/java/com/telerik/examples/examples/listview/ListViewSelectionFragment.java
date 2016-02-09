package com.telerik.examples.examples.listview;

import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.examples.ExampleActivity;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.examples.examples.listview.models.BlogPost;
import com.telerik.examples.examples.listview.models.BlogsParser;
import com.telerik.widget.list.CollapsibleGroupsBehavior;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SelectionBehavior;
import com.telerik.widget.list.SwipeExecuteBehavior;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class ListViewSelectionFragment extends ExampleFragmentBase {
    static final long INVALID_ID = -1;

    private RadListView listView;
    private BlogPostAdapter adapter;
    private TextView actionModeTitleView;
    private TextView txtBlogTitle;
    private TextView txtBlogContent;
    private View selectedBlogPostView;
    private View exampleMainContent;
    private Function<Object, Object> groupDescriptor;
    private Function<Object, Boolean> filterDescriptor;
    private SelectionBehavior selectionBehavior;
    private SwipeExecuteBehavior swipeExecuteBehavior;
    private ReorderWithHandlesBehavior reorderBehavior;
    private CollapsibleGroupsBehavior collapsibleGroupsBehavior;
    private View menuItemAll;
    private View menuItemFavorites;
    private View menuSelection;
    private boolean isWideScreen;
    private String reorderModeHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_list_view_selection, container, false);

        this.adapter = new BlogPostAdapter(new ArrayList<BlogPost>());

        this.listView = (RadListView) rootView.findViewById(R.id.listView);
        this.listView.setAdapter(this.adapter);
        this.listView.addItemClickListener(new ListViewClickListener());

        this.reorderBehavior = new ReorderWithHandlesBehavior(R.id.imgReorder);

        this.swipeExecuteBehavior = new SwipeExecuteBehavior();
        this.swipeExecuteBehavior.addListener(new SwipeListener());
        this.swipeExecuteBehavior.setAutoDissolve(false);
        this.listView.addBehavior(this.swipeExecuteBehavior);

        this.selectionBehavior = new SelectionBehavior();

        this.selectionBehavior.addListener(new ListViewSelectionListener());
        this.listView.addBehavior(this.selectionBehavior);


        this.collapsibleGroupsBehavior = new CollapsibleGroupsBehavior();
        this.listView.addBehavior(collapsibleGroupsBehavior);

        this.initExampleSettings(rootView);
        boolean isAllSelected = true;
        boolean isInReorder = false;

        List<BlogPost> allItems = null;
        if (savedInstanceState != null) {
            allItems = savedInstanceState.getParcelableArrayList("allItems");
            bindListView(allItems);

            isInReorder = savedInstanceState.getBoolean("isInReorder");
            isAllSelected = savedInstanceState.getBoolean("isAllSelected");

            long currentId = savedInstanceState.getLong("currentItemId");
            boolean wasFullScreen = savedInstanceState.getBoolean("isFullScreen");

            int position = adapter.getPosition(currentId);
            if (position < 0) {
                loadBlogPostContent();
            } else {
                loadBlogPostContent(position);
            }

            if (!isWideScreen && currentId != INVALID_ID && wasFullScreen) {
                showBlogPostView(true);
            }
        }

        if (allItems == null || allItems.size() == 0) {
            this.initMenu(rootView, true);
            BlogPostLoaderTask blogPostsLoader = new BlogPostLoaderTask();
            blogPostsLoader.execute();
        } else {
            this.initMenu(rootView, isAllSelected);
            if (!isAllSelected) {
                switchFilter(true);
            }

            if (isInReorder) {
                enterReorderMode();
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listView == null) {
            return;
        }

        adapter.clearFilterDescriptors();
        ArrayList<BlogPost> allItems = getItems();
        outState.putParcelableArrayList("allItems", allItems);

        outState.putBoolean("isInReorder", reorderBehavior.isInProgress());
        outState.putBoolean("isAllSelected", menuSelection == menuItemAll);

        outState.putLong("currentItemId", adapter.getCurrentItemId());
        outState.putBoolean("isFullScreen", !isWideScreen && exampleMainContent.getVisibility() != View.VISIBLE);
    }

    private void enterReorderMode() {
        if (swipeExecuteBehavior.isInProgress()) {
            swipeExecuteBehavior.endExecute();
        }
        this.listView.addBehavior(reorderBehavior);
        this.listView.removeBehavior(swipeExecuteBehavior);
        this.listView.removeBehavior(selectionBehavior);

        this.adapter.enterReorderMode();

        if (isWideScreen) {
            adapter.setShowCurrent(false);
        }

        this.changeMenuIsEnabled(false);

        this.actionModeTitleView.setText(this.reorderModeHeader);
        this.getActivity().startActionMode(new ReorderModeCallback());
    }

    private void initDescriptors() {
        final Calendar today = Calendar.getInstance();
        final Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        final Calendar blogCalendar = Calendar.getInstance();

        this.groupDescriptor = new Function<Object, Object>() {
            @Override
            public Object apply(Object argument) {
                BlogPost blogPost = (BlogPost) argument;
                blogCalendar.setTimeInMillis(blogPost.getPublishDate());
                if (areInSameDay(today, blogCalendar)) {
                    return "Today";
                } else if (areInSameDay(yesterday, blogCalendar)) {
                    return "Yesterday";
                } else {
                    return "Older";
                }
            }
        };

        this.filterDescriptor = new Function<Object, Boolean>() {
            @Override
            public Boolean apply(Object argument) {
                return ((BlogPost) argument).isFavourite();
            }
        };
    }

    private void initExampleSettings(View rootView) {

        this.listView.setEmptyContentEnabled(true);

        this.selectedBlogPostView = rootView.findViewById(R.id.selectedBlogPostView);
        this.exampleMainContent = rootView.findViewById(R.id.exampleMainContent);

        this.isWideScreen = this.selectedBlogPostView == null;

        this.txtBlogTitle = (TextView) rootView.findViewById(R.id.selectedBlogPostTitle);
        this.txtBlogContent = (TextView) rootView.findViewById(R.id.selectedBlogPostContent);

        ImageView reorderButton = (ImageView) rootView.findViewById(R.id.btnReorder);
        reorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterReorderMode();
            }
        });

        this.listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return adapter.confirmDelete(true);
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        break;
                }
                return false;
            }
        });

        this.actionModeTitleView = new TextView(getActivity());
        this.actionModeTitleView.setTextColor(Color.WHITE);
        this.actionModeTitleView.setTextSize(20);

        this.reorderModeHeader = getResources().getString(R.string.list_view_selection_header_in_reorder);

        this.initDescriptors();
    }

    private void initMenu(View rootView, boolean isAllSelected) {

        MenuItemOnClickListener listener = new MenuItemOnClickListener();

        this.menuItemAll = rootView.findViewById(R.id.menuItemAll);
        this.menuItemAll.setOnClickListener(listener);

        this.menuItemFavorites = rootView.findViewById(R.id.menuItemFavorites);
        this.menuItemFavorites.setOnClickListener(listener);

        if (isAllSelected) {
            this.menuSelection = this.menuItemAll;
        } else {
            this.menuSelection = this.menuItemFavorites;
        }
        this.menuSelection.setSelected(true);
    }

    private void changeMenuIsEnabled(boolean isEnabled) {
        this.menuItemAll.setEnabled(isEnabled);
        this.menuItemFavorites.setEnabled(isEnabled);
    }

    private void toggleItemFavourite(BlogPost item) {
        item.setFavourite(!item.isFavourite());
    }

    private void deleteItem(BlogPost item) {
        this.adapter.remove(item);
    }

    private void switchFilter(boolean add) {
        if (add) {
            this.adapter.addFilterDescriptor(this.filterDescriptor);
        } else {
            this.adapter.removeFilterDescriptor(this.filterDescriptor);
        }
    }

    private ArrayList<BlogPost> getItems() {
        ArrayList<BlogPost> allItems = new ArrayList<BlogPost>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (!adapter.isGroupHeader(i)) {
                allItems.add((BlogPost) adapter.getItem(i));
            }
        }
        return allItems;
    }

    private void toggleFavorite(List items) {
        for (Object blogPost : items) {
            BlogPost typedPost = (BlogPost) blogPost;
            this.toggleItemFavourite(typedPost);
        }

        this.adapter.notifyDataSetChanged();
    }

    private void deleteItems(List items) {
        for (Object blogPost : items) {
            BlogPost typedPost = (BlogPost) blogPost;
            this.deleteItem(typedPost);
        }
    }

    private boolean areInSameDay(Calendar calendar1, Calendar calendar2) {
        if (calendar1 == null || calendar2 == null) {
            return false;
        }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    private void bindListView(List<BlogPost> blogPosts) {
        this.adapter = new BlogPostAdapter(blogPosts);
        this.adapter.addGroupDescriptor(this.groupDescriptor);
        if (this.isWideScreen) {
            this.adapter.setShowCurrent(true);
        }
        this.listView.setAdapter(this.adapter);
    }

    private void loadBlogPostContent() {
        for (int i = 0; i < this.adapter.getItemCount(); i++) {
            if (!this.adapter.isGroupHeader(i)) {
                this.loadBlogPostContent(i);
                return;
            }
        }
    }

    private void loadBlogPostContent(int position) {
        BlogPost blogPost = (BlogPost) this.adapter.getItem(position);
        this.adapter.setCurrentItemId(this.adapter.getItemId(blogPost));
        this.txtBlogTitle.setText(blogPost.getTitle());
        this.txtBlogContent.setText(blogPost.getContent());
    }

    private void showBlogPostView(boolean show) {
        if (this.isWideScreen) {
            return;
        }
        if (show) {
            this.exampleMainContent.setVisibility(View.INVISIBLE);
            this.selectedBlogPostView.setVisibility(View.VISIBLE);

            actionModeTitleView.setText("");
            long id = adapter.getCurrentItemId();
            int position = adapter.getPosition(id);
            getActivity().startActionMode(new BlogPostViewCallback(position));
        } else {
            this.exampleMainContent.setVisibility(View.VISIBLE);
            this.selectedBlogPostView.setVisibility(View.GONE);
        }
    }

    class ReorderModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setCustomView(actionModeTitleView);
            ExampleActivity ea = (ExampleActivity) getActivity();
            ea.toggleExampleInfoMenuStripVisibility(false);
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            adapter.exitReorderMode();

            if (isWideScreen) {
                adapter.setShowCurrent(true);
            }

            listView.removeBehavior(reorderBehavior);
            listView.addBehavior(swipeExecuteBehavior);
            listView.addBehavior(selectionBehavior);

            changeMenuIsEnabled(true);

            ExampleActivity ea = (ExampleActivity) getActivity();
            ea.toggleExampleInfoMenuStripVisibility(true);
        }
    }

    class SelectionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.list_view_selection_menu, menu);
            ExampleActivity ea = (ExampleActivity) getActivity();
            ea.toggleExampleInfoMenuStripVisibility(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setCustomView(actionModeTitleView);
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.list_view_selection_example_delete_action) {
                deleteItems(selectionBehavior.selectedItems());
            } else if (item.getItemId() == R.id.list_view_selection_example_fav_action) {
                toggleFavorite(selectionBehavior.selectedItems());
            }
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectionBehavior.endSelection();
            ExampleActivity ea = (ExampleActivity) getActivity();
            ea.toggleExampleInfoMenuStripVisibility(true);
        }
    }

    class BlogPostViewCallback implements ActionMode.Callback {
        int itemPosition;

        BlogPostViewCallback(int position) {
            this.itemPosition = position;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.list_view_selection_menu, menu);
            ExampleActivity ea = (ExampleActivity) getActivity();
            ea.toggleExampleInfoMenuStripVisibility(false);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setCustomView(actionModeTitleView);

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            BlogPost currentPost = (BlogPost) adapter.getItem(itemPosition);
            if (item.getItemId() == R.id.list_view_selection_example_delete_action) {
                adapter.remove(currentPost);
            } else if (item.getItemId() == R.id.list_view_selection_example_fav_action) {
                toggleItemFavourite(currentPost);
                adapter.notifyItemChanged(itemPosition);
            }
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            showBlogPostView(false);
            adapter.setCurrentItemId(INVALID_ID);
            ExampleActivity ea = (ExampleActivity) getActivity();
            ea.toggleExampleInfoMenuStripVisibility(true);
        }
    }

    class ListViewSelectionListener implements SelectionBehavior.SelectionChangedListener {
        ActionMode actionMode = null;

        @Override
        public void onSelectionStarted() {
            if (isWideScreen) {
                adapter.setShowCurrent(false);
            }
            changeMenuIsEnabled(false);
            actionMode = getActivity().startActionMode(new SelectionModeCallback());
        }

        @Override
        public void onItemIsSelectedChanged(int position, boolean newValue) {
            if (actionMode != null) {
                actionModeTitleView.setText(String.valueOf(selectionBehavior.selectedItems().size()));
            }
        }

        @Override
        public void onSelectionEnded() {
            if (actionMode != null) {
                actionMode.finish();
            }
            changeMenuIsEnabled(true);
            if (isWideScreen) {
                adapter.setShowCurrent(true);
            }
        }
    }

    class ListViewClickListener implements RadListView.ItemClickListener {
        @Override
        public void onItemClick(int itemPosition, MotionEvent motionEvent) {
            if (selectionBehavior.isInProgress() ||
                    reorderBehavior.isInProgress() ||
                    swipeExecuteBehavior.isInProgress() ||
                    adapter.isGroupHeader(itemPosition)) {
                return;
            }

            loadBlogPostContent(itemPosition);

            if (!isWideScreen) {
                showBlogPostView(true);
            }
        }

        @Override
        public void onItemLongClick(int itemPosition, MotionEvent motionEvent) {

        }
    }

    class MenuItemOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            if (v == menuSelection) {
                return;
            }
            int delay = 0;
            if(swipeExecuteBehavior.isInProgress()) {
                swipeExecuteBehavior.endExecute();
                delay = 150;
            }
            menuSelection.setSelected(false);
            menuSelection = v;
            menuSelection.setSelected(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    switchFilter(v == menuItemFavorites);
                }
            }, delay);
        }
    }

    class BlogPostLoaderTask extends AsyncTask<Void, Void, List<BlogPost>> {
        @Override
        protected List<BlogPost> doInBackground(Void... params) {
            List<BlogPost> blogPosts = null;
            try {
                XmlResourceParser parser = ListViewSelectionFragment.this.getResources().getXml(R.xml.blogs);
                blogPosts = BlogsParser.parseXML(parser);
                initBlogPostSampleDates(blogPosts);
            } catch (Exception e) {
                Log.e("Telerik_Examples", "Could not parse blog posts.");
            }
            return blogPosts;
        }

        @Override
        protected void onPostExecute(List<BlogPost> blogPosts) {
            bindListView(blogPosts);

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    loadBlogPostContent();
                }
            });
        }

        private void initBlogPostSampleDates(List<BlogPost> blogPosts) {
            if (blogPosts == null || blogPosts.size() == 0) {
                return;
            }
            Calendar calendar = Calendar.getInstance();
            int categoryItemsCount = blogPosts.size() / 3;
            for (int i = 0; i < categoryItemsCount; i++) {
                blogPosts.get(i).setPublishDate(calendar.getTimeInMillis());
            }
            calendar.add(Calendar.DATE, -1);
            for (int i = categoryItemsCount; i < 2 * categoryItemsCount; i++) {
                blogPosts.get(i).setPublishDate(calendar.getTimeInMillis());
            }
            calendar.add(Calendar.DATE, -1);
            for (int i = 2 * categoryItemsCount; i < blogPosts.size(); i++) {
                blogPosts.get(i).setPublishDate(calendar.getTimeInMillis());
            }
        }
    }

    class SwipeListener implements SwipeExecuteBehavior.SwipeExecuteListener {
        public SwipeListener() {
        }

        @Override
        public void onSwipeStarted(int position) {
        }

        @Override
        public void onSwipeProgressChanged(int position, int currentOffset, View swipeContent) {
            if (swipeContent == null) {
                return;
            }

            View leftLayout = ((ViewGroup) swipeContent).getChildAt(0);
            View rightLayout = ((ViewGroup) swipeContent).getChildAt(1);

            if (currentOffset > 0) {
                leftLayout.setVisibility(View.VISIBLE);
                rightLayout.setVisibility(View.INVISIBLE);
            } else {
                leftLayout.setVisibility(View.INVISIBLE);
                rightLayout.setVisibility(View.VISIBLE);
            }

            if(selectionBehavior.isInProgress()) {
                selectionBehavior.endSelection();
            }
        }

        @Override
        public void onSwipeEnded(int position, int finalOffset) {

        }

        @Override
        public void onExecuteFinished(int position) {

        }
    }
}
