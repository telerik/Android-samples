package com.telerik.examples.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.telerik.examples.ExampleActivity;
import com.telerik.examples.ExampleGroupActivity;
import com.telerik.examples.ExampleInfoActivity;
import com.telerik.examples.MainActivity;
import com.telerik.examples.R;
import com.telerik.examples.SettingsActivity;
import com.telerik.examples.ViewCodeActivity;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.ExampleGroup;
import com.telerik.examples.viewmodels.ExampleSourceModel;
import com.telerik.examples.viewmodels.GalleryExample;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExamplesApplicationContext extends TrackedApplication implements Thread.UncaughtExceptionHandler {

    public static String PACKAGE_NAME;

    private List<Example> exampleGroups;
    private Set<String> favorites;
    private boolean tipLearned = false;
    private boolean analyticsActive = false;
    private boolean analyticsLearned = false;
    private int openedExamplesCount = 0;
    private List<FavouritesChangedListener> favouritesChangedListeners = new ArrayList<FavouritesChangedListener>();

    private ExampleGroup selectedControl;
    private Example currentExample;

    private static final String PREFS_NAME = "telerik_examples_preferences";
    private static final String FAVORITES = "favorites";
    private static final String TIP_LEARNED_KEY = "tip_learned";
    private static final String ANALYTICS_LEARNED_KEY = "tip_learned";
    private static final String ANALYTICS_ACTIVE_KEY = "analytics_active";
    private static final String OPENED_EXAMPLES_COUNT_KEY = "opened_examples_count";
    private static final int OPEN_ANALYTICS_PROMPT_AFTER_COUNT = 3;

    private Thread.UncaughtExceptionHandler defaultUEHandler;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();

        PACKAGE_NAME = getApplicationContext().getPackageName();
        defaultUEHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

        // check for a more appropriate place to call parse
        this.parseExamples();

        // Restore preferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();

        favorites = new HashSet<String>(
                preferences.getStringSet(FAVORITES, new HashSet<String>())
        );
        this.tipLearned = preferences.getBoolean(TIP_LEARNED_KEY, false);
        this.analyticsLearned = preferences.getBoolean(ANALYTICS_LEARNED_KEY, false);
        this.analyticsActive = preferences.getBoolean(ANALYTICS_ACTIVE_KEY, false);
        this.openedExamplesCount = preferences.getInt(OPENED_EXAMPLES_COUNT_KEY, 0);
    }

    public void addOnFavouritesChangedListener(FavouritesChangedListener listener) {
        if (this.favouritesChangedListeners.contains(listener)) {
            return;
        }
        this.favouritesChangedListeners.add(listener);
    }

    public void removeOnFavouritesChangedListener(FavouritesChangedListener listener) {
        if (!this.favouritesChangedListeners.contains(listener)) {
            return;
        }
        this.favouritesChangedListeners.remove(listener);
    }

    public boolean isExampleInFavourites(Example example) {
        if (this.favorites.size() == 0) {
            return false;
        }

        final String fragmentName = example.getFragmentName();
        return this.favorites.contains(fragmentName);
    }

    public void addFavorite(Example example) {
        this.favorites.add(example.getFragmentName());
        savePreferences();

        for (FavouritesChangedListener listener : this.favouritesChangedListeners) {
            listener.favouritesChanged();
        }
    }

    public void setAnalyticsActive(Activity callingActivity, boolean active, boolean showPrompt) {
        if (!this.analyticsLearned && active && showPrompt) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(callingActivity);
            dialogBuilder.setView(View.inflate(callingActivity, R.layout.analytics_message, null));
            dialogBuilder.setTitle(R.string.analytics_message_title);
            dialogBuilder.setPositiveButton(R.string.analytics_message_send, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setAnalyticsActive(true);
                    setAnalyticsLearned(true);
                }
            });

            dialogBuilder.setNegativeButton(R.string.analytics_message_dont_send, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setAnalyticsActive(false);
                    setAnalyticsLearned(true);
                }
            });

            AlertDialog dialog = dialogBuilder.create();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    resetExamplesCounter();
                }
            });
            dialog.show();
        } else {
            this.setAnalyticsActive(active);
        }
    }

    private void resetExamplesCounter() {
        this.openedExamplesCount = 0;
        this.editor.putInt(OPENED_EXAMPLES_COUNT_KEY, 0);
        this.editor.commit();
    }

    public void setAnalyticsLearned(boolean learned) {
        this.editor.putBoolean(ANALYTICS_LEARNED_KEY, learned);
        this.analyticsLearned = learned;
        editor.commit();
    }

    public void setAnalyticsActive(boolean active) {
        this.analyticsActive = active;
        this.editor.putBoolean(ANALYTICS_ACTIVE_KEY, active);
        this.editor.commit();
        if (active && this.canStartAnalytics()) {
            this.startMonitor();
        } else if (this.canStopAnalytics()) {
            this.stopMonitor();
            if (active) {
                this.analyticsActive = false;
                this.editor.putBoolean(ANALYTICS_ACTIVE_KEY, false);
                this.editor.commit();
                Toast.makeText(this, R.string.could_not_change_setting, Toast.LENGTH_SHORT).show();
            }
        } else if (active) {
            this.analyticsActive = false;
            this.editor.putBoolean(ANALYTICS_ACTIVE_KEY, false);
            this.editor.commit();
            Toast.makeText(this, R.string.could_not_change_setting, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected boolean canStartAnalytics() {
        return super.canStartAnalytics() && this.analyticsActive;
    }

    public boolean analyticsActive() {
        return this.analyticsActive;
    }

    @Override
    public void onActivityStarted(final Activity activity) {
        super.onActivityStarted(activity);
        if (!this.analyticsLearned) {
            if (activity instanceof ExampleActivity) {
                if (this.openedExamplesCount == OPEN_ANALYTICS_PROMPT_AFTER_COUNT) {
                    this.setAnalyticsActive(activity, true, true);
                } else {
                    this.openedExamplesCount++;
                    this.editor.putInt(OPENED_EXAMPLES_COUNT_KEY, this.openedExamplesCount);
                    this.editor.commit();
                }
            }
        }
    }

    public void setTipLearned(boolean tipLearned) {
        this.editor.putBoolean(TIP_LEARNED_KEY, tipLearned);
        this.editor.commit();
        this.tipLearned = tipLearned;
    }

    public boolean getTipLearned() {
        return this.tipLearned;
    }

    public void removeFavorite(Example example) {
        this.favorites.remove(example.getFragmentName());
        savePreferences();

        for (FavouritesChangedListener listener : this.favouritesChangedListeners) {
            listener.favouritesChanged();
        }
    }

    public String extractClassName(final String className, final int wordsFromEnd) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String[] nameSeparated = className.split("\\.");

        for (int len = nameSeparated.length, i = len - wordsFromEnd; i < len; i++) {
            stringBuilder.append(String.format("%s ", nameSeparated[i]));
        }

        return stringBuilder.toString();
    }

    public List<Example> getControlExamples() {
        if (this.exampleGroups == null) {
            this.parseExamples();
        }

        return this.exampleGroups;
    }

    public ExampleGroup selectedControl() {
        return this.selectedControl;
    }

    public void selectControl(ExampleGroup controlExamples) {
        this.selectedControl = controlExamples;
    }

    public Example selectedExample() {
        return this.currentExample;
    }

    public void selectExample(Example example) {

        if (example == null) {
            this.currentExample = null;
            return;
        }

        if (example instanceof GalleryExample || !(example instanceof ExampleGroup)) {
            if (this.selectedControl != null &&
                    this.selectedControl != example &&
                    !this.selectedControl.getExamples().contains(example)) {
                throw new IllegalArgumentException("The provided example is not part of the currently selected control.");
            } else {
                this.currentExample = example;
            }
        } else {
            this.selectedControl = (ExampleGroup) example;
        }
    }

    public void showInfo(Activity callingActivity, Example example) {
        this.initSelection(example);
        Intent exampleInfoIntent = new Intent(callingActivity, ExampleInfoActivity.class);
        callingActivity.startActivity(exampleInfoIntent);
    }

    public void showCode(Activity callingActivity, ExampleSourceModel sourceModel) {
        Intent viewCodeIntent = new Intent(callingActivity, ViewCodeActivity.class);
        viewCodeIntent.putExtra("source_model", sourceModel);
        callingActivity.startActivity(viewCodeIntent);
    }

    public void showSettings(Activity callingActivity) {
        Intent viewCodeIntent = new Intent(callingActivity, SettingsActivity.class);
        callingActivity.startActivity(viewCodeIntent);
    }

    public void openExample(Activity callingActivity, Example example) {
        this.initSelection(example);
        if (example instanceof GalleryExample || !(example instanceof ExampleGroup)) {
            Intent exampleInfoIntent = new Intent(callingActivity, ExampleActivity.class);
            callingActivity.startActivity(exampleInfoIntent);
        } else {
            Intent exampleInfoIntent = new Intent(callingActivity, ExampleGroupActivity.class);
            exampleInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            callingActivity.startActivity(exampleInfoIntent);
        }
    }

    public void navigateToSection(Activity callingActivity, int section) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainActivity.putExtra("section", section);
        callingActivity.startActivity(mainActivity);
    }

    public void loadFragment(FragmentActivity callingActivity, Fragment fragment, int containerId, boolean addToBackStack) {
        this.loadFragment(callingActivity, fragment, containerId, addToBackStack, 0, 0);
    }

    public void loadFragment(FragmentActivity callingActivity, Fragment fragment, int containerId, boolean addToBackStack, int outTransition, int inTransition) {
        FragmentManager supportFragmentManager = callingActivity.getSupportFragmentManager();
        if (!addToBackStack) {
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.setCustomAnimations(inTransition, outTransition, inTransition, outTransition);
            transaction.replace(containerId, fragment).commit();
        } else {
            FragmentManager.BackStackEntry currentEntry = this.getBackStackEntryForFragment(supportFragmentManager, fragment);
            if (currentEntry == null) {
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                transaction.setCustomAnimations(inTransition, outTransition, inTransition, outTransition);
                transaction.replace(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
            } else {
                supportFragmentManager.popBackStack(currentEntry.getId(), 0);
            }
        }
    }

    public int getDrawableResource(String name) {
        return this.getResources().getIdentifier("@" + name, "drawable", this.getPackageName());
    }

    public void setBackgroundDrawableSafe(View backgroundContainer, Drawable background) {
        int sdk = android.os.Build.VERSION.SDK_INT;

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Method setBackgroundDrawableMethod = FrameLayout.class.getMethod("setBackgroundDrawable", Drawable.class);
                setBackgroundDrawableMethod.invoke(backgroundContainer, background);
            } catch (Exception e) {
                Log.e("RD", "Could not set background gradient " + android.os.Build.VERSION.SDK_INT, e);
            }
        } else {
            try {
                Method setBackgroundDrawableMethod = FrameLayout.class.getMethod("setBackground", Drawable.class);
                setBackgroundDrawableMethod.invoke(backgroundContainer, background);
            } catch (Exception e) {
                Log.e("RD", "Could not set background gradient " + android.os.Build.VERSION.SDK_INT, e);
            }
        }
    }

    private FragmentManager.BackStackEntry getBackStackEntryForFragment(FragmentManager fragmentManager, Fragment entry) {
        int entriesCount = fragmentManager.getBackStackEntryCount();

        for (int i = 0; i < entriesCount; i++) {
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(i);
            if (entry.getClass().getName() == backStackEntry.getName()) {
                return backStackEntry;
            }
        }

        return null;
    }

    private void initSelection(Example example) {
        if (example instanceof GalleryExample || !(example instanceof ExampleGroup)) {
            this.selectControl(example.getParentControl());
            this.selectExample(example);
        } else {
            this.selectExample(null);
            this.selectControl((ExampleGroup) example);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        trackException(throwable);

        defaultUEHandler.uncaughtException(thread, throwable);
    }

    private void savePreferences() {
        // Workaround for the problem where the stored set must be immutable bellow API 17
        editor.putStringSet(FAVORITES, new HashSet<String>(this.favorites));

        editor.commit();
    }

    private void parseExamples() {
        final XmlResourceParser parser = getResources().getXml(R.xml.examples);
        final RuntimeException ex = new RuntimeException("Cannot parse XML");
        try {
            this.exampleGroups = XmlParser.parseXML(parser);
        } catch (XmlPullParserException e) {
            throw ex;
        } catch (IOException e) {
            throw ex;
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    public interface FavouritesChangedListener {
        void favouritesChanged();
    }
}
