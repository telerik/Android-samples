using System;
using Java.Util;

namespace Samples
{
	public class ListViewExamples : Java.Lang.Object, ExamplesProvider
	{
		private HashMap listViewExamples;

		public ListViewExamples ()
		{
			this.listViewExamples = this.getChartExamples();
		}

		public String ControlName() {
			return "ListView";
		}

		public HashMap Examples(){
			return this.listViewExamples;
		}

		private HashMap getChartExamples(){

			HashMap examples = new HashMap();

			ArrayList examplesSet = new ArrayList();

			examplesSet.Add(new ListViewGettingStartedFragment());

			examples.Put("Binding", examplesSet);

			examplesSet = new ArrayList();

			examplesSet.Add(new ListViewReorderFragment());
			examplesSet.Add(new ListViewSwipeToExecuteFragment());
			examplesSet.Add(new ListViewSwipeToRefreshFragment());
			examplesSet.Add(new ListViewItemAnimationFragment());
			examplesSet.Add(new ListViewManualLoadOnDemandFragment());
			examplesSet.Add(new ListViewDataAutomaticLoadOnDemandFragment());
			examplesSet.Add(new ListViewDataOperationsFragment());
			examplesSet.Add(new ListViewLayoutsFragment());

			examples.Put("Features", examplesSet);

			return examples;
		
		}
	}
}

