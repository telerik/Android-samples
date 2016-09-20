
using System;
using System.Collections;
using Android.OS;
using Android.Runtime;
using Android.Support.V4.App;
using Android.Util;
using Android.Views;
using Android.Views.Animations;
using Android.Widget;
using Com.Telerik.Widget.List;

namespace Samples
{
	public class ListViewSwipeActionsStickyThresholdFragment : Fragment, ExampleFragment, SwipeActionsBehavior.ISwipeActionsListener
	{
		private RadListView listView;
		private SwipeActionsBehavior sap;
		private int leftWidth = -1;
		private int rightWidth = -1;
		private ViewGroup swipeView;
		private ViewGroup leftActionView;
		private ViewGroup rightActionView;
		private bool animationApplied;


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
			return "Swipe Actions: threshold animations";
		}

		// >> swipe-actions-animated-actions-xamarin

		public void OnExecuteFinished(SwipeActionsBehavior.SwipeActionEvent p0)
		{
			// Fired when the swipe-execute procedure has ended, i.e. the item being swiped is at
			// its original position.
			this.leftWidth = -1;
			this.rightWidth = -1;
			this.animationApplied = false;
		}

		public void OnSwipeEnded(SwipeActionsBehavior.SwipeActionEvent p0)
		{

		}

		public void OnSwipeStateChanged(SwipeActionsBehavior.SwipeActionsState oldState, SwipeActionsBehavior.SwipeActionsState newState)
		{
		}

		public void OnSwipeProgressChanged(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent)
		{
			if (swipeActionEvent.CurrentOffset() > leftWidth)
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

			if (!this.animationApplied)
			{
				if (Math.Abs(swipeActionEvent.CurrentOffset()) > Math.Abs(leftWidth) * 1.5f)
				{
					if (swipeActionEvent.CurrentOffset() < 0)
					{
						this.rightActionView.GetChildAt(0).ClearAnimation();
						RotateAnimation ra = new RotateAnimation(0, 360, 0.5f, 0.5f);
						ra.Interpolator = new AccelerateDecelerateInterpolator();
						ra.Duration = 200;
						this.rightActionView.GetChildAt(0).StartAnimation(ra);
					}
					else if (swipeActionEvent.CurrentOffset() > 0)
					{
						this.leftActionView.GetChildAt(0).ClearAnimation();
						RotateAnimation ra = new RotateAnimation(0, 360, 0.5f, 0.5f);
						ra.Interpolator = new AccelerateDecelerateInterpolator();
						ra.Duration = 200;
						this.leftActionView.GetChildAt(0).StartAnimation(ra);
					}
					this.animationApplied = true;
				}
			}
		}

		public void OnSwipeStarted(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent)
		{
			this.animationApplied = false;
			this.swipeView = (ViewGroup)swipeActionEvent.SwipeView();
			this.leftActionView = (ViewGroup)this.swipeView.GetChildAt(0);
			this.rightActionView = (ViewGroup)this.swipeView.GetChildAt(1);

			if (leftWidth == -1)
			{
				leftWidth = this.leftActionView.Width;
			}

			if (rightWidth == -1)
			{
				rightWidth = this.rightActionView.Width;
			}
		}

		// << swipe-actions-animated-actions-xamarin
	}
}
