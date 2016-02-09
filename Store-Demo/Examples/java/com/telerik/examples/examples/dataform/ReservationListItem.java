package com.telerik.examples.examples.dataform;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.examples.R;
import com.telerik.widget.dataform.engine.PropertyChangedListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationListItem extends FrameLayout implements PropertyChangedListener {
    private Reservation reservation;
    private TextView time;
    private TextView amPmText;
    private TextView name;
    private TextView tableInfo;
    private TextView phone;
    private SimpleDateFormat format = new SimpleDateFormat("hh:mm aaa");

    public ReservationListItem(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.data_form_reservation_list_item, this);

        time = Util.getLayoutPart(this, R.id.reservation_list_item_time, TextView.class);
        amPmText = Util.getLayoutPart(this, R.id.reservation_list_item_ampm, TextView.class);
        name = Util.getLayoutPart(this, R.id.reservation_list_item_name, TextView.class);
        tableInfo = Util.getLayoutPart(this, R.id.reservation_list_item_table_info, TextView.class);
        phone = Util.getLayoutPart(this, R.id.reservation_list_item_phone, TextView.class);

        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setReservation(Reservation value) {
        if(this.reservation != null) {
            this.reservation.removePropertyChangedListener(this);
        }

        this.reservation = value;

        if(this.reservation != null) {
            this.reservation.addPropertyChangedListener(this);
        }

        setReservationTimeText(value.getReservationTime());
        name.setText(reservation.getCreatorName());
        tableInfo.setText(String.format("Table #%d for %d", reservation.getTableNumber(), reservation.getNumberOfGuests()));

        setPhoneText(reservation.getCreatorPhone());
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    private void setPhoneText(String text) {
        if(text == null) {
            return;
        }

        SpannableString underlinedText = new SpannableString(text);
        underlinedText.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        phone.setText(underlinedText);
    }

    @Override
    public void onPropertyChanged(String propertyName, Object value) {
        if(propertyName.equals("ReservationTime")) {
            this.setReservationTimeText((Long) value);
        } else if(propertyName.equals("CreatorName")) {
            name.setText(value.toString());
        } else if(propertyName.equals("TableNumber") || propertyName.equals("NumberOfGuests")) {
            tableInfo.setText(String.format("Table #%d for %d", reservation.getTableNumber(), reservation.getNumberOfGuests()));
        } else if(propertyName.equals("CreatorPhone")) {
            phone.setText(value.toString());
        }
    }

    public void setReservationTimeText(Long value) {
        String text = format.format(new Date(value));
        time.setText(text.substring(0, text.length() - 3));

        amPmText.setText(text.substring(text.length() - 2, text.length()).toLowerCase());
    }
}
