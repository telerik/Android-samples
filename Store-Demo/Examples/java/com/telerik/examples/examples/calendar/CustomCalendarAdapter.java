package com.telerik.examples.examples.calendar;

import com.telerik.widget.calendar.CalendarAdapter;
import com.telerik.widget.calendar.CalendarDayCell;
import com.telerik.widget.calendar.RadCalendarView;

/**
 * Created by ajekov on 6/30/2015.
 */
public class CustomCalendarAdapter extends CalendarAdapter {

    public CustomCalendarAdapter(RadCalendarView owner) {
        super(owner);
    }

    @Override
    public CalendarDayCell getDateCell() {
        CustomDayCell cell = new CustomDayCell(owner);

        this.dateCells.add(cell);

        return cell;
    }
}
