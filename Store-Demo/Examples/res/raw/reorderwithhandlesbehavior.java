package com.telerik.examples.examples.listview;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.widget.list.ItemReorderBehavior;
import com.telerik.widget.list.RadListView;

/**
 * Extended ItemReorderBehavior which allows reorder with a special element used as a handle.
 */
public class ReorderWithHandlesBehavior extends ItemReorderBehavior {

    private RadListView owner;
    private boolean reorderStarted = false;
    private boolean reorderAttached = false;
    private int handleId;

    public ReorderWithHandlesBehavior(int handleId) {
        this.handleId = handleId;
    }

    @Override
    public boolean onShortPressDrag(float startX, float startY, float currentX, float currentY) {
        if (!reorderStarted) {
            ViewGroup view = (ViewGroup) owner.findChildViewUnder(startX, startY);
            if (view == null) {
                return false;
            }
            View handleView = view.findViewById(handleId);
            if (handleView == null || handleView.getLeft() > startX || handleView.getRight() < startX) {
                return false;
            }
            startReorder(startX, startY);
            reorderStarted = true;
        }

        moveReorderImage(startX, startY, currentX, currentY);
        return true;
    }

    @Override
    public boolean onActionUpOrCancel(boolean isCanceled) {
        if (!reorderStarted) {
            return false;
        }
        endReorder(isCanceled);
        reorderStarted = false;
        return true;
    }

    @Override
    public boolean isInProgress() {
        return this.reorderAttached;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return reorderStarted;
    }

    @Override
    public void onAttached(RadListView owner) {
        super.onAttached(owner);
        this.owner = owner;
        this.reorderAttached = true;
    }

    @Override
    public void onDetached(RadListView listView) {
        super.onDetached(listView);
        this.reorderAttached = false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public void onLongPressDrag(float startX, float startY, float currentX, float currentY) {
    }

    @Override
    public boolean onLongPressDragEnded(boolean isCanceled) {
        return false;
    }
}
