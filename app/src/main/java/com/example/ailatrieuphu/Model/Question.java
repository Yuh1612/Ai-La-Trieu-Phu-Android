package com.example.ailatrieuphu.Model;

import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("_id")
    private int id;
    @SerializedName("question")
    private String question;
    @SerializedName("answer")
    private String answer;
    @SerializedName("choices")
    private String[] choices;
    @SerializedName("level")
    private int level;
    @SerializedName("category")
    private String category;

    public Question(int id, String question, String answer, String[] choices, int level, String category) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.choices = choices;
        this.level = level;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
