package com.telerik.examples.viewmodels;

import java.io.Serializable;
import java.util.ArrayList;

public class ExampleSourceModel implements Serializable {
    private ArrayList<String> dependencies;
    private int currentIndex;

    public ExampleSourceModel(ArrayList<String> dependencyFileNames) {
        this.dependencies = dependencyFileNames;

        if (this.dependencies == null) {
            this.dependencies = new ArrayList<String>();
        }
    }

    public ArrayList<String> getDependencies() {
        return this.dependencies;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int value) {
        this.currentIndex = value;
    }
}
