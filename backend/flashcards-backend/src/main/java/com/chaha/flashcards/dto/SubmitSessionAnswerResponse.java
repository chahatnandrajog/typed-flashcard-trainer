package com.chaha.flashcards.dto;

public class SubmitSessionAnswerResponse {
    
    private boolean correct;
    private String correctAnswer;
    private int remainingCards;

    public SubmitSessionAnswerResponse(boolean correct, String correctAnswer, int remainingCards) {
        this.correct = correct;
        this.correctAnswer = correctAnswer;
        this.remainingCards = remainingCards;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getRemainingCards() {
        return remainingCards;
    }
        
}