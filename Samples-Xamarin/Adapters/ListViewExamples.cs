using System;
using Java.Util;

namespace Samples
{
	public class ListViewExamples : Java.Lang.Object, ExamplesProvider
	{
		private LinkedHashMap listViewExamples;

		public ListViewExamples ()
		{
			this.listViewExamples = this.getListViewExamples();
		}

		public String ControlName() {
			return "ListView";
		}

		public LinkedHashMap Examples(){
			return this.listViewExamples;
		}

		private LinkedHashMap getListViewExamples(){

			LinkedHashMap examples = new LinkedHashMap();

			ArrayList examplesSet = new ArrayList();

			examplesSet.Add (new ListViewGettingStartedFragment());
			examplesSet.Add (new ListViewLayoutsFragment());
			examplesSet.Add (new ListViewDeckOfCardsFragment());
			examplesSet.Add (new ListViewSlideFragment());
			examplesSet.Add (new ListViewWrapFragment());
			examplesSet.Add (new ListViewItemAnimationFragment());
			examplesSet.Add (new ListViewDataOperationsFragment());

			examples.Put("Features", examplesSet);

			examplesSet = new ArrayList();

			examplesSet.Add (new ListViewReorderFragment());
			examplesSet.Add (new ListViewSwipeToExecuteFragment());
			examplesSet.Add(new ListViewSwipeActionsGettingStartedFragment());
			examplesSet.Add(new ListViewSwipeActionsStickyFragment());
			examplesSet.Add(new ListViewSwipeActionsThresholdsFragment());
			examplesSet.Add(new ListViewSwipeActionsStickyThresholdFragment());
			examplesSet.Add (new ListViewSwipeToRefreshFragment());
			examplesSet.Add (new ListViewManualLoadOnDemandFragment());
			examplesSet.Add (new ListViewDataAutomaticLoadOnDemandFragment());
			examplesSet.Add (new ListViewSelectionFragment());
			examplesSet.Add (new ListViewStickyHeadersFragment ());
			examplesSet.Add (new ListViewCollapsibleFragment ());

			examples.Put("Behaviors", examplesSet);

			return examples;
		
		}
	}
}

