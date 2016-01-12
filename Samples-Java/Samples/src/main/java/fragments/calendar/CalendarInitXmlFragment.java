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

public class CalendarInitXmlFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.example_calendar_init, container, false);

        RadCalendarView calendarView = (RadCalendarView) rootView.findViewById(R.id.calendarView);
        // customize calendar here

        return rootView;
    }

    @Override
    public String title() {
        return "Init from xml";
    }
}
