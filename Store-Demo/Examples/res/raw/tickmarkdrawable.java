package com.telerik.examples.examples.sidedrawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.telerik.android.common.Util;

public class TickMarkDrawable extends Drawable {
    Paint white = new Paint();

    public TickMarkDrawable() {
        white.setColor(Color.WHITE);
        white.setAntiAlias(true);
        white.setStyle(Paint.Style.STROKE);
        white.setStrokeWidth(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 2));
    }

    @Override
    public void draw(Canvas canvas) {
        float startX = this.getIntrinsicWidth() / 3;
        float endX = this.getIntrinsicWidth() / 2;
        float startY = this.getIntrinsicHeight() / 3;
        float endY = startY + this.getIntrinsicHeight() /  4;
        canvas.drawLine(startX, startY, endX, endY, white);

        startX = endX;
        startY = endY;
        endX = this.getIntrinsicWidth() - this.getIntrinsicWidth() / 3;
        endY = this.getIntrinsicHeight() / 5;
        canvas.drawLine(startX, startY, endX, endY, white);
    }

    @Override
    public void setAlpha(int alpha) {
        white.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        white.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
