package com.telerik.examples.examples.calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.calendar.CalendarAdapter;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.CalendarStyles;
import com.telerik.widget.calendar.CalendarTextElement;
import com.telerik.widget.calendar.DateRange;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.ScrollMode;
import com.telerik.widget.calendar.decorations.CircularRangeDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A fragment to be used for the range selection example of RadCalendarView.
 */
public class CalendarRangeSelectionFragment extends ExampleFragmentBase {

    private static final int SELECTION_COLOR_MAIN = Color.parseColor("#C56BB9");
    private static final int SELECTION_COLOR_SECONDARY = Color.parseColor("#E6BDE0");
    private static final int PURPLE_COLOR = Color.parseColor("#511749");
    private static final int BACKGROUND_COLOR = Color.parseColor("#F2F2F2");

    private Spinner spinnerLocation;
    private Spinner spinnerRange;
    private Spinner spinnerGuests;
    private Spinner spinnerRooms;
    private TextView txtRange;
    private ListView listHotels;
    private DateRange selectionRange;
    private AlertDialog dialog;
    private RadCalendarView calendarView;
    private boolean dialogOpened;

    public CalendarRangeSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            root = inflater.inflate(R.layout.fragment_calendar_range_selection, container, false);
        } else {
            root = inflater.inflate(R.layout.fragment_calendar_range_selection_landscape, container, false);
        }
        this.spinnerLocation = (Spinner) root.findViewById(R.id.spinnerLocation);
        this.txtRange = (TextView) root.findViewById(R.id.txtSelectedRange);
        this.txtRange.setClickable(false);

        this.spinnerRange = (Spinner) root.findViewById(R.id.spinnerDates);
        this.spinnerRange.setOnTouchListener(new View.OnTouchListener() {
            boolean hasMoved = false;
            float deltaX;
            float deltaY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getPointerCount() > 1) {
                        return false;
                    }
                    deltaX = event.getX();
                    deltaY = event.getY();
                    hasMoved = false;
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (event.getPointerCount() > 1) {
                        return false;
                    }
                    hasMoved = Math.abs(deltaX - event.getX()) > 10 || Math.abs(deltaY - event.getY()) > 10;
                    return false;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (hasMoved) {
                        return false;
                    }
                    showRangeSelector();
                    return true;
                }
                return false;
            }
        });
        this.spinnerGuests = (Spinner) root.findViewById(R.id.spinnerGuests);
        this.spinnerRooms = (Spinner) root.findViewById(R.id.spinnerRooms);
        this.txtRange = (TextView) root.findViewById(R.id.txtSelectedRange);
        this.listHotels = (ListView) root.findViewById(R.id.listHotels);
        this.listHotels.setAdapter(new HotelListAdapter(this.getActivity(), 0));

        this.spinnerLocation.setAdapter(new CalendarSpinnerAdapter(new String[]{"London", "Oxford"}, getActivity()));
        this.spinnerGuests.setAdapter(new CalendarSpinnerAdapter(new String[]{"1", "2", "3", "4"}, getActivity()));
        this.spinnerRooms.setAdapter(new CalendarSpinnerAdapter(new String[]{"1", "2"}, getActivity()));

        return root;
    }

    private void showRangeSelector() {
        if (this.dialogOpened)
            return;

        this.dialogOpened = true;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
        final View rootView = View.inflate(this.getActivity(), R.layout.calendar_range_popup, null);
        rootView.findViewById(R.id.left_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.animateToPrevious();
            }
        });
        rootView.findViewById(R.id.right_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.animateToNext();
            }
        });

        calendarView = (RadCalendarView) rootView.findViewById(R.id.calendarView);
        calendarView.setMinDate(Calendar.getInstance().getTimeInMillis());
        calendarView.setHorizontalScroll(true);
        calendarView.setShowGridLines(false);
        calendarView.setScrollMode(ScrollMode.None);
        updateHandledGestures(calendarView);

        calendarView.setSelectionMode(CalendarSelectionMode.Range);
        CircularRangeDecorator decorator = new CircularRangeDecorator(calendarView);
        calendarView.setCellDecorator(decorator);

        calendarView.setDateToColor(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long argument) {
                Calendar today = Calendar.getInstance();
                Calendar cellDate = Calendar.getInstance();
                cellDate.setTimeInMillis(argument);
                if (cellDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                        cellDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                        cellDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                    return PURPLE_COLOR;
                }
                return null;
            }

        });
        if (selectionRange != null) {
            calendarView.setSelectedRange(selectionRange);
        } else {
            ArrayList<Long> selectedDates = new ArrayList<Long>();
            Calendar today = Calendar.getInstance();
            selectedDates.add(today.getTimeInMillis());
            today.add(Calendar.DATE, 1);
            selectedDates.add(today.getTimeInMillis());
            calendarView.setSelectedDates(selectedDates);
        }
        dialogBuilder.setView(rootView);

        dialogBuilder.setPositiveButton(R.string.abc_action_mode_done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                selectionRange = calendarView.getSelectedRange();
                txtRange.setText(getFormattedRangeString(selectionRange));
            }
        });
        dialog = dialogBuilder.create();

        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogOpened = false;
            }
        });

        CalendarAdapter adapter = calendarView.getAdapter();
        adapter.setStyle(CalendarStyles.light(getActivity()));
        adapter.setTitleBackgroundColor(BACKGROUND_COLOR);
        adapter.setTitleTextColor(PURPLE_COLOR);
        adapter.setTodayCellBorderColor(Color.TRANSPARENT);

        adapter.setDateCellBackgroundColor(BACKGROUND_COLOR, BACKGROUND_COLOR);
        adapter.setTodayCellBackgroundColor(BACKGROUND_COLOR);
        adapter.setSelectedCellBackgroundColor(BACKGROUND_COLOR);
        adapter.setDateTextPosition(CalendarTextElement.CENTER);

        adapter.setDayNameBackgroundColor(BACKGROUND_COLOR);
        adapter.setDayNameTextPosition(CalendarTextElement.CENTER);

        decorator.setColor(SELECTION_COLOR_SECONDARY);
        decorator.setShapeColor(SELECTION_COLOR_MAIN);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectionRange != null) {
            outState.putLong("selectionRangeStart", selectionRange.getStart());
            outState.putLong("selectionRangeEnd", selectionRange.getEnd());
        }
        if (dialog != null) {
            outState.putBoolean("dialogShown", dialog.isShowing());
            if (dialog.isShowing() && calendarView != null && calendarView.getSelectedRange() != null) {
                outState.putLong("dialogRangeStart", calendarView.getSelectedRange().getStart());
                outState.putLong("dialogRangeEnd", calendarView.getSelectedRange().getEnd());
                outState.putLong("dialogDisplayDate", calendarView.getDisplayDate());
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            if (savedInstanceState.containsKey("selectionRangeStart") && savedInstanceState.containsKey("selectionRangeEnd")) {
                Long start = savedInstanceState.getLong("selectionRangeStart");
                Long end = savedInstanceState.getLong("selectionRangeEnd");
                this.selectionRange = new DateRange(start, end);
                txtRange.setText(getFormattedRangeString(selectionRange));
            }
            if (savedInstanceState.containsKey("dialogShown")) {
                boolean isShown = savedInstanceState.getBoolean("dialogShown");
                if (isShown) {
                    showRangeSelector();
                    if (savedInstanceState.containsKey("dialogRangeStart") && savedInstanceState.containsKey("dialogRangeEnd")) {
                        Long start = savedInstanceState.getLong("dialogRangeStart");
                        Long end = savedInstanceState.getLong("dialogRangeEnd");
                        calendarView.setSelectedRange(new DateRange(start, end));
                    }
                    if (savedInstanceState.containsKey("dialogDisplayDate")) {
                        long displayDate = savedInstanceState.getLong("dialogDisplayDate");
                        calendarView.setDisplayDate(displayDate);
                    }
                }
            }
        }
    }

    private String getFormattedRangeString(DateRange date) {
        Calendar startValue = Calendar.getInstance();
        startValue.setTimeInMillis(date.getStart());
        Calendar endValue = Calendar.getInstance();
        endValue.setTimeInMillis(date.getEnd());
        int nightsCount = this.getDayCount(date.getEnd() - date.getStart());
        String startMonth = startValue.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int startMonthDate = startValue.get(Calendar.DAY_OF_MONTH);
        int endMonthDate = endValue.get(Calendar.DAY_OF_MONTH);
        String endMonth = endValue.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String nightsText = nightsCount == 1 ? "night" : "nights";
        return String.format("%s %d - %s %d (%d %s)", startMonth, startMonthDate, endMonth, endMonthDate, nightsCount, nightsText);
    }

    private void updateHandledGestures(RadCalendarView calendarView) {

        calendarView.getGestureManager().setSwipeUpToChangeDisplayMode(false);

        calendarView.getGestureManager().setPinchCloseToChangeDisplayMode(false);
        calendarView.getGestureManager().setPinchOpenToChangeDisplayMode(false);

        calendarView.getGestureManager().setDoubleTapToChangeDisplayMode(false);

        calendarView.getGestureManager().setUsingDragToMakeRangeSelection(true);
    }

    private int getDayCount(long millis) {
        return (int) (((millis / 1000) / 60) / 60) / 24;
    }
}
