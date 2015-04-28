package com.telerik.examples;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.telerik.android.common.Util;
import com.telerik.examples.common.ExamplesApplicationContext;
import com.telerik.examples.common.TelerikActivityHelper;
import com.telerik.examples.common.TrackedApplication;
import com.telerik.examples.common.contracts.TrackedActivity;
import com.telerik.examples.common.contracts.TransitionHandler;
import com.telerik.examples.common.fragments.ExampleGroupAllExamplesFragment;
import com.telerik.examples.common.fragments.ExampleGroupFavoriteExamplesFragment;
import com.telerik.examples.common.fragments.ExampleGroupListFragment;
import com.telerik.examples.common.fragments.NavigationDrawerFragment;
import com.telerik.examples.primitives.ExamplesGridView;
import com.telerik.examples.primitives.drawables.Triangle;
import com.telerik.examples.viewmodels.ExampleGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExampleGroupActivity extends ActionBarActivity implements TransitionHandler,
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        ExampleGroupListFragment.AbsListViewScrollListener,
        PopupMenu.OnMenuItemClickListener,
        AbsListView.OnScrollListener,
        ExamplesGridView.OnOverScrollByHandler,
        TrackedActivity,
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private float deltaOffset;
    private int previousY = -1;
    private int previousX = -1;

    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    //private View actionBarView;
    private ImageView tabPointer;

    private AnimatorSet backgroundAnimator;
    private AnimatorSet logoAnimator;

    private boolean rotationStarted;

    private ImageView controlLogo;
    private TextView controlLabel;

    private TextView allTab;
    private TextView favoritesTab;

    protected ExamplesApplicationContext app;
    private LinkedHashMap<Class<?>, String> sections = new LinkedHashMap<Class<?>, String>();
    private TranslateAnimation translateAnimation;
    private float previousTranslation = 0;
    private FrameLayout backgroundImage;
    private Button btnShowGroupMenu;
    private Button btnShowNavigationDrawer;

    private ToggleButton btnSwitchLayout;

    private String controlIdentifier;
    private ExampleGroup selectedControl;

    private static final long ANIMATION_DURATION = 200;

    private void loadContent() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_example_group);
        } else {
            setContentView(R.layout.activity_example_group_horizontal);
        }
    }

    private void showGroupMenu() {
        PopupMenu menu = new PopupMenu(this, btnShowGroupMenu);
        if (!app.isExampleInFavourites(this.selectedControl)) {
            menu.inflate(R.menu.example_list_default);
        } else {
            menu.inflate(R.menu.example_list_in_favourites);
        }
        menu.setOnMenuItemClickListener(this);
        menu.show();
    }

    public void onClick(View v) {
        if (v == allTab) {
            viewPager.setCurrentItem(0);
        } else if (v == favoritesTab) {
            viewPager.setCurrentItem(1);
        } else if (v == btnShowNavigationDrawer) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else if (v == btnShowGroupMenu) {
            this.showGroupMenu();
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int mode = isChecked ? ExampleGroupListFragment.EXAMPLE_GROUP_GRID_MODE : ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE;
        swapExampleListViews(mode);
    }

    private void initUI() {
        btnShowGroupMenu = Util.getLayoutPart(this, R.id.btnShowGroupMenu, Button.class);
        controlLogo = Util.getLayoutPart(this, R.id.controlLogo, ImageView.class);
        controlLabel = Util.getLayoutPart(this, R.id.controlLabel, TextView.class);
        allTab = Util.getLayoutPart(this, R.id.tabAll, TextView.class);
        allTab.setOnClickListener(this);
        favoritesTab = Util.getLayoutPart(this, R.id.tabFavorites, TextView.class);
        favoritesTab.setOnClickListener(this);
        tabPointer = Util.getLayoutPart(this, R.id.tabPointer, ImageView.class);

        backgroundImage = Util.getLayoutPart(this, R.id.imageBackground, FrameLayout.class);

        btnSwitchLayout = Util.getLayoutPart(this, R.id.btnSwitchLayout, ToggleButton.class);
        btnShowNavigationDrawer = Util.getLayoutPart(this, R.id.hamburger, Button.class);
        drawerLayout = Util.getLayoutPart(this, R.id.drawer_layout, DrawerLayout.class);
        btnShowNavigationDrawer.setOnClickListener(this);
        btnSwitchLayout.setOnCheckedChangeListener(this);
        btnShowGroupMenu.setOnClickListener(this);

        viewPager = Util.getLayoutPart(this, R.id.pager, ViewPager.class);
        this.sections.put(ExampleGroupAllExamplesFragment.class, getResources().getString(R.string.allStringPascalCase));
        this.sections.put(ExampleGroupFavoriteExamplesFragment.class, getResources().getString(R.string.favoritesStringPascalCase));
        ExampleGroupFragmentAdapter viewPagerAdapter = new ExampleGroupFragmentAdapter(getSupportFragmentManager(), sections);
        viewPager.setAdapter(viewPagerAdapter);

        backgroundImage.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                initPageHeader(viewPager.getCurrentItem());
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                initPageHeader(position);
            }
        });

        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment) this.getFragmentManager().findFragmentById(R.id.navigation_drawer);
        navigationDrawerFragment.setUp(R.id.navigation_drawer, this.drawerLayout);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TelerikActivityHelper.updateActivityTaskDescription(this);
        this.app = (ExamplesApplicationContext) this.getApplicationContext();
        Intent intent = this.getIntent();
        this.controlIdentifier = intent.getStringExtra(ExamplesApplicationContext.INTENT_CONTROL_ID);
        this.selectedControl = this.app.findControlById(this.controlIdentifier);

        this.loadContent();
        this.initUI();

        this.updateExampleInfo();
        this.loadHeaderBackground();
        this.updatePointerColor();

        if (savedInstanceState == null){
            this.app.trackScreenOpened(this);
        }
    }

    private void initPageHeader(int position) {
        if (position == 0) {
            favoritesTab.setTypeface(Typeface.create("roboto", Typeface.NORMAL));
            allTab.setTypeface(Typeface.create("roboto", Typeface.BOLD));
            animateTabPointer(allTab, ANIMATION_DURATION);
            this.app.trackEvent(TrackedApplication.CONTROL_SCREEN, TrackedApplication.EVENT_SWIPE_SHOW_ALL);
        } else {
            allTab.setTypeface(Typeface.create("roboto", Typeface.NORMAL));
            favoritesTab.setTypeface(Typeface.create("roboto", Typeface.BOLD));
            animateTabPointer(favoritesTab, ANIMATION_DURATION);
            this.app.trackEvent(TrackedApplication.CONTROL_SCREEN, TrackedApplication.EVENT_SWIPE_SHOW_FAVOURITES);
        }
    }


    private void updateExampleInfo() {
        this.controlLogo.setImageResource(app.getDrawableResource(this.selectedControl.getHomePageLogoResource()));
        this.controlLabel.setText(this.selectedControl.getHeaderText());
    }

    @Override
    public void updateTransition(float step) {
    }

    @Override
    public void onNavigationDrawerSectionSelected(String section) {
        if (!section.equals(NavigationDrawerFragment.NAV_DRAWER_SECTION_SETTINGS)) {
            this.app.navigateToSection(this, section);
        } else {
            this.app.showSettings(this);
        }
    }

    @Override
    public void onNavigationDrawerControlSelected(ExampleGroup control) {
        if (control == this.selectedControl) {
            return;
        }
        this.app.openExample(this, control);
    }

    @Override
    public void onNavigationDrawerOpened() {
        this.app.trackEvent(TrackedApplication.CONTROL_SCREEN, TrackedApplication.EVENT_DRAWER_OPENED);
    }

    @Override
    public void onNavigationDrawerClosed() {
        this.app.trackEvent(TrackedApplication.CONTROL_SCREEN, TrackedApplication.EVENT_DRAWER_CLOSED);
    }

    private void animateTabPointer(TextView view, long duration) {
        if (this.translateAnimation != null && !this.translateAnimation.hasEnded()) {
            this.translateAnimation.reset();
        }

        int[] pointerLocation = new int[2];
        int[] tabLocation = new int[2];
        this.tabPointer.getLocationInWindow(pointerLocation);
        view.getLocationInWindow(tabLocation);
        float fromAnimation = previousTranslation;
        float toAnimation = tabLocation[0];
        toAnimation = fromAnimation + (toAnimation - fromAnimation) + (view.getWidth() / 2 - this.tabPointer.getWidth() / 2);
        this.translateAnimation = new TranslateAnimation(
                previousTranslation,
                toAnimation,
                0,
                0);
        previousTranslation = toAnimation;
        this.translateAnimation.setDuration(duration);
        this.translateAnimation.setInterpolator(new DecelerateInterpolator());
        this.translateAnimation.setFillAfter(true);
        this.tabPointer.startAnimation(this.translateAnimation);
    }

    private void swapExampleListViews(int mode) {
        List<Fragment> fragments = this.getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof ExampleGroupListFragment) {
                ((ExampleGroupListFragment) fragment).setViewMode(mode);
            }
        }
        this.app.trackEvent(TrackedApplication.CONTROL_SCREEN, TrackedApplication.EVENT_LIST_LAYOUT_CHANGED);
    }

    private void updatePointerColor() {
        Triangle drawable = new Triangle();
        Resources res = this.getResources();
        int headerHeight = (int) res.getDimension(R.dimen.example_group_header_height);
        LayerDrawable layerDrawable = (LayerDrawable) this.backgroundImage.getBackground();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) layerDrawable.getDrawable(0);
        Bitmap image = bitmapDrawable.getBitmap();
        int imageHeight = Math.min(headerHeight, image.getHeight());
        int heightDiff = Math.max(0, image.getHeight() - headerHeight) / 2;
        int imageWidth = image.getWidth();
        drawable.setColor(image.getPixel(imageWidth / 2 - 1, heightDiff + imageHeight - 1));
        this.tabPointer.setImageDrawable(drawable);
    }

    private void loadHeaderBackground() {
        ExampleGroup selectedGroup = this.selectedControl;
        BitmapDrawable backgroundImage = (BitmapDrawable) this.getResources().getDrawable(this.app.getDrawableResource(selectedGroup.getHomePageHeaderResource()));
        backgroundImage.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{backgroundImage});
        this.app.setBackgroundDrawableSafe(this.backgroundImage, layerDrawable);
    }

    @Override
    public boolean handleScrolling(View v, MotionEvent e) {
        if (e.getPointerCount() > 1) {
            return false;
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            this.deltaOffset = 0;
            this.previousY = -1;
            this.previousX = -1;
            if (this.rotationStarted) {
                this.resetLogoAndBackground();
                return true;
            }
        }

        if (e.getAction() == MotionEvent.ACTION_CANCEL) {
            if (this.rotationStarted) {
                this.resetLogoAndBackground();
            }
            return false;
        }

        GridView typedView = (GridView) v;

        if (typedView.getChildCount() == 0) {
            return false;
        }

        if (this.previousY == -1) {
            this.previousY = (int) e.getY();
        }

        if (this.previousX == -1) {
            this.previousX = (int) e.getX();
        }

        float deltaX = e.getX() - this.previousX;
        this.previousX = (int) e.getX();
        float deltaY = e.getY() - this.previousY;
        this.previousY = (int) e.getY();

        if (typedView.getChildAt(0).getTop() - typedView.getPaddingTop() == 0) {
            if ((int) this.controlLogo.getRotationX() >= 0) {

                if (this.logoAnimator != null) {
                    this.logoAnimator.cancel();
                }

                if (this.backgroundAnimator != null) {
                    this.backgroundAnimator.cancel();
                }

                if (deltaX > deltaY && !this.rotationStarted) {
                    return false;
                }
                this.deltaOffset += deltaY;
                this.rotationStarted = true;
                float rotationX = Math.max(0, (float) (Math.atan(((this.deltaOffset / this.viewPager.getHeight()) * 10)) * 2 / Math.PI)) * 180;
                this.controlLogo.setRotationX(rotationX);
                float scaleFactor = Math.max(1, 1 + this.controlLogo.getRotationX() / 1000);

                this.backgroundImage.setScaleX(scaleFactor);
                this.backgroundImage.setScaleY(scaleFactor);
                if ((int) rotationX == 0) {
                    this.rotationStarted = false;
                    return false;
                }
                return true;
            }
        } else {
            this.previousY = -1;
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_favorites) {
            this.app.addFavorite(this.selectedControl);
            this.app.trackEvent(TrackedApplication.CONTROL_SCREEN, TrackedApplication.EVENT_ACTION_BAR_FAVORITE_ADD);
            return true;
        } else if (item.getItemId() == R.id.action_remove_from_favorites) {
            this.app.removeFavorite(this.selectedControl);
            this.app.trackEvent(TrackedApplication.CONTROL_SCREEN, TrackedApplication.EVENT_ACTION_BAR_FAVORITE_REMOVE);
            return true;
        } else if (item.getItemId() == R.id.action_view_example_info) {
            this.app.showInfo(this, this.selectedControl);
            return true;
        }
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if (this.rotationStarted) {
                this.resetLogoAndBackground();
            }
        }
    }

    private void resetLogoAndBackground() {
        this.logoAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.example_group_logo_reset_animation);
        this.logoAnimator.setTarget(this.controlLogo);
        this.logoAnimator.start();

        this.backgroundAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.example_group_background_reset_animation);
        this.backgroundAnimator.setTarget(this.backgroundImage);
        this.backgroundAnimator.start();
        this.rotationStarted = false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onOverScrollByHandler(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        float rotationX = Math.max(0, (float) (Math.atan(((((float) -scrollY) / this.viewPager.getHeight()) * 20)) * 2 / Math.PI)) * 180;
        this.controlLogo.setRotationX(rotationX);
        float scaleFactor = Math.max(1, 1 + this.controlLogo.getRotationX() / 1000);

        this.backgroundImage.setScaleX(scaleFactor);
        this.backgroundImage.setScaleY(scaleFactor);
    }

    @Override
    public String getScreenName() {
        return TrackedApplication.CONTROL_SCREEN;
    }

    @Override
    public HashMap<String, Object> getAdditionalParameters() {
        HashMap<String, Object> additionalParams = new HashMap<String, Object>();
        additionalParams.put(TrackedApplication.PARAM_CONTROL_NAME, this.selectedControl.getShortFragmentName());
        return additionalParams;
    }

    public class ExampleGroupFragmentAdapter extends FragmentStatePagerAdapter {

        private LinkedHashMap<Class<?>, String> classes;

        public ExampleGroupFragmentAdapter(FragmentManager fm, LinkedHashMap<Class<?>, String> classes) {
            super(fm);
            this.classes = classes;
        }

        @Override
        public Fragment getItem(final int position) {
            final Class<?> current = (new ArrayList<Class<?>>(this.classes.keySet())).get(position);
            ExampleGroupListFragment instance;
            try {
                final Constructor<?> fragmentConstructor = current.getConstructor();
                instance = (ExampleGroupListFragment) fragmentConstructor.newInstance();
                instance.setViewMode(btnSwitchLayout.isChecked() ? ExampleGroupListFragment.EXAMPLE_GROUP_GRID_MODE : ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE);
                return instance;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return this.classes.size();
        }
    }
}
