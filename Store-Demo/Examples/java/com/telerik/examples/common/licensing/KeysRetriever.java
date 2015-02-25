package com.telerik.examples.common.licensing;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * This class is used to access the TelerikApiKeys values without directly referencing that type as
 * it is not always publicly available, e.g. when the source is distributed to customers.
 */
public final class KeysRetriever {
    public static final String ANALYTICS_API_KEY = "ANALYTICS_API_KEY";
    public static final String FEEDBACK_API_KEY = "FEEDBACK_API_KEY";

    private static final String errorMessage = "Could not load license keys holder class";

    public static String getAnalyticsKey() {
        try {
            Class keyHolderClass = Class.forName("com.telerik.examples.common.TelerikApiKeys");
            Field analyticsField = keyHolderClass.getField(ANALYTICS_API_KEY);
            return (String) analyticsField.get(null);
        } catch (ClassNotFoundException exception) {
            Log.e("Examples", errorMessage);
        } catch (IllegalAccessException ex) {
            Log.e("Examples", errorMessage);
        } catch (NoSuchFieldException e) {
            Log.e("Examples", errorMessage);
        }

        return "";
    }

    public static String getFeedbackKey() {
        try {
            Class keyHolderClass = Class.forName("com.telerik.examples.common.TelerikApiKeys");
            Field feedbackField = keyHolderClass.getField(FEEDBACK_API_KEY);
            return (String) feedbackField.get(null);
        } catch (ClassNotFoundException exception) {
            Log.e("Examples", errorMessage);
        } catch (IllegalAccessException ex) {
            Log.e("Examples", errorMessage);
        } catch (NoSuchFieldException e) {
            Log.e("Examples", errorMessage);
        }

        return "";
    }
}
