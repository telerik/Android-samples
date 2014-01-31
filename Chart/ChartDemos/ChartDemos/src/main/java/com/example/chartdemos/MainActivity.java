package com.example.chartdemos;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import android.R.anim;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.behaviors.ChartPanAndZoomBehavior;
import com.telerik.widget.chart.visualization.behaviors.ChartPanZoomMode;
import com.telerik.widget.chart.visualization.cartesianChart.CartesianChartGrid;
import com.telerik.widget.chart.visualization.cartesianChart.GridLineVisibility;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.AreaSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.BarSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SplineAreaSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SplineSeries;
import com.telerik.widget.chart.visualization.common.RadChartBase;
import com.telerik.widget.chart.visualization.pieChart.DoughnutSeries;
import com.telerik.widget.chart.visualization.pieChart.PieSeries;
import com.telerik.widget.chart.visualization.pieChart.RadPieChartView;
import com.telerik.widget.palettes.ChartPalettes;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

       
        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5).toUpperCase(l);
                case 5:
                    return getString(R.string.title_section6).toUpperCase(l);
                case 6:
                    return getString(R.string.title_section7).toUpperCase(l);
                case 7:
                    return getString(R.string.title_section8).toUpperCase(l);
                case 8:
                    return getString(R.string.title_section9).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_main, container, false);
            RadChartBase view = this.createChartView(this.getArguments());
            rootView.addView(view);
            return rootView;
        }

        private RadChartBase createChartView(Bundle context) {
            switch (context.getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    return this.createBarSeries();
                case 2:
                    return this.createLineSeries();
                case 3:
                    return this.createSplineSeries();
                case 4:
                    return this.createStackedLineSeries();
                case 5:
                    return this.createStackedSplineSeries();
                case 6:
                    return this.createStackedLineAreaSeries();
                case 7:
                    return this.createStackedSplineAreaSeries();
                case 8:
                    return this.createPieChart();
                case 9:
                    return this.createDoughnutChart();
            }

            return null;
        }

        private RadCartesianChartView createBarSeries() {
            RadCartesianChartView result = this.createBaseCartesianChart();
            BarSeries series = new BarSeries(this.getActivity().getBaseContext());
            series.setShowLabels(true);
            series.setCategoryBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object o) {
                    return ((DataPointModel) o).getCategory();
                }
            });
            series.setValueBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object o) {
                    return ((DataPointModel) o).getValue();
                }
            });

            result.getSeries().add(series);
            result.setPalette(ChartPalettes.light());
            series.setItemsSource(DataPointModel.generateRandomModels(0));

            return result;
        }

        private RadCartesianChartView createLineSeries() {
            RadCartesianChartView result = this.createBaseCartesianChart();
            LineSeries series = new LineSeries(this.getActivity().getBaseContext());
            series.setShowLabels(true);
            series.setCategoryBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object o) {
                    return ((DataPointModel) o).getCategory();
                }
            });
            series.setValueBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object o) {
                    return ((DataPointModel) o).getValue();
                }
            });

            result.getSeries().add(series);
            series.setItemsSource(DataPointModel.generateRandomModels(0));

            return result;
        }

        private RadCartesianChartView createSplineSeries() {
            RadCartesianChartView result = this.createBaseCartesianChart();
            SplineSeries series = new SplineSeries(this.getActivity().getBaseContext());
            series.setShowLabels(true);
            series.setCategoryBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object o) {
                    return ((DataPointModel) o).getCategory();
                }
            });
            series.setValueBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object o) {
                    return ((DataPointModel) o).getValue();
                }
            });

            result.getSeries().add(series);
            series.setItemsSource(DataPointModel.generateRandomModels(0));

            return result;
        }

        private RadCartesianChartView createStackedLineSeries() {
            RadCartesianChartView result = this.createBaseCartesianChart();
            for (int i = 0; i < 8; i++) {
                LineSeries series = new LineSeries(this.getActivity().getBaseContext());
                series.setShowLabels(true);
                series.setCategoryBinding(new DataPointBinding() {
                    @Override
                    public Object getValue(Object o) {
                        return ((DataPointModel) o).getCategory();
                    }
                });
                series.setValueBinding(new DataPointBinding() {
                    @Override
                    public Object getValue(Object o) {
                        return ((DataPointModel) o).getValue();
                    }
                });

                result.getSeries().add(series);
                series.setItemsSource(DataPointModel.generateRandomModels(i));
                series.setCombineMode(ChartSeriesCombineMode.STACK);
            }

            return result;
        }

        private RadCartesianChartView createStackedSplineSeries() {
            RadCartesianChartView result = this.createBaseCartesianChart();
            for (int i = 0; i < 8; i++) {
                SplineSeries series = new SplineSeries(this.getActivity().getBaseContext());
                series.setShowLabels(true);
                series.setCategoryBinding(new DataPointBinding() {
                    @Override
                    public Object getValue(Object o) {
                        return ((DataPointModel) o).getCategory();
                    }
                });
                series.setValueBinding(new DataPointBinding() {
                    @Override
                    public Object getValue(Object o) {
                        return ((DataPointModel) o).getValue();
                    }
                });

                result.getSeries().add(series);
                series.setItemsSource(DataPointModel.generateRandomModels(i));
                series.setCombineMode(ChartSeriesCombineMode.STACK);
            }

            return result;
        }

        private RadCartesianChartView createStackedLineAreaSeries() {
            RadCartesianChartView result = this.createBaseCartesianChart();
            for (int i = 0; i < 8; i++) {
                AreaSeries series = new AreaSeries(this.getActivity().getBaseContext());
                series.setShowLabels(true);
                series.setCategoryBinding(new DataPointBinding() {
                    @Override
                    public Object getValue(Object o) {
                        return ((DataPointModel) o).getCategory();
                    }
                });
                series.setValueBinding(new DataPointBinding() {
                    @Override
                    public Object getValue(Object o) {
                        return ((DataPointModel) o).getValue();
                    }
                });

                result.getSeries().add(series);
                series.setItemsSource(DataPointModel.generateRandomModels(i));
                series.setCombineMode(ChartSeriesCombineMode.STACK);
            }

            return result;
        }

        private RadCartesianChartView createStackedSplineAreaSeries() {
            RadCartesianChartView result = this.createBaseCartesianChart();
            for (int i = 0; i < 8; i++) {
                SplineAreaSeries series = new SplineAreaSeries(this.getActivity().getBaseContext());
                series.setShowLabels(true);
                series.setCategoryBinding(new DataPointBinding() {
                    @Override
                    public Object getValue(Object o) {
                        return ((DataPointModel) o).getCategory();
                    }
                });
                series.setValueBinding(new DataPointBinding() {
                    @Override
                    public Object getValue(Object o) {
                        return ((DataPointModel) o).getValue();
                    }
                });

                result.getSeries().add(series);
                series.setItemsSource(DataPointModel.generateRandomModels(i));
                series.setCombineMode(ChartSeriesCombineMode.STACK);
            }


            return result;
        }

        private RadCartesianChartView createBaseCartesianChart(){
            RadCartesianChartView chart = new RadCartesianChartView(this.getActivity().getBaseContext());
            CartesianChartGrid gridLines = new CartesianChartGrid(this.getActivity().getBaseContext());
            gridLines.setMajorLinesVisibility(GridLineVisibility.XY);
            chart.setGrid(gridLines);

            CategoricalAxis horizontalAxis = new CategoricalAxis(this.getActivity().getBaseContext());
            chart.setHorizontalAxis(horizontalAxis);

            LinearAxis verticalAxis = new LinearAxis(this.getActivity().getBaseContext());
            chart.setVerticalAxis(verticalAxis);
            chart.setPalette(ChartPalettes.light());

            ChartPanAndZoomBehavior panAndZoom = new ChartPanAndZoomBehavior();
            panAndZoom.setPanMode(EnumSet.of(ChartPanZoomMode.BOTH));
            panAndZoom.setZoomMode(EnumSet.of(ChartPanZoomMode.BOTH));
            chart.getBehaviors().add(panAndZoom);
            return chart;
        }

        private RadPieChartView createPieChart(){
            RadPieChartView pie = new RadPieChartView(this.getActivity().getBaseContext());

            pie.setPalette(ChartPalettes.light());

            PieSeries series = new PieSeries(this.getActivity().getBaseContext());
            series.setShowLabels(true);
            series.setValueBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object instance) {
                    return ((DataPointModel)instance).getValue();
                }
            });

            series.setItemsSource(DataPointModel.generateRandomModels(0));

            pie.getSeries().add(series);
            return pie;
        }

        private RadPieChartView createDoughnutChart(){
            RadPieChartView pie = new RadPieChartView(this.getActivity().getBaseContext());
            DoughnutSeries doughnutSeries = new DoughnutSeries(this.getActivity().getBaseContext());
            doughnutSeries.setShowLabels(true);

            doughnutSeries.setValueBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object instance) {
                    return ((DataPointModel) instance).getValue();
                }
            });

            pie.setPalette(ChartPalettes.light());

            doughnutSeries.setItemsSource(DataPointModel.generateRandomModels(4));
            pie.getSeries().add(doughnutSeries);
            return pie;
        }
    }


    public static class DataPointModel {

        private String category;
        private double value;

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public static Iterable<DataPointModel> generateRandomModels(int dimension) {
            ArrayList<DataPointModel> source = new ArrayList<DataPointModel>();

            double[] values = ValueProvider.getSource(dimension);

            for (int i = 0; i < 8; i++) {
                DataPointModel model = new DataPointModel();
                model.setCategory("Item " + i);
                model.setValue(values[i]);
                source.add(model);
            }

            return source;
        }
    }

    public static class ValueProvider{
        private static final Random random = new Random();
        private static final HashMap<Integer, double[]> cachedValues = new HashMap<Integer, double[]>();

        public static double[] getSource(int dimension){
            Integer key = new Integer(dimension);

            if (cachedValues.containsKey(key)){
                return cachedValues.get(key);
            }
            else{
                double[] result = new double[8];
                for (int i = 0; i < 8; i++) {
                    int value = random.nextInt(100) + 10;
                    result[i] = value;
                }
                cachedValues.put(new Integer(dimension), result);
                return result;
            }
        }
    }
}
