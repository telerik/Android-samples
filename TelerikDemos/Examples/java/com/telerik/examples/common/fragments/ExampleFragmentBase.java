package com.telerik.examples.common.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ExampleFragmentBase extends Fragment {

    private ExampleLoadedListener listener;

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

    public String getSourceKey(){
        return this.getClass().getSimpleName();
    }

    public String getEQATECCategory(){
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
