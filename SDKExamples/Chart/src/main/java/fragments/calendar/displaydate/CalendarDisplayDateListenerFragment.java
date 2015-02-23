package fragments.calendar.displaydate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.telerik.widget.calendar.RadCalendarView;

import java.util.Calendar;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarDisplayDateListenerFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RadCalendarView calendarView = new RadCalendarView(getActivity());

        calendarView.setOnDisplayDateChangedListener(new RadCalendarView.OnDisplayDateChangedListener() {
            Calendar calendar = Calendar.getInstance();

            @Override
            public void onDisplayDateChanged(long oldDate, long newDate) {
                calendar.setTimeInMillis(newDate);
                Toast.makeText(getActivity(), calendar.getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return calendarView;
    }

    @Override
    public String title() {
        return "Listener";
    }
}
