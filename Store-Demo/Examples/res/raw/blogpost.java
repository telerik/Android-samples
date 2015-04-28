package com.telerik.examples.examples.listview.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simple type used by ListViewSelectionFragment.
 */
public class BlogPost implements Parcelable {
    private String title;
    private String content;
    private boolean isFavourite;
    private long publishDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(isFavourite ? 1 : 0);
        dest.writeLong(publishDate);
    }

    public BlogPost() {
    }

    public BlogPost(Parcel source) {
        title = source.readString();
        content = source.readString();
        isFavourite = source.readInt() != 0;
        publishDate = source.readLong();
    }

    public final Creator<BlogPost> CREATOR = new Creator<BlogPost>() {
        @Override
        public BlogPost[] newArray(int size) {
            return new BlogPost[size];
        }

        @Override
        public BlogPost createFromParcel(Parcel source) {
            return new BlogPost(source);
        }
    };
}
