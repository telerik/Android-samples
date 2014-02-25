package com.telerik.qsf.common;

import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;

import com.telerik.qsf.R;
import com.telerik.qsf.viewmodels.Example;
import com.telerik.qsf.viewmodels.ExampleGroup;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class App extends TrackedApplication implements Thread.UncaughtExceptionHandler {

    private final static String EXCEPTIONS_CATEGORY_KEY = "Unhandled Exceptions";
    public static String PACKAGE_NAME;

    private ArrayList<ExampleGroup> exampleGroups;
    private ExampleGroup selectedGroup;
    private Example currentExample;
    private Set<String> favorites;
    private String PREFS_NAME = "qsf_preferences";
    private String FAVORITES = "favorites";
    private Thread.UncaughtExceptionHandler defaultUEHandler;

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
        this.favorites = preferences.getStringSet(FAVORITES, new HashSet<String>());

        // FOR DEBUGGING
        if (this.favorites.size() == 0) {
            this.favorites.add("com.telerik.qsf.examples.chart.series.area");
            this.favorites.add("com.telerik.qsf.examples.chart.series.line");
        }

    }

    public Set<String> getFavorites() {
        return this.favorites;
    }

    public Boolean isExampleGroupInFavorites(ExampleGroup exampleGroup) {
        if (this.favorites.size() == 0) {
            return false;
        }

        String fragmentName = exampleGroup.getFragmentName();
        return this.favorites.contains(fragmentName);
    }

    public void savePreferences() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(FAVORITES, this.favorites);

        // Commit the edits!
        editor.commit();
    }

    public String extractClassName(final String className, final int wordsFromEnd) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String[] nameSeparated = className.split("\\.");

        for (int len = nameSeparated.length, i = len - wordsFromEnd; i < len; i++) {
            stringBuilder.append(String.format("%s ", nameSeparated[i]));
        }

        return stringBuilder.toString();
    }

    private void parseExamples() {
        XmlResourceParser parser = getResources().getXml(R.xml.examples);
        RuntimeException ex = new RuntimeException("Cannot parse XML");
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

    public ArrayList<ExampleGroup> getExampleGroups() {
        if (this.exampleGroups == null) {
            this.parseExamples();
        }
        return this.exampleGroups;
    }

    public ExampleGroup selectedGroup() {
        return this.selectedGroup;
    }

    public Example selectedExample() {
        return this.currentExample;
    }

    public void selectExampleGroup(ExampleGroup group) {
        this.selectedGroup = group;
    }

    public void selectExample(Example example) {
        this.currentExample = example;
    }

    public void selectExampleGroup(int position) {
        this.selectExampleGroup(this.getExampleGroups().get(position));
    }

    public static ControlsFragment getControlsFragment() {
        return controlsFragment;
    }

    private static ControlsFragment controlsFragment;

    public static void setControlsFragment(ControlsFragment newFragment) {
        controlsFragment = newFragment;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        trackFeature(EXCEPTIONS_CATEGORY_KEY, thread.getName());
        trackException(throwable);

        defaultUEHandler.uncaughtException(thread, throwable);
    }
}
