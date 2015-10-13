package com.telerik.examples.examples.dataform;

import com.telerik.examples.R;
import com.telerik.widget.dataform.engine.MinimumLengthValidator;
import com.telerik.widget.dataform.engine.NonEmptyValidator;
import com.telerik.widget.dataform.engine.NotifyPropertyChangedBase;
import com.telerik.widget.dataform.engine.PhoneValidator;
import com.telerik.widget.dataform.engine.RangeValidator;
import com.telerik.widget.dataform.visualization.annotations.DataFormProperty;
import com.telerik.widget.dataform.visualization.editors.DataFormDateEditor;
import com.telerik.widget.dataform.visualization.editors.DataFormNumberPickerEditor;
import com.telerik.widget.dataform.visualization.editors.DataFormSpinnerEditor;
import com.telerik.widget.dataform.visualization.editors.DataFormTimeEditor;

import java.util.Calendar;

public class Reservation extends NotifyPropertyChangedBase {
    private String creatorName;
    private String creatorPhone;
    private long reservationDate = Calendar.getInstance().getTimeInMillis();
    private long reservationTime = Calendar.getInstance().getTimeInMillis();
    private int numberOfGuests = 1;
    private String tableSection = "patio";
    private int tableNumber = 1;
    private String origin = "in-person";
    private boolean cancelled;

    @DataFormProperty(label = " ",
            index = 0,
            editorLayout = R.layout.reservation_editor_layout,
            coreEditorLayout = R.layout.reservation_text_editor,
            headerLayout = R.layout.reservation_editor_collapsed_header,
            validator = NonEmptyValidator.class,
            group = "group1")
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        notifyListeners("CreatorName", creatorName);
    }

    @DataFormProperty(label = " ",
            index = 1,
            validator = PhoneValidator.class,
            editorLayout = R.layout.reservation_editor_layout,
            coreEditorLayout = R.layout.reservation_phone_editor,
            headerLayout = R.layout.reservation_editor_collapsed_header,
            group = "group1")
    public String getCreatorPhone() {
        return creatorPhone;
    }

    public void setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
        notifyListeners("CreatorPhone", creatorPhone);
    }

    @DataFormProperty(label = "Date",
            index = 2,
            coreEditorLayout = R.layout.reservation_date_editor,
            editorLayout = R.layout.reservation_editor_layout,
            editor = DataFormDateEditor.class,
            validator = FutureDateValidator.class,
            group = "Reservation Date")
    public long getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(long reservationDate) {
        this.reservationDate = reservationDate;
        notifyListeners("ReservationDate", reservationDate);
    }

    @DataFormProperty(label = "Time",
            coreEditorLayout = R.layout.reservation_time_editor,
            editorLayout = R.layout.reservation_editor_layout,
            index = 2,
            editor = DataFormTimeEditor.class,
            group = "Reservation Date")
    public long getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(long reservationTime) {
        this.reservationTime = reservationTime;
        notifyListeners("ReservationTime", reservationTime);
    }

    @DataFormProperty(label = " ",
            index = 3,
            editor = DataFormNumberPickerEditor.class,
            editorLayout = R.layout.reservation_editor_layout,
            coreEditorLayout = R.layout.data_form_number_picker_image,
            headerLayout = R.layout.reservation_editor_collapsed_header,
            converter = DoubleToIntConverter.class,
            group = "group3")
    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        notifyListeners("NumberOfGuests", numberOfGuests);
    }

    @DataFormProperty(label = "Section",
            index = 4,
            editor = DataFormSpinnerEditor.class,
            editorLayout = R.layout.reservation_editor_layout,
            coreEditorLayout = R.layout.reservation_spinner_editor,
            columnIndex = 1,
            group = "TABLE DETAILS",
            headerLayout = R.layout.reservation_table_editor_header)
    public String getTableSection() {
        return tableSection;
    }

    public void setTableSection(String tableSection) {
        this.tableSection = tableSection;
        notifyListeners("TableSection", tableSection);
    }

    @DataFormProperty(label = "Table",
            index = 4,
            editorLayout = R.layout.reservation_editor_layout,
            editor = DataFormSpinnerEditor.class,
            coreEditorLayout = R.layout.reservation_spinner_editor,
            group = "TABLE DETAILS",
            headerLayout = R.layout.reservation_table_editor_header)
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        notifyListeners("TableNumber", tableNumber);
    }

    @DataFormProperty(index = 5,
            editorLayout = R.layout.reservation_editor_layout_no_image,
            coreEditorLayout = R.layout.reservation_spinner_editor_no_image,
            editor = DataFormSpinnerEditor.class,
            group = "group5", headerLayout = R.layout.reservation_origin_header)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
        notifyListeners("Origin", origin);
    }

    @DataFormProperty(editor = CancelButtonEditor.class,
            editorLayout = R.layout.reservation_editor_layout_no_image,
            index = 6,
            group = "group5")
    public boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
        notifyListeners("Cancelled", cancelled);
    }
}
