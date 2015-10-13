package com.telerik.examples.examples.dataform;

import com.telerik.widget.dataform.engine.PropertyValidatorBase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FutureDateValidator extends PropertyValidatorBase {
    public FutureDateValidator() {
        this.setNegativeMessage("The selected date must be a future date.");
    }

    @Override
    protected boolean validateCore(Object input, String propertyName) {
        Long millis = (Long)input;
        Calendar today = Calendar.getInstance();
        Calendar selected = new GregorianCalendar();
        selected.setTimeInMillis(millis);

        return selected.after(today);
    }
}
