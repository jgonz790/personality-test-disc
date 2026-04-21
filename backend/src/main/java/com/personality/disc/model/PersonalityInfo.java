package com.personality.disc.model;

import java.util.List;

public class PersonalityInfo {
    private String key;
    private String name;
    private String emoji;
    private String keyword;
    private String description;
    private List<String> negatives;
    private List<String> positives;
    private List<String> needs;

    public PersonalityInfo() {}

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getNegatives() { return negatives; }
    public void setNegatives(List<String> negatives) { this.negatives = negatives; }
    public List<String> getPositives() { return positives; }
    public void setPositives(List<String> positives) { this.positives = positives; }
    public List<String> getNeeds() { return needs; }
    public void setNeeds(List<String> needs) { this.needs = needs; }
}
