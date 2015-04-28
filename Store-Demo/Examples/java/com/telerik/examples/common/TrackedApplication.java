package com.telerik.examples.common;

import android.app.Activity;

import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.telerik.examples.R;
import com.telerik.examples.common.contracts.TrackedActivity;
import com.telerik.examples.common.google.TagManagerApi;
import com.telerik.examples.common.licensing.KeysRetriever;

import java.util.Map;


public class TrackedApplication extends StateAwareApplication implements TagManagerApi.ContainerHolderLoadedCallback {


    /**
     * Categories
     */
    public static final String HOME_SCREEN = "home-screen";
    public static final String CONTROL_SCREEN = "control-screen";
    public static final String EXAMPLE_SCREEN = "example-screen";
    public static final String CODE_SCREEN = "code-screen";
    public static final String INFO_SCREEN = "info-screen";
    public static final String SETTINGS_SCREEN = "settings-screen";

    public static final String EVENT_SHOW_FAVOURITES = "show-favourites-event";
    public static final String EVENT_SHOW_ABOUT = "show-about-event";
    public static final String EVENT_CHANGE_SOURCE_FILE = "change-source-file-event";
    public final static String EVENT_GOT_IT_CLICK = "got-it-click-event";
    public final static String EVENT_ALL_CONTROLS = "all-controls-event";
    public final static String EVENT_HIGHLIGHTED_CONTROLS = "highlighted-controls-event";
    public final static String EVENT_DRAWER_OPENED = "drawer-opened-event";
    public final static String EVENT_DRAWER_CLOSED = "drawer-closed-event";
    public final static String EVENT_SWIPE_SHOW_FAVOURITES = "swipe-show-favourites-event";
    public final static String EVENT_SWIPE_SHOW_ALL = "swipe-show-all-event";
    public final static String EVENT_LIST_LAYOUT_CHANGED = "list-layout-changed-event";
    public final static String EVENT_SHOW_EXAMPLE_TOOLBAR = "show-example-toolbar-event";
    public final static String EVENT_HIDE_EXAMPLE_TOOLBAR = "hide-example-toolbar-event";
    public final static String EVENT_NAVIGATE_EXAMPLE = "navigate-example-event";
    public final static String EVENT_SEND_FEEDBACK = "send-feedback-event";

    public static final String EVENT_ADD_FAVOURITE = "add-favourite-event";
    public static final String EVENT_REMOVE_FAVOURITE = "remove-favourite-event";

    public static final String EVENT_SCROLL = "scroll-event";
    public static final String EVENT_ACTION_BAR_FAVORITE_ADD = "event-add-favourite-action-bar";
    public static final String EVENT_ACTION_BAR_FAVORITE_REMOVE = "event-remove-favourite-action-bar";

    public static final String PARAM_CONTROL_NAME = "control-name-param";
    public static final String PARAM_EXAMPLE_NAME = "example-name-param";


    @Override
    public void onCreate() {
        super.onCreate();

        try {

            TagManagerApi.init(this);
            TagManagerApi.removeContainerHolderLoadedCallback(this);
            TagManagerApi.addContainerHolderLoadedCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void trackEvent(final String screen, final String event) {
        this.trackEvent(screen, event, null);
    }

    public void trackEvent(final String screen, final String event, Map<String, Object> params) {
        if (!this.isMonitorActive()) {
            return;
        }

        //this.monitor.trackFeature(String.format(MONITOR_TRACK_FEATURE_FORMAT, screen, event));
        TagManagerApi.registerScreenEvent(screen, event, params);
    }

    public void trackScreenOpened(Activity activity) {
        if (!this.isMonitorActive()) {
            return;
        }
        //this.monitor.trackFeatureStart(String.format(MONITOR_TRACK_FEATURE_FORMAT, category, feature));

        if (activity instanceof TrackedActivity) {
            TrackedActivity typedActivity = (TrackedActivity) activity;
            Map<String, Object> params = typedActivity.getAdditionalParameters();
            if (params == null) {
                params = DataLayer.mapOf();
            }
            TagManagerApi.registerScreen(typedActivity.getScreenName(), params);
        }
    }

    public void trackException(final Throwable e) {
        if (!this.isMonitorActive()) {
            return;
        }
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
            TagManagerApi.start();
        }
    }

    protected void stopMonitor() {
        if (this.canStopAnalytics()) {
            TagManagerApi.stop();
        }
    }

    private boolean isMonitorActive() {
        //return this.monitor != null && this.monitor.getStatus().getIsStarted();
        return TagManagerApi.isLoaded() && this.canStartAnalytics();
    }

    protected boolean canStartAnalytics() {
        return TagManagerApi.isLoaded();
    }

    protected boolean canStopAnalytics() {
        return TagManagerApi.isLoaded();
    }

    @Override
    public void containerHolderLoaded(ContainerHolder holder) {
        this.startMonitor();
    }
}
