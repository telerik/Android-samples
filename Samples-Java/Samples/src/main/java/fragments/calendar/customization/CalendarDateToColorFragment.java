package fragments.calendar.customization;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Function;
import com.telerik.widget.calendar.RadCalendarView;

import java.util.Calendar;

import activities.ExampleFragment;

public class CalendarDateToColorFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RadCalendarView calendarView = new RadCalendarView(getActivity());

        // >> calendar-customizations-date-to-color
        final Calendar calendar = Calendar.getInstance();
        calendarView.setDateToColor(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long aLong) {
                calendar.setTimeInMillis(aLong);
                if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    return Color.RED;
                }
                return null;
            }
        });
        // << calendar-customizations-date-to-color

        return calendarView;
    }

    @Override
    public String title() {
        return "Date to Color";
    }
}
