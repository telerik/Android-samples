package fragments.calendar.events;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.CalendarDayCell;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.events.Event;
import com.telerik.widget.calendar.events.EventRenderer;
import com.telerik.widget.calendar.events.EventsDisplayMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import activities.ExampleFragment;

public class CalendarEventRenderCustom extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 3);
        long end = calendar.getTimeInMillis();
        Event newEvent1 = new Event("Event 1", start, end);
        newEvent1.setEventColor(Color.CYAN);
        Event newEvent2 = new Event("Event 2", start, end);
        newEvent2.setEventColor(Color.MAGENTA);
        Event newEvent3 = new Event("Event 3", start, end);
        newEvent3.setEventColor(Color.RED);
        Event newEvent4 = new Event("Event 4", start, end);
        newEvent4.setEventColor(Color.GREEN);
        Event newEvent5 = new Event("Event 5", start, end);
        newEvent5.setEventColor(Color.BLUE);

        List<Event> events = new ArrayList<Event>();
        events.add(newEvent1);
        events.add(newEvent2);
        events.add(newEvent3);
        events.add(newEvent4);
        events.add(newEvent5);

        calendarView.getEventAdapter().setEvents(events);

        // >> calendar-custom-event-renderer-init
        MyEventRenderer eventRenderer = new MyEventRenderer(getContext());
        calendarView.getEventAdapter().setRenderer(eventRenderer);
        // << calendar-custom-event-renderer-init

        calendarView.setSelectionMode(CalendarSelectionMode.Single);
        calendarView.setEventsDisplayMode(EventsDisplayMode.Inline);

        return calendarView;
    }

    @Override
    public String title() {
        return "Event renderer Custom";
    }

    // >> calendar-custom-event-renderer
    public class MyEventRenderer extends EventRenderer {

        int shapeSpacing = 25;
        int shapeRadius = 10;
        Paint paint;

        public MyEventRenderer(Context context) {
            super(context);

            paint = new Paint();
            paint.setAntiAlias(true);
        }

        @Override
        public void renderEvents(Canvas canvas, CalendarDayCell cell) {
            int startX = cell.getLeft() + shapeSpacing;
            int startY = cell.getTop() + shapeSpacing;

            Rect drawTextRect = new Rect();
            if (cell.getText() != null) {
                String text = cell.getText();
                cell.getTextPaint().getTextBounds(text, 0, text.length(), drawTextRect);
            }

            int x = startX;
            int y = startY;

            int spacingForDate = drawTextRect.width();

            for(int i = 0; i < cell.getEvents().size();i++) {
                Event e = cell.getEvents().get(i);
                paint.setColor(e.getEventColor());
                canvas.drawCircle(x, y, shapeRadius, paint);
                x += shapeSpacing;
                if(x > cell.getRight() - spacingForDate - shapeSpacing) {
                    x = startX;
                    y += shapeSpacing;
                }
            }
        }
    }
    // << calendar-custom-event-renderer
}
