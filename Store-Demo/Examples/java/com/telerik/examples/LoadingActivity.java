package com.telerik.examples;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;

import com.google.android.gms.tagmanager.ContainerHolder;
import com.telerik.examples.common.google.TagManagerApi;

/**
 * Created by ginev on 3/24/2015.
 */
public class LoadingActivity extends ActionBarActivity implements TagManagerApi.ContainerHolderLoadedCallback {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.loading_activity);
        ProgressBar pb = (ProgressBar)this.findViewById(R.id.progressBar);
        int color = this.getResources().getColor(R.color.telerikGreen);
        if (pb.getIndeterminateDrawable() != null) {
            pb.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler currentHandler = new Handler(Looper.getMainLooper());
        currentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TagManagerApi.isLoaded()) {
                    TagManagerApi.removeContainerHolderLoadedCallback(LoadingActivity.this);
                    TagManagerApi.addContainerHolderLoadedCallback(LoadingActivity.this);
                } else {
                    launchMainActivity();
                }
            }
        }, 400);
    }

    private void launchMainActivity() {
        Intent mainActivity = new Intent(LoadingActivity.this, MainActivity.class);
        LoadingActivity.this.startActivity(mainActivity);
        this.overridePendingTransition(R.anim.abc_fade_in, R.anim.activity_no_transition);
    }

    @Override
    public void containerHolderLoaded(ContainerHolder holder) {
        launchMainActivity();
    }
}
