package com.telerik.examples.examples.chart.series.financial;

import com.telerik.android.common.Function;
import com.telerik.android.primitives.widget.tooltip.contracts.TooltipContentAdapter;
import com.telerik.examples.examples.chart.ChartSelectionAndTooltipFragment;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public abstract class FinancialSeriesFragment extends ChartSelectionAndTooltipFragment {
    protected String dateFormat = "MM/dd";

    @Override
    protected void prepareChart() {
        super.prepareChart();

        TooltipContentAdapter contentAdapter = this.tooltipBehavior.getPopupPresenter().getContentAdapter();
        contentAdapter.setCategoryToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                GregorianCalendar calendar = (GregorianCalendar) argument;
                SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                return format.format(calendar.getTime());
            }
        });
        contentAdapter.setValueToStringConverter(new Function<Object, String>() {
            @Override
            public String apply(Object argument) {
                Double value = (Double) argument;

                return Double.toString(Math.round(value));
            }
        });

    }
}
