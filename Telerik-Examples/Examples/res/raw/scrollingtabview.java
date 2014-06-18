/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.telerik.examples.primitives;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telerik.examples.R;

import java.util.ArrayList;

/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class ScrollingTabView extends FrameLayout implements AdapterView.OnItemClickListener {
    private static final String TAG = "ScrollingTabContainerView";
    private Activity mActivity;
    private ScrollingTab mSelectedTab;
    Runnable mTabSelector;
    private TabClickListener mTabClickListener;
    private HVScrollView scrollView;

    private ArrayList<ScrollingTab> mTabs = new ArrayList<ScrollingTab>();
    private LinearLayout mTabLayout;

    private int mSelectedTabIndex;
    private int orientation;

    protected Animator mVisibilityAnim;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();

    private static final TimeInterpolator sAlphaInterpolator = new DecelerateInterpolator();

    private static final int FADE_DURATION = 200;

    public ScrollingTabView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.scrollingTabViewStyle);
    }

    public ScrollingTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTabLayout = new LinearLayout(context, null, defStyle);
        mTabLayout.setMeasureWithLargestChildEnabled(true);
        mTabLayout.setGravity(Gravity.CENTER);
        mTabLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));


        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ScrollingTabView, defStyle, 0);

        this.setOrientation(styledAttrs.getInt(R.styleable.ScrollingTabView_orientation, LinearLayout.HORIZONTAL));

        if (this.orientation == LinearLayout.HORIZONTAL) {
            mTabLayout.setOrientation(LinearLayout.HORIZONTAL);
            this.scrollView = new CustomHorizontalScrollView(context, null);
            ((View) this.scrollView).setHorizontalScrollBarEnabled(false);
            ((ViewGroup) scrollView).addView(mTabLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            mTabLayout.setOrientation(LinearLayout.VERTICAL);
            this.scrollView = new CustomVerticalScrollView(context, null);
            ((View) this.scrollView).setVerticalScrollBarEnabled(false);

            ((ViewGroup) scrollView).addView(mTabLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

        }

        this.addView((View) this.scrollView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        if (!this.isInEditMode()) {
            this.mActivity = (Activity) context;
        }
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public ScrollingTab getTabAt(int position) {
        return this.mTabs.get(position);
    }

    public void setTabSelected(int position) {
        mSelectedTabIndex = position;
        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = i == position;
            child.setSelected(isSelected);
            if (isSelected) {
                mSelectedTab = mTabs.get(i);
                animateToTab(position);
            }
        }
    }

    public void animateToVisibility(int visibility) {
        if (mVisibilityAnim != null) {
            mVisibilityAnim.cancel();
        }
        if (visibility == VISIBLE) {
            if (getVisibility() != VISIBLE) {
                setAlpha(0);
            }
            ObjectAnimator anim = ObjectAnimator.ofFloat(this, "alpha", 1);
            anim.setDuration(FADE_DURATION);
            anim.setInterpolator(sAlphaInterpolator);

            anim.addListener(mVisAnimListener.withFinalVisibility(visibility));
            anim.start();
        } else {
            ObjectAnimator anim = ObjectAnimator.ofFloat(this, "alpha", 0);
            anim.setDuration(FADE_DURATION);
            anim.setInterpolator(sAlphaInterpolator);

            anim.addListener(mVisAnimListener.withFinalVisibility(visibility));
            anim.start();
        }
    }

    public void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = orientation == LinearLayout.HORIZONTAL ?
                        tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2 :
                        tabView.getTop() - (getHeight() - tabView.getHeight()) / 2;
                scrollView.smoothScrollTo(scrollPos);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private TabView createTabView(ScrollingTab tab) {
        final TabView tabView = new TabView(getContext(), tab);
        tab.setView(tabView);
        tabView.setFocusable(true);

        if (mTabClickListener == null) {
            mTabClickListener = new TabClickListener();
        }
        tabView.setOnClickListener(mTabClickListener);
        return tabView;
    }

    public void selectTab(ScrollingTab tab) {
        final FragmentTransaction trans = mActivity.getFragmentManager().beginTransaction()
                .disallowAddToBackStack();

        if (mSelectedTab == tab) {
            if (mSelectedTab != null) {
                mSelectedTab.getCallback().onTabReselected(mSelectedTab, trans);
                animateToTab(tab.getPosition());
            }
        } else {
            setTabSelected(tab != null ? tab.getPosition() : ActionBar.Tab.INVALID_POSITION);
            if (mSelectedTab != null) {
                mSelectedTab.getCallback().onTabUnselected(mSelectedTab, trans);
            }
            mSelectedTab = tab;
            if (mSelectedTab != null) {
                mSelectedTab.getCallback().onTabSelected(mSelectedTab, trans);
            }
        }

        if (!trans.isEmpty()) {
            trans.commit();
        }
    }

    private void configureTab(ScrollingTab tab, int position) {
        final ActionBar.TabListener callback = tab.getCallback();

        if (callback == null) {
            throw new IllegalStateException("Action Bar Tab must have a Callback");
        }

        tab.setPosition(position);
        mTabs.add(position, tab);

        final int count = mTabs.size();
        for (int i = position + 1; i < count; i++) {
            mTabs.get(i).setPosition(i);
        }
    }

    public void addTab(ScrollingTab tab, boolean setSelected) {
        TabView tabView = createTabView(tab);
        LinearLayout.LayoutParams params =
                this.orientation == LinearLayout.HORIZONTAL ?
                        new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1) :
                        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
        mTabLayout.addView(tabView, params);
        this.configureTab(tab, mTabLayout.getChildCount() - 1);
        if (setSelected) {
            tabView.setSelected(true);
        }
    }

    public void updateTab(int position) {
        ((TabView) mTabLayout.getChildAt(position)).update();
    }

    public void removeTabAt(int position) {
        this.mTabs.remove(position);
        mTabLayout.removeViewAt(position);
    }

    public void removeAllTabs() {
        this.mTabs.clear();
        mTabLayout.removeAllViews();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TabView tabView = (TabView) view;
        tabView.getTab().select();
    }

    public class TabView extends LinearLayout implements OnLongClickListener {
        private ScrollingTab mTab;
        private TextView mTextView;
        private ImageView mIconView;
        private View mCustomView;

        public TabView(Context context, ScrollingTab tab) {
            super(context, null, R.attr.scrollingTabStyle);
            mTab = tab;
            update();
        }

        public void bindTab(ScrollingTab tab) {
            mTab = tab;
            update();
        }

        @Override
        public void setSelected(boolean selected) {
            final boolean changed = (isSelected() != selected);
            super.setSelected(selected);
            if (changed && selected) {
                sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
            }
        }

        @Override
        public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(event);
            // This view masquerades as an action bar tab.
            event.setClassName(ActionBar.Tab.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(info);
            // This view masquerades as an action bar tab.
            info.setClassName(ActionBar.Tab.class.getName());
        }

        public void update() {
            final ActionBar.Tab tab = mTab;
            final View custom = tab.getCustomView();
            if (custom != null) {
                final ViewParent customParent = custom.getParent();
                if (customParent != this) {
                    if (customParent != null) ((ViewGroup) customParent).removeView(custom);
                    addView(custom);
                }
                mCustomView = custom;
                if (mTextView != null) mTextView.setVisibility(GONE);
                if (mIconView != null) {
                    mIconView.setVisibility(GONE);
                    mIconView.setImageDrawable(null);
                }
            } else {
                if (mCustomView != null) {
                    removeView(mCustomView);
                    mCustomView = null;
                }

                final Drawable icon = tab.getIcon();
                final CharSequence text = tab.getText();

                if (icon != null) {
                    if (mIconView == null) {
                        ImageView iconView = new ImageView(getContext());
                        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER_VERTICAL;
                        iconView.setLayoutParams(lp);
                        addView(iconView, 0);
                        mIconView = iconView;
                    }
                    mIconView.setImageDrawable(icon);
                    mIconView.setVisibility(VISIBLE);
                } else if (mIconView != null) {
                    mIconView.setVisibility(GONE);
                    mIconView.setImageDrawable(null);
                }

                final boolean hasText = !TextUtils.isEmpty(text);
                if (hasText) {
                    if (mTextView == null) {
//                        TextView textView = new TextView(getContext(), null,
//                                com.android.internal.R.attr.actionBarTabTextStyle);
                        TextView textView = new TextView(getContext());
                        textView.setEllipsize(TruncateAt.END);
                        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER_VERTICAL;
                        textView.setLayoutParams(lp);
                        addView(textView);
                        mTextView = textView;
                    }
                    mTextView.setText(text);
                    mTextView.setVisibility(VISIBLE);
                } else if (mTextView != null) {
                    mTextView.setVisibility(GONE);
                    mTextView.setText(null);
                }

                if (mIconView != null) {
                    mIconView.setContentDescription(tab.getContentDescription());
                }

                if (!hasText && !TextUtils.isEmpty(tab.getContentDescription())) {
                    setOnLongClickListener(this);
                } else {
                    setOnLongClickListener(null);
                    setLongClickable(false);
                }
            }
        }

        public boolean onLongClick(View v) {
            final int[] screenPos = new int[2];
            getLocationOnScreen(screenPos);

            final Context context = getContext();
            final int width = getWidth();
            final int height = getHeight();
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

            Toast cheatSheet = Toast.makeText(context, mTab.getContentDescription(),
                    Toast.LENGTH_SHORT);
            // Show under the tab
            cheatSheet.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,
                    (screenPos[0] + width / 2) - screenWidth / 2, height);

            cheatSheet.show();
            return true;
        }

        public ScrollingTab getTab() {
            return mTab;
        }
    }

    private class TabClickListener implements OnClickListener {
        public void onClick(View view) {
            TabView tabView = (TabView) view;
            tabView.getTab().select();
            final int tabCount = mTabLayout.getChildCount();
            for (int i = 0; i < tabCount; i++) {
                final View child = mTabLayout.getChildAt(i);
                child.setSelected(child == view);
            }
        }
    }

    protected class VisibilityAnimListener implements Animator.AnimatorListener {
        private boolean mCanceled = false;
        private int mFinalVisibility;

        public VisibilityAnimListener withFinalVisibility(int visibility) {
            mFinalVisibility = visibility;
            return this;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            setVisibility(VISIBLE);
            mVisibilityAnim = animation;
            mCanceled = false;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mCanceled) return;

            mVisibilityAnim = null;
            setVisibility(mFinalVisibility);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            mCanceled = true;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
}
