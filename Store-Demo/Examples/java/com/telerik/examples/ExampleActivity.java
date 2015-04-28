package com.telerik.examples;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.telerik.android.common.Util;
import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.InputBlocker;
import com.telerik.examples.common.TelerikActivityHelper;
import com.telerik.examples.common.TrackedApplication;
import com.telerik.examples.common.contracts.TrackedActivity;
import com.telerik.examples.common.fragments.ExampleFragmentBase;
import com.telerik.examples.common.fragments.GalleryExamplesFragment;
import com.telerik.examples.common.licensing.KeysRetriever;
import com.telerik.examples.primitives.AnimatedTextStrip;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;
import com.telerik.examples.viewmodels.GalleryExample;
import com.telerik.widget.feedback.RadFeedback;

import java.lang.reflect.Constructor;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class ExampleActivity extends FragmentActivity implements ExampleFragmentBase.ExampleLoadedListener, RadFeedback.OnSendFeedbackFinishedListener, View.OnClickListener, View.OnTouchListener , TrackedActivity
{
    private ExamplesApplicationContext app;

    private AnimatedTextStrip textStrip;
    private ExampleFragmentBase currentExample;
    private FrameLayout container;
    private FrameLayout menuContainer;
    private ScaleAnimation menuAnimation = null;
    private ScrollView descriptionPanel;
    private LinearLayout buttonsLayout;
    private TextView header;
    private TextView description;
    private RadFeedback feedback;

    private Button buttonNextExample;
    private Button buttonPrevExample;
    private Button buttonViewInfo;
    private Button buttonAddRemoveFavourite;
    private Button btnSendFeedback;
    private Button btnViewCode;

    private ExampleGroup selectedControl;
    private Example selectedExample;

    public ExampleActivity() {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.textStrip.setChecked(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initTheme();
        super.onCreate(savedInstanceState);
        TelerikActivityHelper.updateActivityTaskDescription(this);
        this.initFeedback();
        Intent intent = this.getIntent();
        this.app = (ExamplesApplicationContext) this.getApplicationContext();
        if (savedInstanceState == null) {
            this.selectedControl = this.app.findControlById(intent.getStringExtra(ExamplesApplicationContext.INTENT_CONTROL_ID));
            this.selectedExample = this.app.findExampleById(this.selectedControl, intent.getStringExtra(ExamplesApplicationContext.INTENT_EXAMPLE_ID));
            this.app.trackScreenOpened(this);
        }else{
            this.selectedControl = this.app.findControlById(savedInstanceState.getString(ExamplesApplicationContext.INTENT_CONTROL_ID));
            this.selectedExample = this.app.findExampleById(this.selectedControl, savedInstanceState.getString(ExamplesApplicationContext.INTENT_EXAMPLE_ID));
        }
        this.loadContent();

        this.initUI();

        this.loadExample(savedInstanceState);
        this.refreshNavigationButtons(this.selectedControl.getExamples().indexOf(this.selectedExample));

        this.setBackground();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ExamplesApplicationContext.INTENT_CONTROL_ID, this.selectedControl.getFragmentName());
        outState.putString(ExamplesApplicationContext.INTENT_EXAMPLE_ID, this.selectedExample.getFragmentName());
    }

    private void initUI() {
        this.textStrip = Util.getLayoutPart(this, R.id.animatedTextStrip, AnimatedTextStrip.class);
        this.textStrip.setText(this.selectedExample.getHeaderText());
        this.buttonAddRemoveFavourite = Util.getLayoutPart(this, R.id.btnAddFavourite, Button.class);
        this.buttonAddRemoveFavourite.setOnClickListener(this);
        this.buttonViewInfo = Util.getLayoutPart(this, R.id.btnViewInfo, Button.class);
        this.buttonViewInfo.setOnClickListener(this);
        this.btnViewCode = Util.getLayoutPart(this, R.id.btnViewCode, Button.class);
        this.btnViewCode.setOnClickListener(this);
        this.btnSendFeedback = Util.getLayoutPart(this, R.id.btnSendFeedback, Button.class);
        this.btnSendFeedback.setOnClickListener(this);
        this.container = Util.getLayoutPart(this, R.id.container, FrameLayout.class);
        this.container.setOnTouchListener(this);
        this.menuContainer = Util.getLayoutPart(this, R.id.menuContainer, FrameLayout.class);
        this.descriptionPanel = Util.getLayoutPart(this, R.id.descriptionPanel, ScrollView.class);
        this.buttonsLayout = Util.getLayoutPart(this, R.id.buttonsLayout, LinearLayout.class);
        this.header = Util.getLayoutPart(this, R.id.txtHeader, TextView.class);
        this.description = Util.getLayoutPart(this, R.id.txtDescription, TextView.class);
        this.buttonNextExample = Util.getLayoutPart(this, R.id.btnNext, Button.class);
        this.buttonNextExample.setOnClickListener(this);
        this.buttonPrevExample = Util.getLayoutPart(this, R.id.btnPrev, Button.class);
        this.buttonPrevExample.setOnClickListener(this);
    }

    private void setBackground() {
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                String prefix = ExampleActivity.this.selectedControl.getShortFragmentName();
                return new LinearGradient(
                        0, 0, 0, height,
                        new int[]{
                                getResources().getColor(getResources().getIdentifier(prefix + "_example_menu_color1", "color", getPackageName())),
                                getResources().getColor(getResources().getIdentifier(prefix + "_example_menu_color2", "color", getPackageName())),
                                getResources().getColor(getResources().getIdentifier(prefix + "_example_menu_color3", "color", getPackageName())),
                                getResources().getColor(getResources().getIdentifier(prefix + "_example_menu_color4", "color", getPackageName()))},
                        new float[]{0f, 0.3f, 0.7f, 1f}, Shader.TileMode.REPEAT
                );
            }
        };

        PaintDrawable shaderPaint = new PaintDrawable();
        shaderPaint.setShaderFactory(sf);
        shaderPaint.setShape(new RectShape());

        this.app.setBackgroundDrawableSafe(this.menuContainer, shaderPaint);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getPointerCount() != 1 || !textStrip.isChecked()) {
            return true;
        }
        float scaleFactor = getExampleFragmentScaleFactor();
        Matrix m = new Matrix();
        m.setScale(scaleFactor, scaleFactor);
        Rect r = new Rect();
        container.getDrawingRect(r);
        RectF floatRect = new RectF(r.left, r.top, r.right, r.bottom);
        m.mapRect(floatRect);
        if (floatRect.contains(event.getX(), event.getY())) {
            textStrip.setChecked(!textStrip.isChecked());
            return true;
        }
        return false;
    }

    public void onClick(View v) {
        if (v == this.btnViewCode) {
            this.viewCode();
        } else if (v == this.btnSendFeedback) {
            this.sendFeedback();
        } else if (v == this.buttonNextExample) {
            this.nextExample();
        } else if (v == this.buttonPrevExample) {
            this.previousExample();
        } else if (v == this.buttonViewInfo) {
            this.viewInfo();
        } else if (v == this.buttonAddRemoveFavourite) {
            this.toggleFavorite();
        }
    }

    public void toggleExampleInfoMenuStripVisibility(boolean visible){
        if (visible){
            this.textStrip.setVisibility(View.VISIBLE);
        }else{
            this.textStrip.setVisibility(View.GONE);
        }
    }

    private void toggleFavorite() {
        Example currentExample = this.selectedExample;
        if (app.isExampleInFavourites(currentExample)) {
            app.removeFavorite(currentExample);
            buttonAddRemoveFavourite.setBackgroundResource(R.drawable.example_favourite);
            app.trackEvent(TrackedApplication.EXAMPLE_SCREEN, TrackedApplication.EVENT_REMOVE_FAVOURITE);
        } else {
            app.addFavorite(currentExample);
            buttonAddRemoveFavourite.setBackgroundResource(R.drawable.example_remove_favourite);
            app.trackEvent(TrackedApplication.EXAMPLE_SCREEN, TrackedApplication.EVENT_ADD_FAVOURITE);
        }
    }

    private void viewInfo() {
        app.showInfo(this, this.selectedExample);
        app.trackEvent(TrackedApplication.EXAMPLE_SCREEN, TrackedApplication.EVENT_SHOW_EXAMPLE_TOOLBAR);
    }

    private void previousExample() {
        if (!this.buttonPrevExample.isClickable()) {
            return;
        }
        setViewEnabled(buttonPrevExample, false);
        setViewEnabled(buttonNextExample, false);
        navigateToPreviousExample();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(TrackedApplication.PARAM_CONTROL_NAME, this.selectedControl.getShortFragmentName());
        params.put(TrackedApplication.PARAM_EXAMPLE_NAME, this.selectedExample.getShortFragmentName());
        app.trackEvent(TrackedApplication.EXAMPLE_SCREEN, TrackedApplication.EVENT_NAVIGATE_EXAMPLE, params);
    }

    private void nextExample() {
        if (!this.buttonNextExample.isClickable()) {
            return;
        }
        setViewEnabled(buttonPrevExample, false);
        setViewEnabled(buttonNextExample, false);
        navigateToNextExample();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(TrackedApplication.PARAM_CONTROL_NAME, this.selectedControl.getShortFragmentName());
        params.put(TrackedApplication.PARAM_EXAMPLE_NAME, this.selectedExample.getShortFragmentName());
        app.trackEvent(TrackedApplication.EXAMPLE_SCREEN, TrackedApplication.EVENT_NAVIGATE_EXAMPLE, params);
    }

    private void sendFeedback() {
        feedback.show(ExampleActivity.this);
        app.trackEvent(TrackedApplication.EXAMPLE_SCREEN, TrackedApplication.EVENT_SEND_FEEDBACK);
    }

    private void viewCode() {
        ExampleFragmentBase exampleFragment = (ExampleFragmentBase) getSupportFragmentManager().findFragmentById(R.id.exampleContainer);
        app.showCode(ExampleActivity.this, this.selectedExample, exampleFragment.getSourceCodeModel());
    }

    private void loadContent() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_example);
        } else {
            setContentView(R.layout.activity_example_horizontal);
        }
    }

    private void initTheme() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.setTheme(R.style.DarkTheme);
        } else {
            this.setTheme(R.style.DarkThemeLandscape);
        }
    }

    private void initFeedback() {
        this.feedback = RadFeedback.instance();
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String telephonyId = telephonyManager.getDeviceId();
        String androidId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String uniqueHash = telephonyId;
        if (androidId != null) {
            uniqueHash = String.format("%s", (telephonyId + androidId).hashCode());
        }

        try {
            this.feedback.init(KeysRetriever.getFeedbackKey(), "https://platform.telerik.com/feedback/api/v1", uniqueHash);
            this.feedback.setOnFeedbackFinishedListener(this);
        } catch (InvalidParameterException ex) {
            Log.w("Telerik Feedback", ex.getMessage());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // We do this here as we don't want to handle the event when the state of the button is reset
        // in the onRestoreInstanceState call.
        this.textStrip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleSubMenu(true);
            }
        });

        this.refreshFavouriteButton();
    }

    private void refreshNavigationButtons(int currentExampleIndex) {
        this.setViewEnabled(this.buttonPrevExample, currentExampleIndex > 0);
        this.setViewEnabled(this.buttonNextExample, currentExampleIndex != this.selectedControl.getExamples().size() - 1);
    }

    private void refreshFavouriteButton() {
        Example currentExample = this.selectedExample;
        if (app.isExampleInFavourites(currentExample)) {
            buttonAddRemoveFavourite.setBackgroundDrawable(getResources().getDrawable(R.drawable.example_remove_favourite));
        } else {
            buttonAddRemoveFavourite.setBackgroundDrawable(getResources().getDrawable(R.drawable.example_favourite));
        }
    }

    private float getExampleFragmentScaleFactor() {
        return 1f - ((float) this.buttonsLayout.getWidth()) / ((float) this.container.getWidth());
    }

    private void createMenuAnimation(boolean isMenuVisible) {
        final float scaleFactor = this.getExampleFragmentScaleFactor();
        if (!isMenuVisible) {
            this.menuAnimation = new ScaleAnimation(1f, scaleFactor, 1f, scaleFactor);
            this.menuAnimation.setDuration(200);
            this.menuAnimation.setInterpolator(new DecelerateInterpolator());
            this.menuAnimation.setFillAfter(true);
            this.menuAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    textStrip.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    menuAnimation.setAnimationListener(null);
                    menuAnimation = null;
                    toggleViewGroupTouch(container, false);
                    textStrip.setClickable(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            this.menuAnimation = new ScaleAnimation(scaleFactor, 1f, scaleFactor, 1f);
            this.menuAnimation.setDuration(200);
            this.menuAnimation.setInterpolator(new DecelerateInterpolator());
            this.menuAnimation.setFillAfter(true);
            this.menuAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    textStrip.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    menuAnimation.setAnimationListener(null);
                    menuAnimation = null;
                    toggleViewGroupTouch(container, true);
                    textStrip.setClickable(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    private void toggleViewGroupTouch(ViewGroup viewGroup, boolean enabled) {
        if (viewGroup instanceof InputBlocker) {
            InputBlocker blocker = (InputBlocker) viewGroup;
            blocker.setInterceptTouch(!enabled);
        } else {
            int childCount = viewGroup.getChildCount();

            for (int i = 0; i < childCount; i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof ViewGroup) {
                    toggleViewGroupTouch((ViewGroup) view, enabled);
                }
            }
        }
    }

    private void toggleSubMenu(boolean animate) {
        if (this.currentExample != null) {
            if (this.textStrip.isChecked()) {
                this.currentExample.onExampleSuspended();
            } else {
                this.currentExample.onExampleResumed();
            }
        }

        if (this.menuAnimation == null && this.textStrip.isChecked()) {
            this.updateDescriptionPanelParams();
            if (animate) {
                this.createMenuAnimation(false);
                this.container.startAnimation(this.menuAnimation);
                this.animateMenuButtons(true);
                this.app.trackEvent(TrackedApplication.EXAMPLE_SCREEN, TrackedApplication.EVENT_SHOW_EXAMPLE_TOOLBAR);
            } else {
                float scaleFactor = this.getExampleFragmentScaleFactor();
                this.container.setScaleX(scaleFactor);
                this.container.setScaleY(scaleFactor);
                this.toggleViewGroupTouch(container, false);
                this.animateMenuButtons(true);
            }

        } else if (this.menuAnimation == null) {
            if (animate) {
                this.createMenuAnimation(true);
                this.container.startAnimation(this.menuAnimation);
                this.animateMenuButtons(false);
                this.app.trackEvent(TrackedApplication.EXAMPLE_SCREEN, TrackedApplication.EVENT_HIDE_EXAMPLE_TOOLBAR);
            } else {
                this.container.setScaleX(1);
                this.container.setScaleY(1);
                this.toggleViewGroupTouch(container, true);
                this.animateMenuButtons(false);
            }
        }
    }

    private void animateMenuButtons(boolean show) {
        if (show) {
            TranslateAnimation animation = new TranslateAnimation(this.buttonsLayout.getWidth(), 0, 0, 0);
            animation.setStartOffset(150);
            animation.setFillAfter(true);
            animation.setDuration(200);
            animation.setInterpolator(new DecelerateInterpolator());
            this.buttonsLayout.startAnimation(animation);

            animation = new TranslateAnimation(0, 0, this.descriptionPanel.getHeight(), 0);
            animation.setStartOffset(150);
            animation.setFillAfter(true);
            animation.setDuration(150);
            animation.setInterpolator(new DecelerateInterpolator());
            this.descriptionPanel.startAnimation(animation);
        } else {
            TranslateAnimation animation = new TranslateAnimation(0, this.buttonsLayout.getWidth(), 0, 0);
            animation.setFillAfter(true);
            animation.setDuration(150);
            animation.setInterpolator(new AccelerateInterpolator());
            this.buttonsLayout.startAnimation(animation);

            animation = new TranslateAnimation(0, 0, 0, this.descriptionPanel.getHeight());
            animation.setFillAfter(true);
            animation.setDuration(150);
            animation.setInterpolator(new AccelerateInterpolator());
            this.descriptionPanel.startAnimation(animation);
        }
    }

    @Override
    public void onBackPressed() {
        if(this.currentExample.onBackPressed()) {
            if (this.textStrip.isChecked()) {
                this.textStrip.setChecked(!this.textStrip.isChecked());
            }
            return;
        }

        if (this.textStrip.isChecked()) {
            this.textStrip.setChecked(!this.textStrip.isChecked());
        } else {
            this.currentExample.unloadExample();
            super.onBackPressed();
        }
    }

    private void loadExample(Bundle savedInstanceState, boolean animate, int outTransition, int inTransition) {
        if (savedInstanceState == null) {
            Example selectedExample = this.selectedExample;
            try {
                Class<?> fragmentClass;
                if (selectedExample instanceof GalleryExample) {
                    fragmentClass = GalleryExamplesFragment.class;
                } else {
                    fragmentClass = Class.forName(selectedExample.getFragmentName());
                }
                if (this.currentExample != null) {
                    this.currentExample.unloadExample();
                }
                Constructor<?> fragmentConstructor = fragmentClass.getConstructor();
                Fragment instance = this.currentExample = (ExampleFragmentBase) fragmentConstructor.newInstance();

                if (instance instanceof GalleryExamplesFragment){
                    ((GalleryExamplesFragment)instance).setControlId(this.selectedControl.getFragmentName());
                    ((GalleryExamplesFragment)instance).setExampleId(this.selectedExample.getFragmentName());
                }

                if (animate) {
                    this.app.loadFragment(this, instance, R.id.exampleContainer, false, outTransition, inTransition);
                } else {
                    this.app.loadFragment(this, instance, R.id.exampleContainer, false);
                }

            } catch (Exception e) {
                Log.w("Could not load example.", e.getMessage());
            }
        } else {
            this.currentExample = (ExampleFragmentBase) this.getSupportFragmentManager().findFragmentById(R.id.exampleContainer);
        }

        this.header.setText(this.selectedExample.getHeaderText());
        this.description.setText(this.selectedExample.getDescriptionText());
        this.currentExample.setOnExampleLoadedListener(this);
    }

    private void loadExample(Bundle savedInstanceState) {
        this.loadExample(savedInstanceState, false, 0, 0);
    }

    public void navigateToNextExample() {
        int exampleCount = this.selectedControl.getExamples().size();

        if (exampleCount == 1) {
            return;
        }

        int indexOfNext = this.selectedControl.getExamples().indexOf(this.selectedExample) + 1;
        this.selectedExample = this.selectedControl.getExamples().get(Math.min(exampleCount - 1, indexOfNext));
        this.loadExample(null, true, R.anim.example_activity_left_out_transition, R.anim.example_activity_right_in_transition);

        this.refreshFavouriteButton();
    }

    public void navigateToPreviousExample() {
        int exampleCount = this.selectedControl.getExamples().size();

        if (exampleCount == 1) {
            return;
        }
        int indexOfPrevious = this.selectedControl.getExamples().indexOf(this.selectedExample) - 1;
        this.selectedExample = this.selectedControl.getExamples().get(Math.max(0, indexOfPrevious) % exampleCount);
        this.loadExample(null, true, R.anim.example_activity_right_out_transition, R.anim.example_activity_left_in_transition);

        this.refreshFavouriteButton();
    }

    public void onExampleLoaded(View root) {
        this.currentExample.setOnExampleLoadedListener(null);
        if (this.textStrip.isChecked()) {
            if (root instanceof ViewGroup) {
                this.toggleViewGroupTouch((ViewGroup) root, false);
            } else {
                root.setClickable(false);
            }
        }
        this.refreshNavigationButtons(this.selectedControl.getExamples().indexOf(this.selectedExample));
    }

    private void setViewEnabled(View target, boolean enabled) {
        target.setEnabled(enabled);
        target.setClickable(enabled);
        target.setAlpha(enabled ? 1 : 0.5f);
    }

    private void updateDescriptionPanelParams() {
        float scrollViewHeight = (float) this.container.getHeight() -
                this.container.getHeight() * (1 - ((float) this.buttonsLayout.getWidth()) / ((float) this.container.getWidth()));
        float scrollViewWidth = this.container.getWidth() * (1 - ((float) this.buttonsLayout.getWidth()) / ((float) this.container.getWidth()));
        ViewGroup.LayoutParams params = this.descriptionPanel.getLayoutParams();
        params.height = (int) scrollViewHeight;
        params.width = (int) scrollViewWidth;
        this.descriptionPanel.setLayoutParams(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        final Example selectedExample = this.selectedExample;

        if (id == R.id.action_add_to_favorites) {
            this.app.addFavorite(selectedExample);
        } else if (id == R.id.action_remove_from_favorites) {
            this.app.removeFavorite(selectedExample);
        } else if (id == R.id.action_view_example_info) {
            this.app.showInfo(this, selectedExample);
        }

        if (id == android.R.id.home) {
            this.finish();
        }

        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendFeedbackFinished() {

    }

    @Override
    public String getScreenName() {
        return TrackedApplication.EXAMPLE_SCREEN;
    }

    @Override
    public HashMap<String, Object> getAdditionalParameters() {
        HashMap<String, Object> additionalParams = new HashMap<String, Object>();
        additionalParams.put(TrackedApplication.PARAM_CONTROL_NAME, this.selectedControl.getShortFragmentName());
        additionalParams.put(TrackedApplication.PARAM_EXAMPLE_NAME, this.selectedExample.getShortFragmentName());
        return additionalParams;
    }
}
