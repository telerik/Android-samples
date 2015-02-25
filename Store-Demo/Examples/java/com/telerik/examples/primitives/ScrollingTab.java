package com.telerik.examples.primitives;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.view.View;

public class ScrollingTab extends ActionBar.Tab {
    private ActionBar.TabListener mCallback;
    private Object mTag;
    private Drawable mIcon;
    private CharSequence mText;
    private CharSequence mContentDesc;
    private int mPosition = -1;
    private View mCustomView;
    private ScrollingTabView owner;
    private ScrollingTabView.TabView tabView;

    public ScrollingTab(ScrollingTabView owner) {
        this.owner = owner;
    }

    @Override
    public Object getTag() {
        return mTag;
    }

    @Override
    public ScrollingTab setTag(Object tag) {
        mTag = tag;
        return this;
    }

    public ActionBar.TabListener getCallback() {
        return mCallback;
    }

    @Override
    public ScrollingTab setTabListener(ActionBar.TabListener callback) {
        mCallback = callback;
        return this;
    }

    @Override
    public View getCustomView() {
        return mCustomView;
    }

    @Override
    public ScrollingTab setCustomView(View view) {
        mCustomView = view;
        if (mPosition >= 0) {
            owner.updateTab(mPosition);
        }
        return this;
    }

    public ScrollingTabView.TabView getView() {
        return this.tabView;
    }

    void setView(ScrollingTabView.TabView view) {
        this.tabView = view;
    }

    @Override
    public ScrollingTab setCustomView(int layoutResId) {
        return null;
    }

    @Override
    public Drawable getIcon() {
        return mIcon;
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    @Override
    public CharSequence getText() {
        return mText;
    }

    @Override
    public ScrollingTab setIcon(Drawable icon) {
        mIcon = icon;
        if (mPosition >= 0) {
            owner.updateTab(mPosition);
        }
        return this;
    }

    @Override
    public ScrollingTab setIcon(int resId) {
        return setIcon(owner.getContext().getResources().getDrawable(resId));
    }

    @Override
    public ScrollingTab setText(CharSequence text) {
        mText = text;
        if (mPosition >= 0) {
            owner.updateTab(mPosition);
        }
        return this;
    }

    @Override
    public ScrollingTab setText(int resId) {
        return setText(owner.getContext().getResources().getText(resId));
    }

    @Override
    public void select() {
        owner.selectTab(this);
    }

    @Override
    public ScrollingTab setContentDescription(int resId) {
        return setContentDescription(owner.getContext().getResources().getText(resId));
    }

    @Override
    public ScrollingTab setContentDescription(CharSequence contentDesc) {
        mContentDesc = contentDesc;
        if (mPosition >= 0) {
            owner.updateTab(mPosition);
        }
        return this;
    }

    @Override
    public CharSequence getContentDescription() {
        return mContentDesc;
    }
}
