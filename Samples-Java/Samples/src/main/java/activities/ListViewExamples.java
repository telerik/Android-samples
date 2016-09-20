package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.listview.ListViewCollapsibleFragment;
import fragments.listview.ListViewDeckOfCardsFragment;
import fragments.listview.ListViewGettingStartedFragment;
import fragments.listview.ListViewLoadOnDemandFragment;
import fragments.listview.ListViewSelectionFragment;
import fragments.listview.ListViewDataOperationsFragment;
import fragments.listview.ListViewItemAnimationFragment;
import fragments.listview.ListViewLayoutsFragment;
import fragments.listview.ListViewReorderFragment;
import fragments.listview.ListViewSlideFragment;
import fragments.listview.ListViewStickyHeadersFragment;
import fragments.listview.ListViewSwipeActionsGettingStartedFragment;
import fragments.listview.ListViewSwipeActionsStickyFragment;
import fragments.listview.ListViewSwipeActionsStickyThresholdFragment;
import fragments.listview.ListViewSwipeActionsThresholdsFragment;
import fragments.listview.ListViewSwipeToExecuteFragment;
import fragments.listview.ListViewSwipeToRefreshFragment;
import fragments.listview.ListViewWrapFragment;

public class ListViewExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "ListView";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> examples = new LinkedHashMap<>();

        ArrayList<ExampleFragment> examplesSet = new ArrayList<>();

        examplesSet.add(new ListViewGettingStartedFragment());
        examplesSet.add(new ListViewLayoutsFragment());
        examplesSet.add(new ListViewDeckOfCardsFragment());
        examplesSet.add(new ListViewSlideFragment());
        examplesSet.add(new ListViewWrapFragment());
        examplesSet.add(new ListViewItemAnimationFragment());
        examplesSet.add(new ListViewDataOperationsFragment());

        examples.put("Features", examplesSet);

        examplesSet = new ArrayList<>();

        examplesSet.add(new ListViewReorderFragment());
        examplesSet.add(new ListViewSwipeToExecuteFragment());
        examplesSet.add(new ListViewSwipeActionsGettingStartedFragment());
        examplesSet.add(new ListViewSwipeActionsThresholdsFragment());
        examplesSet.add(new ListViewSwipeActionsStickyThresholdFragment());
        examplesSet.add(new ListViewSwipeActionsStickyFragment());
        examplesSet.add(new ListViewSwipeToRefreshFragment());
        examplesSet.add(new ListViewLoadOnDemandFragment());
        examplesSet.add(new ListViewSelectionFragment());
        examplesSet.add(new ListViewStickyHeadersFragment());
        examplesSet.add(new ListViewCollapsibleFragment());

        examples.put("Behaviors", examplesSet);

        return examples;
    }
}
