package fragments.gauges;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.android.common.Util;
import com.telerik.android.sdk.R;
import com.telerik.widget.gauge.RadRadialGaugeView;
import com.telerik.widget.indicators.GaugeRadialBarIndicator;
import com.telerik.widget.indicators.GaugeRadialNeedle;
import com.telerik.widget.scales.GaugeRadialScale;

import activities.ExampleFragment;

public class GaugesGettingStartedFragment extends Fragment implements ExampleFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gauge_getting_started, container, false);

        // >> radial-gauge-load
        RadRadialGaugeView gauge = Util.getLayoutPart(rootView, R.id.radial_gauge, RadRadialGaugeView.class);
        // << radial-gauge-load

        // >> radial-scale-setup
        GaugeRadialScale scale = new GaugeRadialScale(getContext());
        scale.setMinimum(0);
        scale.setMaximum(6);
        scale.setMajorTicksCount(7);
        scale.setMinorTicksCount(9);
        scale.setLabelsCount(7);
        scale.setLineVisible(false);
        scale.setRadius(0.95f);
        // << radial-scale-setup

        // >> radial-indicators-setup
        int[] colors = new int[] {
                Color.rgb(221,221,221),
                Color.rgb(157,202,86),
                Color.rgb(240,196,77),
                Color.rgb(226,118,51),
                Color.rgb(167,1,14)
        };

        float rangeWidth = scale.getMaximum() / colors.length;
        float start = 0;
        for (int color : colors) {
            GaugeRadialBarIndicator indicator = new GaugeRadialBarIndicator(getContext());
            indicator.setMinimum(start);
            indicator.setMaximum(start + rangeWidth);
            indicator.setFillColor(color);
            scale.addIndicator(indicator);
            start += rangeWidth;
        }

        GaugeRadialNeedle needle = new GaugeRadialNeedle(getContext());
        needle.setValue(2);
        scale.addIndicator(needle);
        // << radial-indicators-setup

        // >> add-radial-scale
        gauge.addScale(scale);
        // << add-radial-scale

        return rootView;
    }

    @Override
    public String title() {
        return "Getting started";
    }
}
