package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.dataform.*;

public class DataFormExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "DataForm";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> dataFormExamples = new LinkedHashMap<>();

        ArrayList<ExampleFragment> result = new ArrayList<>();
        result.add(new DataFormGettingStartedFragment());
        result.add(new DataFormFeaturesFragment());
        result.add(new DataFormEditorsFragment());
        result.add(new DataFormAutoCompleteTextViewEditorFragment());
        result.add(new DataFormValidationFragment());

        result.add(new DataFormGroupLayoutFragment());
        result.add(new DataFormExpandableGroupsFragment());
        result.add(new DataFormPlaceholderLayoutFragment());
        result.add(new DataFormValidationBehaviorFragment());
        result.add(new DataFormValidationModeFragment());

        result.add(new DataFormJsonEditFragment());
        result.add(new DataFormSchemaSetupFragment());

        result.add(new DataFormCommitEventsFragment());

        result.add(new DataFormEditorStylesFragment());
        result.add(new DataFormLabelPositionsFragment());
        result.add(new DataFormImageLabelsFragment());

        dataFormExamples.put("Features", result);

        return dataFormExamples;
    }
}
