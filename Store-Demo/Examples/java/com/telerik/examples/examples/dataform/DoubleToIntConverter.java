package com.telerik.examples.examples.dataform;

import com.telerik.widget.dataform.engine.PropertyConverter;

public class DoubleToIntConverter implements PropertyConverter {

    @Override
    public Object convertTo(Object source) {
        return ((Number)source).intValue();
    }

    @Override
    public Object convertFrom(Object source) {
        return ((Number)source).doubleValue();
    }
}
