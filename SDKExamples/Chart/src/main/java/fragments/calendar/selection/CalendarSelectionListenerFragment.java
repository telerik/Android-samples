package fragments.calendar.selection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.telerik.widget.calendar.RadCalendarView;

import java.util.Calendar;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarSelectionListenerFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        calendarView.setOnSelectedDatesChangedListener(new RadCalendarView.OnSelectedDatesChangedListener() {
            @Override
            public void onSelectedDatesChanged(RadCalendarView.SelectionContext selectionContext) {
                List<Long> datesAdded = selectionContext.datesAdded();
                List<Long> datesRemoved = selectionContext.datesRemoved();
                List<Long> oldSelection = selectionContext.oldSelection();
                List<Long> newSelection = selectionContext.newSelection();

                Calendar calendar = Calendar.getInstance();
                String log = "";
                log += "=======================================\n";
                log += "Dates added:\n";
                for (long date : datesAdded) {
                    calendar.setTimeInMillis(date);
                    log += calendar.getTime().toString() + "\n";
                }
                log += datesAdded.size() + "\n";

                log += "------------------------------------";

                log += "\nDates removed:\n";
                for (long date : datesRemoved) {
                    calendar.setTimeInMillis(date);
                    log += calendar.getTime().toString() + "\n";
                }
                log += datesRemoved.size() + "\n";

                log += "------------------------------------";

                log += "\nNew selection:\n";
                for (long date : newSelection) {
                    calendar.setTimeInMillis(date);
                    log += calendar.getTime().toString() + "\n";
                }
                log += newSelection.size() + "\n";

                log += "------------------------------------";

                log += "\nOld selection:\n";
                for (long date : oldSelection) {
                    calendar.setTimeInMillis(date);
                    log += calendar.getTime().toString() + "\n";
                }
                log += oldSelection.size() + "\n";

                Toast.makeText(getActivity(), log, Toast.LENGTH_LONG).show();
            }
        });

        return calendarView;
    }

    @Override
    public String title() {
        return "Listener";
    }
}
