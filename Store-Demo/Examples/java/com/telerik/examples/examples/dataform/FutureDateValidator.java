package com.telerik.examples.examples.dataform;

import com.telerik.widget.dataform.engine.PropertyValidator;
import com.telerik.widget.dataform.engine.ValidationCompletedCallback;
import com.telerik.widget.dataform.engine.ValidationInfo;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FutureDateValidator implements PropertyValidator {
    @Override
    public void validate(Object input, ValidationCompletedCallback callback) {
        Long millis = (Long)input;
        Calendar today = Calendar.getInstance();
        Calendar selected = new GregorianCalendar();
        selected.setTimeInMillis(millis);

        ValidationInfo info;
        if(today.after(selected)) {
            info = new ValidationInfo(false, "The selected date must be a future date.", millis);
        } else {
            info = new ValidationInfo(true, "The selected date is valid.", millis);
        }

        callback.validationCompleted(info);
    }
}
