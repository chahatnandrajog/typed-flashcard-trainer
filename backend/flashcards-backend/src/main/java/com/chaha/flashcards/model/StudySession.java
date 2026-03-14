package com.chaha.flashcards.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

public class StudySession {
    private final String sessionId;
    private final Queue<Flashcard> queue;

    public StudySession(List<Flashcard> flashcards) {
        this.sessionId = UUID.randomUUID().toString();
        this.queue = new LinkedList<>(flashcards);
    }

    public String getSessionId() {
        return sessionId;
    }

    public Queue<Flashcard> getQueue() {
        return queue;
    }
}