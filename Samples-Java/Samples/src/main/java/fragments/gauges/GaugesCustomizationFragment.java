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
import com.telerik.widget.indicators.GaugeBarIndicatorCapMode;
import com.telerik.widget.indicators.GaugeIndicator;
import com.telerik.widget.indicators.GaugeRadialBarIndicator;
import com.telerik.widget.scales.GaugeRadialScale;
import java.util.ArrayList;
import java.util.Random;
import activities.ExampleFragment;

public class GaugesCustomizationFragment extends Fragment implements ExampleFragment {
    RadRadialGaugeView gauge;
    @Override
    public String title() {
        return "Customization";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gauge_customization, container, false);
        gauge = Util.getLayoutPart(rootView, R.id.radial_gauge, RadRadialGaugeView.class);

        GaugeRadialScale scale = new GaugeRadialScale(getActivity());
        scale.setStartAngle(0);
        scale.setSweepAngle(360);
        scale.setLineVisible(false);
        scale.setMinimum(0);
        scale.setMaximum(100);
        scale.setTicksVisible(false);
        scale.setLabelsVisible(false);

        // >> gauges-indicators-bars
        int[] transparentColors = new int[] {
                Color.argb(100,224,151,36),
                Color.argb(100,196,241,57),
                Color.argb(100,132,235,247)
        };

        int[] colors = new int[] {
                Color.argb(255,224,151,36),
                Color.argb(255,196,241,57),
                Color.argb(255,132,235,247)
        };

        for (int i = 0; i < 3; i++) {
            GaugeRadialBarIndicator trnspIndicator = new GaugeRadialBarIndicator(getActivity());
            trnspIndicator.setMinimum(0);
            trnspIndicator.setMaximum(100);
            trnspIndicator.setFillColor(transparentColors[i]);
            trnspIndicator.setBarWidth(0.2f);
            trnspIndicator.setLocation(0.5f +  i * 0.25f);
            scale.addIndicator(trnspIndicator);


            GaugeRadialBarIndicator indicator = new GaugeRadialBarIndicator(getActivity());
            indicator.setMinimum(0);
            Random r = new Random();
            indicator.setMaximum(r.nextInt(100));
            indicator.setAnimated(true);
            indicator.setAnimationDuration(500);
            indicator.setBarWidth(0.2f);
            indicator.setLocation(0.5f +  i * 0.25f);
            indicator.setFillColor(colors[i]);
            indicator.setCap(GaugeBarIndicatorCapMode.ROUND);
            scale.addIndicator(indicator);
        }
        // << gauges-indicators-bars
        gauge.addScale(scale);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        gauge.animateGauge();
    }
}
