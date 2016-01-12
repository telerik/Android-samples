package fragments.calendar.scrolling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.RadCalendarView;

import activities.ExampleFragment;

public class CalendarScrollingDirectionFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());
        calendarView.setHorizontalScroll(true);

        return calendarView;
    }

    @Override
    public String title() {
        return "Horizontal scroll";
    }
}
