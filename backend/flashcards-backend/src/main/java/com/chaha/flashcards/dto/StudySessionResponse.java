package com.chaha.flashcards.dto;

public class StudySessionResponse {

    private String sessionId;
    private int totalCards;

    public StudySessionResponse(String sessionId, int totalCards) {
        this.sessionId = sessionId;
        this.totalCards = totalCards;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getTotalCards() {
        return totalCards;
    }
}