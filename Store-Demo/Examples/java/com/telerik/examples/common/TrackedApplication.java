package com.telerik.examples.common;

import android.app.Activity;

import com.telerik.examples.R;
import com.telerik.examples.common.licensing.KeysRetriever;

import eqatec.analytics.monitor.AnalyticsMonitorFactory;
import eqatec.analytics.monitor.IAnalyticsMonitor;
import eqatec.analytics.monitor.Version;

public class TrackedApplication extends StateAwareApplication {


    private static final String MONITOR_TRACK_FEATURE_FORMAT = "%s.%s";

    /**
     * Categories
     */
    public static final String HOME_CATEGORY = "HOME";
    public static final String CONTROL_CATEGORY = "CONTROL";
    public static final String EXAMPLE_CATEGORY = "EXAMPLE";
    public static final String DRAWER_CATEGORY = "NAVIGATION_DRAWER";

    /**
     * Features
     */
    public static final String DRAWER_CONTROL_SELECTED = "DRAWER_CONTROL_SELECTED";
    public static final String DRAWER_SECTION_SELECTED = "DRAWER_SECTION_SELECTED";
    public final static String DRAWER_OPENED = "DRAWER_OPENED";

    public final static String ACTION_BAR_LIST_LAYOUT_TOGGLED = "ACTION_BAR_LIST_LAYOUT_TOGGLED";
    public final static String ACTION_BAR_SPINNER_ITEM_SELECTED = "ACTION_BAR_SPINNER_ITEM_SELECTED";
    public static final String ACTION_BAR_MENU_FAVOURITE_ADDED = "ACTION_BAR_MENU_FAVOURITE_ADDED";
    public static final String ACTION_BAR_MENU_FAVOURITE_REMOVED = "ACTION_BAR_MENU_FAVOURITE_REMOVED";
    public static final String ACTION_BAR_MENU_VIEW_INFO = "ACTION_BAR_MENU_VIEW_INFO";

    public static final String EXAMPLE_TOOLBAR_VIEW_CODE = "EXAMPLE_TOOLBAR_VIEW_CODE";
    public static final String EXAMPLE_TOOLBAR_VIEW_INFO = "EXAMPLE_TOOLBAR_VIEW_INFO";
    public static final String EXAMPLE_TOOLBAR_SEND_FEEDBACK = "EXAMPLE_TOOLBAR_SEND_FEEDBACK";
    public static final String EXAMPLE_TOOLBAR_ADD_FAVOURITE = "EXAMPLE_TOOLBAR_ADD_FAVOURITE";
    public static final String EXAMPLE_TOOLBAR_REMOVE_FAVOURITE = "EXAMPLE_TOOLBAR_REMOVE_FAVOURITE";
    public static final String EXAMPLE_TOOLBAR_NAVIGATE = "EXAMPLE_TOOLBAR_NAVIGATE";
    public static final String EXAMPLE_TOOLBAR_TOGGLED = "EXAMPLE_TOOLBAR_TOGGLED";

    public static final String LIST_ITEM_SELECTED = "LIST_ITEM_SELECTED";
    public static final String LIST_ITEM_OVERFLOW_FAVOURITE_ADDED = "LIST_ITEM_OVERFLOW_FAVOURITE_ADDED";
    public static final String LIST_ITEM_OVERFLOW_FAVOURITE_REMOVED = "LIST_ITEM_OVERFLOW_FAVOURITE_ADDED";
    public static final String LIST_ITEM_OVERFLOW_VIEW_INFO = "LIST_ITEM_OVERFLOW_VIEW_INFO";

    private IAnalyticsMonitor monitor;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            monitor = AnalyticsMonitorFactory.createMonitor(
                    getApplicationContext(), KeysRetriever.getAnalyticsKey(),
                    new Version(getResources().getString(R.string.version_code))

            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void trackFeature(final String category, final String feature) {
        if (!this.isMonitorActive()) {
            return;
        }

        this.monitor.trackFeature(String.format(MONITOR_TRACK_FEATURE_FORMAT, category, feature));
    }

    public void trackFeatureStart(final String category, final String feature) {
        if (!this.isMonitorActive()) {
            return;
        }
        this.monitor.trackFeatureStart(String.format(MONITOR_TRACK_FEATURE_FORMAT, category, feature));
    }

    public void trackFeatureEnd(final String category, final String feature) {
        if (!this.isMonitorActive()) {
            return;
        }
        this.monitor.trackFeatureStart(String.format(MONITOR_TRACK_FEATURE_FORMAT, category, feature));
    }

    @Override
    public void onActivityStarted(Activity activity) {
        super.onActivityStarted(activity);
        this.trackFeatureStart(activity.getClass().getName(), "start");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        super.onActivityStopped(activity);
        this.trackFeatureEnd(activity.getClass().getName(), "stop");
    }

    public void trackException(final Throwable e) {
        if (!this.isMonitorActive()) {
            return;
        }
        this.monitor.trackException(e);
    }

    @Override
    public void onApplicationGoingToBackground() {
        this.stopMonitor();
    }

    @Override
    public void onApplicationResume() {
        this.startMonitor();
    }

    protected void startMonitor() {
        if (this.canStartAnalytics()) {
            this.monitor.start();
        }
    }

    protected void stopMonitor() {
        if (this.canStopAnalytics()) {
            this.monitor.stop();
            this.monitor.forceSync();
        }
    }

    private boolean isMonitorActive() {
        return this.monitor != null && this.monitor.getStatus().getIsStarted();
    }

    protected boolean canStartAnalytics() {
        return this.monitor != null;
    }

    protected boolean canStopAnalytics() {
        return this.monitor != null;
    }
}
