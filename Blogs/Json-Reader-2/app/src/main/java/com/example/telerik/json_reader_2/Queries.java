package com.example.telerik.json_reader_2;

/**
 * Created by kstanoev on 1/23/2015.
 */
public class Queries {
    // select all matches:
    // SQL:
    // select *
    public static String selectAll = "https://spreadsheets.google.com/tq?tqx=out:JSON&tq=select%20%2A&key=1o728ZECFa8A05nqlN50e9aPicnYheb26SS2gMwVMxsE";
    // select all matches before 1900
    // SQL:
    // select * where A < 1900
    public static String selectAllBefore1900 = "https://spreadsheets.google.com/tq?tqx=out:JSON&tq=select%20%2A%20where%20A%20%3C%201900&key=1o728ZECFa8A05nqlN50e9aPicnYheb26SS2gMwVMxsE";
    // select all the wins of Aston Villa between 1990 and 2000
    // SQL:
    // select * where (A >= 1990 and A <= 2000) and ((D contains 'Aston Villa' and E>G) or (F contains 'Aston Villa' and G > E))
    public static String selectWinsAstonVilla1900_2000 = "https://spreadsheets.google.com/tq?tqx=out:JSON&tq=select%20%2A%20where%20%28A%20%3E%3D%201990%20and%20A%20%3C%3D%202000%29%20and%20%28%28D%20contains%20%27Aston%20Villa%27%20and%20E%3EG%29%20or%20%28F%20contains%20%27Aston%20Villa%27%20and%20G%20%3E%20E%29%29&key=1o728ZECFa8A05nqlN50e9aPicnYheb26SS2gMwVMxsE";
    // select all the wins of Liverpool since 1888
    // SQL:
    // select * where ((D contains 'Liverpool' and E>G) or (F contains 'Liverpool' and G > E))
    public static String selectWinsLiverpool = "https://spreadsheets.google.com/tq?tqx=out:JSON&tq=select%20%2A%20where%20%28%28D%20contains%20%27Liverpool%27%20and%20E%3EG%29%20or%20%28F%20contains%20%27Liverpool%27%20and%20G%20%3E%20E%29%29&key=1o728ZECFa8A05nqlN50e9aPicnYheb26SS2gMwVMxsE";
    // select all matches of season 2013-2014
    // SQL:
    // select * where (A=2013 and B>7) or (A=2014 and B<7)
    // A season in the English Premier League is considered the period between September, YEAR and May, YEAR+1
    public static String selectSeason2013_2014 = "https://spreadsheets.google.com/tq?tqx=out:JSON&tq=select%20%2A%20where%20%28A%3D2013%20and%20B%3E7%29%20or%20%28A%3D2014%20and%20B%3C7%29&key=1o728ZECFa8A05nqlN50e9aPicnYheb26SS2gMwVMxsE";
}
