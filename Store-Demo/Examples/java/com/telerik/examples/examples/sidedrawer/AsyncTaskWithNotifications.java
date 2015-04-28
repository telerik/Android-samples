package com.telerik.examples.examples.sidedrawer;

import android.os.AsyncTask;

public abstract class AsyncTaskWithNotifications<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private AsyncTaskNotificationListener<Result> listener;

    public AsyncTaskWithNotifications(AsyncTaskNotificationListener<Result> listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        if(this.listener != null)
            this.listener.onTaskFinished(this, result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        if(this.listener != null)
            this.listener.onTaskCancelled(this);
    }
}
