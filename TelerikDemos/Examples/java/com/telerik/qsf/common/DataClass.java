package com.telerik.qsf.common;

import java.util.Calendar;

public class DataClass {
    public String category;
    public double value;
    public double value2;
    public Calendar date;

    public DataClass(Calendar date) {
        this(date, 0, 0);
    }

    public DataClass(String category, double value) {
        this(category, value, 0);
    }

    public DataClass(Calendar date, double value) {
        this(date, value, 0);
    }

    public DataClass(Calendar date, double value, double value2) {
        this("", value, value2);
        this.date = date;
    }

    public DataClass(String category, double value, double value2) {
        this.category = category;
        this.value = value;
        this.value2 = value2;
    }


}
