package com.telerik.examples.examples.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.telerik.widget.calendar.CalendarCell;
import com.telerik.widget.calendar.CalendarDayCell;
import com.telerik.widget.calendar.CellDecorationsLayer;
import com.telerik.widget.calendar.RadCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CircleCellDecorationLayer extends CellDecorationsLayer {

    private final Calendar workCalendar;
    private List<CalendarCell> cellsWithDecoration;
    private long firstDateSelected;
    private Paint paint;

    private int shapeRadius;

    final int SELECTION_COLOR_MAIN = Color.parseColor("#C56BB9");
    final int SELECTION_COLOR_SECONDARY = Color.parseColor("#E6BDE0");

    public CircleCellDecorationLayer(RadCalendarView calendarView) {
        super(calendarView);

        this.workCalendar = owner.getCalendar();

        this.cellsWithDecoration = new ArrayList<CalendarCell>();

        this.paint = new Paint();
        this.paint.setAntiAlias(true);
    }

    public void addDecorationForCell(Rect bounds, long date) {
        CalendarDayCell cell = new CalendarDayCell(this.owner);
        cell.arrange(bounds.left, bounds.top, bounds.right, bounds.bottom);
        cell.setDate(date);
        workCalendar.setTimeInMillis(date);
        cell.setText(String.valueOf(workCalendar.get(Calendar.DAY_OF_MONTH)));
        owner.getAdapter().updateDateCellStyle(cell);
        this.cellsWithDecoration.add(cell);
    }

    @Override
    public void clearDecorations() {
        this.cellsWithDecoration.clear();
        super.clearDecorations();
    }

    @Override
    public void render(Canvas canvas) {
        int decoratedCellsCount = this.cellsWithDecoration == null ? 0 : this.cellsWithDecoration.size();
        if (decoratedCellsCount == 0) {
            return;
        }

        CalendarCell firstCellWithDecoration = this.cellsWithDecoration.get(0);
        CalendarCell lastCellWithDecoration = this.cellsWithDecoration.get(decoratedCellsCount - 1);

        this.shapeRadius = (firstCellWithDecoration.getWidth() > firstCellWithDecoration.getHeight() ?
                firstCellWithDecoration.getHeight() :
                firstCellWithDecoration.getWidth());
        this.shapeRadius *= 0.45;

        if (decoratedCellsCount > 1) {

            Rect rectCellStart = firstCellWithDecoration.calcBorderRect();
            int horizontalStart = (rectCellStart.left + rectCellStart.right) / 2;
            int verticalStart = (rectCellStart.top + rectCellStart.bottom) / 2;

            Rect rectCellEnd = lastCellWithDecoration.calcBorderRect();
            int horizontalEnd = (rectCellEnd.left + rectCellEnd.right) / 2;
            int verticalEnd = (rectCellEnd.top + rectCellEnd.bottom) / 2;

            int currentVerticalPosition = verticalStart;
            int outsideLeft = -50;
            int outsideRight = (int) (this.owner.getWidth() * 1.2f);

            int selectedDatesCount = this.owner.getSelectedDates().size();
            long firstSelectedDate = this.owner.getSelectedDates().get(0);
            long lastSelectedDate = this.owner.getSelectedDates().get(selectedDatesCount - 1);

            if (selectedDatesCount > decoratedCellsCount) {
                if (firstSelectedDate != firstCellWithDecoration.getDate()) {
                    horizontalStart = outsideLeft;
                }
                if (lastSelectedDate != lastCellWithDecoration.getDate()) {
                    horizontalEnd = outsideRight;
                }
            }
            if (currentVerticalPosition >= verticalEnd) {
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
            if (firstDateSelected == 0 || lastCellWithDecoration.getDate() > firstDateSelected) {
                if (lastSelectedDate == lastCellWithDecoration.getDate()) {
                    this.drawCircle(canvas, horizontalEnd, verticalEnd);
                }
            } else if (firstCellWithDecoration.getDate() < firstDateSelected) {
                this.drawCircle(canvas, horizontalStart, verticalStart);
            }
            for (CalendarCell cell : this.cellsWithDecoration) {
                Rect rectCell = cell.calcBorderRect();
                int rectCenterX = (rectCell.left + rectCell.right) / 2;
                int rectCenterY = (rectCell.top + rectCell.bottom) / 2;
                String cellText = cell.getText() != null ? cell.getText() : "";
                this.drawText(canvas, cellText, rectCenterX, rectCenterY, cell.getTextPaint());
            }
        } else {
            this.firstDateSelected = this.cellsWithDecoration.get(0).getDate();

            Rect borderRect = this.cellsWithDecoration.get(0).calcBorderRect();
            int centerX = (borderRect.left + borderRect.right) / 2;
            int centerY = (borderRect.top + borderRect.bottom) / 2;

            this.drawCircle(canvas, centerX, centerY);
            String cellText = this.cellsWithDecoration.get(0).getText() != null ?
                    this.cellsWithDecoration.get(0).getText() :
                    "";
            this.drawText(canvas, cellText, centerX, centerY, this.cellsWithDecoration.get(0).getTextPaint());
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
        paint.getTextBounds(dateText, 0, dateText.length(), textRect);
        int horizontalTextLocation = x - textRect.width() / 2 - textRect.left;
        int verticalTextLocation = y + textRect.height() / 2 - textRect.bottom;
        canvas.drawText(dateText, horizontalTextLocation, verticalTextLocation, paint);
    }
}