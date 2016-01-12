package fragments.calendar.events;

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

public class CalendarEventAllDayFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 3);
        long end = calendar.getTimeInMillis();
        Event allDayEvent = new Event("Enjoy Life", start, end);
        allDayEvent.setAllDay(true);

        List<Event> events = new ArrayList<Event>();
        events.add(allDayEvent);

        calendarView.getEventAdapter().setEvents(events);

        return calendarView;
    }

    @Override
    public String title() {
        return "All day event";
    }
}
