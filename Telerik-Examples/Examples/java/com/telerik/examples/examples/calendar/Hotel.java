package com.telerik.examples.examples.calendar;

public class Hotel {
    private String name;
    private float price;
    private int roomCount;
    private int bedsPerRoom;

    public int getBedsPerRoom() {
        return this.bedsPerRoom;
    }

    public void setBedsPerRoom(int beds) {
        this.bedsPerRoom = beds;
    }

    public int getRoomCount() {
        return this.roomCount;
    }

    public void setRoomCount(int rooms) {
        this.roomCount = rooms;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}