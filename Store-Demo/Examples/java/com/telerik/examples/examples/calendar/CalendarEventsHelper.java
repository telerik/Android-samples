package com.telerik.examples.examples.calendar;

import android.graphics.Color;

import com.telerik.widget.calendar.events.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tgpetrov on 16.07.2015.
 */
public class CalendarEventsHelper {
    private final String[] corpAppointmentTitles = new String[]{
            "Job interview",
            "Conference call with HQ2",
            "Tokyo deal call",
            "Coordination meeting",
            "Brainstorming session"

    };
    private int corpAppointmentsColor = Color.parseColor("#FBAF5D");

    private final String[] personalMeetingTitles = new String[]{
            "Watch a movie",
            "Date with Candice",
            "Birthday party"

    };
    private int personalMeetingColor = Color.parseColor("#C23EB8");

    private final String[] sportTitles = new String[]{
            "Fitness",
            "Table tennis",
            "Football",
            "Mountain biking",
            "Jogging with Molly"

    };
    private int sportColor = Color.parseColor("#F35755");

    private final String[] funTitles = new String[]{
            "Yachting",
            "Theater evening",
            "Dinner with the Morgans",
            "Weekend barbecue",
            "Watch your favorite Show",
    };

    private int funColor = Color.parseColor("#47C2B4");

    public ArrayList<Event> generateEvents() {
        ArrayList<Event> events = new ArrayList<Event>();
        Calendar eventDate = getToday();

        for (int week = 0; week < 2; week++) {
            for (int i = 0; i < personalMeetingTitles.length; i++) {

                eventDate.set(Calendar.AM_PM, Calendar.PM);
                eventDate.set(Calendar.HOUR, 8 + i / 2);
                long start = eventDate.getTimeInMillis();
                eventDate.add(Calendar.HOUR, 2);
                long end = eventDate.getTimeInMillis();
                Event event = new Event(personalMeetingTitles[i], start, end);
                event.setEventColor(personalMeetingColor);
                events.add(event);

                int daysToAdd = i < personalMeetingTitles.length / 2 ? 1 - i * 3 : 1 + i * 4;
                eventDate = getToday();
                eventDate.add(Calendar.DATE, 7 * week + daysToAdd);
            }
        }

        eventDate = getToday();
        for (int i = 0; i < sportTitles.length; i++) {

            if (i != 3) {
                eventDate.set(Calendar.AM_PM, Calendar.PM);
                eventDate.add(Calendar.DATE, i);
                eventDate.set(Calendar.HOUR, 6 + i / 2);
                long start = eventDate.getTimeInMillis();
                eventDate.add(Calendar.MINUTE, 90);
                long end = eventDate.getTimeInMillis();
                Event event = new Event(sportTitles[i], start, end);
                event.setEventColor(sportColor);
                events.add(event);
            } else {
                eventDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                eventDate.add(Calendar.DATE, -7);
                moveToDayStart(eventDate);
                long start = eventDate.getTimeInMillis();
                eventDate.add(Calendar.MINUTE, 90);
                long end = eventDate.getTimeInMillis();
                Event event = new Event(sportTitles[i], start, end);
                event.setAllDay(true);
                event.setEventColor(sportColor);
                events.add(event);
                eventDate.add(Calendar.DATE, 7);
            }

            int daysToAdd = i < sportTitles.length / 2 ? 2 - i * 2 : 2 + i * 3;
            eventDate = getToday();
            eventDate.add(Calendar.DATE, daysToAdd);
        }

        eventDate = getToday();
        for (int week = 0; week < 2; week++) {
            for (int i = 0; i < funTitles.length; i++) {

                if (i != 3) {
                    eventDate.set(Calendar.AM_PM, Calendar.PM);
                    eventDate.set(Calendar.HOUR, 2 + i);
                    long start = eventDate.getTimeInMillis();
                    eventDate.add(Calendar.MINUTE, 90);
                    long end = eventDate.getTimeInMillis();
                    Event event = new Event(funTitles[i], start, end);
                    event.setEventColor(funColor);
                    events.add(event);
                } else {
                    eventDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    eventDate.add(Calendar.DATE, -14);
                    moveToDayStart(eventDate);
                    long start = eventDate.getTimeInMillis();
                    eventDate.add(Calendar.MINUTE, 90);
                    long end = eventDate.getTimeInMillis();
                    Event event = new Event(funTitles[i], start, end);
                    event.setAllDay(true);
                    event.setEventColor(funColor);
                    events.add(event);
                    eventDate.add(Calendar.DATE, 14);
                }

                int daysToAdd = i < funTitles.length / 2 ? 3 - i * 3 : 3 + i * 2;
                eventDate = getToday();
                eventDate.add(Calendar.DATE, 7 * -week + daysToAdd);
            }
        }

        eventDate = getToday();
        for (int week = -1; week < 2; week++) {

            for (int i = 0; i < corpAppointmentTitles.length; i++) {
                eventDate.set(Calendar.AM_PM, Calendar.AM);
                eventDate.set(Calendar.HOUR, 9 + i / 2);
                long start = eventDate.getTimeInMillis();
                eventDate.add(Calendar.HOUR, 1);
                long end = eventDate.getTimeInMillis();
                Event event = new Event(corpAppointmentTitles[i], start, end);
                event.setEventColor(corpAppointmentsColor);
                events.add(event);

                int daysToAdd = i < funTitles.length / 2 ? 4 - i * 4 : 4 + i * 3;
                eventDate = getToday();
                eventDate.add(Calendar.DATE, 7 * week + daysToAdd);
            }
        }

        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event event, Event event2) {
                return (event.getStartDate() < event2.getStartDate()) ? -1 : (event.getStartDate() > event2.getStartDate()) ? 1 : 0;
            }
        });

        return events;
    }

    public void updateEventsColors(List<Event> events, int color, int allDayColor) {
        for(Event e : events) {
            if(e.isAllDay()) {
                e.setEventColor(allDayColor);
            } else {
                e.setEventColor(color);
            }
        }
    }

    private Calendar getToday() {
        Calendar calendar = Calendar.getInstance();
        moveToDayStart(calendar);
        return calendar;
    }

    private void moveToDayStart(Calendar calendar) {
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
