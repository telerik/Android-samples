package com.telerik.examples.examples.dataform;

import android.view.View;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.widget.dataform.engine.EntityProperty;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.dataform.visualization.core.EntityPropertyEditor;

public class CancelButtonEditor extends EntityPropertyEditor implements View.OnClickListener {
    private TextView cancelButton;
    private boolean cancelled;

    public CancelButtonEditor(RadDataForm dataForm, EntityProperty property) {
        super(dataForm, dataForm.getEditorsMainLayout(),
                dataForm.getEditorsHeaderLayout(),
                R.id.data_form_text_viewer_header,
                R.layout.data_form_cancel_editor_button,
                R.id.data_form_cancel_editor_button,
                dataForm.getEditorsValidationLayout(), property);

        cancelButton = (TextView)editorView;
        cancelButton.setOnClickListener(this);
    }

    @Override
    protected void initHeader(View headerView, EntityProperty property) {
        super.initHeader(headerView, property);

        headerView.setVisibility(View.GONE);
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
    public Object value() {
        return cancelled;
    }

    @Override
    public void onClick(View v) {
        applyEntityValueToEditor(!cancelled);

        onEditorValueChanged(cancelled);
    }
}
