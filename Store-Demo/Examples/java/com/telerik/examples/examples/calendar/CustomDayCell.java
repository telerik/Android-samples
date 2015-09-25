package com.telerik.examples.examples.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

import com.telerik.android.common.Util;
import com.telerik.widget.calendar.CalendarDayCell;
import com.telerik.widget.calendar.RadCalendarView;

/**
 * Created by ajekov on 6/30/2015.
 */
public class CustomDayCell extends CalendarDayCell {
    private Paint paint;
    private float radius;

    public CustomDayCell(RadCalendarView owner) {
        super(owner);

        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.parseColor("#723943"));
        this.paint.setStyle(Paint.Style.FILL);
        this.radius = Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 3);
    }

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);

        if (getEvents() != null && getEvents().size() > 0 && !this.isSelected()) {
            canvas.drawCircle(getLeft() + (getWidth() / 2), getTop() + (getHeight() / 5), radius, paint);
        }
    }
}
