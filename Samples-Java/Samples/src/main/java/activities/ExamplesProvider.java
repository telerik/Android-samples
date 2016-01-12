package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface ExamplesProvider {
    String controlName();
    LinkedHashMap<String, ArrayList<ExampleFragment>> examples();
}
