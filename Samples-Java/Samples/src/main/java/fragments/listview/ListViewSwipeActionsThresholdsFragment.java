package fragments.listview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.telerik.android.sdk.R;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.RadListView;
import com.telerik.widget.list.SwipeActionsBehavior;
import com.telerik.widget.list.SwipeExecuteBehavior;

import java.util.ArrayList;
import java.util.List;

import activities.ExampleFragment;

/**
 * Created by ginev on 2/20/2015.
 */
public class ListViewSwipeActionsThresholdsFragment extends Fragment implements ExampleFragment {

    private RadListView listView;
    private SwipeActionsBehavior sab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_view_example, container, false);

        this.listView = (RadListView) rootView.findViewById(R.id.listView);

        ArrayList<EmailMessage> dataSource = new ArrayList<>();

        EmailMessage message = new EmailMessage();
        message.title = "Tech news";
        message.content = "Here is your daily LinkedIn feed with news about .NET, Java, iOS and more...";
        dataSource.add(message);

        message = new EmailMessage();
        message.title = "Awaiting Payment";
        message.content = "Monthly bills summary: water supply, electricity, earth gas...";
        dataSource.add(message);

        message = new EmailMessage();
        message.title = "Greetings from Hawai";
        message.content = "Hey Betty, we've just arrived! What a flight!...";
        dataSource.add(message);

        this.listView.setAdapter(new MyListViewAdapter(dataSource));

        // >> list-swipe-actions-thresholds
        this.sab = new SwipeActionsBehavior();
        this.sab.addListener(new SwipeActionsBehavior.SwipeActionsListener() {
            private int leftContentSize = -1;
            private int rightContentSize = -1;
            @Override
            public void onSwipeStarted(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent) {
                View swipeView = swipeActionEvent.swipeView();
                if (this.leftContentSize == -1 || rightContentSize == -1) {
                    sab.setSwipeThresholdStart((((ViewGroup)swipeView).getChildAt(0)).getWidth());
                    sab.setSwipeThresholdEnd((((ViewGroup)swipeView).getChildAt(1)).getWidth());
                }
            }

            @Override
            public void onSwipeProgressChanged(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent) {

            }

            @Override
            public void onSwipeEnded(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent) {

            }

            @Override
            public void onExecuteFinished(SwipeActionsBehavior.SwipeActionEvent swipeActionEvent) {

            }
        });

        this.listView.addBehavior(this.sab);
        // >> list-swipe-actions-thresholds

        return rootView;
    }

    @Override
    public String title() {
        return "Swipe actions: thresholds";
    }

    class EmailMessage {
        public String title;
        public String content;
    }

    class MyListViewAdapter extends ListViewAdapter {

        public MyListViewAdapter(List items) {
            super(items);
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.example_list_view_item_swipe_layout, parent, false);
            MyCustomViewHolder customHolder = new MyCustomViewHolder(itemView);
            return customHolder;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            MyCustomViewHolder customVH = (MyCustomViewHolder) holder;
            EmailMessage message = (EmailMessage) this.getItem(position);
            customVH.txtTitle.setText(message.title);
            customVH.txtContent.setText(message.content);
        }

        @Override
        public ListViewHolder onCreateSwipeContentHolder(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View swipeContentView = inflater.inflate(R.layout.example_list_view_swipe_content, viewGroup, false);
            MySwipeContentViewHolder vh = new MySwipeContentViewHolder(swipeContentView);
            return vh;
        }

        @Override
        public void onBindSwipeContentHolder(final ListViewHolder viewHolder, final int position) {
            final EmailMessage currentMessage = (EmailMessage)getItem(position);
            MySwipeContentViewHolder swipeContentHolder = (MySwipeContentViewHolder)viewHolder;
            swipeContentHolder.action1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(((MySwipeContentViewHolder) viewHolder).itemView.getContext(), currentMessage.title + " successfully archived.", Toast.LENGTH_SHORT).show();
                    sab.endExecute();
                }
            });

            swipeContentHolder.action2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(position);
                    Toast.makeText(((MySwipeContentViewHolder) viewHolder).itemView.getContext(), currentMessage.title + " successfully deleted.", Toast.LENGTH_SHORT).show();
                    sab.endExecute();
                }
            });
        }
    }

    class MyCustomViewHolder extends ListViewHolder {

        public TextView txtTitle;
        public TextView txtContent;

        public MyCustomViewHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtContent = (TextView) itemView.findViewById(R.id.txtContent);
        }
    }

    class MySwipeContentViewHolder extends ListViewHolder {

        public Button action1;
        public Button action2;

        public MySwipeContentViewHolder(final View itemView) {
            super(itemView);
            this.action1 = (Button) itemView.findViewById(R.id.btnAction1);
            this.action2 = (Button) itemView.findViewById(R.id.btnAction2);
        }
    }
}
