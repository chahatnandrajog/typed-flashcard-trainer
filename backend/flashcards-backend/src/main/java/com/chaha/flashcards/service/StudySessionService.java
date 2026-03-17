package com.chaha.flashcards.service;

import com.chaha.flashcards.dto.NextFlashcardResponse;
import com.chaha.flashcards.dto.StudySessionResponse;
import com.chaha.flashcards.dto.SubmitAnswerRequest;
import com.chaha.flashcards.dto.SubmitSessionAnswerResponse;
import com.chaha.flashcards.model.Flashcard;
import com.chaha.flashcards.model.StudySession;
import com.chaha.flashcards.repository.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Service
public class StudySessionService {
    
    private final FlashcardRepository flashcardRepository;
    private final Map<String, StudySession> sessions = new HashMap<>();

    public StudySessionService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public StudySessionResponse startSession() {
        List<Flashcard> flashcards = flashcardRepository.findAll();

        if (flashcards.isEmpty()) {
            throw new RuntimeException("Cannot start session with no flashcards");
        
        }

        StudySession session = new StudySession(flashcards);
        sessions.put(session.getSessionId(), session);

        return new StudySessionResponse(session.getSessionId(), flashcards.size());
    }

    public NextFlashcardResponse getNextFlashcard(String sessionId) {
        StudySession session = getSessionById(sessionId);
        Queue<Flashcard> queue = session.getQueue();

        Flashcard nextCard = queue.peek();

        if (nextCard == null) {
            return new NextFlashcardResponse(null, null, true);
        }

        return new NextFlashcardResponse(
            nextCard.getId(),
            nextCard.getQuestion(),
            false
        );
    }

    public SubmitSessionAnswerResponse submitAnswer(String sessionId, SubmitAnswerRequest request) {
        StudySession session = getSessionById(sessionId);
        Queue<Flashcard> queue = session.getQueue();

        Flashcard currentCard = queue.poll();

        if (currentCard == null) {
            throw new RuntimeException("Study session is already complete");
        }

        String correctAnswer = currentCard.getAnswer().trim();
        String userAnswer = request.getUserAnswer().trim();

        boolean isCorrect = correctAnswer.equalsIgnoreCase(userAnswer);

        if (isCorrect) {
            currentCard.setCorrectCount(currentCard.getCorrectCount() +1);
        } else {
            currentCard.setIncorrectCount(currentCard.getIncorrectCount() +1);
            queue.offer(currentCard);
        }
        
        System.out.println("Answered card ID: " + currentCard.getId() + ", correct=" + isCorrect);
        System.out.println("Queue now contains IDs: " +
                queue.stream().map(card -> card.getId().toString()).toList());

        flashcardRepository.save(currentCard);

        return new SubmitSessionAnswerResponse(
            isCorrect,
            currentCard.getAnswer(),
            queue.size()
        );

    }

    private StudySession getSessionById(String sessionId) {
        StudySession session = sessions.get(sessionId);

        if (session == null) {
            throw new RuntimeException("Study session not found with id: " + sessionId);
        }

        return session;
    }
}