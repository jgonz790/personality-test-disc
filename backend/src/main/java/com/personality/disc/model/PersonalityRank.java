package com.personality.disc.model;

public class PersonalityRank {
    private int position;
    private String key;
    private String name;
    private String emoji;
    private int score;

    public PersonalityRank() {}

    public PersonalityRank(int position, String key, String name, String emoji, int score) {
        this.position = position;
        this.key = key;
        this.name = name;
        this.emoji = emoji;
        this.score = score;
    }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
