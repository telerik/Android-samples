package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.dataform.DataFormGroupLayoutFragment;
import fragments.dataform.*;

public class DataFormExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "Data Form";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> dataFormExamples = new LinkedHashMap<>();

        ArrayList<ExampleFragment> result = new ArrayList<>();
        result.add(new DataFormGettingStartedFragment());
        result.add(new DataFormFeaturesFragment());
        result.add(new DataFormEditorsFragment());
        result.add(new DataFormValidationFragment());

        result.add(new DataFormGroupLayoutFragment());
        result.add(new DataFormPlaceholderLayoutFragment());
        result.add(new DataFormValidationBehaviorFragment());
        result.add(new DataFormValidationModeFragment());

        result.add(new DataFormJsonEditFragment());
        result.add(new DataFormSchemaSetupFragment());

        dataFormExamples.put("Features", result);

        return dataFormExamples;
    }
}
