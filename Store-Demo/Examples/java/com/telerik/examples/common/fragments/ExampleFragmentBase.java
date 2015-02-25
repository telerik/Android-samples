package com.telerik.examples.common.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import com.telerik.examples.viewmodels.ExampleSourceModel;

import java.util.ArrayList;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ExampleFragmentBase extends Fragment {

    private ExampleLoadedListener listener;

    public void onBackPressed() {
    }

    public ExampleFragmentBase() {
        // Required empty public constructor
    }

    public void unloadExample() {
    }

    public void onHidden() {

    }

    public void onVisualized() {

    }

    public void onExampleSuspended() {

    }

    public void onExampleResumed() {

    }

    public ExampleSourceModel getSourceCodeModel() {
        return new ExampleSourceModel(this.getClassHierarchyNames());
    }

    private ArrayList<String> getClassHierarchyNames() {
        ArrayList<String> classes = new ArrayList<String>();

        for (Class c = this.getClass(); c != null; c = c.getSuperclass()) {
            if (c.getSimpleName().equals(ExampleFragmentBase.class.getSimpleName())) {
                break;
            }

            classes.add(c.getSimpleName());
        }

        return classes;
    }

    public String getEQATECCategory() {
        return "";
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.listener != null) {
            this.listener.onExampleLoaded(this.getView());
        }
    }

    public void setOnExampleLoadedListener(ExampleLoadedListener listener) {
        if (listener != null && this.listener != null) {
            throw new IllegalArgumentException("Listener already set!");
        }
        this.listener = listener;
    }

    public interface ExampleLoadedListener {
        void onExampleLoaded(View root);
    }
}
