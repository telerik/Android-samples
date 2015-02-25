package com.telerik.examples.examples.calendar;

import android.graphics.Color;

import com.telerik.widget.calendar.CalendarAdapter;
import com.telerik.widget.calendar.RadCalendarView;

public class CircleCellsCalendarAdapter extends CalendarAdapter {
    final int BACKGROUND_COLOR = Color.parseColor("#F2F2F2");
    int purpleColor = Color.parseColor("#511749");

    CircleCellsCalendarAdapter(RadCalendarView calendar) {
        super(calendar);
    }
}