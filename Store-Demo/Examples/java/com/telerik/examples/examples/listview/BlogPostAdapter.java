package com.telerik.examples.examples.listview;

import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.examples.R;
import com.telerik.examples.examples.listview.models.BlogPost;
import com.telerik.widget.list.ListViewDataSourceAdapter;
import com.telerik.widget.list.ListViewHolder;
import com.telerik.widget.list.ListViewTextHolder;

import java.util.List;

/**
 * An adapter used by ListViewSelectionFragment.
 */
public class BlogPostAdapter extends ListViewDataSourceAdapter {
    private boolean isInReorderMode = false;
    private boolean showCurrent = false;
    private long currentItemId = -1;
    private long deletedItemId = -1;
    private int listViewCurrentSwipeIndex;

    public BlogPostAdapter(List<BlogPost> items) {
        super(items);
    }

    @Override
    public ListViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_view_selection_item_layout, viewGroup, false);
        return new BlogPostViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ListViewHolder holder, Object entity) {
        BlogPostViewHolder blogPostViewHolder = (BlogPostViewHolder) holder;
        BlogPost blogPost = (BlogPost) entity;

        long itemId = getItemId(entity);
        if (deletedItemId == itemId) {
            blogPostViewHolder.mainContent.setVisibility(View.INVISIBLE);
            blogPostViewHolder.deletedContent.setVisibility(View.VISIBLE);
        } else {
            blogPostViewHolder.mainContent.setVisibility(View.VISIBLE);
            blogPostViewHolder.deletedContent.setVisibility(View.GONE);
        }
        blogPostViewHolder.itemView.setActivated(itemId == currentItemId && showCurrent);

        blogPostViewHolder.txtContent.setText(blogPost.getContent());

        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (blogPost.isFavourite()) {
            builder.append("  ");
            ImageSpan span = new ImageSpan(blogPostViewHolder.txtContent.getContext(), R.drawable.ic_listview_selection_fav1, DynamicDrawableSpan.ALIGN_BASELINE);
            builder.setSpan(span, 0, 1, 0);
        }
        builder.append(blogPost.getTitle());

        blogPostViewHolder.txtTitle.setText(builder);
        blogPostViewHolder.imgReorder.setVisibility(isInReorderMode ? View.VISIBLE : View.GONE);
    }

    @Override
    public ListViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_view_selection_group_header_layout, parent, false);
        return new ListViewTextHolder(view);
    }

    @Override
    public ListViewHolder onCreateSwipeContentHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_view_selection_item_swipe_content, viewGroup, false);
        final BlogPostSwipeViewHolder viewHolder = new BlogPostSwipeViewHolder(view);
        viewHolder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlogPost postToFav = getSwipedBlogPost();
                postToFav.setFavourite(!postToFav.isFavourite());
                notifySwipeExecuteFinished();
            }
        });
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlogPost postToDelete = getSwipedBlogPost();
                deletedItemId = getItemId(postToDelete);
                notifyItemChanged(listViewCurrentSwipeIndex);
                notifySwipeExecuteFinished();
            }
        });
        return new BlogPostSwipeViewHolder(view);
    }

    @Override
    public void onBindSwipeContentHolder(ListViewHolder viewHolder, int position) {
        listViewCurrentSwipeIndex = position;
    }

    @Override
    public boolean reorderItem(int oldPosition, int newPosition) {
        if (newPosition == 0) {
            return false;
        }
        return super.reorderItem(oldPosition, newPosition);
//        boolean sameGroup = true;
//        int min = oldPosition < newPosition ? oldPosition : newPosition;
//        int max = min == oldPosition ? newPosition : oldPosition;
//        for(int i = min; i <= max; i++) {
//            if(isGroupHeader(i)) {
//                sameGroup = false;
//                break;
//            }
//        }
//        if(sameGroup) {
//            return super.reorderItem(oldPosition, newPosition);
//        }
//        return false;
    }

    public boolean confirmDelete(boolean isConfirmed) {
        int position = getPosition(deletedItemId);
        if (position == INVALID_ID) {
            return false;
        }
        if (isConfirmed) {
            Object item = getItem(position);
            remove(item);
        } else {
            deletedItemId = INVALID_ID;
            notifyItemChanged(position);
        }
        return true;
    }

    public void enterReorderMode() {
        this.isInReorderMode = true;
        notifyDataSetChanged();
    }

    public void exitReorderMode() {
        this.isInReorderMode = false;
        notifyDataSetChanged();
    }

    long getCurrentItemId() {
        return currentItemId;
    }

    void setCurrentItemId(long currentItemId) {
        if (this.currentItemId == currentItemId) {
            return;
        }

        long oldItem = this.currentItemId;
        this.currentItemId = currentItemId;

        int oldPosition = getPosition(oldItem);
        int currentPosition = getPosition(currentItemId);

        this.notifyItemChanged(oldPosition);
        this.notifyItemChanged(currentPosition);
    }

    boolean isShowCurrent() {
        return showCurrent;
    }

    void setShowCurrent(boolean showCurrent) {
        if (this.showCurrent == showCurrent) {
            return;
        }
        this.showCurrent = showCurrent;
        int currentItemPosition = getPosition(this.currentItemId);
        this.notifyItemChanged(currentItemPosition);
    }

    private BlogPost getSwipedBlogPost() {
        return (BlogPost) this.getItem(listViewCurrentSwipeIndex);
    }

    @Override
    public int getPosition(long id) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemId(i) == id) {
                return i;
            }
        }
        return INVALID_ID;
    }

    @Override
    public int getPosition(Object searchItem) {
        long id = getItemId(searchItem);
        return getPosition(id);
    }

    class BlogPostSwipeViewHolder extends ListViewHolder {

        public ImageButton imgFavourite;
        public ImageButton imgDelete;

        public BlogPostSwipeViewHolder(View itemView) {
            super(itemView);
            this.imgFavourite = (ImageButton) itemView.findViewById(R.id.imgFavourite);
            this.imgDelete = (ImageButton) itemView.findViewById(R.id.imgDelete);
        }
    }

    class BlogPostViewHolder extends ListViewHolder {

        private TextView txtTitle;
        private TextView txtContent;
        private ImageView imgReorder;

        private View mainContent;
        private View deletedContent;
        private View undo;

        public BlogPostViewHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            this.imgReorder = (ImageView) itemView.findViewById(R.id.imgReorder);
            this.mainContent = itemView.findViewById(R.id.item_layout_main);
            this.deletedContent = itemView.findViewById(R.id.item_layout_deleted);
            this.undo = itemView.findViewById(R.id.item_deleted_undo);
            this.undo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDelete(false);
                }
            });
        }
    }
}