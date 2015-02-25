package com.telerik.examples.primitives;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomVerticalScrollView extends ScrollView implements HVScrollView {
    public CustomVerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setClickable(true);
    }

    @Override
    public void smoothScrollTo(int position) {
        this.smoothScrollTo(0, position);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isClickable()) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isClickable()) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }
}
