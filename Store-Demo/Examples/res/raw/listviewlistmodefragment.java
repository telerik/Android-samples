package com.telerik.examples.examples.listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewDataSourceAdapter;
import com.telerik.widget.list.RadListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewListModeFragment extends ExampleFragmentBase {


    private static final String BREAKFAST_STRING = "Breakfast";
    private static final String LUNCH_STRING = "Lunch";
    private static final String DINNER_STRING = "Dinner";
    private RadListView listView;
    private RadioButton btnWrap;
    private RadioButton btnList;
    private RadioButton btnStaggered;
    private List photos;
    private ArrayList<String> availableFilters = new ArrayList<String>();

    private Toolbar toolbar;

    //This is not thread-safe but since we've got a single async task that accesses it here
    //we can leave it as a shared object.

    public ListViewListModeFragment() {
        // Required empty public constructor
    }

    @Override
    public void unloadExample() {
        super.unloadExample();

        ListViewAdapter adapter = (ListViewAdapter)this.listView.getAdapter();
        if(adapter == null) {
            return;
        }

        ArrayList<PhotoItemContainer> photoItemContainers = (ArrayList<PhotoItemContainer>)adapter.getItems();
        for(PhotoItemContainer container : photoItemContainers) {
            container.cancelDownload();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_view_list_mode, container, false);
        TextView txtToolbarCaption = (TextView) rootView.findViewById(R.id.txtToolbarCaption);
        txtToolbarCaption.setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlab300.ttf"));
        availableFilters.add(BREAKFAST_STRING);
        availableFilters.add(LUNCH_STRING);
        availableFilters.add(DINNER_STRING);
        this.btnStaggered = (RadioButton) rootView.findViewById(R.id.btnStaggeredLayout);
        this.btnStaggered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    StaggeredGridLayoutManager sl = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    listView.setLayoutManager(sl);
                }
            }
        });
        this.btnWrap = (RadioButton) rootView.findViewById(R.id.btnWrapLayout);
        this.btnWrap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GridLayoutManager gridLayout = new GridLayoutManager(getActivity(), 2);
                    listView.setLayoutManager(gridLayout);
                }
            }
        });
        this.btnList = (RadioButton) rootView.findViewById(R.id.btnListLayout);
        this.btnList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }
        });
        this.listView = (RadListView) rootView.findViewById(R.id.listView);
        this.listView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        this.btnList.setChecked(true);

        this.downloadPhotoList();

        ImageButton btnShowSettings = (ImageButton) rootView.findViewById(R.id.btnShowSettings);
        btnShowSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListViewListModeFragment.this.photos == null){
                    Toast.makeText(getActivity(), "Please wait for the photos to download...", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle(R.string.list_view_layout_modes_dialog_title);
                dialogBuilder.setMultiChoiceItems(
                        R.array.food_types,
                        new boolean[]{
                                availableFilters.contains(BREAKFAST_STRING),
                                availableFilters.contains(LUNCH_STRING),
                                availableFilters.contains(DINNER_STRING)},
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                String[] typedArray = getResources().getStringArray(R.array.food_types);
                                if (isChecked) {
                                    availableFilters.add(typedArray[which]);
                                } else {
                                    availableFilters.remove(typedArray[which]);
                                }
                            }
                        });

                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ListViewDataSourceAdapter adapter = (ListViewDataSourceAdapter)listView.getAdapter();

                        adapter.clearFilterDescriptors();
                        adapter.addFilterDescriptor(new Function<Object, Boolean>() {
                            @Override
                            public Boolean apply(Object argument) {
                                PhotoItemContainer photoItem = (PhotoItemContainer)argument;
                                boolean passesFilter = false;

                                for (String s: availableFilters){
                                    if (s.equalsIgnoreCase(photoItem.getGroupKey())){
                                        return true;
                                    }
                                }

                                return passesFilter;
                            }
                        });

                        dialog.dismiss();
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialogBuilder.create().show();

            }
        });

        return rootView;
    }

    @Override
    protected boolean usesInternet() {
        return true;
    }

    private void downloadPhotoList() {
        AsyncTask photosDownloader = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                int count = 10;
                ArrayList<PhotoItemContainer> source = new ArrayList<PhotoItemContainer>();
                PhotoList breakfastPhotos = FlickrHelper.downloadPicturesForTag("breakfast", count, 0);

                if (breakfastPhotos != null) {
                    for (Photo p : breakfastPhotos) {
                        PhotoItemContainer cont = new PhotoItemContainer();
                        cont.setGroupKey("breakfast");
                        cont.setPhoto(p);
                        source.add(cont);
                    }
                }

                PhotoList lunch = FlickrHelper.downloadPicturesForTag("lunch", count, 0);

                if (lunch != null) {
                    for (Photo p : lunch) {
                        PhotoItemContainer cont = new PhotoItemContainer();
                        cont.setGroupKey("lunch");
                        cont.setPhoto(p);
                        source.add(cont);
                    }
                }

                PhotoList dinnerPhotos = FlickrHelper.downloadPicturesForTag("dinner", count, 0);

                if (dinnerPhotos != null) {
                    for (Photo p : dinnerPhotos) {
                        PhotoItemContainer cont = new PhotoItemContainer();
                        cont.setGroupKey("dinner");
                        cont.setPhoto(p);
                        source.add(cont);
                    }
                }

                return source;
            }

            @Override
            protected void onCancelled(Object o) {
                super.onCancelled(o);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (o instanceof List) {
                    ListViewListModeFragment.this.photos = (List) o;
                    FoodItemAdapter adapter = new FoodItemAdapter((List) o, ListViewListModeFragment.this.getActivity(), R.layout.recipe_item_layout);

                    adapter.addGroupDescriptor(new Function<Object, Object>() {
                        @Override
                        public Object apply(Object argument) {
                            PhotoItemContainer container = (PhotoItemContainer) argument;
                            return container.getGroupKey();
                        }
                    });

                    listView.setAdapter(adapter);
                }
            }
        };
        photosDownloader.execute();
    }
}
