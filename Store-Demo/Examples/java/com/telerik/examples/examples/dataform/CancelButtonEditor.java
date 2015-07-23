package com.telerik.examples.examples.dataform;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.widget.dataform.engine.EntityProperty;
import com.telerik.widget.dataform.visualization.core.EntityPropertyEditor;

public class CancelButtonEditor extends EntityPropertyEditor implements View.OnClickListener {
    private TextView cancelButton;
    private boolean cancelled;

    public CancelButtonEditor(Context context, EntityProperty property) {
        super(context, R.layout.data_form_cancel_editor, R.id.data_form_cancel_editor_header, R.id.data_form_cancel_editor_button, R.id.data_form_cancel_button_validation, property);

        cancelButton = (TextView)editorView;
        cancelButton.setOnClickListener(this);
    }

    @Override
    protected void applyEntityValueToEditor(Object entityValue) {
        Boolean isCancelled = (Boolean)entityValue;

        if(isCancelled) {
            cancelButton.setText("CANCELLED");
        } else {
            cancelButton.setText("CANCEL RESERVATION");
        }

        cancelled = isCancelled;
    }

    @Override
    protected boolean supportsType(Class type) {
        return type == boolean.class || type == Boolean.class;
    }

    @Override
    public Object value() {
        return cancelled;
    }

    @Override
    public void onClick(View v) {
        applyEntityValueToEditor(!cancelled);

        onEditorValueChanged(cancelled);
    }
}
