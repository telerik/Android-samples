package com.telerik.examples.examples.sidedrawer;

public interface AsyncTaskNotificationListener<T> {
    void onTaskFinished(AsyncTaskWithNotifications asyncTask, T result);
    void onTaskCancelled(AsyncTaskWithNotifications asyncTask);
}