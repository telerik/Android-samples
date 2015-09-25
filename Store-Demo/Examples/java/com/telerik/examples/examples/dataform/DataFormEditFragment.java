package com.telerik.examples.examples.dataform;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.telerik.examples.R;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.widget.dataform.visualization.RadDataForm;
import com.telerik.widget.dataform.visualization.core.CommitMode;
import com.telerik.widget.dataform.visualization.editors.DataFormSpinnerEditor;
import com.telerik.widget.dataform.visualization.editors.adapters.EditorSpinnerAdapter;

public class DataFormEditFragment extends ExampleFragmentBase implements View.OnClickListener {
    private RadDataForm dataForm;
    private OnEditEndListener editEndListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data_form_edit, container, false);
        dataForm = (RadDataForm)rootView.findViewById(R.id.reservation_data_form);
        dataForm.setEntity(DataFormFragment.getCurrentReservation());

        if(dataForm.getEntity() != null) {
            initSpinnerData(dataForm);
        }

        dataForm.setCommitMode(CommitMode.MANUAL);

        if(getResources().getBoolean(R.bool.dual_pane)) {
            rootView.findViewById(R.id.data_form_edit_commit_bar).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.data_form_done_button).setOnClickListener(this);
            rootView.findViewById(R.id.data_form_cancel_button).setOnClickListener(this);
        }

        return rootView;
    }

    public RadDataForm dataForm() {
        return dataForm;
    }

    public void setReservation(Reservation value) {
        if(value == null) {
            return;
        }

        this.dataForm.setEntity(value);
        initSpinnerData(dataForm);
    }

    public static void initSpinnerData(RadDataForm dataForm) {
        ((DataFormSpinnerEditor) dataForm.getExistingEditorForProperty("TableSection")).setAdapter(new EditorSpinnerAdapter(dataForm.getContext(), new String[]{"Patio", "First floor", "Second floor", "Third floor"}));
        ((DataFormSpinnerEditor) dataForm.getExistingEditorForProperty("Origin")).setAdapter(new EditorSpinnerAdapter(dataForm.getContext(), new String[]{"phone", "in-person", "online", "other"}));
        ((DataFormSpinnerEditor) dataForm.getExistingEditorForProperty("TableNumber")).setAdapter(new EditorSpinnerAdapter(dataForm.getContext(), createTableNumbers()));
    }

    public static Integer[] createTableNumbers() {
        Integer[] result = new Integer[15];
        for(Integer i = 1; i <= 15; i++) {
            result[i - 1] = i;
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.data_form_done_button) {
            dataForm.commitChanges();
            if(!dataForm.hasValidationErrors()) {
                getFragmentManager().popBackStackImmediate();
                hideKeyboard();
                notifyListener(true, dataForm.getEditedObject());
            }
        } else {
            getFragmentManager().popBackStackImmediate();
            hideKeyboard();
            notifyListener(false, dataForm.getEditedObject());
        }
    }

    @Override
    public boolean onBackPressed() {
        boolean handled = super.onBackPressed();

        if(!handled) {
            notifyListener(false, dataForm.getEditedObject());
        }

        return handled;
    }

    public void setOnEditEndListener(OnEditEndListener listener) {
        this.editEndListener = listener;
    }

    private void notifyListener(boolean success, Object editedObject) {
        if(this.editEndListener == null) {
            return;
        }

        this.editEndListener.onEditEnded(success, editedObject);
    }

    private void hideKeyboard() {
        Activity activity = (Activity)this.dataForm.getContext();
        InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if(activity.getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
