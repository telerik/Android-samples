package com.telerik.examples.examples.dataform;

import com.telerik.examples.R;
import com.telerik.widget.dataform.engine.NonEmptyValidator;
import com.telerik.widget.dataform.engine.NotifyPropertyChangedBase;
import com.telerik.widget.dataform.engine.PhoneValidator;
import com.telerik.widget.dataform.visualization.annotations.DataFormProperty;
import com.telerik.widget.dataform.visualization.editors.DataFormDateEditor;
import com.telerik.widget.dataform.visualization.editors.DataFormSpinnerEditor;
import com.telerik.widget.dataform.visualization.editors.DataFormTimeEditor;

import java.util.Calendar;

public class Reservation extends NotifyPropertyChangedBase {
    private String creatorName;
    private String creatorPhone;
    private long reservationDate = Calendar.getInstance().getTimeInMillis();
    private long reservationTime = Calendar.getInstance().getTimeInMillis();
    private int numberOfGuests = 1;
    private String tableSection = "Patio";
    private int tableNumber = 1;
    private String origin = "In Person";
    private boolean cancelled;

    @DataFormProperty(label = " ", index = 0, columnSpan = 2, editorLayout = R.layout.reservation_text_editor, validator = NonEmptyValidator.class)
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        notifyListeners("CreatorName", creatorName);
    }

    @DataFormProperty(label = " ", index = 1, columnSpan = 2, validator = PhoneValidator.class, editorLayout = R.layout.reservation_phone_editor)
    public String getCreatorPhone() {
        return creatorPhone;
    }

    public void setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
        notifyListeners("CreatorPhone", creatorPhone);
    }

    @DataFormProperty(label = "Date", index = 2, editor = DataFormDateEditor.class, editorLayout = R.layout.reservation_date_editor, validator = FutureDateValidator.class)
    public long getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(long reservationDate) {
        this.reservationDate = reservationDate;
        notifyListeners("ReservationDate", reservationDate);
    }

    @DataFormProperty(label = "Time", index = 2, columnIndex = 1, editor = DataFormTimeEditor.class)
    public long getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(long reservationTime) {
        this.reservationTime = reservationTime;
        notifyListeners("ReservationTime", reservationTime);
    }

    @DataFormProperty(label = " ", index = 3, columnSpan = 2, editor = DataFormNumberPickerEditor.class)
    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        notifyListeners("NumberOfGuests", numberOfGuests);
    }

    @DataFormProperty(label = "Section", index = 4, editorLayout = R.layout.reservation_spinner_editor, editor = DataFormSpinnerEditor.class)
    public String getTableSection() {
        return tableSection;
    }

    public void setTableSection(String tableSection) {
        this.tableSection = tableSection;
        notifyListeners("TableSection", tableSection);
    }

    @DataFormProperty(label = "Table", index = 4, columnIndex = 1, editor = DataFormSpinnerEditor.class)
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        notifyListeners("TableNumber", tableNumber);
    }

    @DataFormProperty(index = 5, columnSpan = 2, editorLayout = R.layout.reservation_spinner_editor_no_image, editor = DataFormSpinnerEditor.class)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
        notifyListeners("Origin", origin);
    }

    @DataFormProperty(editor = CancelButtonEditor.class, index = 6, columnSpan = 2)
    public boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
        notifyListeners("Cancelled", cancelled);
    }
}
