package com.telerik.examples.examples.dataform;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SelectionBehavior;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataFormFragment extends ExampleFragmentBase implements View.OnClickListener, OnEditEndListener, SelectionBehavior.SelectionChangedListener {
    private static List<Reservation> reservations;
    private boolean newItem;
    private DataFormEditFragment editFragment;
    private ReservationAdapter adapter;
    private static Reservation current;
    private SelectionBehavior selection;

    public static Reservation getCurrentReservation() {
        if(reservations == null) {
            reservations = createReservationList();
        }

        if(current == null) {
            current = reservations.get(0);
        }

        return current;
    }

    public DataFormFragment() {
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data_form, container, false);

        RadListView listView = (RadListView) rootView.findViewById(R.id.data_form_main_list);
        if(reservations == null) {
            reservations = createReservationList();
        }

        TextView dateText = (TextView)rootView.findViewById(R.id.reservation_date_spinner_item_text);
        if(dateText != null) {
            DateFormat format = new SimpleDateFormat("EEE, dd.MM");
            dateText.setText(format.format(Calendar.getInstance().getTime()));
        }

        adapter = new ReservationAdapter(reservations);
        listView.setAdapter(adapter);

        rootView.findViewById(R.id.data_form_new_button).setOnClickListener(this);

        if(getResources().getBoolean(R.bool.dual_pane)) {
            editFragment = (DataFormEditFragment)this.getChildFragmentManager().findFragmentById(R.id.data_form_edit_fragment_id);
        }

        SelectionBehavior selection = new SelectionBehavior();
        selection.setSelectionMode(SelectionBehavior.SelectionMode.SINGLE);
        selection.setSelectionOnTouch(SelectionBehavior.SelectionOnTouch.ALWAYS);
        selection.addListener(this);
        listView.addBehavior(selection);

        this.selection = selection;

        if(getResources().getBoolean(R.bool.dual_pane)) {
            rootView.findViewById(R.id.data_form_done_button).setOnClickListener(this);
            rootView.findViewById(R.id.data_form_cancel_button).setOnClickListener(this);
        }

        return rootView;
    }

    public static List<Reservation> createReservationList() {
        List<Reservation> result = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setCreatorName("Rachel Nabors");
        reservation.setCreatorPhone("359-555-1236");
        reservation.setReservationDate(Calendar.getInstance().getTimeInMillis());
        reservation.setReservationTime(Calendar.getInstance().getTimeInMillis());
        reservation.setNumberOfGuests(10);
        reservation.setTableNumber(10);
        result.add(reservation);

        reservation = new Reservation();
        reservation.setCreatorName("Christian Heilmann");
        reservation.setCreatorPhone("359-555-1236");
        reservation.setReservationDate(Calendar.getInstance().getTimeInMillis());
        reservation.setReservationTime(Calendar.getInstance().getTimeInMillis());
        reservation.setNumberOfGuests(6);
        reservation.setTableNumber(15);
        result.add(reservation);

        reservation = new Reservation();
        reservation.setCreatorName("Thomas Drake");
        reservation.setCreatorPhone("359-555-1236");
        reservation.setReservationDate(Calendar.getInstance().getTimeInMillis());
        reservation.setReservationTime(Calendar.getInstance().getTimeInMillis());
        reservation.setNumberOfGuests(4);
        reservation.setTableNumber(1);
        result.add(reservation);

        reservation = new Reservation();
        reservation.setCreatorName("Aaron White");
        reservation.setCreatorPhone("359-555-1236");
        reservation.setReservationDate(Calendar.getInstance().getTimeInMillis());
        reservation.setReservationTime(Calendar.getInstance().getTimeInMillis());
        reservation.setNumberOfGuests(2);
        reservation.setTableNumber(2);
        result.add(reservation);

        reservation = new Reservation();
        reservation.setCreatorName("Nancy Davolio");
        reservation.setCreatorPhone("359-555-1236");
        reservation.setReservationDate(Calendar.getInstance().getTimeInMillis());
        reservation.setReservationTime(Calendar.getInstance().getTimeInMillis());
        reservation.setNumberOfGuests(6);
        reservation.setTableNumber(15);
        result.add(reservation);

        return result;
    }

    private void editCurrentReservation() {
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        DataFormEditFragment editFragment = new DataFormEditFragment();
        editFragment.setOnEditEndListener(this);
        transaction.replace(R.id.exampleContainer, editFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.data_form_new_button:
                createNewReservation();
                break;
            case R.id.data_form_done_button:
                commitEdit();
                break;
            case R.id.data_form_cancel_button:
                cancelEdit();
                break;
        }

    }

    private void cancelEdit() {
        RadDataForm dataForm = editFragment.dataForm();
        Object editedObject = dataForm.getEditedObject();
        if(editedObject == null) {
            return;
        }

        dataForm.setEntity(null);
        dataForm.setEntity(editedObject);

        DataFormEditFragment.initSpinnerData(editFragment.dataForm());
    }

    private void commitEdit() {
        RadDataForm dataForm = editFragment.dataForm();

        dataForm.commitChanges();

        if(newItem && !dataForm.hasValidationErrors()) {
            reservations.add((Reservation)dataForm.getEditedObject());
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
            newItem = false;
        }
    }

    private void createNewReservation() {
        newItem = true;
        current = new Reservation();
        selection.endSelection();
        if(getResources().getBoolean(R.bool.dual_pane)) {
            editFragment.setReservation(current);
        } else {
            this.editCurrentReservation();
        }
    }

    @Override
    public void onEditEnded(boolean success, Object editedItem) {
        if(newItem) {
            newItem = false;
            if(success) {
                reservations.add((Reservation)editedItem);
            }
        }
    }

    @Override
    public void onSelectionStarted() {

    }

    @Override
    public void onItemIsSelectedChanged(int position, boolean newValue) {
        if(!newValue) {
            return;
        }

        if(newItem) {
            newItem = false;
        }

        current = reservations.get(position);
        if(getResources().getBoolean(R.bool.dual_pane)) {
            editFragment.setReservation(current);
        } else {
            selection.endSelection();
            this.editCurrentReservation();
        }
    }

    @Override
    public void onSelectionEnded() {

    }
}
