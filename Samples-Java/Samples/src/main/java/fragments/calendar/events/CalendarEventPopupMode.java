package fragments.calendar.events;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.events.Event;
import com.telerik.widget.calendar.events.EventsDisplayMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarEventPopupMode extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        // Setting the events display mode
        calendarView.setSelectionMode(CalendarSelectionMode.Single);
        calendarView.setEventsDisplayMode(EventsDisplayMode.Popup);

        // Creating some events
        List<Event> events = new ArrayList<Event>();
        Calendar calendar = Calendar.getInstance();

        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 1);
        long end = calendar.getTimeInMillis();

        events.add(new Event("Test1", start, end));

        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.HOUR, -1);
        start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 3);
        end = calendar.getTimeInMillis();

        events.add(new Event("Test2", start, end));

        calendarView.getEventAdapter().setEvents(events);

        // Customization
        calendarView.getAdapter().setPopupEventsWindowBackgroundColor(Color.BLACK);
        calendarView.getAdapter().setPopupEventTitleTextSize(22.0F);
        calendarView.getAdapter().setPopupEventTimeTextSize(14.0F);

        return calendarView;
    }

    @Override
    public String title() {
        return "Popup mode";
    }
}
