
using System;
using System.Collections;
using Android.Content;
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
	public class ListViewSwipeActionsThresholdsFragment : Fragment, ExampleFragment, SwipeActionsBehavior.ISwipeActionsListener
	{
		private RadListView listView;
		private SwipeActionsBehavior sap;
		private int leftContentSize = -1;
		private int rightContentSize = -1;


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

			sap = new SwipeActionsBehavior();

			this.listView.SetAdapter(new ListViewSwipeActionsThresholdsAdapter(dataSource, sap));

			sap.AddListener(this);

			this.listView.AddBehavior(sap);

			return rootView;
		}

		public string Title()
		{
			return "Swipe Actions: button actions";
		}

		// >> list-swipe-actions-thresholds-xamarin

		public void OnExecuteFinished(SwipeActionsBehavior.SwipeActionEvent p0)
		{
		}

		public void OnSwipeEnded(SwipeActionsBehavior.SwipeActionEvent p0)
		{

		}

		public void OnSwipeProgressChanged(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent)
		{
		}

		public void OnSwipeStarted(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent)
		{
			View swipeView = swipeActionEvent.SwipeView();
			if (this.leftContentSize == -1 || rightContentSize == -1)
			{
				sap.SetSwipeThresholdStart((((ViewGroup)swipeView).GetChildAt(0)).Width);
				sap.SetSwipeThresholdEnd((((ViewGroup)swipeView).GetChildAt(1)).Width);
			}
		}

		// << list-swipe-actions-thresholds-xamarin
	}

	// >> list-swipe-actions-thresholds-xamarin-1
	class ListViewSwipeActionsThresholdsAdapter : ListViewAdapter
	{
		private SwipeActionsBehavior sab;

		public ListViewSwipeActionsThresholdsAdapter(IList items, SwipeActionsBehavior sab) : base(items)
		{
			this.sab = sab;
		}

		public override Android.Support.V7.Widget.RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup p0, int p1)
		{
			LayoutInflater inflater = LayoutInflater.From(p0.Context);
			View itemView = inflater.Inflate(Resource.Layout.example_list_view_item_swipe_layout, p0, false);
			SwipeToExecuteCustomViewHolder customHolder = new SwipeToExecuteCustomViewHolder(itemView);
			return customHolder;
		}


		public override void OnBindListViewHolder(ListViewHolder p0, int p1)
		{
			SwipeToExecuteCustomViewHolder customVH = (SwipeToExecuteCustomViewHolder)p0;
			EmailMessage message = (EmailMessage)this.GetItem(p1);
			customVH.txtTitle.Text = message.title;
			customVH.txtContent.Text = message.content;
		}


		public override ListViewHolder OnCreateSwipeContentHolder(ViewGroup viewGroup)
		{
			LayoutInflater inflater = LayoutInflater.From(viewGroup.Context);
			View swipeContentView = inflater.Inflate(Resource.Layout.example_list_swipe_actions_buttons, viewGroup, false);
			SwipeActionsThresholdsViewHolder vh = new SwipeActionsThresholdsViewHolder(swipeContentView);
			return vh;
		}


		public override void OnBindSwipeContentHolder(ListViewHolder viewHolder, int position)
		{
			EmailMessage currentMessage = (EmailMessage)GetItem(position);
			SwipeActionsThresholdsViewHolder swipeContentHolder = (SwipeActionsThresholdsViewHolder)viewHolder;
			swipeContentHolder.action1.SetOnClickListener(new ViewHolderActionClickListener(swipeContentHolder, position, sab, this));

			swipeContentHolder.action2.SetOnClickListener(new ViewHolderActionClickListener(swipeContentHolder, position, sab, this));
		}
	}

	class ViewHolderActionClickListener : Java.Lang.Object, View.IOnClickListener
	{
		private SwipeActionsBehavior sab;
		private SwipeActionsThresholdsViewHolder vh;
		private int position;
		private ListViewSwipeActionsThresholdsAdapter adapter;
		public ViewHolderActionClickListener(SwipeActionsThresholdsViewHolder vh, int position,  SwipeActionsBehavior sab, ListViewSwipeActionsThresholdsAdapter adapter)
		{
			this.vh = vh;
			this.sab = sab;
			this.position = position;
			this.adapter = adapter;
		}

		public void OnClick(View v)
		{
			EmailMessage currentMessage = (EmailMessage)this.adapter.GetItem(this.position);
			if (v == this.vh.action1)
			{
				Toast.MakeText(this.vh.ItemView.Context, currentMessage.title + " successfully archived.", ToastLength.Short).Show();
				sab.EndExecute();
			}

			if (v == this.vh.action2)
			{
				this.adapter.Remove(position);
				Toast.MakeText(this.vh.ItemView.Context, currentMessage.title + " successfully deleted.", ToastLength.Short).Show();
				sab.EndExecute();
			}
		}
	}



	class SwipeActionsThresholdsViewHolder : ListViewHolder
	{
		public Button action1;
		public Button action2;

		public SwipeActionsThresholdsViewHolder(View itemView) : base(itemView)
		{

			this.action1 = (Button)itemView.FindViewById(Resource.Id.btnAction1);
			this.action2 = (Button)itemView.FindViewById(Resource.Id.btnAction2);
		}

	}

	// << list-swipe-actions-thresholds-xamarin-1
}
