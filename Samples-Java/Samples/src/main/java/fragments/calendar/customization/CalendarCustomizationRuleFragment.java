package fragments.calendar.customization;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Procedure;
import com.telerik.widget.calendar.CalendarCell;
import com.telerik.widget.calendar.RadCalendarView;

import java.util.Calendar;

import activities.ExampleFragment;

public class CalendarCustomizationRuleFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RadCalendarView calendarView = new RadCalendarView(getActivity());

        // >> calendar-customizations-customization-rule
        final Calendar calendar = Calendar.getInstance();
        calendarView.setCustomizationRule(new Procedure<CalendarCell>() {
            @Override
            public void apply(CalendarCell calendarCell) {
                calendar.setTimeInMillis(calendarCell.getDate());
                if(calendar.get(Calendar.DAY_OF_MONTH) == 21 &&
                        calendar.get(Calendar.MONTH) ==
                                Calendar.getInstance().get(Calendar.MONTH)) {
                    calendarCell.setBackgroundColor(Color.parseColor("#FF00A1"), Color.parseColor("#F988CF"));
                } else {
                    calendarCell.setBackgroundColor(Color.parseColor("#FFFFFF"), Color.parseColor("#FBFBFB"));
                }
            }
        });
        // << calendar-customizations-customization-rule

        return calendarView;
    }

    @Override
    public String title() {
        return "Customization Rules";
    }
}
