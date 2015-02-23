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

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarInitXmlFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.example_calendar_init, null);

        RadCalendarView calendarView = (RadCalendarView) root.findViewById(R.id.calendarView);
        // customize calendar here

        return root;
    }

    @Override
    public String title() {
        return "Init From Xml";
    }
}
