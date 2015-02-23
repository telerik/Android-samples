
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Telerik.Widget.List;
using System.Collections;
using Android.Support.V4.App;

namespace Samples
{
	public class ListViewSwipeToExecuteFragment : Fragment, ExampleFragment, SwipeExecuteBehavior.ISwipeExecuteListener
	{

		private RadListView listView;
		private View swipeView;
		private Button action1;
		private Button action2;
		private SwipeExecuteBehavior seb;

		public override View OnCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.Inflate(Resource.Layout.fragment_list_view_example, container, false);

			this.listView = (RadListView) rootView.FindViewById(Resource.Id.listView).JavaCast<RadListView>();

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

			this.listView.SetAdapter(new SwipeToExecuteListViewAdapter(dataSource));

			seb = new SwipeExecuteBehavior();

			seb.AddListener(this);

			this.listView.AddBehavior(seb);

			return rootView;
		}

		public String Title(){
			return "Swipe to execute";
		}

		public void OnSwipeStarted(int position) {
		}


		public void OnSwipeProgressChanged(int position, int currentOffset, View swipeView) {
			if (this.swipeView == null) {
				this.swipeView = swipeView;
				this.action1 = (Button) this.swipeView.FindViewById(Resource.Id.btnAction1);
				this.action2 = (Button) this.swipeView.FindViewById(Resource.Id.btnAction2);
			}
		}


		public void OnSwipeEnded(int position, int finalOffset) {

			if (this.action1 == null || this.action2 == null) {
				seb.SwipeOffset = 0;
				return;
			}

			SwipeToExecuteListViewAdapter adapter = ((SwipeToExecuteListViewAdapter) listView.GetAdapter());
			EmailMessage item = (EmailMessage) adapter.GetItem(position);
			if (finalOffset > 0) {
				seb.SwipeOffset = this.action1.Width;
				this.toggleAction1(item);
				seb.EndExecute();
			} else {
				seb.SwipeOffset = -this.action2.Width;
				this.toggleAction2(item);
				seb.EndExecute();
			}

			this.swipeView = null;
			this.action1 = null;
			this.action2 = null;
		}

		public void OnExecuteFinished(int position) {

		}

		private void toggleAction1(EmailMessage message) {
			//Archive message
			Toast.MakeText(this.Activity, "Message archived", ToastLength.Short).Show();
		}

		private void toggleAction2(EmailMessage message) {
			//Delete message
			Toast.MakeText(this.Activity, "Message deleted", ToastLength.Short).Show();
		}

	}

	class EmailMessage : Java.Lang.Object{
		public String title;
		public String content;
	}


	class SwipeToExecuteListViewAdapter : ListViewAdapter {

		public SwipeToExecuteListViewAdapter(IList items) : base(items){
		}

		public override Android.Support.V7.Widget.RecyclerView.ViewHolder OnCreateViewHolder (ViewGroup p0, int p1)
		{
			LayoutInflater inflater = LayoutInflater.From(p0.Context);
			View itemView = inflater.Inflate(Resource.Layout.example_list_view_item_swipe_layout, p0, false);
			SwipeToExecuteCustomViewHolder customHolder = new SwipeToExecuteCustomViewHolder(itemView);
			return customHolder;
		}


		public override void OnBindListViewHolder (ListViewHolder p0, int p1)
		{
			SwipeToExecuteCustomViewHolder customVH = (SwipeToExecuteCustomViewHolder) p0;
			EmailMessage message = (EmailMessage)this.GetItem( p1);
			customVH.txtTitle.Text = message.title;
			customVH.txtContent.Text = message.content;
		}


		public override ListViewHolder OnCreateSwipeContentHolder(ViewGroup viewGroup) {
			LayoutInflater inflater = LayoutInflater.From(viewGroup.Context);
			View swipeContentView = inflater.Inflate(Resource.Layout.example_list_view_swipe_content, viewGroup, false);
			ListViewHolder vh = new ListViewHolder(swipeContentView);
			return vh;
		}


		public override void OnBindSwipeContentHolder(ListViewHolder viewHolder, int position) {
			View swipeContent = viewHolder.ItemView;

			EmailMessage currentDataItem = (EmailMessage) this.GetItem(position);
			Button action1 = (Button) swipeContent.FindViewById(Resource.Id.btnAction1);
			Button action2 = (Button) swipeContent.FindViewById(Resource.Id.btnAction2);

			action1.Text = "Archive " + currentDataItem.title;
			action2.Text = "Delete " + currentDataItem.title;
		}
	}

	class SwipeToExecuteCustomViewHolder : ListViewHolder {

		public TextView txtTitle;
		public TextView txtContent;

		public SwipeToExecuteCustomViewHolder(View itemView) : base(itemView){

			this.txtTitle = (TextView) itemView.FindViewById(Resource.Id.txtTitle);
			this.txtContent = (TextView) itemView.FindViewById(Resource.Id.txtContent);
		}
	}
}

