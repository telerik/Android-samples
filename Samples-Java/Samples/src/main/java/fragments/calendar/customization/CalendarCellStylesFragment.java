package fragments.calendar.customization;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.CalendarDayCellFilter;
import com.telerik.widget.calendar.CalendarDayCellStyle;
import com.telerik.widget.calendar.CalendarMonthCellFilter;
import com.telerik.widget.calendar.CalendarMonthCellStyle;
import com.telerik.widget.calendar.RadCalendarView;

import activities.ExampleFragment;

public class CalendarCellStylesFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RadCalendarView calendarView = new RadCalendarView(getActivity());

        // >> calendar-customizations-cell-styles-days
        CalendarDayCellFilter weekendCellFilter = new CalendarDayCellFilter();
        weekendCellFilter.setIsWeekend(true);
        CalendarDayCellStyle weekendCellStyle = new CalendarDayCellStyle();
        weekendCellStyle.setFilter(weekendCellFilter);
        weekendCellStyle.setTextColor(Color.RED);
        calendarView.addDayCellStyle(weekendCellStyle);

        CalendarDayCellFilter todayCellFilter = new CalendarDayCellFilter();
        todayCellFilter.setIsToday(true);
        CalendarDayCellStyle todayCellStyle = new CalendarDayCellStyle();
        todayCellStyle.setFilter(todayCellFilter);
        todayCellStyle.setBorderColor(Color.GREEN);
        float widthInDp = 4;
        float widthInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                widthInDp, getResources().getDisplayMetrics());
        todayCellStyle.setBorderWidth(widthInPixels);
        calendarView.addDayCellStyle(todayCellStyle);
        // << calendar-customizations-cell-styles-days

        // >> calendar-customizations-cell-styles-months
        CalendarMonthCellFilter monthCellTitleFilter = new CalendarMonthCellFilter();
        monthCellTitleFilter.setTextIsMonthName(true);
        CalendarMonthCellStyle monthCellTitleStyle = new CalendarMonthCellStyle();
        monthCellTitleStyle.setFilter(monthCellTitleFilter);
        monthCellTitleStyle.setTextColor(Color.BLUE);
        calendarView.addMonthCellStyle(monthCellTitleStyle);
        // << calendar-customizations-cell-styles-months

        return calendarView;
    }

    @Override
    public String title() {
        return "Cell Styles";
    }
}
