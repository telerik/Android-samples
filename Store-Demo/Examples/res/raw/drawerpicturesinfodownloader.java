package com.telerik.examples.examples.sidedrawer;

import com.googlecode.flickrjandroid.photos.PhotoList;
import com.telerik.examples.examples.listview.FlickrHelper;

public class DrawerPicturesInfoDownloader extends AsyncTaskWithNotifications<String, Integer, PhotoList> {
    public DrawerPicturesInfoDownloader(AsyncTaskNotificationListener<PhotoList> listener) {
        super(listener);
    }

    @Override
    protected PhotoList doInBackground(String... params) {
        return FlickrHelper.downloadPicturesForTag(params[0], 10, 0);
    }
}
