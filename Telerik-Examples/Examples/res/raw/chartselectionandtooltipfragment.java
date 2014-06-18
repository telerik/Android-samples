package com.telerik.examples.examples.chart;

import com.telerik.widget.chart.visualization.behaviors.ChartSelectionContext;
import com.telerik.widget.chart.visualization.behaviors.ChartTooltipBehavior;
import com.telerik.widget.chart.visualization.behaviors.TooltipTriggerMode;

public abstract class ChartSelectionAndTooltipFragment extends ChartSelectionFragment {
    protected ChartTooltipBehavior tooltipBehavior;

    protected void prepareChart() {
        super.prepareChart();

        this.tooltipBehavior = new ChartTooltipBehavior(this.getActivity());
        this.tooltipBehavior.setTriggerMode(TooltipTriggerMode.HOLD);
        chart.getBehaviors().add(this.tooltipBehavior);
    }

    @Override
    public void onSelectionChanged(ChartSelectionContext selectionContext) {
        super.onSelectionChanged(selectionContext);

        if(selectionContext.selectedDataPoint() != null) {
            this.tooltipBehavior.open(selectionContext.selectedDataPoint());
        } else {
            this.tooltipBehavior.close();
        }
    }

    @Override
    public void onHidden() {
        this.tooltipBehavior.close();
    }

    @Override
    public void onExampleSuspended() {
        this.tooltipBehavior.close();
    }
}
