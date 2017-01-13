package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.tabview.TabViewChangesFragment;
import fragments.tabview.TabViewGettingStartedFragment;
import fragments.tabview.TabViewLayoutsFragment;
import fragments.tabview.TabViewTabsPositionFragment;

public class TabViewExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "TabView Beta";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> tabViewExamples = new LinkedHashMap<>();

        ArrayList<ExampleFragment> result = new ArrayList<>();
        result.add(new TabViewGettingStartedFragment());
        result.add(new TabViewTabsPositionFragment());
        result.add(new TabViewLayoutsFragment());
        result.add(new TabViewChangesFragment());
        tabViewExamples.put("Init", result);


        return tabViewExamples;
    }
}
