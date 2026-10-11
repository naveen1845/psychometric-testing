package com.careercompass.strategy;

import com.careercompass.model.Dimension;
import com.careercompass.model.Question;
import com.careercompass.model.Response;
import com.careercompass.model.TraitScore;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Demonstrates the OOP concept of POLYMORPHISM:
 * Concrete implementation of ScoringStrategy for standard 5-point Likert scale assessments.
 *
 * Scoring Formula:
 * - Each dimension contains 3 questions.
 * - Each question is rated from 1 (Strongly Disagree) to 5 (Strongly Agree).
 * - Dimension Raw Score = Sum of 3 ratings (Min: 3, Max: 15).
 * - Normalized Score = ((Raw Score - 3) / 12) * 100, rounded to nearest whole number.
 *   Example: Raw = 12 -> ((12 - 3) / 12) * 100 = 75%
 * - Score is strictly clamped between 0 and 100.
 */
@Component
public class LikertScoringStrategy implements ScoringStrategy {

    public static final int QUESTIONS_PER_DIMENSION = 3;
    public static final int MIN_RAW_SCORE = 3;
    public static final int MAX_RAW_SCORE = 15;
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;

    @Override
    public Map<Dimension, TraitScore> calculateScores(List<Response> responses, List<Question> questions) {
        if (responses == null || responses.isEmpty()) {
            throw new IllegalArgumentException("Responses cannot be null or empty.");
        }
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("Questions reference list cannot be null or empty.");
        }

        // Map question ID to its Dimension
        Map<Long, Dimension> questionDimensionMap = new HashMap<>();
        for (Question q : questions) {
            questionDimensionMap.put(q.getId(), q.getDimension());
        }

        // Accumulate raw scores per dimension and track answered questions
        Map<Dimension, Integer> rawScores = new EnumMap<>(Dimension.class);
        for (Dimension d : Dimension.values()) {
            rawScores.put(d, 0);
        }

        Map<Dimension, Integer> dimensionAnswerCount = new EnumMap<>(Dimension.class);
        for (Dimension d : Dimension.values()) {
            dimensionAnswerCount.put(d, 0);
        }

        Set<Long> processedQuestionIds = new HashSet<>();

        for (Response r : responses) {
            if (r == null) {
                throw new IllegalArgumentException("Response item cannot be null.");
            }
            Long qId = r.getQuestionId();
            if (qId == null || !questionDimensionMap.containsKey(qId)) {
                throw new IllegalArgumentException("Invalid question ID in submission: " + qId);
            }
            if (processedQuestionIds.contains(qId)) {
                throw new IllegalArgumentException("Duplicate answer submitted for question ID: " + qId);
            }
            processedQuestionIds.add(qId);

            int rating = r.getRating();
            if (rating < MIN_RATING || rating > MAX_RATING) {
                throw new IllegalArgumentException("Answer rating must be between " + MIN_RATING +
                        " and " + MAX_RATING + ". Received: " + rating + " for question " + qId);
            }

            Dimension dimension = questionDimensionMap.get(qId);
            rawScores.put(dimension, rawScores.get(dimension) + rating);
            dimensionAnswerCount.put(dimension, dimensionAnswerCount.get(dimension) + 1);
        }

        // Validate that each dimension has all required questions answered
        for (Dimension d : Dimension.values()) {
            int count = dimensionAnswerCount.get(d);
            if (count != QUESTIONS_PER_DIMENSION) {
                throw new IllegalArgumentException("Dimension '" + d.getDisplayName() +
                        "' requires exactly " + QUESTIONS_PER_DIMENSION + " answers, but received " + count);
            }
        }

        // Compute normalized score for each dimension
        Map<Dimension, TraitScore> traitScores = new EnumMap<>(Dimension.class);
        for (Dimension d : Dimension.values()) {
            int raw = rawScores.get(d);
            int normalized = normalizeScore(raw);
            traitScores.put(d, new TraitScore(d, raw, normalized));
        }

        return traitScores;
    }

    /**
     * Normalizes a raw score (3..15) into a whole-number percentage (0..100).
     * Formula: ((raw - 3) / 12) * 100
     */
    public int normalizeScore(int rawScore) {
        if (rawScore < MIN_RAW_SCORE || rawScore > MAX_RAW_SCORE) {
            throw new IllegalArgumentException("Raw score must be between " + MIN_RAW_SCORE +
                    " and " + MAX_RAW_SCORE + ". Received: " + rawScore);
        }
        double normalized = ((double) (rawScore - MIN_RAW_SCORE) / (double) (MAX_RAW_SCORE - MIN_RAW_SCORE)) * 100.0;
        int rounded = (int) Math.round(normalized);
        // Safety clamp 0..100
        return Math.max(0, Math.min(100, rounded));
    }

    @Override
    public String getStrategyName() {
        return "Standard 5-Point Likert Scoring Strategy";
    }
}
