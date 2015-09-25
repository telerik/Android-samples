package com.telerik.examples.examples.dataform;

import android.view.ViewGroup;

import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;

import java.util.List;

public class ReservationAdapter extends ListViewAdapter {
    /**
     * Creates an instance of the {@link ListViewAdapter} class.
     *
     * @param items a list of items that will be handled by this adapter
     */
    public ReservationAdapter(List items) {
        super(items);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(new ReservationListItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        ReservationListItem itemView = (ReservationListItem) holder.itemView;
        itemView.setReservation((Reservation) getItems().get(position));
    }
}
