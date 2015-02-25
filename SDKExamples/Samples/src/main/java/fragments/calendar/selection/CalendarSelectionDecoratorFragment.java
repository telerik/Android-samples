package fragments.calendar.selection;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.CalendarElement;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.decorations.CircularRangeDecorator;
import com.telerik.widget.calendar.decorations.RangeDecorator;

import activities.ExampleFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarSelectionDecoratorFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        calendarView.getAdapter().setDateTextPosition(CalendarElement.CENTER);
        calendarView.getAdapter().setSelectedCellTextColor(Color.WHITE);
        calendarView.setShowGridLines(false);

        calendarView.setSelectionMode(CalendarSelectionMode.Range);

        RangeDecorator decorator = new CircularRangeDecorator(calendarView);
        decorator.setScale(1f);
        decorator.setShapeScale(.77f);
        decorator.setColor(Color.MAGENTA);
        decorator.setShapeColor(Color.DKGRAY);

        calendarView.setCellDecorator(decorator);

        return calendarView;
    }

    @Override
    public String title() {
        return "Decorator";
    }
}
