package activities;

import java.util.ArrayList;
import java.util.HashMap;

import fragments.sidedrawer.SideDrawerFeaturesFragment;

public class SideDrawerExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "Side Drawer";
    }

    @Override
    public HashMap<String, ArrayList<ExampleFragment>> examples() {
        HashMap<String, ArrayList<ExampleFragment>> drawerExamples = new HashMap<String, ArrayList<ExampleFragment>>();

        ArrayList<ExampleFragment> result = new ArrayList<ExampleFragment>();
        result.add(new SideDrawerFeaturesFragment());
        drawerExamples.put("Features", result);


        return drawerExamples;
    }
}
