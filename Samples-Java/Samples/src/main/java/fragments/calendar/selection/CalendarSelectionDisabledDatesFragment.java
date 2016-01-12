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

                if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    dayCell.setSelectable(false);
                } else {
                    dayCell.setSelectable(true);
                }
            }
        });

        return calendarView;
    }

    @Override
    public String title() {
        return "Selection disabled dates";
    }
}
