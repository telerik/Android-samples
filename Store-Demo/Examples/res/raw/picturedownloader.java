package com.telerik.examples.examples.listview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.googlecode.flickrjandroid.photos.Photo;
import com.telerik.examples.common.ImageUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PictureDownloader {
    private PictureDownloader() {
    }

    public static Bitmap downloadPicture(Photo photo, int requiredWidth) {
        int width;
        int height;
        URL imageURL;

        PictureDownloadInfo di = getDownloadInfo(photo);

        width = di.width;
        height = di.height;
        imageURL = di.url;

        if (imageURL == null) {
            return null;
        }

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.outWidth = width;
            options.outHeight = height;

            options.inSampleSize = ImageUtils.calculateInSampleSize(options, requiredWidth);
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeStream(imageURL.openConnection().getInputStream(), null, options);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static PictureDownloadInfo getDownloadInfo(Photo photo) {
        PictureDownloadInfo result = new PictureDownloadInfo();

        URL imageURL = null;
        int width = 0;
        int height = 0;

        if (photo.getLargeSize() != null) {
            try {
                imageURL = new URL(photo.getLargeSize().getSource());
                width = photo.getLargeSize().getWidth();
                height = photo.getLargeSize().getHeight();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

        } else if (photo.getLargeSquareSize() != null) {
            try {
                imageURL = new URL(photo.getLargeSquareSize().getSource());
                width = photo.getLargeSquareSize().getWidth();
                height = photo.getLargeSquareSize().getHeight();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

        } else if (photo.getMediumSize() != null) {
            try {
                imageURL = new URL(photo.getMediumSize().getSource());
                width = photo.getMediumSize().getWidth();
                height = photo.getMediumSize().getHeight();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

        result.url = imageURL;
        result.width = width;
        result.height = height;
        return result;
    }
}
