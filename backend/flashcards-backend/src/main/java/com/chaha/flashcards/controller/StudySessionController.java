package com.chaha.flashcards.controller;

import com.chaha.flashcards.dto.NextFlashcardResponse;
import com.chaha.flashcards.dto.StudySessionResponse;
import com.chaha.flashcards.dto.SubmitAnswerRequest;
import com.chaha.flashcards.dto.SubmitSessionAnswerResponse;
import com.chaha.flashcards.service.StudySessionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study-sessions")
@CrossOrigin(origins = "*")
public class StudySessionController {

    private final StudySessionService studySessionService;

    public StudySessionController(StudySessionService studySessionService) {
        this.studySessionService = studySessionService;
    }

    @PostMapping("/start")
    public StudySessionResponse startSession() {
        return studySessionService.startSession();
    }

    @GetMapping("/{sessionId}/next")
    public NextFlashcardResponse getNextFlashcard(@PathVariable String sessionId) {
        return studySessionService.getNextFlashcard(sessionId);
    }

    @PostMapping("/{sessionId}/submit")
    public SubmitSessionAnswerResponse submitAnswer(
            @PathVariable String sessionId,
            @Valid @RequestBody SubmitAnswerRequest request
    ) {
        return studySessionService.submitAnswer(sessionId, request);
    }
    
}