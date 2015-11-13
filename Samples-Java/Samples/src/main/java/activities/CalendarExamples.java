package activities;

import java.util.ArrayList;
import java.util.HashMap;

import fragments.calendar.CalendarInitFragment;
import fragments.calendar.CalendarInitXmlFragment;
import fragments.calendar.displaydate.CalendarDisplayDateListenerFragment;
import fragments.calendar.displaydate.CalendarDisplayDateMinMaxFragment;
import fragments.calendar.displaydate.CalendarDisplayDateSetFragment;
import fragments.calendar.displaymode.CalendarDisplayModeListenerFragment;
import fragments.calendar.displaymode.CalendarDisplayModeSetFragment;
import fragments.calendar.events.CalendarEventAllDayFragment;
import fragments.calendar.events.CalendarEventCreationFragment;
import fragments.calendar.events.CalendarEventInlineMode;
import fragments.calendar.events.CalendarEventPopupMode;
import fragments.calendar.events.CalendarEventReadFragment;
import fragments.calendar.events.CalendarEventRenderModeFragment;
import fragments.calendar.scrolling.CalendarScrollingDirectionFragment;
import fragments.calendar.scrolling.CalendarScrollingFlingSpeedFragment;
import fragments.calendar.scrolling.CalendarScrollingFrictionFragment;
import fragments.calendar.scrolling.CalendarScrollingModeFragment;
import fragments.calendar.scrolling.CalendarScrollingProgrammaticallyFragment;
import fragments.calendar.scrolling.CalendarScrollingSnapSpeedFragment;
import fragments.calendar.selection.CalendarSelectionDatesFragment;
import fragments.calendar.selection.CalendarSelectionDecoratorFragment;
import fragments.calendar.selection.CalendarSelectionDisabledDatesFragment;
import fragments.calendar.selection.CalendarSelectionListenerFragment;
import fragments.calendar.selection.CalendarSelectionModeFragment;
import fragments.calendar.selection.CalendarSelectionRangeFragment;

/**
 * Created by ajekov on 2/23/2015.
 */
public class CalendarExamples implements ExamplesProvider {
    private HashMap<String, ArrayList<ExampleFragment>> calendarExamples;

    public CalendarExamples() {
        this.calendarExamples = this.getCalendarExamples();
    }

    @Override
    public String controlName() {
        return "Calendar";
    }

    @Override
    public HashMap<String, ArrayList<ExampleFragment>> examples() {
        return this.calendarExamples;
    }

    private HashMap<String, ArrayList<ExampleFragment>> getCalendarExamples() {
        HashMap<String, ArrayList<ExampleFragment>> calendarExamples = new HashMap<String, ArrayList<ExampleFragment>>();

        ArrayList<ExampleFragment> result = new ArrayList<ExampleFragment>();

        result.add(new CalendarInitFragment());
        result.add(new CalendarInitXmlFragment());

        calendarExamples.put("Init", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new CalendarDisplayDateListenerFragment());
        result.add(new CalendarDisplayDateMinMaxFragment());
        result.add(new CalendarDisplayDateSetFragment());

        calendarExamples.put("Display Date", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new CalendarDisplayModeListenerFragment());
        result.add(new CalendarDisplayModeSetFragment());

        calendarExamples.put("Display Mode", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new CalendarEventAllDayFragment());
        result.add(new CalendarEventCreationFragment());
        result.add(new CalendarEventRenderModeFragment());
        result.add(new CalendarEventReadFragment());
        result.add(new CalendarEventInlineMode());
        result.add(new CalendarEventPopupMode());

        calendarExamples.put("Events", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new CalendarScrollingDirectionFragment());
        result.add(new CalendarScrollingFlingSpeedFragment());
        result.add(new CalendarScrollingFrictionFragment());
        result.add(new CalendarScrollingModeFragment());
        result.add(new CalendarScrollingProgrammaticallyFragment());
        result.add(new CalendarScrollingSnapSpeedFragment());

        calendarExamples.put("Scrolling", result);

        result = new ArrayList<ExampleFragment>();

        result.add(new CalendarSelectionListenerFragment());
        result.add(new CalendarSelectionDecoratorFragment());
        result.add(new CalendarSelectionDisabledDatesFragment());
        result.add(new CalendarSelectionModeFragment());
        result.add(new CalendarSelectionDatesFragment());
        result.add(new CalendarSelectionRangeFragment());

        calendarExamples.put("Selection", result);

        return calendarExamples;
    }
}
