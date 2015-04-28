package com.telerik.examples.examples.listview;

import android.graphics.Bitmap;

import com.googlecode.flickrjandroid.photos.Photo;
import com.telerik.examples.examples.sidedrawer.AsyncTaskNotificationListener;
import com.telerik.examples.examples.sidedrawer.AsyncTaskWithNotifications;

public class PictureDownloaderTask extends AsyncTaskWithNotifications<Photo, Integer, Bitmap> {
    private int requiredWidth = 0;

    public PictureDownloaderTask(AsyncTaskNotificationListener<Bitmap> listener, int requiredWidth) {
        super(listener);

        this.requiredWidth = requiredWidth;
    }

    @Override
    protected Bitmap doInBackground(Photo[] params) {
        return PictureDownloader.downloadPicture(params[0], this.requiredWidth);
    }
}
