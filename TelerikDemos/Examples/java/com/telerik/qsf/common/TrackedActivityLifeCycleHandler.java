package com.telerik.qsf.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class TrackedActivityLifeCycleHandler implements Application.ActivityLifecycleCallbacks {

    private final MonitorStateHandler monitorStateHandler;

    private int resumedActivitiesCount = 0;
    private int stoppedActivitiesCount = 0;

    public TrackedActivityLifeCycleHandler(MonitorStateHandler monitorStateHandler) {
        this.monitorStateHandler = monitorStateHandler;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (appIsInBackground()) { // If true that means the application is either just starting or it just has been resumed after being in the background.
            monitorStateHandler.startMonitor();
            resetCounters();
        }

        resumedActivitiesCount++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        stoppedActivitiesCount++;
        if (appIsInBackground()) { // If true that means the application is going to the background (User received a call or pressed the Home button etc.).
            monitorStateHandler.stopMonitor();
            resetCounters();
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
