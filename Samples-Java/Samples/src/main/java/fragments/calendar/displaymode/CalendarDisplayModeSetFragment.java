package fragments.calendar.displaymode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.sdk.R;
import com.telerik.widget.calendar.CalendarDisplayMode;
import com.telerik.widget.calendar.RadCalendarView;

import activities.ExampleFragment;

public class CalendarDisplayModeSetFragment extends Fragment implements ExampleFragment {
    RadCalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        calendarView = new RadCalendarView(getActivity());
        calendarView.getGestureManager().setDoubleTapToChangeDisplayMode(false);
        setHasOptionsMenu(true);

        /*
         * Setting the display mode with `false` for animation. This way the change
         * will not be visible at the initial state of the calendar.
         */
        calendarView.changeDisplayMode(CalendarDisplayMode.Week, false);

        return calendarView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.calendar_display_modes, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        CalendarDisplayMode currentDisplayMode = calendarView.getDisplayMode();

        if (currentDisplayMode == CalendarDisplayMode.Month) {
            menu.findItem(R.id.itemMonth).setEnabled(false);
            menu.findItem(R.id.itemWeek).setEnabled(true);
            menu.findItem(R.id.itemYear).setEnabled(true);
        } else if (currentDisplayMode == CalendarDisplayMode.Week){
            menu.findItem(R.id.itemMonth).setEnabled(true);
            menu.findItem(R.id.itemWeek).setEnabled(false);
            menu.findItem(R.id.itemYear).setEnabled(true);
        }else if (currentDisplayMode == CalendarDisplayMode.Year){
            menu.findItem(R.id.itemMonth).setEnabled(true);
            menu.findItem(R.id.itemWeek).setEnabled(true);
            menu.findItem(R.id.itemYear).setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.itemWeek) {
            calendarView.changeDisplayMode(CalendarDisplayMode.Week, false);
            return true;
        } else if (itemId == R.id.itemMonth) {
            calendarView.changeDisplayMode(CalendarDisplayMode.Month, false);
            return true;
        }else if (itemId == R.id.itemYear) {
            calendarView.changeDisplayMode (CalendarDisplayMode.Year, false);
            return true;
        }

        return false;
    }

    @Override
    public String title() {
        return "Display modes";
    }
}
