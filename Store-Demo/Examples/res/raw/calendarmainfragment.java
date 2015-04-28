package com.telerik.examples.examples.calendar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.android.common.Procedure;
import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.calendar.CalendarCell;
import com.telerik.widget.calendar.CalendarCellType;
import com.telerik.widget.calendar.CalendarDayCell;
import com.telerik.widget.calendar.CalendarDisplayMode;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.ScrollMode;
import com.telerik.widget.calendar.WeekNumbersDisplayMode;
import com.telerik.widget.calendar.events.Event;
import com.telerik.widget.calendar.events.EventRenderMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * A fragment to be used for the first look example of RadCalendarView.
 */
public class CalendarMainFragment extends ExampleFragmentBase {

    private RadCalendarView calendarView;
    private Spinner calendarModesSpinner;
    private ListView listEvents;
    private Button btnShowToday;
    private TextView txtDayTitle;
    private ImageButton btnUpCaret;

    public static final int SELECTION_WEEK = 0;
    public static final int SELECTION_MONTH = 1;
    public static final int SELECTION_YEAR = 2;

    private final String[] corpAppointmentTitles = new String[]{
            "Job interview",
            "Conference call with HQ2",
            "Tokyo deal call",
            "Coordination meeting",
            "Brainstorming session"

    };
    private int corpAppointmentsColor = Color.parseColor("#FBAF5D");

    private final String[] personalMeetingTitles = new String[]{
            "Watch a movie",
            "Date with Candice",
            "Birthday party"

    };
    private int personalMeetingColor = Color.parseColor("#C23EB8");

    private final String[] sportTitles = new String[]{
            "Fitness",
            "Table tennis",
            "Football",
            "Mountain biking",
            "Jogging with Molly"

    };
    private int sportColor = Color.parseColor("#F35755");

    private final String[] funTitles = new String[]{
            "Yachting",
            "Theater evening",
            "Dinner with the Morgans",
            "Weekend barbecue",
            "Watch your favorite Show",
    };

    private int funColor = Color.parseColor("#47C2B4");

    public CalendarMainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar_main, null);
        this.calendarView = (RadCalendarView) root.findViewById(R.id.calendarView);

        this.btnShowToday = (Button) root.findViewById(R.id.btnShowToday);
        this.btnUpCaret = (ImageButton) root.findViewById(R.id.btnUpCaret);
        this.btnUpCaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getDisplayMode() == CalendarDisplayMode.Week) {
                    calendarModesSpinner.setSelection(SELECTION_MONTH);
                } else if (calendarView.getDisplayMode() == CalendarDisplayMode.Month) {
                    calendarModesSpinner.setSelection(SELECTION_YEAR);
                }
            }
        });
        this.txtDayTitle = (TextView) root.findViewById(R.id.txtDayTitle);
        this.btnShowToday.setText(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        this.btnShowToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar today = Calendar.getInstance();
                calendarView.setDisplayDate(today.getTimeInMillis());

                if (calendarView.getDisplayMode() == CalendarDisplayMode.Week) {
                    ArrayList<Long> dates = new ArrayList<Long>();
                    dates.add(today.getTimeInMillis());
                    calendarView.setSelectedDates(dates);
                }

                listEvents.setAdapter(new EventsListAdapter(getActivity(), 0, today.getTimeInMillis()));
                updateWeekModeDayString(true);
            }
        });
        this.calendarView.setScrollMode(ScrollMode.Combo);
        final Calendar calendar = Calendar.getInstance();
        this.calendarView.setDateToColor(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long argument) {
                calendar.setTimeInMillis(argument);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                    return Color.RED;

                return null;
            }
        });
        final int colorEnabled = calendarView.getAdapter().getDateCellBackgroundColorEnabled();
        final int colorDisabled = calendarView.getAdapter().getDateCellBackgroundColorDisabled();
        final int borderColor = Color.parseColor("#f1891b");
        final float borderWidth = Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 3);
        final Bitmap sun = BitmapFactory.decodeResource(getResources(), R.drawable.ic_calendar_sun);
        this.calendarView.setCustomizationRule(new Procedure<CalendarCell>() {
            @Override
            public void apply(CalendarCell argument) {
                if (argument.getCellType() == CalendarCellType.Date) {
                    calendar.setTimeInMillis(argument.getDate());
                    if (calendar.get(Calendar.DAY_OF_MONTH) == 6 || calendar.get(Calendar.DAY_OF_MONTH) == 7) {
                        argument.setBackgroundColor(Color.parseColor("#f9cc9d"), Color.parseColor("#f9cc9d"));
                        argument.setBorderColor(borderColor);
                        argument.setBorderWidth(borderWidth);
                        argument.setBitmap(sun);
                    } else {
                        argument.setBackgroundColor(colorEnabled, colorDisabled);
                        argument.setBorderColor(Color.TRANSPARENT);
                        argument.setBitmap(null);
                    }
                }
            }
        });
        this.calendarView.setSelectionMode(CalendarSelectionMode.Single);
        this.calendarView.setOnDisplayDateChangedListener(new RadCalendarView.OnDisplayDateChangedListener() {
            @Override
            public void onDisplayDateChanged(long oldValue, long newValue) {
                ((CalendarModesSpinnerAdapter) calendarModesSpinner.getAdapter()).notifyDataSetChanged();
            }
        });
        this.calendarView.setOnCellClickListener(new RadCalendarView.OnCellClickListener() {
            @Override
            public void onCellClick(CalendarCell clickedCell) {
                if (clickedCell instanceof CalendarDayCell) {
                    List<Long> selectedDates = calendarView.getSelectedDates();

                    if (selectedDates.size() > 0) {
                        if (calendarView.getDisplayMode() != CalendarDisplayMode.Week) {
                            calendarView.setDisplayDate(selectedDates.get(0));
                            calendarModesSpinner.setSelection(SELECTION_WEEK);
                        } else {
                            listEvents.setAdapter(new EventsListAdapter(getActivity(), 0, selectedDates.get(0)));
                            updateWeekModeDayString(false);
                        }
                    }
                }
            }
        });
        this.calendarView.setOnDisplayModeChangedListener(new RadCalendarView.OnDisplayModeChangedListener() {
            @Override
            public void onDisplayModeChanged(CalendarDisplayMode oldValue, CalendarDisplayMode newValue) {
                switch (newValue) {
                    case Week:
                        calendarModesSpinner.setSelection(SELECTION_WEEK);
                        break;
                    case Month:
                        calendarModesSpinner.setSelection(SELECTION_MONTH);
                        break;
                    case Year:
                        calendarModesSpinner.setSelection(SELECTION_YEAR);
                }
            }
        });
        this.listEvents = (ListView) root.findViewById(R.id.listEvents);
        this.calendarModesSpinner = (Spinner) root.findViewById(R.id.calendarModesSpinner);
        this.calendarModesSpinner.setAdapter(new CalendarModesSpinnerAdapter());
        this.calendarModesSpinner.setSelection(SELECTION_MONTH);
        this.calendarModesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case SELECTION_WEEK:
                        calendarView.changeDisplayMode(CalendarDisplayMode.Week, true);
                        long date;
                        if (calendarView.getSelectedDates() != null && calendarView.getSelectedDates().size() > 0) {
                            date = calendarView.getSelectedDates().get(0);
                        } else {
                            date = calendarView.getDisplayDate();
                        }
                        listEvents.setAdapter(new EventsListAdapter(getActivity(), 0, date));
                        updateWeekModeDayString(false);
                        btnShowToday.setVisibility(View.VISIBLE);
                        btnUpCaret.setVisibility(View.VISIBLE);
                        break;
                    case SELECTION_MONTH:
                        calendarView.changeDisplayMode(CalendarDisplayMode.Month, true);
                        listEvents.setAdapter(null);
                        btnShowToday.setVisibility(View.VISIBLE);
                        btnUpCaret.setVisibility(View.VISIBLE);
                        break;
                    case SELECTION_YEAR:
                        calendarView.changeDisplayMode(CalendarDisplayMode.Year, true);
                        listEvents.setAdapter(null);
                        btnShowToday.setVisibility(View.INVISIBLE);
                        btnUpCaret.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.calendarView.setShowTitle(false);
        updateHandledGestures(this.calendarView);
        boolean isLandscape = false;
        boolean isXLarge = false;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
        }
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            isXLarge = true;
        }
        if (isXLarge || isLandscape) {
            this.calendarView.getEventAdapter().getRenderer().setEventRenderMode(EventRenderMode.Text);
            this.calendarView.setWeekNumbersDisplayMode(WeekNumbersDisplayMode.None);
        } else {
            this.calendarView.getEventAdapter().getRenderer().setEventRenderMode(EventRenderMode.Shape);
            this.calendarView.setWeekNumbersDisplayMode(WeekNumbersDisplayMode.Inline);
        }
        this.calendarView.getEventAdapter().setEvents(this.generateEvents());
        this.calendarView.notifyDataChanged();
        return root;
    }

    private void increaseDisplayDate() {
        Calendar c = Calendar.getInstance();
        Long date;
        if (calendarView.getSelectedDates() != null && calendarView.getSelectedDates().size() > 0) {
            date = calendarView.getSelectedDates().get(0);
        } else {
            date = calendarView.getDisplayDate();
        }
        c.setTimeInMillis(date);
        c.add(Calendar.DATE, 1);
        ArrayList<Long> dates = new ArrayList<Long>();
        dates.add(c.getTimeInMillis());
        calendarView.setDisplayDate(c.getTimeInMillis());
        calendarView.setSelectedDates(dates);
    }

    private void decreaseDisplayDate() {
        Calendar c = Calendar.getInstance();
        Long date;
        if (calendarView.getSelectedDates() != null && calendarView.getSelectedDates().size() > 0) {
            date = calendarView.getSelectedDates().get(0);
        } else {
            date = calendarView.getDisplayDate();
        }
        c.setTimeInMillis(date);
        c.add(Calendar.DATE, -1);
        ArrayList<Long> dates = new ArrayList<Long>();
        dates.add(c.getTimeInMillis());
        calendarView.setDisplayDate(c.getTimeInMillis());
        calendarView.setSelectedDates(dates);
    }

    private Calendar getToday() {
        Calendar calendar = Calendar.getInstance();
        moveToDayStart(calendar);
        return calendar;
    }

    private void moveToDayStart(Calendar calendar) {
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private ArrayList<Event> generateEvents() {
        ArrayList<Event> events = new ArrayList<Event>();
        Calendar eventDate = getToday();

        for (int week = 0; week < 2; week++) {
            for (int i = 0; i < personalMeetingTitles.length; i++) {

                eventDate.set(Calendar.AM_PM, Calendar.PM);
                eventDate.set(Calendar.HOUR, 8 + i / 2);
                long start = eventDate.getTimeInMillis();
                eventDate.add(Calendar.HOUR, 2);
                long end = eventDate.getTimeInMillis();
                Event event = new Event(personalMeetingTitles[i], start, end);
                event.setEventColor(personalMeetingColor);
                events.add(event);

                int daysToAdd = i < personalMeetingTitles.length / 2 ? 1 - i * 3 : 1 + i * 4;
                eventDate = getToday();
                eventDate.add(Calendar.DATE, 7 * week + daysToAdd);
            }
        }

        eventDate = getToday();
        for (int i = 0; i < sportTitles.length; i++) {

            if (i != 3) {
                eventDate.set(Calendar.AM_PM, Calendar.PM);
                eventDate.add(Calendar.DATE, i);
                eventDate.set(Calendar.HOUR, 6 + i / 2);
                long start = eventDate.getTimeInMillis();
                eventDate.add(Calendar.MINUTE, 90);
                long end = eventDate.getTimeInMillis();
                Event event = new Event(sportTitles[i], start, end);
                event.setEventColor(sportColor);
                events.add(event);
            } else {
                eventDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                eventDate.add(Calendar.DATE, -7);
                moveToDayStart(eventDate);
                long start = eventDate.getTimeInMillis();
                eventDate.add(Calendar.MINUTE, 90);
                long end = eventDate.getTimeInMillis();
                Event event = new Event(sportTitles[i], start, end);
                event.setAllDay(true);
                event.setEventColor(sportColor);
                events.add(event);
                eventDate.add(Calendar.DATE, 7);
            }

            int daysToAdd = i < sportTitles.length / 2 ? 2 - i * 2 : 2 + i * 3;
            eventDate = getToday();
            eventDate.add(Calendar.DATE, daysToAdd);
        }

        eventDate = getToday();
        for (int week = 0; week < 2; week++) {
            for (int i = 0; i < funTitles.length; i++) {

                if (i != 3) {
                    eventDate.set(Calendar.AM_PM, Calendar.PM);
                    eventDate.set(Calendar.HOUR, 2 + i);
                    long start = eventDate.getTimeInMillis();
                    eventDate.add(Calendar.MINUTE, 90);
                    long end = eventDate.getTimeInMillis();
                    Event event = new Event(funTitles[i], start, end);
                    event.setEventColor(funColor);
                    events.add(event);
                } else {
                    eventDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    eventDate.add(Calendar.DATE, -14);
                    moveToDayStart(eventDate);
                    long start = eventDate.getTimeInMillis();
                    eventDate.add(Calendar.MINUTE, 90);
                    long end = eventDate.getTimeInMillis();
                    Event event = new Event(funTitles[i], start, end);
                    event.setAllDay(true);
                    event.setEventColor(funColor);
                    events.add(event);
                    eventDate.add(Calendar.DATE, 14);
                }

                int daysToAdd = i < funTitles.length / 2 ? 3 - i * 3 : 3 + i * 2;
                eventDate = getToday();
                eventDate.add(Calendar.DATE, 7 * -week + daysToAdd);
            }
        }

        eventDate = getToday();
        for (int week = -1; week < 2; week++) {

            for (int i = 0; i < corpAppointmentTitles.length; i++) {
                eventDate.set(Calendar.AM_PM, Calendar.AM);
                eventDate.set(Calendar.HOUR, 9 + i / 2);
                long start = eventDate.getTimeInMillis();
                eventDate.add(Calendar.HOUR, 1);
                long end = eventDate.getTimeInMillis();
                Event event = new Event(corpAppointmentTitles[i], start, end);
                event.setEventColor(corpAppointmentsColor);
                events.add(event);

                int daysToAdd = i < funTitles.length / 2 ? 4 - i * 4 : 4 + i * 3;
                eventDate = getToday();
                eventDate.add(Calendar.DATE, 7 * week + daysToAdd);
            }
        }

        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event event, Event event2) {
                return (event.getStartDate() < event2.getStartDate()) ? -1 : (event.getStartDate() > event2.getStartDate()) ? 1 : 0;
            }
        });

        return events;
    }

    private void updateHandledGestures(RadCalendarView calendarView) {
        calendarView.setHorizontalScroll(false);
        calendarView.getGestureManager().setSwipeUpToChangeDisplayMode(false);
        //calendarView.getGestureManager().setTapToChangeDisplayMode(true);
        calendarView.getGestureManager().setSwipeDownToChangeDisplayMode(true);

    }

    private void updateWeekModeDayString(boolean showToday) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(calendarView.getDisplayDate());
        if (!showToday && calendarView.getSelectedDates() != null && calendarView.getSelectedDates().size() > 0) {
            currentDate.setTimeInMillis(calendarView.getSelectedDates().get(0));
        }
        txtDayTitle.setText(String.format("%s, %s %d",
                currentDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).toUpperCase(),
                currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).toUpperCase(),
                currentDate.get(Calendar.DAY_OF_MONTH)));
    }

    class CalendarModesSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        public CalendarModesSpinnerAdapter() {
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return getHeaderText(position, true);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View rootView;
            if (convertView == null) {
                rootView = View.inflate(getActivity(), R.layout.calendar_main_spinner_item, null);
            } else {
                rootView = convertView;
            }

            TextView modeName = (TextView) rootView.findViewById(R.id.txtCalendarViewMode);
            TextView modeValue = (TextView) rootView.findViewById(R.id.txtCurrentModeValue);
            switch (position) {
                case 0:
                    modeName.setText("Week");
                    break;
                case 1:
                    modeName.setText("Month");
                    break;
                case 2:
                    modeName.setText("Year");
                    break;
            }
            modeValue.setText(getHeaderText(position, false));
            return rootView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View spinView;
            if (convertView == null) {
                spinView = View.inflate(getActivity(), R.layout.calendar_main_spinner_header, null);
            } else {
                spinView = convertView;
            }
            if (spinView != null) {
                TextView textView = (TextView) spinView.findViewById(R.id.txtCalendarViewMode);
                if (textView != null) {
                    textView.setText(getHeaderText(position, true));
                }
            }
            return spinView;
        }

        private String getHeaderText(int position, boolean includeYear) {
            Calendar currentValue = Calendar.getInstance();
            currentValue.setTimeInMillis(calendarView.getDisplayDate());
            switch (position) {
                case SELECTION_WEEK:
                    Calendar weekStart = (Calendar) currentValue.clone();
                    weekStart.set(Calendar.DAY_OF_WEEK, currentValue.getFirstDayOfWeek());
                    Calendar weekEnd = (Calendar) weekStart.clone();
                    weekEnd.add(Calendar.DATE, 6);
                    if (weekStart.get(Calendar.MONTH) == weekEnd.get(Calendar.MONTH)) {
                        return String.format("%s %d-%d",
                                weekStart.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()),
                                weekStart.get(Calendar.DAY_OF_MONTH),
                                weekEnd.get(Calendar.DAY_OF_MONTH));
                    } else {
                        return String.format("%s %d - %s %d",
                                weekStart.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()),
                                weekStart.get(Calendar.DAY_OF_MONTH),
                                weekEnd.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()),
                                weekEnd.get(Calendar.DAY_OF_MONTH));
                    }
                case SELECTION_MONTH:
                    if (includeYear) {
                        return String.format("%s %d",
                                currentValue.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                                currentValue.get(Calendar.YEAR));
                    } else {
                        return String.format("%s",
                                currentValue.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                    }
                case SELECTION_YEAR:
                    return String.format("%d", currentValue.get(Calendar.YEAR));
            }
            return null;
        }
    }

    class EventsListAdapter extends ArrayAdapter<Event> {

        private ArrayList<Event> events;

        public EventsListAdapter(Context context, int resource, long date) {
            super(context, resource);
            this.events = new ArrayList<Event>();

            this.events.addAll(calendarView.getEventAdapter().getEventsForDate(date));
        }

        @Override
        public int getCount() {
            return this.events.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rootView;
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
            Calendar value = Calendar.getInstance();
            String formattedValue;
            Event e = events.get(position);
            if (e.isAllDay()) {
                rootView = View.inflate(getActivity(), R.layout.calendar_main_event_list_allday_item, null);
                rootView.setBackgroundColor(events.get(position).getEventColor());
            } else {

                rootView = View.inflate(getActivity(), R.layout.calendar_main_event_list_item, null);
                View eventColor = rootView.findViewById(R.id.eventColor);
                eventColor.setBackgroundColor(events.get(position).getEventColor());
                TextView txtStartDate = (TextView) rootView.findViewById(R.id.txtStart);
                TextView txtEndDate = (TextView) rootView.findViewById(R.id.txtEnd);

                value.setTimeInMillis(e.getStartDate());
                formattedValue = dateFormat.format(value.getTime());
                txtStartDate.setText(formattedValue);

                value.setTimeInMillis(e.getEndDate());
                formattedValue = dateFormat.format(value.getTime());
                txtEndDate.setText(formattedValue);
            }

            TextView txtEventName = (TextView) rootView.findViewById(R.id.txtEventTitle);
            txtEventName.setText(events.get(position).getTitle());

            return rootView;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }
    }
}
