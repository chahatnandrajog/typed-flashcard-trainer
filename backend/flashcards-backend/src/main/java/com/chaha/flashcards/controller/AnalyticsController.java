package com.chaha.flashcards.controller;

import com.chaha.flashcards.dto.AnalyticsSummaryResponse;
import com.chaha.flashcards.dto.FlashcardAnalyticsResponse;
import com.chaha.flashcards.service.AnalyticsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public AnalyticsSummaryResponse getSummary() {
        return analyticsService.getSummary();
    }

    @GetMapping("/hardest")
    public List<FlashcardAnalyticsResponse> getHardestFlashcards() {
        return analyticsService.getHardestFlashcards();
    }
}