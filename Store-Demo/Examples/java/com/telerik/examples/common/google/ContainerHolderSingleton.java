package com.telerik.examples.common.google;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Created by ginev on 3/23/2015.
 * <p/>
 * Singleton to hold the GTM Container (since it should be only created once
 * per run of the app).
 */
public class ContainerHolderSingleton {

    public static final String ANALYTICS_GOT_IT_MESSAGE = "android-got-it-message";
    public static final String ANALYTICS_GOT_IT_ID = "android-got-it-id";

    private static ContainerHolder containerHolder;

    /**
     * Utility class; don't instantiate.
     */
    private ContainerHolderSingleton() {

    }

    public static ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    public static void setContainerHolder(ContainerHolder c) {
        containerHolder = c;
    }
}

