package com.chaha.flashcards.service;

import com.chaha.flashcards.dto.CreateFlashcardRequest;
import com.chaha.flashcards.model.Flashcard;
import com.chaha.flashcards.repository.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;

    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public Flashcard createFlashcard(CreateFlashcardRequest request){
        Flashcard flashcard = new Flashcard(request.getQuestion(), request.getAnswer());
        return flashcardRepository.save(flashcard);
    }

    public List<Flashcard> getAllFlashcards(){
        return flashcardRepository.findAll();
    }

    public Flashcard getFlashcardById(Long id){
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found with id: "+ id));
    }
}