package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.tabview.TabViewGettingStartedFragment;

public class TabViewExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "TabView";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> tabViewExamples = new LinkedHashMap<>();

        ArrayList<ExampleFragment> result = new ArrayList<>();
        result.add(new TabViewGettingStartedFragment());
        tabViewExamples.put("Init", result);


        return tabViewExamples;
    }
}
