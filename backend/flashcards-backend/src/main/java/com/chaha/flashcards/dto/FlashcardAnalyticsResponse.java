package com.chaha.flashcards.dto;

public class FlashcardAnalyticsResponse {
    private Long id;
    private String question;
    private int correctCount;
    private int incorrectCount;
    private int totalAttempts;
    private double accuracyPercentage;

    public FlashcardAnalyticsResponse(
            Long id,
            String question,
            int correctCount,
            int incorrectCount,
            int totalAttempts,
            double accuracyPercentage
    ) {
        this.id = id;
        this.question = question;
        this.correctCount = correctCount;
        this.incorrectCount = incorrectCount;
        this.totalAttempts = totalAttempts;
        this.accuracyPercentage = accuracyPercentage;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public double getAccuracyPercentage() {
        return accuracyPercentage;
    }
}