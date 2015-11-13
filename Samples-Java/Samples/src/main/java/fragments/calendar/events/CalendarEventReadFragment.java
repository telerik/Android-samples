package fragments.calendar.events;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.telerik.android.sdk.R;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.events.read.EventQueryToken;
import com.telerik.widget.calendar.events.read.EventReadAdapter;
import com.telerik.widget.calendar.events.read.GenericResultCallback;

import java.util.Calendar;

import activities.ExampleFragment;

public class CalendarEventReadFragment extends Fragment implements ExampleFragment {

    public static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 12;
    private static final String PREFS_NAME = "telerik_samples_java_preferences";
    private static final String PERMISSION_REJECTED_KEY = "telerik_samples_calendar_rejected";
    private EventReadAdapter adapter;
    private boolean permissionWasRequested = false;

    private LinearLayout settingsLayout;
    private LinearLayout readEventsLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_calendar_events_read, container, false);

        settingsLayout = (LinearLayout)rootView.findViewById(R.id.go_settings_layout);
        readEventsLayout = (LinearLayout)rootView.findViewById(R.id.read_events_layout);

        RadCalendarView calendarView = new RadCalendarView(getActivity());

        adapter = new EventReadAdapter(calendarView);
        calendarView.setEventAdapter(adapter);

        Button settingsButton = (Button)rootView.findViewById(R.id.go_settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
            }
        });

        final Button readEventsButton = (Button)rootView.findViewById(R.id.read_events_button);
        readEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryReadEvents();
            }
        });
        initLayoutVisibility();
        rootView.addView(calendarView);

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @Nullable String[] permissions, @Nullable int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CALENDAR) {
            if(grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted();
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR);
                if (!showRationale) {
                    switchButtonLayout(true);
                    savePermissionWasRejected();
                } else {
                    Toast.makeText(getContext(), "events can't be loaded without the proper permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void tryReadEvents() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},
                    MY_PERMISSIONS_REQUEST_READ_CALENDAR);
        } else {
            readEvents();
        }
    }

    // This method should be called only when a READ_CALENDAR permission is granted.
    // You can see 3 different customization option by uncommenting the corresponding lines.
    // Only one of the options must be uncommented at a time.
    private void readEvents() {

        // 1. Simply read all events from the device's default calendar
        adapter.readEventsAsync();

        // 2. Read the events from all calendars:
//        EventReadAdapter.getAllCalendarsAsync(getActivity(), new GenericResultCallback<EventReadAdapter.CalendarInfo[]>() {
//            @Override
//            public void onResult(EventReadAdapter.CalendarInfo[] calendars) {
//                if (calendars == null)
//                    return;
//
//                String[] calendarIDs = new String[calendars.length];
//
//                for (int i = 0; i < calendars.length; i++) {
//                    calendarIDs[i] = calendars[i].id;
//                }
//
//                EventQueryToken token = EventQueryToken.getCalendarsById(calendarIDs);
//                adapter.setEventsQueryToken(token);
//                adapter.readEventsAsync();
//            }
//        });

        //3. Read events for a specified range:
//        Calendar calendar = Calendar.getInstance();
//        long start = calendar.getTimeInMillis();
//        calendar.add(Calendar.DATE, 7);
//        long end = calendar.getTimeInMillis();
//
//        EventQueryToken token = adapter.getEventsQueryToken();
//        token.setRange(start, end);
//
//        adapter.readEventsAsync();

        readEventsLayout.setVisibility(View.GONE);
        settingsLayout.setVisibility(View.GONE);

        Toast.makeText(getContext(), "events loaded", Toast.LENGTH_SHORT).show();
    }

    private void checkFirstTimePermissions() {
        SharedPreferences preferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        permissionWasRequested = preferences.getBoolean(PERMISSION_REJECTED_KEY, false);
    }

    private void savePermissionWasRejected() {
        SharedPreferences preferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PERMISSION_REJECTED_KEY, true);
        editor.apply();
    }

    private void initLayoutVisibility() {
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            checkFirstTimePermissions();
            if (permissionWasRequested && !shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {
                switchButtonLayout(true);
            }
        }
    }

    private void switchButtonLayout(boolean showSettings) {
        settingsLayout.setVisibility(showSettings ? View.VISIBLE : View.GONE);
        readEventsLayout.setVisibility(showSettings ? View.GONE : View.VISIBLE);
    }

    private void onPermissionGranted() {
        readEvents();
    }

    private void onPermissionDenied() {
        Toast.makeText(getActivity(), "events can't be loaded without the proper permission", Toast.LENGTH_SHORT).show();
    }

    private void goToSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_READ_CALENDAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_CALENDAR) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED) {
                onPermissionDenied();
            } else {
                onPermissionGranted();
            }
        }
    }

    @Override
    public String title() {
        return "Read events";
    }
}
