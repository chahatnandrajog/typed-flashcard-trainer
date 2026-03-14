package com.chaha.flashcards.dto;

public class SubmitAnswerResponse {
    
    private boolean correct;
    private String correctAnswer;
    private int correctCount;
    private int incorrectCount;

    public SubmitAnswerResponse(boolean correct, String correctAnswer, int correctCount, int incorrectCount) {
        this.correct = correct;
        this.correctAnswer = correctAnswer;
        this.correctCount = correctCount;
        this.incorrectCount = incorrectCount;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }
}