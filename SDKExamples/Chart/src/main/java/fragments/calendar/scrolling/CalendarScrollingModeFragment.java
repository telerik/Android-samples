package fragments.calendar.scrolling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.ScrollMode;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarScrollingModeFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());
        calendarView.setScrollMode(ScrollMode.Stack);

        return calendarView;
    }

    @Override
    public String title() {
        return "Mode";
    }
}
