package com.telerik.examples.common;

import java.util.Calendar;

public class FinancialDataClass {

    public float open;
    public float high;
    public float low;
    public float close;
    public float volume;

    public Calendar date;

    public FinancialDataClass(Calendar date, float open, float high, float low, float close, float volume) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.date = date;
        this.volume = volume;
    }
}
