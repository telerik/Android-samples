package com.telerik.qsf.viewmodels;

import com.telerik.qsf.common.DataClass;

import java.util.ArrayList;

public class ExampleDataProvider {

    private static ArrayList<DataClass> lineData;
    private static ArrayList<DataClass> lineDataSecondary;

    private static ArrayList<DataClass> barData;
    private static ArrayList<DataClass> barDataSecondary;

    private static ArrayList<DataClass> areaData;
    private static ArrayList<DataClass> areaDataSecondary;

    private static ArrayList<DataClass> pieData;
    private static ArrayList<DataClass> pieDataAdditional;

    private static ArrayList<DataClass> initialData() {
        ArrayList<DataClass> data = new ArrayList<DataClass>();

        data.add(new DataClass("Greenings", 0));
        data.add(new DataClass("Perfecto", 0));
        data.add(new DataClass("FamilyStore", 0));
        data.add(new DataClass("Fresh&Green", 0));
        data.add(new DataClass("NearBy", 0));

        return data;
    }

    public static Iterable<DataClass> lineData() {
        if (lineData == null) {
            lineData = initialData();
            lineData.get(0).value = 76;
            lineData.get(1).value = 85;
            lineData.get(2).value = 74;
            lineData.get(3).value = 71;
            lineData.get(4).value = 77;
        }

        return lineData;
    }

    public static Iterable<DataClass> lineDataSecondary() {
        if (lineDataSecondary == null) {
            lineDataSecondary = initialData();
            lineDataSecondary.get(0).value = 128;
            lineDataSecondary.get(1).value = 112;
            lineDataSecondary.get(2).value = 162;
            lineDataSecondary.get(3).value = 150;
            lineDataSecondary.get(4).value = 169;
        }

        return lineDataSecondary;
    }

    public static Iterable<DataClass> barData() {
        if (barData == null) {
            barData = initialData();
            barData.get(0).value = 65;
            barData.get(1).value = 62;
            barData.get(2).value = 55;
            barData.get(3).value = 71;
            barData.get(4).value = 92;

            barData.get(0).value2 = 5;
            barData.get(1).value2 = 15;
            barData.get(2).value2 = 3;
            barData.get(3).value2 = 45;
            barData.get(4).value2 = 10;
        }

        return barData;
    }

    public static Iterable<DataClass> barDataSecondary() {
        if (barDataSecondary == null) {
            barDataSecondary = initialData();
            barDataSecondary.get(0).value = 65;
            barDataSecondary.get(1).value = 56;
            barDataSecondary.get(2).value = 89;
            barDataSecondary.get(3).value = 68;
            barDataSecondary.get(4).value = 69;
        }

        return barDataSecondary;
    }

    public static Iterable<DataClass> areaData() {
        if (areaData == null) {
            areaData = initialData();
            areaData.get(0).value = 51;
            areaData.get(1).value = 81;
            areaData.get(2).value = 89;
            areaData.get(3).value = 60;
            areaData.get(4).value = 59;
        }

        return areaData;
    }

    public static Iterable<DataClass> areaDataSecondary() {
        if (areaDataSecondary == null) {
            areaDataSecondary = initialData();
            areaDataSecondary.get(0).value = 60;
            areaDataSecondary.get(1).value = 87;
            areaDataSecondary.get(2).value = 91;
            areaDataSecondary.get(3).value = 95;
            areaDataSecondary.get(4).value = 89;
        }

        return areaDataSecondary;
    }

    public static Iterable<DataClass> pieData() {
        if (pieData == null) {
            pieData = new ArrayList<DataClass>();
            pieData.add(new DataClass("Belgium", 37.68));
            pieData.add(new DataClass("France", 60.32));
        }

        return pieData;
    }

    public static Iterable<DataClass> pieDataAdditional() {
        if (pieDataAdditional == null) {
            pieDataAdditional = new ArrayList<DataClass>();
            pieDataAdditional.add(new DataClass("Belgium", 40.74));
            pieDataAdditional.add(new DataClass("France", 28.70));
            pieDataAdditional.add(new DataClass("Germany", 30.56));
        }

        return pieDataAdditional;
    }
}
