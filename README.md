#Telerik UI for Android Code Examples

This repository contains an up-to-date version of the [Telerik UI for Android](http://www.telerik.com/android-ui) examples app. 
The app demonstrates various usage of Telerik UI components for Android.

![TelerikUIAndroid](http://www.telerik.com/sfimages/default-source/productsimages/Android-UI/ProductFeatures/fillgaps.jpg?sfvrsn=0 "")

The current version of the app focuses on the [Charting framework](http://www.telerik.com/android-ui/chart) and its usage in different scenarios. Some of the features supported by the Charting framework are:

##Most Essential Chart Types

You can use **Pie** and **Donut** charts to visualize each piece of data as part of a whole. *Pie and doughnut charts are most useful when you have to compare percentage values of different items that are part of the same series. Illustrating them as pie slices makes it easier for anyone to quickly understand the underlying data.* 

You can use **Line**, **Spline**, **Area** and **SplineArea** charts to monitor trends. *Area chart displays series as filled areas, with each data point displayed as a peak in the area. This view is useful when you need to show trends for several series on the same diagram, and also show the relationship of the parts to the whole.*

*Line charts are useful when you have to show trends for several series on the same diagram, and to compare the values of several series for the same data point.*

With **Bar** and **RangeBar** charts you can compare several sets of data. *These charts are used to visualize data points as bar blocks with the height of each bar denoting the magnitude of each data point. Styling has been made easy so you can quickly differentiate each bar.*

##Support for Various Data Types

You can feed various data types into the chart. Depending on the chart type, numerical data can be distributed on the X-axis over other numbers, categories (*strings*) or points in time (*DateTime*). RadChart uses several types of axes to plot its data: **Linear**, **Numerical**, **Logarithmic**, **DateTime** and **Categorical**. 

*__Linear axis__ - The linear axis is a plain numerical axis that may work with categorical data and the value that is passed to the axis depends on the chart series type.*

*__Categorical axis__ - the categorical axis displays a range of categories and the values that determine each category.*

*__DateTime axis__ - There are two types of axes that are used to visualize date categories - DateTimeCategorical and DateTimeContinuous. The first is a categorical axis which provides notation for dates and the second is a sort of a hybrid between categorical and linear axes. It works with categorical data but the axis builds time slots which represent an actual timeline.*

*__Logarithmic axis__ - The logarithmic axis is used to represent wide ranges of values in a more manageable way. For example the Richter scale is logarithmic, after a certain point very small values of the scale represent huge differences in energy magnitude. If the Richter scale was to be visualized with a normal linear axis the area required to draw it would be impracticably large.*

##Support for Multiple Axes

RadChart supports multiple axes in case series of a different scale need to be presented on the same graph. You can add vertical and horizontal axes per chart basis and per series basis. This means that if you have two series in one chart instance,you can use different horizontal and vertical axes for each series.

##Handles large amounts of data

With RadChart component for Android, you are enabled to display data in real-time. Telerik’s charting control is backed by super-fast loading, pixel-perfectness and excellent drawing capabilities.

##Smooth interaction
RadChart’s pan and zoom functionality is very simple to use and allows users to zoom in the chart plot area when there is a dense area of data points that cannot be seen clearly at the normal chart scale. The pan functionality allows moving the visible area of the chart when it is zoomed in. 

##Annotations
Annotations are visual elements that can be used to highlight certain areas on the plot area and denote statistical significance. An annotation is an explanatory image or text which can be anchored to a single point, a coordinate, or an arbitrary pixel coordinate on a chart's surface.

RadChart provides the following types of annotations: Cartesian Grid Line, Cartesian Plot Band and custom Cartesian annotations.

*__Cartesian Grid Line__ - visually represented by a straight line across the chart that marks a specific value on the associated Cartesian axis.*

*__Cartesian Plot Band__ - visually represented by a band across the chart that marks a specific range on the associated Cartesian axis.*

*__Custom Cartesian annotations__ - this annotation marks a specific point on the Cartesian chart. It requires both horizontal and vertical coordinates to be specified.*
