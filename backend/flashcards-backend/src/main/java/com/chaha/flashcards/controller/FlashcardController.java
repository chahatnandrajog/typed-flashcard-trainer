package com.chaha.flashcards.controller;

import com.chaha.flashcards.dto.CreateFlashcardRequest;
import com.chaha.flashcards.model.Flashcard;
import com.chaha.flashcards.service.FlashcardService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.chaha.flashcards.dto.SubmitAnswerRequest;
import com.chaha.flashcards.dto.SubmitAnswerResponse;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
@CrossOrigin(origins = "*")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public Flashcard createFlashcard(@Valid @RequestBody CreateFlashcardRequest request) {
        return flashcardService.createFlashcard(request);
    }

    @GetMapping
    public List<Flashcard> getAllFlashcards() {
        return flashcardService.getAllFlashcards();
    }

    @GetMapping("/{id}")
    public Flashcard getFlashcardById(@PathVariable Long id) {
        return flashcardService.getFlashcardById(id);
    }

    @PostMapping("/{id}/answer")
    public SubmitAnswerResponse submitAnswer(
            @PathVariable Long id,
            @Valid @RequestBody SubmitAnswerRequest request
    ) {
        return flashcardService.submitAnswer(id, request);
    }
}