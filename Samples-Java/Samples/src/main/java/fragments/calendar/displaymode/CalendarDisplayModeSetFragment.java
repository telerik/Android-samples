package fragments.calendar.displaymode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.CalendarDisplayMode;
import com.telerik.widget.calendar.RadCalendarView;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarDisplayModeSetFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        // Will change using the current setting for animations in the calendar.
        //calendarView.setDisplayMode(CalendarDisplayMode.Week);

        // Will change using the passed setting for animation.
        calendarView.changeDisplayMode(CalendarDisplayMode.Week, false);

        return calendarView;
    }

    @Override
    public String title() {
        return "Set";
    }
}
