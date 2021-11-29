package com.example.ppl;

public class TeamModel {
    private String name;
    private int totalMatches = 0;
    private int goalFor = 0;
    private int goalAgainst = 0;
    private int wins = 0;
    private int loses = 0;
    private int draws = 0;
    private String image;
    private String abbr;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public TeamModel(String name, String abbr, String image) {
        this.name = name;
        this.abbr = abbr.toUpperCase();
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getGoalFor() {
        return goalFor;
    }

    public void setGoalFor(int goalFor) {
        this.goalFor = goalFor;
    }

    public int getGoalAgainst() {
        return goalAgainst;
    }

    public void setGoalAgainst(int goalAgainst) {
        this.goalAgainst = goalAgainst;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getGD() {
        return (goalFor - goalAgainst);
    }

    public int getPoints() {
        return (getWins()*3 + getDraws()*1);
    }
}
