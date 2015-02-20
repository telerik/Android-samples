package activities;

import java.util.ArrayList;
import java.util.HashMap;

import fragments.listview.ListViewGettingStartedFragment;
import fragments.listview.ListViewDataAutomaticLoadOnDemandFragment;
import fragments.listview.ListViewDataManualLoadOnDemandFragment;
import fragments.listview.ListViewDataOperationsFragment;
import fragments.listview.ListViewItemAnimationFragment;
import fragments.listview.ListViewLayoutsFragment;
import fragments.listview.ListViewReorderFragment;
import fragments.listview.ListViewSwipeToExecuteFragment;
import fragments.listview.ListViewSwipeToRefreshFragment;

/**
 * Created by ginev on 2/20/2015.
 */
public class ListViewExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "ListView";
    }

    @Override
    public HashMap<String, ArrayList<ExampleFragment>> examples() {
        HashMap<String, ArrayList<ExampleFragment>> examples = new HashMap<>();

        ArrayList<ExampleFragment> examplesSet = new ArrayList<>();

        examplesSet.add(new ListViewGettingStartedFragment());

        examples.put("Binding", examplesSet);

        examplesSet = new ArrayList<>();

        examplesSet.add(new ListViewReorderFragment());
        examplesSet.add(new ListViewSwipeToExecuteFragment());
        examplesSet.add(new ListViewSwipeToRefreshFragment());
        examplesSet.add(new ListViewItemAnimationFragment());
        examplesSet.add(new ListViewDataManualLoadOnDemandFragment());
        examplesSet.add(new ListViewDataAutomaticLoadOnDemandFragment());
        examplesSet.add(new ListViewDataOperationsFragment());
        examplesSet.add(new ListViewLayoutsFragment());

        examples.put("Features", examplesSet);

        return examples;
    }
}
