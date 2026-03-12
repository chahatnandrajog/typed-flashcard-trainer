package com.chaha.flashcards.model;

import java.lang.annotation.Inherited;

import jakarta.persistence.*;

@Entity
@Table(name = "flashcards") //creates table
public class Flashcard {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) //col cannot be empty in db
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column(name = "correct_count")
    private int correctCount = 0;

    @Column(name = "incorrect_count")
    private int incorrectCount = 0;

    public Flashcard(){
    }

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Long getId(){
        return id;
    }
    
    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }

    public void setIncorrectCount(int incorrectCount){
        this.incorrectCount = incorrectCount;
    }
}