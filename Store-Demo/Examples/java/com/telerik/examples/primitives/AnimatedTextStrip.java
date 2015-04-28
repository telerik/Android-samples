package com.telerik.examples.primitives;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.telerik.examples.R;

public class AnimatedTextStrip extends ViewGroup {

    private static final int SCALE_DURATION = 150;
    private static final int ALPHA_DURATION = 200;
    private static final int TITLE_START_OFFSET = 1800;
    private static final int START_OFFSET = 2000;
    private static final int ROTATE_DURATION = 250;

    private CompoundButton.OnCheckedChangeListener checkedChangedListener;
    private ToggleButton btnShowMenu;
    private View animatablePanel;
    private View root;
    private TextView panelExampleNameLabel;
    private boolean animationFinished = false;
    private boolean isClickable = true;

    public AnimatedTextStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedTextStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.panel_example_name, this);
        this.root = this.findViewById(R.id.root);
        this.animatablePanel = this.findViewById(R.id.panelExampleName);
        this.btnShowMenu = (ToggleButton) this.findViewById(R.id.btnShowMenu);
        this.btnShowMenu.setClickable(false);
        this.panelExampleNameLabel = (TextView) this.findViewById(R.id.panelExampleNameLabel);
        this.setClipChildren(false);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle state = new Bundle();
        state.putParcelable("instanceState", super.onSaveInstanceState());
        state.putBoolean("animated", this.animationFinished);
        return state;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            this.animationFinished = ((Bundle) state).getBoolean("animated");
            state = ((Bundle) state).getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!this.animationFinished) {
            this.animateExampleTitle();
        } else {
            this.setEndAnimationState();
        }
    }

    public void setText(CharSequence text) {
        this.panelExampleNameLabel.setText(text);
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        this.isClickable = clickable;

    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        this.checkedChangedListener = listener;

        if (this.btnShowMenu != null) {
            this.btnShowMenu.setOnCheckedChangeListener(listener);
        }
    }

    private boolean listensForUp = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isClickable) {
            return false;
        }

        float xCoord = event.getX();
        float yCoord = event.getY();

        if (this.getWidth() - xCoord <= this.getHeight() && yCoord <= this.getHeight()) {

            if (event.getPointerCount() != 1) {
                return false;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.listensForUp = true;
                return true;
            } else if (this.listensForUp && event.getAction() == MotionEvent.ACTION_UP) {
                this.listensForUp = false;
                this.btnShowMenu.setChecked(!this.btnShowMenu.isChecked());
                return true;
            }else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                this.listensForUp = false;
            }

        }

        return false;
    }

    public boolean isChecked() {
        return this.btnShowMenu.isChecked();
    }

    public void setChecked(boolean checked) {
        this.btnShowMenu.setChecked(checked);
    }

    private void setEndAnimationState() {
        this.panelExampleNameLabel.setAlpha(0);
        this.animatablePanel.setPivotX(this.animatablePanel.getMeasuredWidth());
        this.animatablePanel.setRotation(-45);
    }

    private void animateExampleTitle() {
        AlphaAnimation titleFadeout = new AlphaAnimation(1, 0);
        titleFadeout.setFillAfter(true);
        titleFadeout.setStartOffset(TITLE_START_OFFSET);
        titleFadeout.setDuration(ALPHA_DURATION);
        this.panelExampleNameLabel.startAnimation(titleFadeout);

        AnimationSet set = new AnimationSet(false);
        float translationOffsetEndPoint = (float) Math.sqrt(2 * this.btnShowMenu.getMeasuredWidth() * this.btnShowMenu.getMeasuredWidth());
        TranslateAnimation scale = new TranslateAnimation(0, this.animatablePanel.getMeasuredWidth() - translationOffsetEndPoint, 0, 0);
        scale.setInterpolator(new AccelerateInterpolator());
        scale.setDuration(SCALE_DURATION);
        scale.setFillAfter(true);
        set.addAnimation(scale);

        RotateAnimation rotateAnimation = new RotateAnimation(0, -45, (float) this.animatablePanel.getMeasuredWidth(), 0f);
        rotateAnimation.setDuration(ROTATE_DURATION);
        rotateAnimation.setStartOffset(SCALE_DURATION);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(rotateAnimation);
        set.setFillAfter(true);
        set.setStartOffset(START_OFFSET);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationFinished = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        this.animatablePanel.startAnimation(set);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.root.measure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(this.root.getMeasuredWidth(), this.root.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.root.layout(0, 0, this.root.getMeasuredWidth(), this.root.getMeasuredHeight());
    }
}
