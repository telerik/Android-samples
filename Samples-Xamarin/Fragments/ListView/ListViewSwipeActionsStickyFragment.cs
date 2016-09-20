
using System;
using System.Collections;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Support.V4.App;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.List;

namespace Samples
{
	public class ListViewSwipeActionsStickyFragment : Fragment, ExampleFragment, SwipeActionsBehavior.ISwipeActionsListener
	{
		private RadListView listView;
		private SwipeActionsBehavior sap;
		private int leftWidth = -1;
		private int rightWidth = -1;
		private View leftActionView;
		private View rightActionView;

		public override View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_example, container, false);

			this.listView = (RadListView)rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

			ArrayList dataSource = new ArrayList();

			EmailMessage message = new EmailMessage();
			message.title = "Tech news";
			message.content = "Here is your daily LinkedIn feed with news about .NET, Java, iOS and more...";
			dataSource.Add(message);

			message = new EmailMessage();
			message.title = "Awaiting Payment";
			message.content = "Monthly bills summary: water supply, electricity, earth gas...";
			dataSource.Add(message);

			message = new EmailMessage();
			message.title = "Greetings from Hawai";
			message.content = "Hey Betty, we've just arrived! What a flight!...";
			dataSource.Add(message);

			this.listView.SetAdapter(new ListViewSwipeActionsAdapter(dataSource));

			sap = new SwipeActionsBehavior();

			sap.AddListener(this);

			this.listView.AddBehavior(sap);

			return rootView;
		}

		public string Title()
		{
			return "Swipe Actions: Modify swipe content";
		}

		// >> swipe-actions-sticky-xamarin

		public void OnExecuteFinished(SwipeActionsBehavior.SwipeActionEvent p0)
		{
			// Fired when the swipe-execute procedure has ended, i.e. the item being swiped is at
			// its original position.
			this.leftWidth = -1;
			this.rightWidth = -1;
		}

		public void OnSwipeEnded(SwipeActionsBehavior.SwipeActionEvent p0)
		{

		}

		public void OnSwipeStateChanged(SwipeActionsBehavior.SwipeActionsState oldState, SwipeActionsBehavior.SwipeActionsState newState)
		{
		}

		public void OnSwipeProgressChanged(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent)
		{
			if (swipeActionEvent.CurrentOffset() > this.leftWidth)
			{
				ViewGroup.LayoutParams lp = this.leftActionView.LayoutParameters;
				lp.Width = swipeActionEvent.CurrentOffset();
				this.leftActionView.LayoutParameters = lp;
			}

			if (swipeActionEvent.CurrentOffset() < -rightWidth)
			{
				ViewGroup.LayoutParams lp = this.rightActionView.LayoutParameters;
				lp.Width = -swipeActionEvent.CurrentOffset();
				this.rightActionView.LayoutParameters = lp;
			}
		}

		public void OnSwipeStarted(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent)
		{
			ViewGroup swipeView = (ViewGroup)swipeActionEvent.SwipeView();
			this.leftActionView = swipeView.GetChildAt(0);
			this.rightActionView = swipeView.GetChildAt(1);

			if (leftWidth == -1)
			{
				leftWidth = this.leftActionView.Width;
			}

			if (rightWidth == -1)
			{
				rightWidth = this.rightActionView.Width;
			}
		}

		// << swipe-actions-sticky-xamarin
	}
}
