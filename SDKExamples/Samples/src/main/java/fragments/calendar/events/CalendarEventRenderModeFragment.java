package fragments.calendar.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.events.Event;
import com.telerik.widget.calendar.events.EventRenderMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarEventRenderModeFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 3);
        long end = calendar.getTimeInMillis();
        Event newEvent = new Event("Enjoy Life", start, end);

        List<Event> events = new ArrayList<Event>();
        events.add(newEvent);

        calendarView.getEventAdapter().setEvents(events);

        calendarView.getEventAdapter().getRenderer().setEventRenderMode(EventRenderMode.Shape);

        return calendarView;
    }

    @Override
    public String title() {
        return "Render mode";
    }
}
