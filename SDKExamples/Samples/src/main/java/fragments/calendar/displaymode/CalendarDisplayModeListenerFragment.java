package fragments.calendar.displaymode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.telerik.widget.calendar.CalendarDisplayMode;
import com.telerik.widget.calendar.RadCalendarView;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarDisplayModeListenerFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());
        calendarView.setOnDisplayModeChangedListener(new RadCalendarView.OnDisplayModeChangedListener() {
            @Override
            public void onDisplayModeChanged(CalendarDisplayMode calendarDisplayMode, CalendarDisplayMode calendarDisplayMode2) {
                String msg = calendarDisplayMode2.name();

                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        return calendarView;
    }

    @Override
    public String title() {
        return "Listener";
    }
}
