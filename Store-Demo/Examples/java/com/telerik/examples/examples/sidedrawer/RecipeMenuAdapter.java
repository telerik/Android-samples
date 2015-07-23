package com.telerik.examples.examples.sidedrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.telerik.examples.examples.listview.PhotoItemData;

import java.util.ArrayList;

public class RecipeMenuAdapter extends BaseAdapter {
    ArrayList<PhotoItemData> photos;
    Context context;
    LayoutInflater inflater;

    public RecipeMenuAdapter(Context context, PhotoList photos) {
        super();

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.photos = this.wrapPhotos(photos);
    }

    private ArrayList<PhotoItemData> wrapPhotos(PhotoList list) {
        ArrayList<PhotoItemData> result = new ArrayList<PhotoItemData>();

        for(Photo photo : list) {
            PhotoItemData container = new PhotoItemData();
            container.setPhoto(photo);
            result.add(container);
        }

        return result;
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.photos.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoItemData photo = (PhotoItemData) this.getItem(position);
        return new DrawerRecipeListItem(context, photo, parent.getMeasuredWidth());
    }
}
