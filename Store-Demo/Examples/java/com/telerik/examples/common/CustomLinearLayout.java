package com.telerik.examples.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by ginev on 3/6/2015.
 */

public class CustomLinearLayout extends LinearLayout {


    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int availableWidth = ((ViewGroup)this.getParent()).getMeasuredWidth();
        int availableHeight = ((ViewGroup)this.getParent()).getMeasuredHeight();
        int newHeightSpec = Math.max(availableHeight, availableWidth);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(newHeightSpec, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
