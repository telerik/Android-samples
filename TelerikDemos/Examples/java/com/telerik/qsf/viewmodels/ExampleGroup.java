package com.telerik.qsf.viewmodels;
import com.telerik.qsf.viewmodels.Example;

import java.util.ArrayList;

public class ExampleGroup extends Example {

    private ArrayList<Example> examples = null;

    public ArrayList<Example> getExamples() {
        if (this.examples == null) {
            this.examples = new ArrayList<Example>();
        }
        return this.examples;
    }

    public Boolean isExample = false;
}
