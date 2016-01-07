using System;
using Java.Util;

namespace Samples
{
	public class ListViewExamples : Java.Lang.Object, ExamplesProvider
	{
		private HashMap listViewExamples;

		public ListViewExamples ()
		{
			this.listViewExamples = this.getListViewExamples();
		}

		public String ControlName() {
			return "ListView";
		}

		public HashMap Examples(){
			return this.listViewExamples;
		}

		private HashMap getListViewExamples(){

			HashMap examples = new HashMap();

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
			examplesSet.Add (new ListViewSwipeToRefreshFragment());
			examplesSet.Add (new ListViewManualLoadOnDemandFragment());
			examplesSet.Add (new ListViewSelectionFragment());
			examplesSet.Add (new ListViewDataAutomaticLoadOnDemandFragment());
			examplesSet.Add (new ListViewStickyHeadersFragment ());
			examplesSet.Add (new ListViewCollapsibleFragment ());

			examples.Put("Behaviors", examplesSet);

			return examples;
		
		}
	}
}

