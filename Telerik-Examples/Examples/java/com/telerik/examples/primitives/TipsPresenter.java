package com.telerik.examples.primitives;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.telerik.examples.R;
import com.telerik.examples.common.ExamplesApplicationContext;

public class TipsPresenter extends FrameLayout implements Button.OnClickListener {

    private ExamplesApplicationContext app;
    private Button btnOk;
    private Runnable showRunnable;
    private Handler showHandler;
    private long delay = 0;

    public TipsPresenter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setVisibility(View.GONE);
        this.app = (ExamplesApplicationContext) context.getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tips_presenter, this);
        this.btnOk = (Button) this.findViewById(R.id.btnOk);
        this.btnOk.setOnClickListener(this);
        this.showRunnable = new Runnable() {
            @Override
            public void run() {
                if (!app.getTipLearned()) {
                    showTip();
                }
            }
        };
    }

    public void setShowDelay(long milliseconds) {
        this.delay = milliseconds;
    }

    public void hide() {
        this.setVisibility(View.GONE);
    }

    public boolean show() {
        if (this.showHandler != null || this.app.getTipLearned()) {
            return false;
        }

        this.setVisibility(View.VISIBLE);
        return true;
    }

    public boolean scheduleShow() {
        if (this.showHandler != null || this.app.getTipLearned()) {
            return false;
        }

        Looper newLooper = Looper.myLooper();
        this.showHandler = new Handler(newLooper);
        this.showHandler.postDelayed(this.showRunnable, this.delay);
        return true;
    }

    public boolean cancelShow() {
        if (this.showHandler != null) {
            this.showHandler.removeCallbacks(this.showRunnable);

            this.showHandler = null;
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        this.setVisibility(GONE);
        this.app.setTipLearned(true);
    }

    private void showTip() {
        this.setVisibility(VISIBLE);
        if (this.showHandler != null) {
            this.showHandler.removeCallbacks(this.showRunnable);
        }
        this.showHandler = null;
    }
}
