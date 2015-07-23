package com.telerik.examples.examples.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Function;
import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.calendar.CalendarAdapter;
import com.telerik.widget.calendar.CalendarDayCell;
import com.telerik.widget.calendar.CalendarElement;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.CalendarStyles;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.decorations.CircularCellDecorator;
import com.telerik.widget.calendar.events.Event;
import com.telerik.widget.calendar.events.EventRenderer;
import com.telerik.widget.calendar.events.EventsDisplayMode;

import java.util.Calendar;
import java.util.List;

public class CalendarInlineEventsFragment extends ExampleFragmentBase {

    public CalendarInlineEventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.example_calendar_inline_events, container, false);

        final RadCalendarView calendarView = (RadCalendarView) root.findViewById(R.id.calendarView);

        Function<Long, Integer> customColorFunction = new Function<Long, Integer>() {
            private Calendar calendar = Calendar.getInstance();
            private int day;

            @Override
            public Integer apply(Long argument) {
                calendar.setTimeInMillis(argument);
                day = calendar.get(Calendar.DAY_OF_WEEK);
                if (day == Calendar.SATURDAY || day == Calendar.SUNDAY)
                    return Color.WHITE;

                return null;
            }
        };

        calendarView.setDateToColor(customColorFunction);
        calendarView.setDayNameToColor(customColorFunction);

        calendarView.setAdapter(new CustomCalendarAdapter(calendarView));
        calendarView.getAdapter().setStyle(CalendarStyles.materialDark(getActivity()));
        calendarView.setBackgroundColor(Color.TRANSPARENT);

        // Setting events
        calendarView.setSelectionMode(CalendarSelectionMode.Single);
        calendarView.setEventsDisplayMode(EventsDisplayMode.Inline);

        // Customizations
        CalendarAdapter adapter = calendarView.getAdapter();
        adapter.setDateCellBackgroundColor(Color.TRANSPARENT, Color.TRANSPARENT);
        adapter.setTodayCellBackgroundColor(Color.TRANSPARENT);
        adapter.setSelectedCellBackgroundColor(Color.TRANSPARENT);
        adapter.setDayNameBackgroundColor(Color.TRANSPARENT);
        adapter.setTitleBackgroundColor(Color.TRANSPARENT);

        adapter.setTitleTextColor(Color.parseColor("#391033"));
        adapter.setDayNameTextColor(Color.parseColor("#A75065"));
        adapter.setDateTextColor(Color.parseColor("#391033"), Color.parseColor("#A85066"));
        adapter.setTodayCellTextColor(Color.BLACK);
        adapter.setTodayCellTypeFace(Typeface.create("sans-serif", Typeface.BOLD));
        adapter.setSelectedCellTextColor(Color.WHITE);
        adapter.setTitleTextSize(Util.getDimen(TypedValue.COMPLEX_UNIT_SP, 38));
        adapter.setTitleTypeFace(Typeface.create("sans-serif-light", Typeface.NORMAL));
        adapter.setTitleTextPosition(CalendarElement.LEFT | CalendarElement.CENTER_VERTICAL);
        adapter.setTodayCellBorderColor(Color.TRANSPARENT);

        adapter.setDateTextPosition(CalendarElement.CENTER);
        adapter.setDayNameTextPosition(CalendarElement.CENTER);

        adapter.setInlineEventsBackgroundColor(Color.parseColor("#391033"));

        calendarView.setHorizontalScroll(true);
        calendarView.setDrawingVerticalGridLines(false);
        calendarView.getGridLinesLayer().setColor(Color.parseColor("#E16F85"));
        calendarView.setTitleHeight((int) Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 100));
        calendarView.title().setPaddingHorizontal((int) Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 10));

        CircularCellDecorator decorator = new CircularCellDecorator(calendarView);
        decorator.setStroked(false);
        decorator.setColor(Color.parseColor("#391033"));
        decorator.setScale(.7F);

        calendarView.setCellDecorator(decorator);
        calendarView.getEventAdapter().setRenderer(new CustomEventRenderer(getActivity()));

        CalendarEventsHelper calendarEventsHelper = new CalendarEventsHelper();
        List<Event> events = calendarEventsHelper.generateEvents();
        calendarEventsHelper.updateEventsColors(events, Color.WHITE, Color.parseColor("#FAABB9"));

        calendarView.getEventAdapter().setEvents(events);
        calendarView.notifyDataChanged();

        calendarView.getGestureManager().setDoubleTapToChangeDisplayMode(false);
        calendarView.getGestureManager().setPinchOpenToChangeDisplayMode(false);
        calendarView.getGestureManager().setPinchCloseToChangeDisplayMode(false);

        return root;
    }

    class CustomEventRenderer extends EventRenderer {

        public CustomEventRenderer(Context context) {
            super(context);
        }

        @Override
        public void renderEvents(Canvas canvas, CalendarDayCell cell) {
            //super.renderEvents(canvas, cell); Prevent rendering.
        }
    }
}
