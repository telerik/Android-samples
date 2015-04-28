package com.telerik.examples.examples.listview.animators;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.telerik.widget.list.FadeItemAnimator;
import com.telerik.widget.list.ListViewItemAnimator;
import com.telerik.widget.list.SlideItemAnimator;

/**
 * Created by ginev on 3/4/2015.
 */
public class FadeAndSlideItemAnimator extends ListViewItemAnimator {
    private FadeItemAnimator fadeAnimator;
    private SlideItemAnimator slideAnimator;

    public FadeAndSlideItemAnimator() {
        this.fadeAnimator = new FadeItemAnimator();
        this.slideAnimator = new SlideItemAnimator();
    }

    @Override
    protected void animateViewAddedImpl(RecyclerView.ViewHolder holder) {
        this.slideAnimator.animateAdd(holder);
        this.fadeAnimator.animateAdd(holder);

    }

    @Override
    protected void animateViewRemovedImpl(RecyclerView.ViewHolder holder) {
        this.slideAnimator.animateRemove(holder);
        this.fadeAnimator.animateRemove(holder);
    }


}
