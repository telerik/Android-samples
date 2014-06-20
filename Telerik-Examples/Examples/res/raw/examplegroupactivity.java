package com.telerik.examples;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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

import com.telerik.examples.common.ExamplesApplicationContext;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class ExampleGroupActivity extends FragmentActivity implements TransitionHandler,
        NavigationDrawerFragment.NavigationDrawerCallbacks,
        ExampleGroupListFragment.AbsListViewScrollListener,
        PopupMenu.OnMenuItemClickListener,
        AbsListView.OnScrollListener,
        ExamplesGridView.OnOverScrollByHandler,
        TrackedActivity{

    private ViewPager viewPager;
    private ExampleGroupFragmentAdapter viewPagerAdapter;
    private NavigationDrawerFragment navigationDrawerFragment;
    private DrawerLayout drawerLayout;
    private View actionBarView;
    private ImageView tabPointer;
    private Button btnShowNavigationDrawer;

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

    private ToggleButton btnSwitchLayout;

    private static final long ANIMATION_DURATION = 200;


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (this.drawerLayout.isDrawerVisible(Gravity.LEFT)) {
            this.actionBarView.setVisibility(View.VISIBLE);
            this.actionBarView.setAlpha(1f);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.app = (ExamplesApplicationContext) this.getApplicationContext();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_example_group);
        } else {
            setContentView(R.layout.activity_example_group_horizontal);
        }

        this.btnShowGroupMenu = (Button) this.findViewById(R.id.btnShowGroupMenu);
        this.controlLogo = (ImageView) this.findViewById(R.id.controlLogo);
        this.controlLabel = (TextView) this.findViewById(R.id.controlLabel);
        this.allTab = (TextView) this.findViewById(R.id.tabAll);
        this.allTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        this.favoritesTab = (TextView) this.findViewById(R.id.tabFavorites);
        this.favoritesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        this.sections.put(ExampleGroupAllExamplesFragment.class, getResources().getString(R.string.allStringPascalCase));
        this.sections.put(ExampleGroupFavoriteExamplesFragment.class, getResources().getString(R.string.favoritesStringPascalCase));
        this.tabPointer = (ImageView) this.findViewById(R.id.tabPointer);
        this.backgroundImage = (FrameLayout) this.findViewById(R.id.imageBackground);
        this.btnSwitchLayout = (ToggleButton) this.findViewById(R.id.btnSwitchLayout);
        this.btnShowNavigationDrawer = (Button) this.findViewById(R.id.hamburger);
        this.drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        this.btnShowNavigationDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        this.btnSwitchLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int mode = isChecked ? ExampleGroupListFragment.EXAMPLE_GROUP_GRID_MODE : ExampleGroupListFragment.EXAMPLE_GROUP_LIST_MODE;
                swapExampleListViews(mode);
            }
        });
        final ExampleGroupActivity thisActivity = this;
        this.btnShowGroupMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(thisActivity, btnShowGroupMenu);
                if (!app.isExampleInFavourites(app.selectedControl())) {
                    menu.inflate(R.menu.example_list_default);
                } else {
                    menu.inflate(R.menu.example_list_in_favourites);
                }
                menu.setOnMenuItemClickListener(thisActivity);
                menu.show();
            }
        });

        final ActionBar actionBar = this.getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        viewPager = (ViewPager) findViewById(R.id.pager);
        this.viewPagerAdapter = new ExampleGroupFragmentAdapter(getSupportFragmentManager(), sections);
        viewPager.setAdapter(this.viewPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                initPageHeader(position);
            }
        });


        this.navigationDrawerFragment = (NavigationDrawerFragment) this.getFragmentManager().findFragmentById(R.id.navigation_drawer);
        this.navigationDrawerFragment.setUp(R.id.navigation_drawer, this.drawerLayout);

        Window window = getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        this.actionBarView = v.findViewById(resId);
        this.actionBarView.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initPageHeader(viewPager.getCurrentItem());
            }
        }, 20);
        this.updateExampleInfo();
        this.loadHeaderBackground();
        this.updatePointerColor();
    }

    private void initPageHeader(int position) {
        if (position == 0) {
            favoritesTab.setTypeface(Typeface.create("roboto", Typeface.NORMAL));
            allTab.setTypeface(Typeface.create("roboto", Typeface.BOLD));
            animateTabPointer(allTab, ANIMATION_DURATION);
        } else {
            allTab.setTypeface(Typeface.create("roboto", Typeface.NORMAL));
            favoritesTab.setTypeface(Typeface.create("roboto", Typeface.BOLD));
            animateTabPointer(favoritesTab, ANIMATION_DURATION);
        }
    }

    private void updateExampleInfo() {
        ExampleGroup control = this.app.selectedControl();
        this.controlLogo.setImageResource(app.getDrawableResource(control.getHomePageLogoResource()));
        this.controlLabel.setText(control.getHeaderText());
    }

    @Override
    public void updateTransition(float step) {
        this.actionBarView.setAlpha(step);
        if (step > 0f && this.actionBarView.getVisibility() == View.GONE) {
            this.actionBarView.setVisibility(View.VISIBLE);
        }

        if (step == 0f && this.actionBarView.getVisibility() == View.VISIBLE) {
            this.actionBarView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNavigationDrawerSectionSelected(int position) {
        this.app.navigateToSection(this, position);
        this.app.trackFeature(TrackedApplication.DRAWER_CATEGORY, TrackedApplication.DRAWER_SECTION_SELECTED + ": " + (position == 0 ? "home" : (position == 1 ? "favourites" : "about")));
    }

    @Override
    public void onNavigationDrawerControlSelected(ExampleGroup control) {
        if (control == this.app.selectedControl()) {
            return;
        }
        this.app.openExample(this, control);
        this.app.trackFeature(TrackedApplication.DRAWER_CATEGORY, TrackedApplication.DRAWER_CONTROL_SELECTED + ": " + control.getFragmentName());
    }

    @Override
    public void onNavigationDrawerOpened() {
        this.actionBarView.setAlpha(1f);
        this.app.trackFeature(TrackedApplication.CONTROL_CATEGORY + "_" + this.app.selectedControl().getFragmentName(), TrackedApplication.DRAWER_OPENED);
    }

    @Override
    public void onNavigationDrawerClosed() {
        this.actionBarView.setAlpha(0f);
        this.actionBarView.setVisibility(View.GONE);
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
        this.app.trackFeature(TrackedApplication.CONTROL_CATEGORY + "_" + this.app.selectedControl().getFragmentName(),
                TrackedApplication.ACTION_BAR_LIST_LAYOUT_TOGGLED + ": " + (mode == ExampleGroupListFragment.EXAMPLE_GROUP_GRID_MODE ? "grid" : "list"));
    }

    private void updatePointerColor() {
        Triangle drawable = new Triangle();
        int headerHeight = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                (int) getResources().getDimension(R.dimen.example_group_header_height) :
                (int) getResources().getDimension(R.dimen.example_group_header_horizontal_height);
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
        ExampleGroup selectedGroup = this.app.selectedControl();
        BitmapDrawable backgroundImage = (BitmapDrawable) this.getResources().getDrawable(this.app.getDrawableResource(selectedGroup.getHomePageHeaderResource()));
        backgroundImage.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{backgroundImage});
        this.app.setBackgroundDrawableSafe(this.backgroundImage, layerDrawable);
    }

    private float deltaOffset;
    private float previousY = -1;
    @Override
    public boolean handleScrolling(View v, MotionEvent e) {
        if (e.getPointerCount() > 1) {
            return false;
        }

        if (e.getAction() ==  MotionEvent.ACTION_UP){
            this.deltaOffset = 0;
            this.previousY = -1;
            if (this.rotationStarted) {
                this.resetLogoAndBackground();
                return true;
            }
        }



        GridView typedView = (GridView) v;

        if (typedView.getChildCount() == 0){
            return false;
        }

        if (typedView.getChildAt(0).getTop() - typedView.getPaddingTop() == 0) {
            if ((int)this.controlLogo.getRotationX() >= 0) {

                if (this.previousY == -1){
                    this.previousY = e.getY();
                }

                if (this.logoAnimator != null){
                    this.logoAnimator.cancel();
                }

                if (this.backgroundAnimator != null){
                    this.backgroundAnimator.cancel();
                }

                float deltaY = e.getY() - this.previousY;
                this.previousY = e.getY();
                this.deltaOffset += deltaY;
                this.rotationStarted = true;
                float rotationX = Math.max(0, (float)(Math.atan(((this.deltaOffset / this.viewPager.getHeight()) * 10)) * 2/Math.PI)) * 180;
                this.controlLogo.setRotationX(rotationX);
                float scaleFactor = Math.max(1, 1 + this.controlLogo.getRotationX() / 1000);

                this.backgroundImage.setScaleX(scaleFactor);
                this.backgroundImage.setScaleY(scaleFactor);
                if ((int)rotationX == 0){
                    this.rotationStarted = false;
                    return false;
                }
                return true;
            }
        }else{
            this.previousY = -1;
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_favorites) {
            this.app.addFavorite(this.app.selectedControl());
            this.app.trackFeature(TrackedApplication.CONTROL_CATEGORY + "_" + this.app.selectedControl().getFragmentName(), TrackedApplication.ACTION_BAR_MENU_FAVOURITE_ADDED);
            return true;
        } else if (item.getItemId() == R.id.action_remove_from_favorites) {
            this.app.removeFavorite(this.app.selectedControl());
            this.app.trackFeature(TrackedApplication.CONTROL_CATEGORY + "_" + this.app.selectedControl().getFragmentName(), TrackedApplication.ACTION_BAR_MENU_FAVOURITE_REMOVED);
            return true;
        } else if (item.getItemId() == R.id.action_view_example_info) {
            this.app.showInfo(this, this.app.selectedControl());
            this.app.trackFeature(TrackedApplication.CONTROL_CATEGORY + "_" + this.app.selectedControl().getFragmentName(), TrackedApplication.ACTION_BAR_MENU_VIEW_INFO);
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
        this.backgroundAnimator .setTarget(this.backgroundImage);
        this.backgroundAnimator .start();
        this.rotationStarted = false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onOverScrollByHandler(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        float rotationX = Math.max(0, (float)(Math.atan(((((float)-scrollY) / this.viewPager.getHeight()) * 20)) * 2/Math.PI)) * 180;
        this.controlLogo.setRotationX(rotationX);
        float scaleFactor = Math.max(1, 1 + this.controlLogo.getRotationX() / 1000);

        this.backgroundImage.setScaleX(scaleFactor);
        this.backgroundImage.setScaleY(scaleFactor);
    }

    @Override
    public String getCategoryName() {
        return TrackedApplication.CONTROL_CATEGORY;
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

        public String getText(final int position) {
            final String title = (new ArrayList<String>(this.classes.values())).get(position);

            return title.toUpperCase(Locale.getDefault());
        }
    }
}
