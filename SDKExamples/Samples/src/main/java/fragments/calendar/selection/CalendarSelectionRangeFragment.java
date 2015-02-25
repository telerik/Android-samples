package fragments.calendar.selection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.DateRange;
import com.telerik.widget.calendar.RadCalendarView;

import java.util.Calendar;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarSelectionRangeFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 15);
        long start = calendar.getTimeInMillis();
        calendar.set(Calendar.DAY_OF_MONTH, 22);
        long end = calendar.getTimeInMillis();

        calendarView.setSelectionMode(CalendarSelectionMode.Range);
        calendarView.setSelectedRange(new DateRange(start, end));

        return calendarView;
    }

    @Override
    public String title() {
        return "Range";
    }
}
