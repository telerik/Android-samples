package com.telerik.examples.common.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.ExamplesAdapter;
import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.TrackedApplication;
import com.telerik.examples.common.contracts.TrackedActivity;
import com.telerik.examples.primitives.ExamplesGridView;
import com.telerik.examples.viewmodels.Example;

public class ExampleGroupListFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, PopupMenu.OnMenuItemClickListener {

    public static final int EXAMPLE_GROUP_GRID_MODE = 1;
    public static final int EXAMPLE_GROUP_LIST_MODE = 2;

    private int currentMode = EXAMPLE_GROUP_LIST_MODE;
    protected ExamplesGridView gridView;

    protected ExamplesApplicationContext app;
    protected FragmentActivity parentActivity;
    private TextView emptyContentString;

    private View initContent(LayoutInflater inflater, ViewGroup container) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return inflater.inflate(R.layout.fragment_example_group_list, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_example_group_list_horizontal, container, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = this.initContent(inflater, container);

        int listMode = currentMode;

        if (savedInstanceState != null) {
            listMode = savedInstanceState.getInt("list_mode");
        }

        this.emptyContentString = Util.getLayoutPart(root, R.id.emptyContentPresenter, TextView.class);
        this.gridView = Util.getLayoutPart(root, R.id.grid, ExamplesGridView.class);

        if (this.getActivity() instanceof ExamplesGridView.OnOverScrollByHandler) {
            this.gridView.setOnOverScrollByHandler((ExamplesGridView.OnOverScrollByHandler) this.getActivity());
        }
        this.gridView.setOnTouchListener(this);
        if (this.getActivity() instanceof AbsListView.OnScrollListener) {
            this.gridView.setOnScrollListener((AbsListView.OnScrollListener) this.getActivity());
        }
        this.setViewMode(listMode);
        return root;
    }

    protected void refreshEmptyContent() {
        if (this.getActivity() == null) {
            return;
        }
        if (this.gridView.getAdapter().getCount() == 0) {
            this.emptyContentString.setVisibility(View.VISIBLE);
        } else {
            this.emptyContentString.setVisibility(View.GONE);
        }

        this.getActivity().invalidateOptionsMenu();
    }

    public boolean hasData() {
        if (this.gridView != null) {
            return this.gridView.getAdapter() != null && this.gridView.getAdapter().getCount() > 0;
        }

        return false;
    }

    public void refreshFilters() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("list_mode", this.currentMode);
    }

    @Override
    public void onAttach(Activity activity) {
        this.parentActivity = (FragmentActivity) activity;
        this.app = (ExamplesApplicationContext) parentActivity.getApplicationContext();
        super.onAttach(activity);
    }

    public void setViewMode(int mode) {
        if (this.app == null) {
            this.currentMode = mode;
            return;
        }
        if (mode == EXAMPLE_GROUP_GRID_MODE) {
            this.currentMode = EXAMPLE_GROUP_GRID_MODE;
            this.turnOnGridMode();
        } else {
            this.currentMode = EXAMPLE_GROUP_LIST_MODE;
            this.turnOnListMode();
        }
    }

    private void turnOnGridMode() {
        if (this.gridView.getAdapter() == null) {
            ExamplesAdapter adapter = this.getAdapter(this.currentMode);
            adapter.registerDataSetObserver(new ExampleGroupDataSetObserver());
            this.gridView.setAdapter(adapter);
        }
        int columns = this.getResources().getInteger(R.integer.example_list_fragment_columns_wrap);
        this.gridView.setNumColumns(columns);
        if (this.gridView.getAdapter() != null) {
            ((ExamplesAdapter) this.gridView.getAdapter()).setListMode(this.currentMode);
        }
    }

    private void turnOnListMode() {
        if (this.gridView.getAdapter() == null) {
            ExamplesAdapter adapter = this.getAdapter(this.currentMode);
            adapter.registerDataSetObserver(new ExampleGroupDataSetObserver());
            this.gridView.setAdapter(adapter);
        }
        int columns = this.getResources().getInteger(R.integer.example_list_fragment_columns);
        this.gridView.setNumColumns(columns);
        if (this.gridView.getAdapter() != null) {
            ((ExamplesAdapter) this.gridView.getAdapter()).setListMode(this.currentMode);
        }
    }

    public int currentMode() {
        return this.currentMode;
    }

    protected ExamplesAdapter getAdapter(int mode) {
        return null;
    }

    private Example selectedExample;

    @Override
    public void onClick(View v) {
        selectedExample = (Example) v.getTag();
        if (v instanceof ImageButton) {
            PopupMenu menu = new PopupMenu(getActivity(), v);
            if (!app.isExampleInFavourites(selectedExample)) {
                menu.inflate(R.menu.example_list_default);
            } else {
                menu.inflate(R.menu.example_list_in_favourites);
            }
            menu.setOnMenuItemClickListener(this);
            menu.show();

        } else {
            this.app.openExample(parentActivity, selectedExample);
            TrackedActivity trackedActivity = (TrackedActivity) getActivity();
            if (trackedActivity != null) {
                app.trackFeature(trackedActivity.getCategoryName(), TrackedApplication.LIST_ITEM_SELECTED + ": " + selectedExample.getFragmentName());
            }
        }
    }

    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_favorites) {
            app.addFavorite(selectedExample);
            TrackedActivity trackedActivity = (TrackedActivity) getActivity();
            if (trackedActivity != null) {
                app.trackFeature(trackedActivity.getCategoryName(), TrackedApplication.LIST_ITEM_OVERFLOW_FAVOURITE_ADDED);
            }
            return true;
        } else if (item.getItemId() == R.id.action_remove_from_favorites) {
            app.removeFavorite(selectedExample);
            TrackedActivity trackedActivity = (TrackedActivity) getActivity();
            if (trackedActivity != null) {
                app.trackFeature(trackedActivity.getCategoryName(), TrackedApplication.LIST_ITEM_OVERFLOW_FAVOURITE_REMOVED);
            }
            return true;
        } else if (item.getItemId() == R.id.action_view_example_info) {
            app.showInfo(getActivity(), selectedExample);
            TrackedActivity trackedActivity = (TrackedActivity) getActivity();
            if (trackedActivity != null) {
                app.trackFeature(trackedActivity.getCategoryName(), TrackedApplication.LIST_ITEM_OVERFLOW_VIEW_INFO);
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (this.parentActivity instanceof AbsListViewScrollListener) {
            return ((AbsListViewScrollListener) this.parentActivity).handleScrolling(v, event);
        }
        return false;
    }

    public interface AbsListViewScrollListener {
        boolean handleScrolling(View v, MotionEvent event);
    }

    private class ExampleGroupDataSetObserver extends DataSetObserver {
        @Override
        public void onInvalidated() {
            super.onInvalidated();
            refreshEmptyContent();
        }

        @Override
        public void onChanged() {
            super.onChanged();
            refreshEmptyContent();
        }
    }
}
