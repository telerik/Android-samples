package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.sidedrawer.SideDrawerFeaturesFragment;

public class SideDrawerExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "Side Drawer";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> drawerExamples = new LinkedHashMap<String, ArrayList<ExampleFragment>>();

        ArrayList<ExampleFragment> result = new ArrayList<ExampleFragment>();
        result.add(new SideDrawerFeaturesFragment());
        drawerExamples.put("Init", result);


        return drawerExamples;
    }
}
