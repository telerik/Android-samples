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

import java.util.Calendar;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarEventReadRangeFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        EventReadAdapter adapter = new EventReadAdapter(calendarView);
        calendarView.setEventAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 7);
        long end = calendar.getTimeInMillis();

        EventQueryToken token = adapter.getEventsQueryToken();
        token.setRange(start, end);

        adapter.readEventsAsync();

        return calendarView;
    }

    @Override
    public String title() {
        return "Read range";
    }
}
