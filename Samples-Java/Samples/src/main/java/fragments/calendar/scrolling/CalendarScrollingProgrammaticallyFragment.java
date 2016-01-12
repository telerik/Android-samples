package fragments.calendar.scrolling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.ScrollMode;

import activities.ExampleFragment;

public class CalendarScrollingProgrammaticallyFragment extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RadCalendarView calendarView = new RadCalendarView(getActivity());
        calendarView.setScrollMode(ScrollMode.None);
        calendarView.setHorizontalScroll(true);

        calendarView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() <= calendarView.getWidth() / 2) {
                        calendarView.animateToPrevious();
                    } else {
                        calendarView.animateToNext();
                    }
                }

                return true;
            }
        });

        return calendarView;
    }

    @Override
    public String title() {
        return "Scroll programmatically";
    }
}
