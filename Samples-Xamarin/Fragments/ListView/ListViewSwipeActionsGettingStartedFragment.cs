
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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
	// >> list-swipe-actions-getting-started-xamarin
	public class ListViewSwipeActionsGettingStartedFragment : Fragment, ExampleFragment, SwipeActionsBehavior.ISwipeActionsListener
	{
		private RadListView listView;
		private SwipeActionsBehavior sap;

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

		public void OnExecuteFinished(SwipeActionsBehavior.SwipeActionEvent p0)
		{
		}

		public void OnSwipeEnded(SwipeActionsBehavior.SwipeActionEvent p0)
		{
	
		}

		public void OnSwipeProgressChanged(SwipeActionsBehavior.SwipeActionEvent p0)
		{

		}

		public void OnSwipeStarted(SwipeActionsBehavior.SwipeActionEvent p0)
		{

		}

		public void OnSwipeStateChanged(SwipeActionsBehavior.SwipeActionsState p0, SwipeActionsBehavior.SwipeActionsState p1)
		{

		}

		public string Title()
		{
			return "Swipe Actions Getting Started";
		}
	}
	// << list-swipe-actions-getting-started-xamarin

	// >> list-swipe-actions-adapter-xamarin
	class ListViewSwipeActionsAdapter : ListViewAdapter
	{

		public ListViewSwipeActionsAdapter(IList items) : base(items)
		{
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
			View swipeContentView = inflater.Inflate(Resource.Layout.example_list_swipe_actions_static, viewGroup, false);
			ListViewHolder vh = new ListViewHolder(swipeContentView);
			return vh;
		}


		public override void OnBindSwipeContentHolder(ListViewHolder viewHolder, int position)
		{
			View swipeContent = viewHolder.ItemView;

			EmailMessage currentDataItem = (EmailMessage)this.GetItem(position);
	
		}
	}
	// << list-swipe-actions-adapter-xamarin

	class SwipeActionsViewHolder : ListViewHolder
	{

		public TextView txtTitle;
		public TextView txtContent;

		public SwipeActionsViewHolder(View itemView) : base(itemView)
		{

			this.txtTitle = (TextView)itemView.FindViewById(Resource.Id.txtTitle);
			this.txtContent = (TextView)itemView.FindViewById(Resource.Id.txtContent);
		}
	}
}
