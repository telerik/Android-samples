package com.telerik.examples.examples.dataform;

import com.telerik.widget.dataform.engine.PropertyValidator;
import com.telerik.widget.dataform.engine.ValidationCompletedCallback;
import com.telerik.widget.dataform.engine.ValidationInfo;

public class TableNumberValidator implements PropertyValidator {
    @Override
    public void validate(Object input, ValidationCompletedCallback callback) {
        Integer tableNumber = (Integer)input;

        ValidationInfo info;
        if(tableNumber == null || (tableNumber < 1 || tableNumber > 50)) {
            info = new ValidationInfo(false, "Please select a table from 1 to 50.", tableNumber);
        } else {
            info = new ValidationInfo(true, "The selected table is available.", tableNumber);
        }

        callback.validationCompleted(info);
    }
}
