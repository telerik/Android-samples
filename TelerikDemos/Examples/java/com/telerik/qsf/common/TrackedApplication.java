package com.telerik.qsf.common;

import android.app.Application;

import eqatec.analytics.monitor.IAnalyticsMonitor;

public class TrackedApplication extends Application implements MonitorStateHandler {

    private final static String TELERIK_PLATFORM_ANALYTICS_KEY = "YourTelerikAnalyticsKeyHere";
    private static final String MONITOR_TRACK_FEATURE_FORMAT = "%s.%s";

    private IAnalyticsMonitor monitor;

    @Override
    public void onCreate() {
        super.onCreate();

        /*try {
            monitor = AnalyticsMonitorFactory.createMonitor(
                    getApplicationContext(), TELERIK_PLATFORM_ANALYTICS_KEY,
                    new Version(getResources().getString(R.string.version_name)
                    ));
            registerActivityLifecycleCallbacks(new TrackedActivityLifeCycleHandler(this));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/
    }

    public void trackFeature(final String category, final String feature) {
        //this.monitor.trackFeature(String.format(MONITOR_TRACK_FEATURE_FORMAT, category, feature));
    }

    public void trackException(final Throwable e) {
        //this.monitor.trackException(e);
    }

    @Override
    public void startMonitor() {
        //this.monitor.start();
    }

    @Override
    public void stopMonitor() {
        //this.monitor.stop();
    }
}
