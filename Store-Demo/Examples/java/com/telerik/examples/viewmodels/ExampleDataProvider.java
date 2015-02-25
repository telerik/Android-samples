package com.telerik.examples.viewmodels;

import android.content.res.Resources;

import com.telerik.android.common.Function;
import com.telerik.examples.R;
import com.telerik.examples.common.DataClass;
import com.telerik.examples.common.FinancialDataClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ExampleDataProvider {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private static ArrayList<FinancialDataClass> panZoomData;

    private static ArrayList<DataClass> lineData;
    private static ArrayList<DataClass> lineDataSecondary;

    private static ArrayList<DataClass> barData;
    private static ArrayList<DataClass> barDataSecondary;

    private static ArrayList<DataClass> areaData;
    private static ArrayList<DataClass> areaDataSecondary;

    private static ArrayList<DataClass> pieData;
    private static ArrayList<DataClass> pieDataAdditional;

    private static ArrayList<FinancialDataClass> ohlcData;
    private static ArrayList<FinancialDataClass> indicatorsData;

    private static Function<String, FinancialDataClass> financialDataParser = new Function<String, FinancialDataClass>() {
        @Override
        public FinancialDataClass apply(String argument) {
            return parseFinancialDataClass(argument);
        }
    };

    private static ArrayList<DataClass> initialData() {
        ArrayList<DataClass> data = new ArrayList<DataClass>();

        data.add(new DataClass("Greenings", 0.0F));
        data.add(new DataClass("Perfecto", 0.0F));
        data.add(new DataClass("FamilyStore", 0.0F));
        data.add(new DataClass("Fresh&Green", 0.0F));
        data.add(new DataClass("NearBy", 0.0F));

        return data;
    }

    public static Iterable<DataClass> lineData() {
        if (lineData == null) {
            lineData = initialData();
            lineData.get(0).value = 76.0F;
            lineData.get(1).value = 85.0F;
            lineData.get(2).value = 74.0F;
            lineData.get(3).value = 71.0F;
            lineData.get(4).value = 77.0F;
        }

        return lineData;
    }

    public static Iterable<DataClass> lineDataSecondary() {
        if (lineDataSecondary == null) {
            lineDataSecondary = initialData();
            lineDataSecondary.get(0).value = 128.0F;
            lineDataSecondary.get(1).value = 112.0F;
            lineDataSecondary.get(2).value = 162.0F;
            lineDataSecondary.get(3).value = 150.0F;
            lineDataSecondary.get(4).value = 169.0F;
        }

        return lineDataSecondary;
    }

    public static Iterable<DataClass> barData() {
        if (barData == null) {
            barData = initialData();
            barData.get(0).value = 65.0F;
            barData.get(1).value = 62.0F;
            barData.get(2).value = 55.0F;
            barData.get(3).value = 71.0F;
            barData.get(4).value = 92.0F;

            barData.get(0).value2 = 5.0F;
            barData.get(1).value2 = 15.0F;
            barData.get(2).value2 = 3.0F;
            barData.get(3).value2 = 45.0F;
            barData.get(4).value2 = 10.0F;
        }

        return barData;
    }

    public static Iterable<DataClass> barDataSecondary() {
        if (barDataSecondary == null) {
            barDataSecondary = initialData();
            barDataSecondary.get(0).value = 65.0F;
            barDataSecondary.get(1).value = 56.0F;
            barDataSecondary.get(2).value = 89.0F;
            barDataSecondary.get(3).value = 68.0F;
            barDataSecondary.get(4).value = 69.0F;
        }

        return barDataSecondary;
    }

    public static Iterable<DataClass> areaData() {
        if (areaData == null) {
            areaData = initialData();
            areaData.get(0).value = 51.0F;
            areaData.get(1).value = 81.0F;
            areaData.get(2).value = 89.0F;
            areaData.get(3).value = 60.0F;
            areaData.get(4).value = 59.0F;
        }

        return areaData;
    }

    public static Iterable<DataClass> areaDataSecondary() {
        if (areaDataSecondary == null) {
            areaDataSecondary = initialData();
            areaDataSecondary.get(0).value = 60.0F;
            areaDataSecondary.get(1).value = 87.0F;
            areaDataSecondary.get(2).value = 91.0F;
            areaDataSecondary.get(3).value = 95.0F;
            areaDataSecondary.get(4).value = 89.0F;
        }

        return areaDataSecondary;
    }

    public static Iterable<DataClass> pieData() {
        if (pieData == null) {
            pieData = new ArrayList<DataClass>();
            pieData.add(new DataClass("Belgium", 40));
            pieData.add(new DataClass("France", 60));
        }

        return pieData;
    }

    public static Iterable<DataClass> pieDataAdditional() {
        if (pieDataAdditional == null) {
            pieDataAdditional = new ArrayList<DataClass>();
            pieDataAdditional.add(new DataClass("Belgium", 40));
            pieDataAdditional.add(new DataClass("France", 30));
            pieDataAdditional.add(new DataClass("Germany", 30));
        }

        return pieDataAdditional;
    }

    public static Iterable<FinancialDataClass> ohlcData(Resources resources) {
        if (ohlcData == null) {
            ohlcData = new ArrayList<FinancialDataClass>();
            parseFinancialDataFromXml(ohlcData, R.raw.dowjones, resources, financialDataParser);
        }

        return ohlcData.subList(0, 10); // TODO: Extract first 10 entries in another xml file
    }

    public static Iterable<FinancialDataClass> panZoomData(Resources resources) {
        if (panZoomData == null) {
            panZoomData = new ArrayList<FinancialDataClass>();
            parseFinancialDataFromXml(panZoomData, R.raw.dowjones, resources, financialDataParser);
        }

        return panZoomData;
    }

    public static <T> void parseFinancialDataFromXml(List<T> list, int xmlResource, Resources resources, Function<String, T> parseLine) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resources.openRawResource(xmlResource)));
            String line = reader.readLine();
            while (line != null) {
                list.add(parseLine.apply(line));
                line = reader.readLine();
            }

            reader.close();

        } catch (IOException ex) {
            throw new Error("Could not read financial data from xml.");
        }
    }

    public static FinancialDataClass parseFinancialDataClass(String data) {
        String[] tokens = data.split("\t");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dateFormat.parse(tokens[0], new ParsePosition(0)));
        return new FinancialDataClass(calendar, Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]), Float.parseFloat(tokens[4]), Float.parseFloat(tokens[5]));
    }

    public static Iterable<FinancialDataClass> indicatorsDataOneMonth(Resources resources) {
        return indicatorsData(resources).subList(1447, 1465);
    }

    public static Iterable<FinancialDataClass> indicatorsDataSixMonths(Resources resources) {
        return indicatorsData(resources).subList(1341, 1465);
    }

    public static Iterable<FinancialDataClass> indicatorsDataOneYear(Resources resources) {
        return indicatorsData(resources).subList(1195, 1465);
    }

    public static Iterable<FinancialDataClass> indicatorsDataFiveYears(Resources resources) {
        return indicatorsData(resources);
    }

    private static List<FinancialDataClass> indicatorsData(Resources resources) {
        if (indicatorsData == null) {
            indicatorsData = new ArrayList<FinancialDataClass>();
            parseFinancialDataFromXml(indicatorsData, R.raw.financial_data_raw, resources, financialDataParser);
        }

        return indicatorsData;
    }
}
