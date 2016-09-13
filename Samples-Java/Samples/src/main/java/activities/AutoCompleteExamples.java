package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.autocomplete.AutoCompleteCustomizationFragment;
import fragments.autocomplete.AutoCompleteGettingStartedFragment;
import fragments.autocomplete.AutoCompleteTokenLayoutsFragment;

public class AutoCompleteExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "RadAutoCompleteTextView";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> examples = new LinkedHashMap<>();

        ArrayList<ExampleFragment> examplesSet = new ArrayList<>();
        examplesSet.add(new AutoCompleteGettingStartedFragment());
        examplesSet.add(new AutoCompleteTokenLayoutsFragment());
        examplesSet.add(new AutoCompleteCustomizationFragment());
        examples.put("Feature Review", examplesSet);

        return examples;
    }
}
