package fragments.gauges;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RadioButton;

import com.telerik.android.common.Util;
import com.telerik.android.sdk.R;
import com.telerik.widget.gauge.RadGaugeView;
import com.telerik.widget.gauge.RadRadialGaugeView;
import com.telerik.widget.indicators.GaugeIndicator;
import com.telerik.widget.indicators.GaugeRadialBarIndicator;
import com.telerik.widget.indicators.GaugeRadialNeedle;
import com.telerik.widget.scales.GaugeRadialScale;

import java.util.ArrayList;

import activities.ExampleFragment;

public class GuagesAnimationsFragment extends Fragment implements ExampleFragment {

    GaugeRadialNeedle needle;
    @Override
    public String title() {
        return "Animations";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gauge_animations, container, false);
        RadRadialGaugeView gauge = Util.getLayoutPart(rootView, R.id.radial_gauge, RadRadialGaugeView.class);
        setupGauge(gauge);

        RadioButton btn60 = Util.getLayoutPart(rootView, R.id.radio_60, RadioButton.class);
        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needle.setValue(60);
            }
        });

        RadioButton btn80 = Util.getLayoutPart(rootView, R.id.radio_80, RadioButton.class);
        btn80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needle.setValue(80);
            }
        });

        RadioButton btn120 = Util.getLayoutPart(rootView, R.id.radio_120, RadioButton.class);
        btn120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needle.setValue(120);
            }
        });

        RadioButton btn160 = Util.getLayoutPart(rootView, R.id.radio_160, RadioButton.class);
        btn160.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needle.setValue(160);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        needle.setValue(60);
    }

    private void setupGauge(RadGaugeView gauge) {
        gauge.getSubtitle().setText("km/h");
        gauge.setSubtitleVerticalOffset(20);

        GaugeRadialScale scale = new GaugeRadialScale(getActivity());
        scale.setMinimum(0);
        scale.setMaximum(180);
        scale.setRadius(0.98f);
        scale.setLabelsCount(10);
        scale.setMajorTicksCount(19);
        scale.setMinorTicksCount(1);
        scale.setLabelsOffset(scale.getLabelsOffset() + 20);
        scale.setTicksOffset(0.1f);
        scale.getMajorTicksStrokePaint().setStrokeWidth(2);
        scale.setMajorTicksStrokeColor(Color.rgb(132,132,132));
        scale.setLineVisible(false);
        gauge.addScale(scale);

        needle = new GaugeRadialNeedle(getActivity());
        needle.setLength(0.8f);
        needle.setBottomWidth(8);
        needle.setTopWidth(8);

        // >> gauge-animations-turn-on
        needle.setAnimated(true);
        needle.setAnimationDuration(500);
        // << gauge-animations-turn-on

        // >> gauge-animations-timing-func
        needle.setInterpolator(new AccelerateDecelerateInterpolator());
        // << gauge-animations-timing-func

        scale.addIndicator(needle);

        scale.addIndicator(getIndicator(0, 60, Color.rgb(132,132,132)));
        scale.addIndicator(getIndicator(61, 120, Color.rgb(54,54,54)));
        scale.addIndicator(getIndicator(121, 180, Color.rgb(198,85,90)));
    }

    private GaugeRadialBarIndicator getIndicator(float min, float max, int color) {
        GaugeRadialBarIndicator indicator = new GaugeRadialBarIndicator(getActivity());
        indicator.setMinimum(min);
        indicator.setMaximum(max);
        indicator.setFillColor(color);
        indicator.setBarWidth(0.02f);
        return indicator;
    }
}
