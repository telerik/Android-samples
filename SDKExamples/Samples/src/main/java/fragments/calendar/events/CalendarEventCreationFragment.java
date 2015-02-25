package fragments.calendar.events;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.events.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarEventCreationFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 3);
        long end = calendar.getTimeInMillis();
        Event newEvent = new Event("Enjoy Life", start, end);

        calendar.add(Calendar.HOUR, 1);
        start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 1);
        end = calendar.getTimeInMillis();
        Event newEvent2 = new Event("Walk to work", start, end);
        newEvent2.setEventColor(Color.GREEN);

        List<Event> events = new ArrayList<Event>();
        events.add(newEvent);
        events.add(newEvent2);

        calendarView.getEventAdapter().setEvents(events);

        return calendarView;
    }

    @Override
    public String title() {
        return "Creation";
    }
}
