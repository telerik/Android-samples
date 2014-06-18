package com.telerik.examples.primitives;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by ginev on 16/06/2014.
 */
public class SquareLinearLayout extends LinearLayout {

    public SquareLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public SquareLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
