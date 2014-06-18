package com.telerik.examples.primitives.drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by ginev on 10/04/2014.
 * <p/>
 * A custom drawable class that draws a isosceles triangle using the
 * bottom edge of the bounds of this drawable as a base and the centre of the top edge of
 * the bounds as
 * .
 */
public class Triangle extends Drawable {

    private Paint paint;
    private float strokeWidth = 0;
    private int strokeColor = Color.TRANSPARENT;
    private int color = Color.TRANSPARENT;
    private int alpha;

    public Triangle() {
        this.paint = new Paint();
    }

    @Override
    public int getOpacity() {
        return 1;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = this.getBounds();

        Path triangle = new Path();
        triangle.moveTo(bounds.left, bounds.top);
        triangle.lineTo(bounds.right, bounds.top);
        triangle.lineTo(bounds.left + (bounds.right - bounds.left) / 2, bounds.bottom);
        triangle.lineTo(bounds.left, bounds.top);

        this.paint.setColor(this.color);
        this.paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(triangle, this.paint);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(this.strokeColor);
        this.paint.setStrokeWidth(this.strokeWidth);
        canvas.drawPath(triangle, this.paint);
    }


    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public int getAlpha() {
        return this.alpha;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public int getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeColor(int color) {
        this.strokeColor = color;
    }

    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
    }

    public float getStrokeWidth() {
        return this.strokeWidth;
    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }
}
