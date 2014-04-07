package com.telerik.demo.seismograph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.TypedValue;
import android.view.View;

public class Needle extends View {

    private static final int NEEDLE_HEIGHT = 20;
    private static final int MAX_ACCELERATION = 20;

    private Paint needlePaint;
    private Path needleShape;

    private int halfNeedleHeight;

    private float offsetRight;
    private int width;
    private int halfHeight;
    private int pointerLeft;

    private float currentY = 0;

    public Needle(Context context, float offsetRight) {
        super(context);
        this.offsetRight = offsetRight;

        this.needlePaint = new Paint();
        this.needlePaint.setColor(Color.RED);
        this.needlePaint.setStyle(Paint.Style.FILL);

        this.needleShape = new Path();

        this.halfNeedleHeight = ((int) dpToPx(NEEDLE_HEIGHT)) >> 1;
    }

    public void updatePosition(float y) {
        invalidate();
        this.currentY = y;
    }

    public float typedValueToPixels(int valueType, float value) {
        return TypedValue.applyDimension(valueType, value, getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.halfHeight = h >> 1;
        this.pointerLeft = (int) (this.width - this.offsetRight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 20 is the max acceleration, 30 is the max value for the vertical axis, so 2/3 of 30 is 66.6%
        float y = (float) (this.halfHeight + (((this.halfHeight * (this.currentY / MAX_ACCELERATION)) * .666) * -1));

        this.needleShape.reset();
        this.needleShape.moveTo(this.width, y - this.halfNeedleHeight);
        this.needleShape.lineTo(this.pointerLeft - dpToPx(5), y);
        this.needleShape.lineTo(this.width, y + this.halfNeedleHeight);
        this.needleShape.close();

        canvas.drawPath(this.needleShape, this.needlePaint);
    }

    private float dpToPx(float value) {
        return typedValueToPixels(TypedValue.COMPLEX_UNIT_DIP, value);
    }
}
