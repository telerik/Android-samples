package com.telerik.examples.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.telerik.examples.R;
import com.telerik.examples.common.google.ContainerHolderSingleton;
import com.telerik.examples.common.google.TagManagerApi;

public class StateAwareApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private int resumedActivitiesCount = 0;
    private int stoppedActivitiesCount = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        // Enable to track whether the application is going to the background or coming back from it.
        this.registerActivityLifecycleCallbacks(this);
    }

    public void onApplicationGoingToBackground() {

    }

    public void onApplicationResume() {

    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (appIsInBackground()) { // If true that means the application is either just starting or it just has been resumed after being in the background.
            this.onApplicationResume();
            this.resetCounters();
        }
        resumedActivitiesCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {


    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        stoppedActivitiesCount++;
        if (appIsInBackground()) { // If true that means the application is going to the background (User received a call or pressed the Home button etc.).
            this.onApplicationGoingToBackground();
            this.resetCounters();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    private boolean appIsInBackground() {
        return resumedActivitiesCount == stoppedActivitiesCount;
    }

    private void resetCounters() {
        resumedActivitiesCount = 0;
        stoppedActivitiesCount = 0;
    }
}
