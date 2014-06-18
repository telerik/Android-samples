package com.telerik.examples.examples.chart;

import com.telerik.widget.chart.visualization.behaviors.ChartSelectionContext;
import com.telerik.widget.chart.visualization.common.ChartSeries;

public abstract class ChartSelectionAndLabelsFragment extends ChartSelectionFragment {
    @Override
    public void onSelectionChanged(ChartSelectionContext selectionContext) {
        ChartSeries selectedSeries = selectionContext.selectedSeries();
        if(selectedSeries != null) {
            selectedSeries.setShowLabels(true);
        }

        ChartSeries deselectedSeries = selectionContext.deselectedSeries();
        if(deselectedSeries != null) {
            deselectedSeries.setShowLabels(false);
        }
    }
}
