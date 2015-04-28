package com.telerik.examples.examples.listview;

import android.graphics.Bitmap;

import com.googlecode.flickrjandroid.photos.Photo;
import com.telerik.examples.examples.sidedrawer.AsyncTaskNotificationListener;
import com.telerik.examples.examples.sidedrawer.AsyncTaskWithNotifications;

import java.util.ArrayList;

public class PhotoItemContainer implements PropertyChangeNotifier, AsyncTaskNotificationListener<Bitmap> {
    private Bitmap bitmap;
    private Photo photo;
    private String groupKey;

    ArrayList<PropertyChangeListener> propertyChangeListeners = new ArrayList<PropertyChangeListener>();
    private PropertyChangeListener changeListener;
    PictureDownloaderTask downloaderTask;

    @Override
    public void setPropertyChangeListener(PropertyChangeListener listener) {
        changeListener = listener;
    }

    @Override
    public PropertyChangeListener getPropertyChangeListener() {
        return changeListener;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeListeners.add(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeListeners.remove(listener);
    }

    public void setPhoto(Photo value) {
        if(photo == value) {
            return;
        }

        Photo oldValue = this.photo;
        this.photo = value;
        this.onPropertyChanged("Photo", value, oldValue);
    }

    public void downloadPhoto(int desiredWidth) {
        if(this.downloaderTask != null) {
            return;
        }

        downloaderTask = new PictureDownloaderTask(this, desiredWidth);
        downloaderTask.execute(this.photo);
    }

    public void cancelDownload() {
        if(this.downloaderTask == null) {
            return;
        }

        this.downloaderTask.cancel(true);
    }

    public Photo getPhoto() {
        return this.photo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String value) {
        if(groupKey == value) {
            return;
        }

        String oldValue = this.groupKey;
        this.groupKey = value;
        this.onPropertyChanged("GroupKey", value, oldValue);
    }

    @Override
    public int hashCode() {
        return this.photo.hashCode();
    }

    public void onPropertyChanged(String propertyName, Object newValue, Object oldValue) {
        if(changeListener != null) {
            changeListener.onPropertyChanged(propertyName, newValue, oldValue);
        }

        for(PropertyChangeListener listener : this.propertyChangeListeners) {
            listener.onPropertyChanged(propertyName, newValue, oldValue);
        }
    }

    @Override
    public void onTaskFinished(AsyncTaskWithNotifications asyncTask, Bitmap result) {
        Bitmap oldValue = this.bitmap;
        this.bitmap = result;
        this.onPropertyChanged("Bitmap", result, oldValue);
    }

    @Override
    public void onTaskCancelled(AsyncTaskWithNotifications asyncTask) {

    }
}
