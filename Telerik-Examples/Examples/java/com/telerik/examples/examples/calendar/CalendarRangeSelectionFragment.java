package com.telerik.examples.examples.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.calendar.CalendarAdapter;
import com.telerik.widget.calendar.CalendarCell;
import com.telerik.widget.calendar.CalendarDisplayMode;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.CellDecorationsLayer;
import com.telerik.widget.calendar.DateRange;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.events.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A fragment to be used for the range selection example of RadCalendarView.
 */
public class CalendarRangeSelectionFragment extends ExampleFragmentBase {

    private Spinner spinnerLocation;
    private Spinner spinnerRange;
    private Spinner spinnerGuests;
    private Spinner spinnerRooms;
    private TextView txtRange;
    private ListView listHotels;
    private DateRange selectionRange;
    private AlertDialog dialog;
    private RadCalendarView calendarView;

    private int purpleColor = Color.parseColor("#511749");

    public CalendarRangeSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = null;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            root = inflater.inflate(R.layout.fragment_calendar_range_selection, container, false);
        }else{
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
        this.listHotels = (ListView)root.findViewById(R.id.listHotels);
        this.listHotels.setAdapter(new HotelListAdapter(this.getActivity(), 0));

        this.spinnerLocation.setAdapter(new CalendarSpinnerAdapter(new String[]{"London", "Oxford"}));
        this.spinnerGuests.setAdapter(new CalendarSpinnerAdapter(new String[]{"1", "2", "3", "4"}));
        this.spinnerRooms.setAdapter(new CalendarSpinnerAdapter(new String[]{"1", "2"}));

        return root;
    }

    private void showRangeSelector() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
        final View rootView = View.inflate(this.getActivity(), R.layout.calendar_range_popup, null);
        calendarView = (RadCalendarView) rootView.findViewById(R.id.calendarView);
        calendarView.setMinDate(Calendar.getInstance().getTimeInMillis());
        calendarView.setShowGridLines(false);
        calendarView.setCellDecorationsLayer(new CircleCellDecorationLayer(this.getActivity().getApplicationContext()));
        updateHandledGestures(calendarView);
        calendarView.setAdapter(new CircleCellsCalendarAdapter(calendarView.getContext(), Calendar.getInstance(), Locale.getDefault()));
        calendarView.setSelectionMode(CalendarSelectionMode.Range);
        calendarView.setDateToColor(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long argument) {
                Calendar today = Calendar.getInstance();
                Calendar cellDate = Calendar.getInstance();
                cellDate.setTimeInMillis(argument);
                if(cellDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                        cellDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                        cellDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                    return purpleColor;
                }
                return null;
            };
        });
        if(selectionRange != null) {
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(selectionRange != null) {
            outState.putLong("selectionRangeStart", selectionRange.getStart());
            outState.putLong("selectionRangeEnd", selectionRange.getEnd());
        }
        if(dialog != null) {
            outState.putBoolean("dialogShown", dialog.isShowing());
            if(dialog.isShowing() && calendarView != null && calendarView.getSelectedRange() != null) {
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
            if(savedInstanceState.containsKey("selectionRangeStart") && savedInstanceState.containsKey("selectionRangeEnd")) {
                Long start = savedInstanceState.getLong("selectionRangeStart");
                Long end = savedInstanceState.getLong("selectionRangeEnd");
                this.selectionRange = new DateRange(start, end);
                txtRange.setText(getFormattedRangeString(selectionRange));
            }
            if(savedInstanceState.containsKey("dialogShown")) {
                boolean isShown = savedInstanceState.getBoolean("dialogShown");
                if(isShown) {
                    showRangeSelector();
                    if(savedInstanceState.containsKey("dialogRangeStart") && savedInstanceState.containsKey("dialogRangeEnd")) {
                        Long start = savedInstanceState.getLong("dialogRangeStart");
                        Long end = savedInstanceState.getLong("dialogRangeEnd");
                        calendarView.setSelectedRange(new DateRange(start, end));
                    }
                    if(savedInstanceState.containsKey("dialogDisplayDate")) {
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

        calendarView.gestureAssistant().setSwipeUpToChangeDisplayMode(false);

        calendarView.gestureAssistant().setPinchCloseToChangeDisplayMode(false);
        calendarView.gestureAssistant().setPinchOpenToChangeDisplayMode(false);

        calendarView.gestureAssistant().setDoubleTapToChangeDisplayMode(false);
    }

    private int getDayCount(long millis) {
        return (int) (((millis / 1000) / 60) / 60) / 24;
    }

    class CircleCellsCalendarAdapter extends CalendarAdapter {
        final int BACKGROUND_COLOR = Color.parseColor("#F2F2F2");

        CircleCellsCalendarAdapter(Context context, Calendar calendar, Locale locale) {
            super(context, calendar, locale);
        }

        @Override
        public TextView getTitleView(Context context, long date, CalendarDisplayMode displayMode) {
            TextView ownerView = super.getTitleView(context, date, displayMode);
            ownerView.setBackgroundColor(BACKGROUND_COLOR);
            ownerView.setTextColor(purpleColor);
            return ownerView;
        }

        @Override
        public CalendarCell getDayNameView(int index) {
            CalendarCell ownerCell = super.getDayNameView(index);
            ownerCell.setBackgroundColor(BACKGROUND_COLOR);
            ownerCell.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            return ownerCell;
        }

        @Override
        public CalendarCell getDateView(Long date, List<Event> eventList, boolean includeWeekNumber) {
            CalendarCell ownerCell = super.getDateView(date, eventList, includeWeekNumber);
            ownerCell.setBackgroundColor(BACKGROUND_COLOR);
            ownerCell.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            return ownerCell;
        }
    }

    class CircleCellDecorationLayer extends CellDecorationsLayer {

        private List<CalendarCell> cellsWithDecoration;
        private CalendarCell firstCell;
        private Paint paint;

        private int shapeRadius;

        final int SELECTION_COLOR_MAIN = Color.parseColor("#C56BB9");
        final int SELECTION_COLOR_SECONDARY = Color.parseColor("#E6BDE0");

        public CircleCellDecorationLayer(Context context) {
            super(context);

            this.cellsWithDecoration = new ArrayList<CalendarCell>();

            this.paint = new Paint();
            this.paint.setAntiAlias(true);
        }

        @Override
        public void addDecorationForCell(CalendarCell calendarCell) {
            this.cellsWithDecoration.add(calendarCell);
            this.invalidate();
        }

        @Override
        public void removeDecorationForCell(CalendarCell calendarCell) {
            this.cellsWithDecoration.remove(calendarCell);
            this.invalidate();
        }

        @Override
        public void clearDecorations() {
            this.cellsWithDecoration.clear();
            this.invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if(this.cellsWithDecoration == null || this.cellsWithDecoration.size() == 0) {
                return;
            }
            CalendarCell cellWithDecoration = this.cellsWithDecoration.get(0);
            this.shapeRadius = cellWithDecoration.getWidth() > cellWithDecoration.getHeight() ? cellWithDecoration.getHeight() : cellWithDecoration.getWidth();
            this.shapeRadius *= 0.45;
            if(this.cellsWithDecoration.size() > 1) {
                int cellsCount = this.cellsWithDecoration.size();
                Rect rectCellStart = this.cellsWithDecoration.get(0).calcBorderRect();
                int horizontalStart = (rectCellStart.left + rectCellStart.right) / 2;
                int verticalStart = (rectCellStart.top + rectCellStart.bottom) / 2;

                Rect rectCellEnd = this.cellsWithDecoration.get(cellsCount - 1).calcBorderRect();
                int horizontalEnd = (rectCellEnd.left + rectCellEnd.right) / 2;
                int verticalEnd = (rectCellEnd.top + rectCellEnd.bottom) / 2;

                int currentVerticalPosition = verticalStart;
                int outsideLeft = -50;
                int outsideRight = (int)(this.getWidth() * 1.2f);
                if(currentVerticalPosition >= verticalEnd) {
                    this.drawRoundRect(canvas, horizontalStart, horizontalEnd, verticalStart);
                } else {
                    this.drawRoundRect(canvas, horizontalStart, outsideRight, currentVerticalPosition);
                    currentVerticalPosition += rectCellStart.height();
                    while (currentVerticalPosition < verticalEnd - rectCellStart.height() / 4) {
                        this.drawRoundRect(canvas, outsideLeft, outsideRight, currentVerticalPosition);
                        currentVerticalPosition += rectCellStart.height();
                    }
                    this.drawRoundRect(canvas, outsideLeft, horizontalEnd, verticalEnd);
                }
                if(firstCell == null || firstCell == this.cellsWithDecoration.get(0)) {
                    this.drawCircle(canvas, horizontalEnd, verticalEnd);
                } else {
                    this.drawCircle(canvas, horizontalStart, verticalStart);
                }
                for(CalendarCell cell : this.cellsWithDecoration) {
                    Rect rectCell = cell.calcBorderRect();
                    int rectCenterX = (rectCell.left + rectCell.right) / 2;
                    int rectCenterY = (rectCell.top + rectCell.bottom) / 2;
                    this.drawText(canvas, (String)cell.getText(), rectCenterX, rectCenterY, cell.getPaint());
                }
            } else {
                this.firstCell = this.cellsWithDecoration.get(0);

                Rect borderRect = this.firstCell.calcBorderRect();
                int centerX = (borderRect.left + borderRect.right) / 2;
                int centerY = (borderRect.top + borderRect.bottom) / 2;

                this.drawCircle(canvas, centerX, centerY);
                this.drawText(canvas, (String)this.firstCell.getText(), centerX, centerY, this.firstCell.getPaint());
            }
        }

        private void drawRoundRect(Canvas canvas, int xStart, int xEnd, int y) {
            RectF rect = new RectF(xStart - this.shapeRadius, y - this.shapeRadius, xEnd + this.shapeRadius, y + this.shapeRadius);
            this.paint.setColor(SELECTION_COLOR_MAIN);
            canvas.drawRoundRect(rect, this.shapeRadius, this.shapeRadius, this.paint);
        }

        private void drawCircle(Canvas canvas, int x, int y) {
            this.paint.setColor(SELECTION_COLOR_SECONDARY);
            canvas.drawCircle(x, y, (int) (this.shapeRadius * 0.9f), this.paint);
        }

        private void drawText(Canvas canvas, String dateText, int x, int y, Paint paint) {
            Rect textRect = new Rect();
            paint.getTextBounds(dateText, 0 , dateText.length(), textRect);
            int horizontalTextLocation = x - textRect.width() / 2 - textRect.left;
            int verticalTextLocation = y + textRect.height() / 2 - textRect.bottom;
            canvas.drawText(dateText, horizontalTextLocation, verticalTextLocation, paint);
        }
    }

    class CalendarSpinnerAdapter implements SpinnerAdapter {

        private String[] spinnerOptions;

        public CalendarSpinnerAdapter(String[] options) {
            this.spinnerOptions = options;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView spinnerItem = (TextView) View.inflate(getActivity(), R.layout.calendar_range_spinner_item, null);
            spinnerItem.setText(spinnerOptions[position]);
            return spinnerItem;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView spinnerItem = (TextView) View.inflate(getActivity(), R.layout.calendar_range_spinner_header, null);
            spinnerItem.setText(spinnerOptions[position]);
            return spinnerItem;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return this.spinnerOptions.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

    class HotelListAdapter extends ArrayAdapter<Hotel>{

        private ArrayList<Hotel> hotels;

        public HotelListAdapter(Context context, int resource) {
            super(context, resource);

            this.hotels = new ArrayList<Hotel>();

            Hotel hotel = new Hotel();
            hotel.setName("Buckingham gate");
            hotel.setPrice(265);

            this.hotels.add(hotel);

            hotel = new Hotel();
            hotel.setName("Three Lords Hotel");
            hotel.setPrice(85);

            this.hotels.add(hotel);

            hotel = new Hotel();
            hotel.setName("King's Court");
            hotel.setPrice(155);

            this.hotels.add(hotel);

            hotel = new Hotel();
            hotel.setName("Oxford Palace");
            hotel.setPrice(105);

            this.hotels.add(hotel);

            hotel = new Hotel();
            hotel.setName("Queen's Hotel");
            hotel.setPrice(233);

            this.hotels.add(hotel);

            hotel = new Hotel();
            hotel.setName("London Bridge Hotel");
            hotel.setPrice(302);

            this.hotels.add(hotel);

            hotel = new Hotel();
            hotel.setName("Hyde Park Towers Hotel");
            hotel.setPrice(201);

            this.hotels.add(hotel);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(), R.layout.calendar_range_list_item, null);
            Hotel currentHotel = this.getItem(position);
            TextView hotelName = (TextView)view.findViewById(R.id.txtHotelName);
            TextView priceTag = (TextView)view.findViewById(R.id.txtPrice);
            hotelName.setText(currentHotel.getName());
            priceTag.setText("$ " + currentHotel.getPrice());
            ImageView hotelImage = (ImageView)view.findViewById(R.id.hotelImage);
            hotelImage.setImageResource(getResources().getIdentifier("calendar_pic_" + (position + 1), "drawable", getActivity().getPackageName()));
            return view;
        }

        @Override
        public int getCount() {
            return this.hotels.size();
        }

        @Override
        public Hotel getItem(int position) {
            return this.hotels.get(position);
        }
    }

    public class Hotel {
        private String name;
        private float price;
        private int roomCount;
        private int legsPerRoom;

        public int getLegsPerRoom() {
            return this.legsPerRoom;
        }

        public void setLegsPerRoom(int legs) {
            this.legsPerRoom = legs;
        }

        public int getRoomCount() {
            return this.roomCount;
        }

        public void setRoomCount(int legs) {
            this.roomCount = legs;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice(){
            return this.price;
        }

        public void setPrice(float price){
            this.price = price;
        }
    }
}
