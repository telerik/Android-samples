package com.telerik.examples.primitives;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by ginev on 01/04/14.
 */
public class CustomHorizontalScrollView extends HorizontalScrollView implements HVScrollView {
    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setClickable(true);
    }

    @Override
    public void smoothScrollTo(int position) {
        this.smoothScrollTo(position, 0);
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
