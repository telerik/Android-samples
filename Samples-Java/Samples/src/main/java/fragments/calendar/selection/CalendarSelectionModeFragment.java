package fragments.calendar.selection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.RadCalendarView;

import activities.ExampleFragment;

public class CalendarSelectionModeFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        calendarView.setSelectionMode(CalendarSelectionMode.Range);
        //calendarView.setSelectionMode(CalendarSelectionMode.Single);
        //calendarView.setSelectionMode(CalendarSelectionMode.Multiple);

        return calendarView;
    }

    @Override
    public String title() {
        return "Selection modes";
    }
}
