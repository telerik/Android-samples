package com.telerik.examples.examples.calendar;

import android.view.GestureDetector;

public class HorizontalFlingListener extends GestureDetector.SimpleOnGestureListener {
    private final CalendarMainFragment fragment;

    public HorizontalFlingListener(CalendarMainFragment calendarMainFragment) {
        this.fragment = calendarMainFragment;
    }
}
