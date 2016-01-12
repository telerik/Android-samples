package fragments.calendar.selection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.RadCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarSelectionDatesFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        List<Long> dates = new ArrayList<Long>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        dates.add(calendar.getTimeInMillis());
        calendar.set(Calendar.DAY_OF_MONTH, 17);
        dates.add(calendar.getTimeInMillis());
        calendar.set(Calendar.DAY_OF_MONTH, 18);
        dates.add(calendar.getTimeInMillis());
        calendar.set(Calendar.DAY_OF_MONTH, 22);
        dates.add(calendar.getTimeInMillis());

        calendarView.setSelectedDates(dates);

        return calendarView;
    }

    @Override
    public String title() {
        return "Set selected dates";
    }
}
