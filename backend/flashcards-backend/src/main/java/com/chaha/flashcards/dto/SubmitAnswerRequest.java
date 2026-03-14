package com.chaha.flashcards.dto;

import jakarta.validation.constraints.NotBlank;

public class SubmitAnswerRequest {
    @NotBlank(message = "User answer is required")
    private String userAnswer;

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}