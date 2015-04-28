package com.telerik.examples.common.google;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;
import com.telerik.examples.R;
import com.telerik.examples.common.licensing.KeysRetriever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by ginev on 3/23/2015.
 */
public class TagManagerApi {
    private static TagManager instance = null;

    private static final String SCREEN_OPENED = "screen-opened";
    private static final String SCREEN_EVENT = "screen-event";

    private static final String KEY_SCREEN_NAME = "screen-name-key";
    private static final String KEY_EVENT_NAME = "event-name-key";
    private static final String KEY_EVENT_LOCATION = "event-location-key";


    private static ArrayList<ContainerHolderLoadedCallback> loadedListeners = new ArrayList<ContainerHolderLoadedCallback>();
    private static boolean isStarted;

    private TagManagerApi() {

    }

    public static void addContainerHolderLoadedCallback(ContainerHolderLoadedCallback callback) {
        if (loadedListeners.contains(callback)) {
            return;
        }
        loadedListeners.add(callback);
    }

    public static void removeContainerHolderLoadedCallback(ContainerHolderLoadedCallback callback) {
        if (!loadedListeners.contains(callback)) {
            return;
        }

        loadedListeners.remove(callback);
    }

    public static void start() {
        isStarted = true;
    }

    public static void stop() {
        TagManagerApi.refreshContainer();
        isStarted = false;
    }

    public static void refreshContainer() {
        if (!isStarted) {
            return;
        }

        if (isLoaded()) {
            ContainerHolderSingleton.getContainerHolder().refresh();
        }
    }

    public static void init(final Context context) {
        PendingResult<ContainerHolder> result = TagManager.getInstance(context).loadContainerPreferNonDefault(KeysRetriever.getTagManagerId(), R.raw.tag_manager_container_v9);
        result.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("Telerik Examples: ", "Could not load tag manager container.");
                    return;
                }

                instance = TagManager.getInstance(context);
                instance.setVerboseLoggingEnabled(true);
                ContainerHolderSingleton.setContainerHolder(containerHolder);

                notifyCallbacks(containerHolder);
            }
        });
    }

    public static boolean isLoaded() {
        return instance != null;
    }

    public static boolean isStarted(){
        return isStarted;
    }

    public static void registerScreen(String screenName, Map<String, Object> params) {
        if (!isStarted) {
            throw new IllegalStateException("monitoring not started.");
        }
        Map<String, Object> map = new Hashtable<String, Object>();
        map.put(KEY_SCREEN_NAME, screenName);
        for (String key : params.keySet()) {
            map.put(key, params.get(key));
        }
        instance.getDataLayer().pushEvent(SCREEN_OPENED, map);
    }

    public static void registerScreenEvent(String screenName, String eventName, Map<String, Object> params) {
        if (!isStarted) {
            throw new IllegalStateException("monitoring not started.");
        }
        Map<String, Object> map = DataLayer.mapOf(KEY_EVENT_NAME, eventName, KEY_EVENT_LOCATION, screenName);
        if (params != null) {
            for (String key : params.keySet()) {
                map.put(key, params.get(key));
            }
        }
        instance.getDataLayer().pushEvent(SCREEN_EVENT, map);
    }

    public interface ContainerHolderLoadedCallback {
        void containerHolderLoaded(ContainerHolder holder);
    }

    private static void notifyCallbacks(ContainerHolder holder) {
        for (ContainerHolderLoadedCallback callback : loadedListeners) {
            callback.containerHolderLoaded(holder);
        }
    }
}
