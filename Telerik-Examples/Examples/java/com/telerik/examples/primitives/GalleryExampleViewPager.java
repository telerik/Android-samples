package com.telerik.examples.primitives;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ginev on 01/04/14.
 * <p/>
 * This custom ViewPager allows for disabling the scrolling between the separate pages.
 */
public class GalleryExampleViewPager extends ExampleViewPagerBase {

    private boolean canSwipe = true;

    public GalleryExampleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanSwipe(boolean canSwipe) {
        this.canSwipe = canSwipe;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.canSwipe) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.canSwipe) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }
}
