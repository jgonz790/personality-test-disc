package com.personality.disc.model;

public class QuestionOption {
    private String personality; // LEON, NUTRIA, GOLDEN, CASTOR
    private String text;

    public QuestionOption() {}

    public QuestionOption(String personality, String text) {
        this.personality = personality;
        this.text = text;
    }

    public String getPersonality() { return personality; }
    public void setPersonality(String personality) { this.personality = personality; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
