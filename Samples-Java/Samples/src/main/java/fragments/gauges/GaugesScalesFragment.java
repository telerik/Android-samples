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
import com.telerik.widget.indicators.GaugeIndicator;
import com.telerik.widget.indicators.GaugeRadialBarIndicator;
import com.telerik.widget.indicators.GaugeRadialNeedle;
import com.telerik.widget.scales.GaugeRadialScale;
import com.telerik.widget.scales.GaugeScaleLabelsLayoutMode;
import com.telerik.widget.scales.GaugeScaleTicksLayoutMode;

import activities.ExampleFragment;

public class GaugesScalesFragment extends Fragment implements ExampleFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gauge_scales, container, false);
        RadRadialGaugeView gauge = Util.getLayoutPart(rootView, R.id.radial_gauge, RadRadialGaugeView.class);

        GaugeRadialScale scale1 = new GaugeRadialScale(getActivity());
        scale1.setMinimum(34);
        scale1.setMaximum(40);
        scale1.setRadius(0.6f);
        scale1.setLabelsColor(Color.GRAY);
        scale1.getStrokePaint().setStrokeWidth(2);
        scale1.addIndicator(getIndicator(34,36,Color.BLUE));
        scale1.addIndicator(getIndicator(36.05f,40,Color.RED));
        scale1.setLabelsCount(7);
        scale1.setMajorTicksCount(7);
        scale1.getLabelsPaint().setTextSize(30);

        GaugeRadialNeedle needle = new GaugeRadialNeedle(getActivity());
        needle.setValue(36.5f);
        needle.setLength(0.5f);
        needle.setTopWidth(8);
        needle.setBottomWidth(8);
        scale1.addIndicator(needle);
        gauge.addScale(scale1);

        GaugeRadialScale scale2 = new GaugeRadialScale(getActivity());
        scale2.getStrokePaint().setStrokeWidth(2);
        scale2.setRadius(0.7f);
        scale2.setMinimum(93.2f);
        scale2.setMaximum(104);
        scale2.setTicksLayoutMode(GaugeScaleTicksLayoutMode.OUTER);
        scale2.setMajorTicksCount(7);
        scale2.setMinorTicksCount(20);
        scale2.setLabelsCount(7);
        scale2.setLabelsLayoutMode(GaugeScaleLabelsLayoutMode.OUTER);
        scale2.getLabelsPaint().setTextSize(30);
        scale2.setLabelsColor(Color.GRAY);
        gauge.addScale(scale2);

        gauge.getTitle().setText("celsius");
        gauge.getSubtitle().setText("fahrenheit");
        gauge.setTitleVerticalOffset(90);

        return rootView;

    }

    private GaugeIndicator getIndicator(float min, float max, int color) {
        GaugeRadialBarIndicator indicator = new GaugeRadialBarIndicator(this.getActivity());
        indicator.setMinimum(min);
        indicator.setMaximum(max);
        indicator.setFillColor(color);
        indicator.setLocation(0.69f);
        indicator.setBarWidth(0.08f);
        return indicator;
    }

    @Override
    public String title() {
        return "Scales";
    }
}
