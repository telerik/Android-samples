package com.telerik.examples.primitives;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ExamplesGridView extends GridView {

    private OnOverScrollByHandler onOverScrollByHandler;

    public ExamplesGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExamplesGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean result = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        if (this.onOverScrollByHandler != null) {
            this.onOverScrollByHandler.onOverScrollByHandler(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        }
        return result;
    }

    public void setOnOverScrollByHandler(OnOverScrollByHandler listener) {
        this.onOverScrollByHandler = listener;
    }

    public interface OnOverScrollByHandler {
        void onOverScrollByHandler(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);
    }
}
