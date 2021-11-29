package com.example.ppl;

public class FixturesModal {
    String teamA;
    String teamB;
    int goalA;
    int goalB;
    String logoA;
    String logoB;

    public String getLogoA() {
        return logoA;
    }

    public void setLogoA(String logoA) {
        this.logoA = logoA;
    }

    public String getLogoB() {
        return logoB;
    }

    public void setLogoB(String logoB) {
        this.logoB = logoB;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public int getGoalA() {
        return goalA;
    }

    public void setGoalA(int goalA) {
        this.goalA = goalA;
    }

    public int getGoalB() {
        return goalB;
    }

    public void setGoalB(int goalB) {
        this.goalB = goalB;
    }

    public FixturesModal(String teamA, String teamB, int goalA, int goalB, String logoA, String logoB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.goalA = goalA;
        this.goalB = goalB;
        this.logoA = logoA;
        this.logoB = logoB;
    }
}
