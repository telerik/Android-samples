package com.telerik.examples.common;

import java.util.Calendar;

public class DataClass {

    public String category;
    public Float value;
    public Float value2;
    public Float value3;
    public Float value4;
    public Calendar date;

    public DataClass(Calendar date) {
        this(date, 0.0F, 0.0F);
    }

    public DataClass(String category, float value) {
        this(category, value, 0.0F);
    }

    public DataClass(Calendar date, float value) {
        this(date, value, 0.0F);
    }

    public DataClass(Calendar date, float value, float value2) {
        this("", value, value2);
        this.date = date;
    }

    public DataClass(Calendar date, float value, float value2, float value3, float value4) {
        this(date, value, value2);
        this.value3 = value3;
        this.value4 = value4;
    }

    public DataClass(String category, float value, float value2) {
        this.category = category;
        this.value = value;
        this.value2 = value2;
    }

    public DataClass(String category, float value, float value2, float value3, float value4) {
        this.category = category;
        this.value = value;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }
}
