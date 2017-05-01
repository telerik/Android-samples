package fragments.calendar.events;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.telerik.android.sdk.R;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.EventsManager;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.events.Event;
import com.telerik.widget.calendar.events.EventsDisplayMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import activities.ExampleFragment;

public class CalendarEventInlineModeCustom extends Fragment implements ExampleFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RadCalendarView calendarView = new RadCalendarView(getActivity());

        // Setting the events display mode
        calendarView.setSelectionMode(CalendarSelectionMode.Single);
        calendarView.setEventsDisplayMode(EventsDisplayMode.Inline);

        // Creating some events
        List<Event> events = new ArrayList<Event>();
        Calendar calendar = Calendar.getInstance();

        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 1);
        long end = calendar.getTimeInMillis();

        events.add(new Event("Test1", start, end));

        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.HOUR, -1);
        start = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, 3);
        end = calendar.getTimeInMillis();

        events.add(new Event("Test2", start, end));

        calendarView.getEventAdapter().setEvents(events);


        // >> calendar-custom-inline-events-adapter-init
        MyInlineEventsAdapter adapter = new MyInlineEventsAdapter(getContext());
        calendarView.eventsManager().setAdapter(adapter);
        // << calendar-custom-inline-events-adapter-init

        return calendarView;
    }

    @Override
    public String title() {
        return "Inline mode Custom";
    }

    // >> calendar-custom-inline-events-adapter
    public class MyInlineEventsAdapter extends ArrayAdapter<EventsManager.EventInfo> {
        private LayoutInflater layoutInflater;

        public MyInlineEventsAdapter(Context context) {
            super(context, R.layout.custom_inline_event_layout);
            this.layoutInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            ViewHolder holder;

            if (view == null) {
                view = layoutInflater.inflate(
                        R.layout.custom_inline_event_layout, parent, false);

                holder = new ViewHolder();
                holder.eventTitle = (TextView) view.findViewById(R.id.event_title);
                holder.eventTime = (TextView) view.findViewById(R.id.event_time);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            EventsManager.EventInfo eventInfo = getItem(position);
            Event event = eventInfo.originalEvent();
            holder.eventTitle.setTextColor(event.getEventColor());
            holder.eventTitle.setText(event.getTitle());
            String eventTime = String.format("%s - %s",
                    eventInfo.startTimeFormatted(), eventInfo.endTimeFormatted());
            holder.eventTime.setText(eventTime);

            return view;
        }

        class ViewHolder {
            TextView eventTitle;
            TextView eventTime;
        }
    }
    // << calendar-custom-inline-events-adapter
}
