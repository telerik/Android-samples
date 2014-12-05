package activities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ginev on 12/5/2014.
 */
public interface ExamplesProvider {
    String controlName();
    HashMap<String, ArrayList<ExampleFragment>> examples();
}
