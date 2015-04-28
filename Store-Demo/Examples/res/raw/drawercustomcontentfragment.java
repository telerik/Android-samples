package com.telerik.examples.examples.sidedrawer;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.GridView;
import android.widget.TextView;

import com.googlecode.flickrjandroid.photos.PhotoList;
import com.telerik.android.primitives.widget.sidedrawer.DrawerChangeListener;
import com.telerik.android.primitives.widget.sidedrawer.DrawerFadeLayerBase;
import com.telerik.android.primitives.widget.sidedrawer.DrawerLocation;
import com.telerik.android.primitives.widget.sidedrawer.RadSideDrawer;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;

public class DrawerCustomContentFragment extends ExampleFragmentBase implements AsyncTaskNotificationListener<PhotoList>, DrawerChangeListener {

    private static final int BUTTON_COLOR_IDLE = Color.parseColor("#FC8055");
    private static final int BUTTON_COLOR_SELECTED = Color.parseColor("#FC8055");
    private TextView[] buttons = new TextView[3];
    private DrawerPicturesInfoDownloader downloader;
    private PhotoList photos;
    private GridView gridView;
    private RadSideDrawer drawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);

        downloader = new DrawerPicturesInfoDownloader(this);
        downloader.execute("Lunch");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (downloader != null) {
            downloader.cancel(true);
        }
    }

    public DrawerCustomContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        drawer = (RadSideDrawer)inflater.inflate(R.layout.example_drawer_custom_control, null);
        drawer.addChangeListener(this);
        drawer.setMainContent(R.layout.example_drawer_custom_main);

        ((TextView) drawer.getMainContent().findViewById(R.id.heading)).setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/RobotoSlab300.ttf"));

        drawer.setDrawerLocation(DrawerLocation.BOTTOM);
        drawer.setDrawerSize(0); // Auto-size

        DrawerFadeLayerBase fadeLayer = new DrawerFadeLayerBase(this.getActivity());
        fadeLayer.view().setBackgroundColor(Color.parseColor("#aa000000"));
        drawer.setFadeLayer(fadeLayer);
        drawer.setIsLocked(true);

        gridView = (GridView) drawer.getMainContent().findViewById(R.id.gridView);
        if (this.photos != null) {
            gridView.setAdapter(new RecipeMenuAdapter(this.getActivity(), photos));
        }

        View refineBtn = drawer.getMainContent().findViewById(R.id.refine_btn);

        refineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.setIsOpen(!drawer.getIsOpen());
            }
        });

        ScaleAnimation refineBtnScale = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                ScaleAnimation.RELATIVE_TO_SELF,
                0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,
                0.5f);

        AnimationSet aSet = new AnimationSet(false);
        refineBtnScale.setInterpolator(new DecelerateInterpolator());
        refineBtnScale.setRepeatMode(Animation.REVERSE);
        refineBtnScale.setRepeatCount(Animation.INFINITE);
        refineBtnScale.setDuration(300);
        aSet.addAnimation(refineBtnScale);
        refineBtn.setAnimation(refineBtnScale);
        refineBtnScale.start();

        Configuration newConfig = this.getResources().getConfiguration();
        int orientation = newConfig.orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.setDrawerContent(R.layout.example_drawer_custom_drawer_landscape);
        } else {
            this.setDrawerContent(R.layout.example_drawer_custom_drawer);
        }

        return drawer;
    }

    @Override
    public void unloadExample() {
        super.unloadExample();

        RecipeMenuAdapter adapter = (RecipeMenuAdapter)this.gridView.getAdapter();
        if(adapter != null) {
            adapter.cancelDownloads();
        }
    }

    private void setDrawerContent(int layoutId) {
        drawer.setDrawerContent(layoutId);

        View.OnClickListener buttonListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                disableButtons();
                TextView textView = (TextView) v;
                textView.setBackgroundColor(BUTTON_COLOR_SELECTED);
                textView.setTextColor(Color.BLACK);
            }
        };

        View drawerContent = drawer.getDrawerContent();
        View button = drawerContent.findViewById(R.id.all_btn);
        buttons[0] = (TextView) button;
        button.setOnClickListener(buttonListener);

        button = drawerContent.findViewById(R.id.mains_btn);
        buttons[1] = (TextView) button;
        button.setOnClickListener(buttonListener);

        button = drawerContent.findViewById(R.id.desserts_btn);
        buttons[2] = (TextView) button;
        button.setOnClickListener(buttonListener);
    }

    @Override
    public void onTaskFinished(AsyncTaskWithNotifications task, PhotoList result) {
        if (result == null) {
            return;
        }
        this.photos = result;
        RecipeMenuAdapter recipeMenuAdapter = new RecipeMenuAdapter(this.getActivity(), result);

        if (this.gridView != null) {
            this.gridView.setAdapter(recipeMenuAdapter);
        }

        downloader = null;
    }

    @Override
    public void onTaskCancelled(AsyncTaskWithNotifications asyncTask) {

    }

    @Override
    protected boolean usesInternet() {
        return true;
    }

    private void disableButtons() {
        for (TextView text : buttons) {
            text.setTextColor(BUTTON_COLOR_IDLE);
            text.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onDrawerOpening(RadSideDrawer drawer) {
        return false;
    }

    @Override
    public void onDrawerOpened(RadSideDrawer drawer) {
        drawer.setIsLocked(false);
    }

    @Override
    public boolean onDrawerClosing(RadSideDrawer drawer) {
        return false;
    }

    @Override
    public void onDrawerClosed(RadSideDrawer drawer) {
        drawer.setIsLocked(true);
    }
}
