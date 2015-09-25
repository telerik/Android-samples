package com.telerik.examples.examples.listview;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.examples.primitives.drawables.Triangle;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.LoadOnDemandBehavior;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SlideItemAnimator;

import java.util.ArrayList;

public class ListViewHorizontalScrollingFragment extends ExampleFragmentBase {
    private static final int PAGE_COUNT = 5;
    private RadListView firstList;
    private RadListView secondList;

    private FlickrPicturesDownloader picturesDownloader1;
    private FlickrPicturesDownloader picturesDownloader2;

    private ArrayList<PhotoItemData> photoItems1 = new ArrayList<PhotoItemData>();
    private ArrayList<PhotoItemData> photoItems2 = new ArrayList<PhotoItemData>();

    private int loadedPage1 = 0;
    private int loadedPage2 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_horizontal_scrolling, container, false);

        LinearLayoutManager lmngr = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);

        TextView txtRecommended = (TextView) rootView.findViewById(R.id.txtRecommended);
        txtRecommended.setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlab300.ttf"));

        TextView txtDesserts = (TextView) rootView.findViewById(R.id.txtDesserts);
        Typeface robotoSlabRegular = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlabRegular.ttf");
        txtDesserts.setTypeface(robotoSlabRegular);
        TextView txtPaleo = (TextView) rootView.findViewById(R.id.txtPaleo);
        txtPaleo.setTypeface(robotoSlabRegular);

        this.firstList = (RadListView) rootView.findViewById(R.id.lvFirst);
        SlideItemAnimator slideAnimator = new SlideItemAnimator();
        slideAnimator.setAnimateInDirection(SlideItemAnimator.DIRECTION_TOP);
        this.firstList.setItemAnimator(slideAnimator);
        int progressColor = this.getResources().getColor(R.color.listViewToolbarColor);
        View automaticIndicator = View.inflate(this.getActivity(), R.layout.list_view_horizontal_scrolling_ondemand_indicator, null);
        ((ProgressBar)automaticIndicator.findViewById(R.id.progressBar)).getIndeterminateDrawable().setColorFilter(progressColor, PorterDuff.Mode.SRC_ATOP);
        final LoadOnDemandBehavior ldb1 = new LoadOnDemandBehavior(null, automaticIndicator);
        ldb1.setMode(LoadOnDemandBehavior.LoadOnDemandMode.AUTOMATIC);
        ldb1.addListener(new LoadOnDemandBehavior.LoadOnDemandListener() {
            @Override
            public void onLoadStarted() {
                if (picturesDownloader1.getStatus() == AsyncTask.Status.FINISHED) {
                    if (loadedPage1 < PAGE_COUNT) {
                        picturesDownloader1 = new FlickrPicturesDownloader(firstList, "desserts", ++loadedPage1);
                        picturesDownloader1.execute();
                    } else {
                        ldb1.setEnabled(false);
                    }
                }
            }

            @Override
            public void onLoadFinished() {

            }
        });

        this.firstList.setLayoutManager(lmngr);
        this.firstList.addBehavior(ldb1);

        lmngr = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        automaticIndicator = View.inflate(this.getActivity(), R.layout.list_view_horizontal_scrolling_ondemand_indicator, null);
        ((ProgressBar)automaticIndicator.findViewById(R.id.progressBar)).getIndeterminateDrawable().setColorFilter(progressColor, PorterDuff.Mode.SRC_ATOP);
        final LoadOnDemandBehavior ldb2 = new LoadOnDemandBehavior(null, automaticIndicator);
        ldb2.setMode(LoadOnDemandBehavior.LoadOnDemandMode.AUTOMATIC);
        ldb2.addListener(new LoadOnDemandBehavior.LoadOnDemandListener() {
            @Override
            public void onLoadStarted() {
                if (picturesDownloader2.getStatus() == AsyncTask.Status.FINISHED) {
                    if (loadedPage2 < PAGE_COUNT) {
                        picturesDownloader2 = new FlickrPicturesDownloader(secondList, "paleo", ++loadedPage2);
                        picturesDownloader2.execute();
                    }else{
                        ldb2.setEnabled(false);
                    }
                }
            }

            @Override
            public void onLoadFinished() {

            }
        });

        this.secondList = (RadListView) rootView.findViewById(R.id.lvSecond);
        this.secondList.addBehavior(ldb2);
        this.secondList.setLayoutManager(lmngr);
        slideAnimator = new SlideItemAnimator();
        slideAnimator.setAnimateInDirection(SlideItemAnimator.DIRECTION_TOP);
        this.secondList.setItemAnimator(slideAnimator);


        this.picturesDownloader1 = new FlickrPicturesDownloader(this.firstList, "desserts");
        this.picturesDownloader2 = new FlickrPicturesDownloader(this.secondList, "paleo");

        FoodItemAdapter adapter = new FoodItemAdapter(
                photoItems1,
                ListViewHorizontalScrollingFragment.this.getActivity(),
                R.layout.list_view_horizontal_scrolling_item);
        firstList.setAdapter(adapter);

        adapter = new FoodItemAdapter(
                photoItems2,
                ListViewHorizontalScrollingFragment.this.getActivity(),
                R.layout.list_view_horizontal_scrolling_item);
        secondList.setAdapter(adapter);

        this.picturesDownloader1.execute();
        this.picturesDownloader2.execute();

        Triangle drawable = new Triangle();
        drawable.setColor(getResources().getColor(R.color.listViewHorizontalLayoutHeaderColor));
        ImageView pointerView = (ImageView)rootView.findViewById(R.id.tabPointer);
        pointerView.setImageDrawable(drawable);

        return rootView;
    }

    @Override
    protected boolean usesInternet() {
        return true;
    }

    class FlickrPicturesDownloader extends AsyncTask<String, Integer, PhotoList> {
        private String tag;
        private RadListView associatedListView;
        public int downloadPage;

        public FlickrPicturesDownloader(RadListView listView, String tag) {
            this(listView, tag, 0);
        }

        public FlickrPicturesDownloader(RadListView listView, String tag, int currentPage) {
            this.associatedListView = listView;
            this.tag = tag;
            this.downloadPage = currentPage;
        }

        @Override
        protected PhotoList doInBackground(String[] params) {
            return FlickrHelper.downloadPicturesForTag(tag, 10, this.downloadPage);
        }

        @Override
        protected void onPostExecute(PhotoList o) {
            super.onPostExecute(o);

            if (o == null){
                return;
            }

            ListViewAdapter lva = (ListViewAdapter) this.associatedListView.getAdapter();

            for (Photo p : o) {
                PhotoItemData container = new PhotoItemData();
                container.setPhoto(p);
                container.setGroupKey(tag);
                lva.add(container);
            }

            lva.notifyLoadingFinished();
        }
    }
}
