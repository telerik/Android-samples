package com.telerik.examples.examples.listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.googlecode.flickrjandroid.photos.Photo;
import com.telerik.examples.R;
import com.telerik.widget.list.ListViewDataSourceAdapter;
import com.telerik.widget.list.ListViewHolder;

import java.util.List;

class FoodItemAdapter extends ListViewDataSourceAdapter{

    private Context context;
    private int itemLayoutId;

    public FoodItemAdapter(List items, Context context, int itemLayoutId) {
        super(items);
        this.context = context;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public ListViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View rootView = inflater.inflate(itemLayoutId, viewGroup, false);
        ProgressBar foodItemProgressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        int color = context.getResources().getColor(R.color.listViewToolbarColor);
        if (foodItemProgressBar.getIndeterminateDrawable() != null) {
            foodItemProgressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }

        return new FoodItemViewHolder(rootView);
    }

    @Override
    public void onBindItemViewHolder(ListViewHolder holder, Object entity) {
        FoodItemViewHolder typedViewHolder = (FoodItemViewHolder) holder;
        typedViewHolder.bind((PhotoItemContainer) entity);
    }


    @Override
    public ListViewHolder onCreateGroupViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View rootView = inflater.inflate(R.layout.list_view_list_mode_group_item_layout, viewGroup, false);
        Typeface font = Typeface.createFromAsset(this.context.getAssets(), "fonts/RobotoSlab700.ttf");
        GroupItemViewHolder holder = new GroupItemViewHolder(rootView);
        holder.txtGroupTitle.setTypeface(font);
        return holder;
    }

    @Override
    public void onBindGroupViewHolder(ListViewHolder holder, Object groupKey) {
        GroupItemViewHolder typedViewHolder = (GroupItemViewHolder) holder;
        typedViewHolder.txtGroupTitle.setText(groupKey.toString().toUpperCase());
    }

}
