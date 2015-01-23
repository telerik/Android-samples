package com.example.telerik.json_reader_2;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Match {
    private int year, month, day, homeScore, awayScore;
    private String homeTeam, awayTeam;


    public Match(int year, int month, int day, String homeTeam, int homeScore, String awayTeam, int awayScore) {
        this.setYear(year);
        this.setMonth(month);
        this.setDay(day);
        this.setHomeTeam(homeTeam);
        this.setHomeScore(homeScore);
        this.setAwayTeam(awayTeam);
        this.setAwayScore(awayScore);
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
}
