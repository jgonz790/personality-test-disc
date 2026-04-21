package com.personality.disc.model;

import java.util.List;
import java.util.Map;

public class TestAnswers {
    // List of 10 rounds, each round is a map: personality -> score (1-4)
    private List<Map<String, Integer>> rounds;

    public TestAnswers() {}

    public List<Map<String, Integer>> getRounds() { return rounds; }
    public void setRounds(List<Map<String, Integer>> rounds) { this.rounds = rounds; }
}
