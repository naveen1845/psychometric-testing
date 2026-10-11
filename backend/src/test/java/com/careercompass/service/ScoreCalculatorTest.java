package com.careercompass.service;

import com.careercompass.model.Dimension;
import com.careercompass.model.LikertQuestion;
import com.careercompass.model.Question;
import com.careercompass.model.Response;
import com.careercompass.model.TraitScore;
import com.careercompass.strategy.LikertScoringStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCalculatorTest {

    private ScoreCalculator scoreCalculator;
    private LikertScoringStrategy likertStrategy;
    private List<Question> standardQuestions;

    @BeforeEach
    void setUp() {
        likertStrategy = new LikertScoringStrategy();
        scoreCalculator = new ScoreCalculator(likertStrategy);

        // Build 15 standard reference questions (3 per dimension)
        standardQuestions = new ArrayList<>();
        long id = 1;
        for (Dimension d : Dimension.values()) {
            for (int i = 1; i <= 3; i++) {
                standardQuestions.add(new LikertQuestion(id++, "Test question " + id, d, (int) id));
            }
        }
    }

    @Test
    @DisplayName("Verify normalized score calculation: Raw 12 results in 75%")
    void testNormalizedScoreFormulaExample() {
        // Raw score of 12 -> ((12 - 3) / 12) * 100 = 75
        int normalized = likertStrategy.normalizeScore(12);
        assertEquals(75, normalized, "Raw score 12 must normalize to exactly 75%");
    }

    @Test
    @DisplayName("Verify boundary scores: Minimum raw 3 results in 0%, Maximum raw 15 results in 100%")
    void testBoundaryScores() {
        assertEquals(0, likertStrategy.normalizeScore(3), "Raw score 3 (all 1s) must normalize to 0%");
        assertEquals(100, likertStrategy.normalizeScore(15), "Raw score 15 (all 5s) must normalize to 100%");
    }

    @Test
    @DisplayName("Verify all 5s across all 15 questions produces 100% on all five dimensions")
    void testAllMaxRatingsProduces100Percent() {
        List<Response> responses = new ArrayList<>();
        for (Question q : standardQuestions) {
            responses.add(new Response(q.getId(), 5));
        }

        List<TraitScore> scores = scoreCalculator.computeTraitScores(responses, standardQuestions);
        assertEquals(5, scores.size(), "Must calculate scores for all 5 dimensions");

        for (TraitScore ts : scores) {
            assertEquals(15, ts.getRawScore(), "Raw score must be 15");
            assertEquals(100, ts.getNormalizedScore(), "Normalized score must be 100%");
        }
    }

    @Test
    @DisplayName("Verify all 1s across all 15 questions produces 0% on all five dimensions")
    void testAllMinRatingsProduces0Percent() {
        List<Response> responses = new ArrayList<>();
        for (Question q : standardQuestions) {
            responses.add(new Response(q.getId(), 1));
        }

        List<TraitScore> scores = scoreCalculator.computeTraitScores(responses, standardQuestions);
        assertEquals(5, scores.size());

        for (TraitScore ts : scores) {
            assertEquals(3, ts.getRawScore(), "Raw score must be 3");
            assertEquals(0, ts.getNormalizedScore(), "Normalized score must be 0%");
        }
    }

    @Test
    @DisplayName("Verify rejection of invalid ratings (< 1 or > 5)")
    void testInvalidRatingThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Response(1L, 0),
                "Rating of 0 must throw IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () -> new Response(1L, 6),
                "Rating of 6 must throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Verify rejection when a question is unanswered")
    void testIncompleteAnswersThrowsException() {
        List<Response> incompleteResponses = new ArrayList<>();
        // Only answer 14 questions instead of 15
        for (int i = 0; i < 14; i++) {
            incompleteResponses.add(new Response(standardQuestions.get(i).getId(), 4));
        }

        assertThrows(IllegalArgumentException.class, () ->
                scoreCalculator.computeTraitScores(incompleteResponses, standardQuestions));
    }

    @Test
    @DisplayName("Verify rejection of duplicate answers for the same question ID")
    void testDuplicateAnswersThrowsException() {
        List<Response> duplicateResponses = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            duplicateResponses.add(new Response(standardQuestions.get(i).getId(), 4));
        }
        // Duplicate question 1
        duplicateResponses.add(new Response(standardQuestions.get(0).getId(), 3));

        assertThrows(IllegalArgumentException.class, () ->
                scoreCalculator.computeTraitScores(duplicateResponses, standardQuestions));
    }
}
