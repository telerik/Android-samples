package com.telerik.examples.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class InputBlocker extends FrameLayout {

    private boolean interceptTouch;

    public InputBlocker(Context context) {
        super(context);
    }

    public InputBlocker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInterceptTouch(boolean value) {
        this.interceptTouch = value;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.interceptTouch;
    }
}
