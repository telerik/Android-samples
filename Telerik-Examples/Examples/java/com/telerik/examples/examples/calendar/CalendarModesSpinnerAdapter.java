package com.telerik.examples.examples.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.widget.calendar.RadCalendarView;

import java.util.Calendar;
import java.util.Locale;

public class CalendarModesSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final RadCalendarView calendarView;
    private final Context context;

    public CalendarModesSpinnerAdapter(Context context, RadCalendarView calendarView) {
        this.calendarView = calendarView;
        this.context = context;
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
            rootView = View.inflate(context, R.layout.calendar_main_spinner_item, null);
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
            spinView = View.inflate(context, R.layout.calendar_main_spinner_header, null);
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
            case CalendarMainFragment.SELECTION_WEEK:
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
            case CalendarMainFragment.SELECTION_MONTH:
                if (includeYear) {
                    return String.format("%s %d",
                            currentValue.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                            currentValue.get(Calendar.YEAR));
                } else {
                    return String.format("%s",
                            currentValue.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                }
            case CalendarMainFragment.SELECTION_YEAR:
                return String.format("%d", currentValue.get(Calendar.YEAR));
        }
        return null;
    }
}