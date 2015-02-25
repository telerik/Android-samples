package fragments.calendar.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.events.read.EventQueryToken;
import com.telerik.widget.calendar.events.read.EventReadAdapter;
import com.telerik.widget.calendar.events.read.GenericResultCallback;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarEventReadOptionsFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        final EventReadAdapter adapter = new EventReadAdapter(calendarView);
        calendarView.setEventAdapter(adapter);

        EventReadAdapter.getAllCalendarsAsync(getActivity(), new GenericResultCallback<EventReadAdapter.CalendarInfo[]>() {
            @Override
            public void onResult(EventReadAdapter.CalendarInfo[] calendars) {
                String[] calendarIDs = new String[calendars.length];

                for (int i = 0; i < calendars.length; i++) {
                    calendarIDs[i] = calendars[i].id;
                }

                EventQueryToken token = EventQueryToken.getCalendarsById(calendarIDs);
                adapter.setEventsQueryToken(token);
                adapter.readEventsAsync();
            }
        });

        return calendarView;
    }

    @Override
    public String title() {
        return "Read events options";
    }
}
