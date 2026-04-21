package com.personality.disc.model;

import java.util.List;

public class QuestionRound {
    private int roundNumber;
    private List<QuestionOption> options;

    public QuestionRound() {}

    public QuestionRound(int roundNumber, List<QuestionOption> options) {
        this.roundNumber = roundNumber;
        this.options = options;
    }

    public int getRoundNumber() { return roundNumber; }
    public void setRoundNumber(int roundNumber) { this.roundNumber = roundNumber; }
    public List<QuestionOption> getOptions() { return options; }
    public void setOptions(List<QuestionOption> options) { this.options = options; }
}
