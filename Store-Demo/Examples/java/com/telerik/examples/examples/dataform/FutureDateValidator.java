package com.telerik.examples.examples.dataform;

import com.telerik.widget.calendar.CalendarTools;
import com.telerik.widget.dataform.engine.PropertyValidatorBase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FutureDateValidator extends PropertyValidatorBase {
    public FutureDateValidator() {
        this.setNegativeMessage("The selected date must be a future date.");
    }

    @Override
    protected boolean validateCore(Object input, String propertyName) {
        Calendar today = Calendar.getInstance();
        Calendar selected = new GregorianCalendar();

        if(Calendar.class.isAssignableFrom(input.getClass())) {
            selected = (Calendar)input;
        } else {
            Long millis = (Long) input;
            selected.setTimeInMillis(millis);
        }
        long todayTime = CalendarTools.getDateStart(today.getTimeInMillis());
        long selectedTime = CalendarTools.getDateStart(selected.getTimeInMillis());
        return selectedTime >= todayTime;
    }
}
