package com.example.progress.json_reader;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Team {
    private int position;
    private String name;
    private int wins, draws, losses;
    private int points;

    public Team(int position, String name, int wins, int draws, int losses, int points)
    {
        this.setPosition(position);
        this.setName(name);
        this.setWins(wins);
        this.setDraws(draws);
        this.setLosses(losses);
        this.setPoints(points);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
