package fragments.calendar.displaydate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.RadCalendarView;

import java.util.GregorianCalendar;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarDisplayDateSetFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());
        calendarView.setDisplayDate(new GregorianCalendar(2022, 10, 1).getTimeInMillis());

        return calendarView;
    }

    @Override
    public String title() {
        return "Set";
    }
}
