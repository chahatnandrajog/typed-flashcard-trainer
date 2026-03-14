package com.chaha.flashcards.service;

import com.chaha.flashcards.dto.AnalyticsSummaryResponse;
import com.chaha.flashcards.dto.FlashcardAnalyticsResponse;
import com.chaha.flashcards.model.Flashcard;
import com.chaha.flashcards.repository.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AnalyticsService {

    private final FlashcardRepository flashcardRepository;

    public AnalyticsService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public AnalyticsSummaryResponse getSummary() {
        List<Flashcard> flashcards = flashcardRepository.findAll();

        int totalFlashcards = flashcards.size();
        int totalCorrectAnswers = 0;
        int totalIncorrectAnswers = 0;
        
        for (Flashcard flashcard : flashcards) {
            totalCorrectAnswers += flashcard.getCorrectCount();
            totalIncorrectAnswers += flashcard.getIncorrectCount();
        }

        int totalAttempts = totalCorrectAnswers + totalIncorrectAnswers;

        double accuracyPercentage = totalAttempts == 0
                ? 0.0
                : ((double) totalCorrectAnswers / totalAttempts) * 100.0;
        
        return new AnalyticsSummaryResponse(
                totalFlashcards,
                totalCorrectAnswers,
                totalIncorrectAnswers,
                totalAttempts,
                accuracyPercentage
        );
    }

    public List<FlashcardAnalyticsResponse> getHardestFlashcards() {
        List<Flashcard> flashcards = flashcardRepository.findAll();

        return flashcards.stream()
                .filter(card -> (card.getCorrectCount() + card.getIncorrectCount()) > 0)
                .map(card -> {
                    int totalAttempts = card.getCorrectCount() + card.getIncorrectCount();
                    double accuracyPercentage = ((double) card.getCorrectCount() / totalAttempts) * 100.0;

                    return new FlashcardAnalyticsResponse(
                            card.getId(),
                            card.getQuestion(),
                            card.getCorrectCount(),
                            card.getIncorrectCount(),
                            totalAttempts,
                            accuracyPercentage
                    );
                })
                .sorted(Comparator
                        .comparingDouble(FlashcardAnalyticsResponse::getAccuracyPercentage)
                        .thenComparing(Comparator.comparingInt(FlashcardAnalyticsResponse::getIncorrectCount).reversed()))
                .toList();
                
    }
}