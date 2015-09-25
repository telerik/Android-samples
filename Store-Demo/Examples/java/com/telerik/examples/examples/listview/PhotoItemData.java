package com.telerik.examples.examples.listview;

import com.googlecode.flickrjandroid.photos.Photo;

public class PhotoItemData {

    private Photo photo;
    private String groupKey;

    public void setPhoto(Photo value) {
        if (photo == value) {
            return;
        }

        this.photo = value;
    }

    public Photo getPhoto() {
        return this.photo;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String value) {
        if (groupKey == value) {
            return;
        }

        this.groupKey = value;
    }

    @Override
    public int hashCode() {
        return this.photo.hashCode();
    }
}
