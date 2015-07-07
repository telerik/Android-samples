package activities;

import java.util.ArrayList;
import java.util.HashMap;

import fragments.dataform.*;

public class DataFormExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "Data Form";
    }

    @Override
    public HashMap<String, ArrayList<ExampleFragment>> examples() {

        HashMap<String, ArrayList<ExampleFragment>> dataFormExamples = new HashMap<String, ArrayList<ExampleFragment>>();

        ArrayList<ExampleFragment> result = new ArrayList<ExampleFragment>();
        result.add(new DataFormGettingStartedFragment());
        result.add(new DataFormFeaturesFragment());
        result.add(new DataFormEditorsFragment());
        result.add(new DataFormLayoutFragment());
        result.add(new DataFormValidationFragment());
        result.add(new DataFormFeaturesFragment());
        dataFormExamples.put("Examples", result);


        return dataFormExamples;
    }
}
