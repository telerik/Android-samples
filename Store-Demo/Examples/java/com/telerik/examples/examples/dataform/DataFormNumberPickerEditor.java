package com.telerik.examples.examples.dataform;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.widget.dataform.engine.EntityProperty;
import com.telerik.widget.dataform.engine.NotifyPropertyChanged;
import com.telerik.widget.dataform.engine.NotifyPropertyChangedBase;
import com.telerik.widget.dataform.engine.PropertyChangedListener;
import com.telerik.widget.dataform.visualization.core.EntityPropertyEditor;

public class DataFormNumberPickerEditor extends EntityPropertyEditor implements View.OnClickListener, NotifyPropertyChanged {
    private int minimum = 1;
    private int maximum = 10;
    private TextView numberView;
    private Integer value = 0;
    private NotifyPropertyChangedBase propertyChangeImpl = new NotifyPropertyChangedBase();

    public DataFormNumberPickerEditor(Context context, EntityProperty property) {
        super(context, R.layout.data_form_number_picker, R.id.data_form_number_picker_header, R.id.data_form_number_picker_editor, R.id.data_form_validation_view, property);

        ((TextView)headerView).setText(property.getHeader());

        TextView plusButton = (TextView)this.findViewById(R.id.number_picker_plus);
        plusButton.setOnClickListener(this);
        TextView minusButton = (TextView)this.findViewById(R.id.number_picker_minus);
        minusButton.setOnClickListener(this);

        numberView = (TextView)this.editorView;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMinimum(int value) {
        this.minimum = value;
    }

    public void setMaximum(int value) {
        this.maximum = value;
    }

    @Override
    public Object value() {
        return value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        if(value < minimum) {
            value = minimum;
        }

        if(value > maximum) {
            value = maximum;
        }

        this.value = value;
    }

    @Override
    protected void applyEntityValueToEditor(Object entityValue) {
        Integer newValue = (Integer)entityValue;
        if(newValue == null) {
            newValue = minimum;
        }

        this.value = newValue;

        this.updateTextView(value);
    }

    @Override
    protected boolean supportsType(Class type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.number_picker_minus) {
            if(value > minimum) {
                value--;
            } else {
                return;
            }
        } else {
            if(value < maximum) {
                value++;
            } else {
                return;
            }
        }

        this.updateTextView(value);
        propertyChangeImpl.notifyListeners("Value", this.value);

        onEditorValueChanged(value);
    }

    private void updateTextView(Integer value) {
        String guestText;
        if(value == 1) {
            guestText = " guest";
        } else {
            guestText = " guests";
        }

        numberView.setText(value.toString() + guestText);
    }

    @Override
    public void addPropertyChangedListener(PropertyChangedListener listener) {
        propertyChangeImpl.addPropertyChangedListener(listener);
    }

    @Override
    public void removePropertyChangedListener(PropertyChangedListener listener) {
        propertyChangeImpl.removePropertyChangedListener(listener);
    }
}
