package com.chaha.flashcards.dto;

public class AnalyticsSummaryResponse {
    private int totalFlashcards;
    private int totalCorrectAnswers;
    private int totalIncorrectAnswers;
    private int totalAttempts;
    private double accuracyPercentage;

    public AnalyticsSummaryResponse (
            int totalFlashcards,
            int totalCorrectAnswers,
            int totalIncorrectAnswers,
            int totalAttempts,
            double accuracyPercentage
    ) {
        this.totalFlashcards = totalFlashcards;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalIncorrectAnswers = totalIncorrectAnswers;
        this.totalAttempts = totalAttempts;
        this.accuracyPercentage = accuracyPercentage;
    }
    
    public int getTotalFlashcards() {
        return totalFlashcards;
    }

    public int getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    public int getTotalIncorrectAnswers() {
        return totalIncorrectAnswers;
    }

    public int getTotalAttempts() {
        return totalAttempts;

    }

    public double getAccuracyPercentage() {
        return accuracyPercentage;
    }

}