package fragments.calendar.selection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Procedure;
import com.telerik.widget.calendar.CalendarCell;
import com.telerik.widget.calendar.CalendarCellType;
import com.telerik.widget.calendar.CalendarDayCell;
import com.telerik.widget.calendar.CalendarMonthCell;
import com.telerik.widget.calendar.RadCalendarView;

import java.util.Calendar;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarSelectionDisabledDatesFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        calendarView.setCustomizationRule(new Procedure<CalendarCell>() {
            Calendar calendar = Calendar.getInstance();

            @Override
            public void apply(CalendarCell calendarCell) {
                if (calendarCell instanceof CalendarMonthCell) {
                    return;
                }

                CalendarDayCell dayCell = (CalendarDayCell) calendarCell;
                if (dayCell.getCellType() != CalendarCellType.Date) {
                    return;
                }

                this.calendar.setTimeInMillis(dayCell.getDate());

                dayCell.setSelectable(!(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY));
            }
        });

        return calendarView;
    }

    @Override
    public String title() {
        return "Disabled dates";
    }
}
