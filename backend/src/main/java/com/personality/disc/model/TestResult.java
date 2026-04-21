package com.personality.disc.model;

import java.util.List;
import java.util.Map;

public class TestResult {
    private Map<String, Integer> scores; // LEON -> 30, NUTRIA -> 25, etc.
    private List<PersonalityRank> ranking; // sorted from highest to lowest
    private PersonalityInfo dominantPersonality;

    public TestResult() {}

    public Map<String, Integer> getScores() { return scores; }
    public void setScores(Map<String, Integer> scores) { this.scores = scores; }
    public List<PersonalityRank> getRanking() { return ranking; }
    public void setRanking(List<PersonalityRank> ranking) { this.ranking = ranking; }
    public PersonalityInfo getDominantPersonality() { return dominantPersonality; }
    public void setDominantPersonality(PersonalityInfo dominantPersonality) { this.dominantPersonality = dominantPersonality; }
}
