package com.telerik.examples.examples.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.examples.R;

import java.util.ArrayList;

public class HotelListAdapter extends ArrayAdapter<Hotel> {
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
        View view = View.inflate(getContext(), R.layout.calendar_range_list_item, null);
        Hotel currentHotel = this.getItem(position);
        TextView hotelName = (TextView) view.findViewById(R.id.txtHotelName);
        TextView priceTag = (TextView) view.findViewById(R.id.txtPrice);
        hotelName.setText(currentHotel.getName());
        priceTag.setText("$ " + currentHotel.getPrice());
        ImageView hotelImage = (ImageView) view.findViewById(R.id.hotelImage);
        hotelImage.setImageResource(getContext().getResources().getIdentifier("calendar_pic_" + (position + 1), "drawable", getContext().getPackageName()));
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