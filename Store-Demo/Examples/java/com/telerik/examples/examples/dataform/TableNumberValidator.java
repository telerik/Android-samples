package com.telerik.examples.examples.dataform;

import com.telerik.widget.dataform.engine.PropertyValidatorBase;

public class TableNumberValidator extends PropertyValidatorBase {
    public TableNumberValidator() {
        this.setNegativeMessage("Please select a table from 1 to 50.");
    }

    @Override
    protected boolean validateCore(Object input, String propertyName) {
        Integer tableNumber = (Integer)input;

        return tableNumber != null && tableNumber >= 1 && tableNumber <= 50;
    }
}
