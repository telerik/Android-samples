package activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.autocomplete.AutoCompleteGettingStartedFragment;
import fragments.listview.ListViewCollapsibleFragment;
import fragments.listview.ListViewDataOperationsFragment;
import fragments.listview.ListViewDeckOfCardsFragment;
import fragments.listview.ListViewGettingStartedFragment;
import fragments.listview.ListViewItemAnimationFragment;
import fragments.listview.ListViewLayoutsFragment;
import fragments.listview.ListViewLoadOnDemandFragment;
import fragments.listview.ListViewReorderFragment;
import fragments.listview.ListViewSelectionFragment;
import fragments.listview.ListViewSlideFragment;
import fragments.listview.ListViewStickyHeadersFragment;
import fragments.listview.ListViewSwipeToExecuteFragment;
import fragments.listview.ListViewSwipeToRefreshFragment;
import fragments.listview.ListViewWrapFragment;

/**
 * Created by slazarov on 9/1/16.
 */
public class AutoCompleteExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "AutoComplete";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> examples = new LinkedHashMap<>();

        ArrayList<ExampleFragment> examplesSet = new ArrayList<>();
        examplesSet.add(new AutoCompleteGettingStartedFragment());
        examples.put("Init", examplesSet);

        return examples;
    }
}
