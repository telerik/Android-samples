package com.telerik.examples.examples.listview;

import android.net.Uri;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Extras;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.SearchParameters;
import com.telerik.examples.common.licensing.KeysRetriever;

public class FlickrHelper {

    public static PhotoList downloadPicturesForTag(String tag, int count, int page) {
        Flickr flickrApi = new Flickr(KeysRetriever.getFlickrApiKey(), KeysRetriever.getFlickrSecret());
        PhotoList breakfastPhotos = null;

        SearchParameters searchParams = new SearchParameters();
        searchParams.setText("food, " + tag);
        searchParams.setTags(new String[]{tag, "dishes", "restaurant"});

        try {
            searchParams.setMedia("photos");

        } catch (FlickrException e) {
            e.printStackTrace();
        }
        searchParams.setSort(SearchParameters.RELEVANCE);
        searchParams.setExtras(Extras.ALL_EXTRAS);
        try {
            breakfastPhotos = flickrApi.getPhotosInterface().search(searchParams, count, page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return breakfastPhotos;
    }

    public static Uri getPhotoDownloadUrl(Photo photo) {
        if (photo.getLargeSize() != null) {
            return Uri.parse(photo.getLargeSize().getSource());
        } else if (photo.getLargeSquareSize() != null) {
            return Uri.parse(photo.getLargeSquareSize().getSource());

        } else if (photo.getMediumSize() != null) {
            return Uri.parse(photo.getMediumSize().getSource());
        }

        return null;
    }
}

