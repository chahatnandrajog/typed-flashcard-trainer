package com.chaha.flashcards.dto;

public class NextFlashcardResponse {

    private Long id;
    private String question;
    private boolean completed;

    public NextFlashcardResponse(Long id, String question, boolean completed) {
        this.id = id;
        this.question = question;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isCompleted() {
        return completed;
    }
}