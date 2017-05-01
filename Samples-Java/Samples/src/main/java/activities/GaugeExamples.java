package activities;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import fragments.gauges.GaugesCustomizationFragment;
import fragments.gauges.GaugesGettingStartedFragment;
import fragments.gauges.GaugesScalesFragment;
import fragments.gauges.GuagesAnimationsFragment;

public class GaugeExamples implements ExamplesProvider {
    @Override
    public String controlName() {
        return "GaugeView";
    }

    @Override
    public LinkedHashMap<String, ArrayList<ExampleFragment>> examples() {
        LinkedHashMap<String, ArrayList<ExampleFragment>> gaugeExamples = new LinkedHashMap<String, ArrayList<ExampleFragment>>();
        ArrayList<ExampleFragment> samples = new ArrayList<ExampleFragment>();
        samples.add(new GaugesGettingStartedFragment());
        samples.add(new GuagesAnimationsFragment());
        samples.add(new GaugesScalesFragment());
        samples.add(new GaugesCustomizationFragment());
        gaugeExamples.put("Init", samples);
        return gaugeExamples;
    }
}
