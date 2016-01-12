package fragments.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.calendar.RadCalendarView;

import activities.ExampleFragment;

public class CalendarInitFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_calendar_example, container, false);
        RadCalendarView calendarView = new RadCalendarView(getActivity());
        rootView.addView(calendarView);
        return rootView;
    }

    @Override
    public String title() {
        return "Init from code";
    }
}
