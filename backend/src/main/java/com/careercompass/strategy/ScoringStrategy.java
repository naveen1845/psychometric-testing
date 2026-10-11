package com.careercompass.strategy;

import com.careercompass.model.Dimension;
import com.careercompass.model.Question;
import com.careercompass.model.Response;
import com.careercompass.model.TraitScore;

import java.util.List;
import java.util.Map;

/**
 * Demonstrates the OOP concept of ABSTRACTION:
 * - Defines an abstract contract for scoring an assessment without dictating
 *   the underlying mathematical calculation or question evaluation algorithm.
 * - Decouples higher-level assessment workflows from specific psychometric
 *   scoring mechanisms (Likert, scenario, weighted, adaptive).
 */
public interface ScoringStrategy {

    /**
     * Calculates trait scores for all assessed dimensions based on submitted responses
     * and question metadata.
     *
     * @param responses List of submitted question responses (ratings)
     * @param questions List of reference questions (to map questionId -> dimension)
     * @return Map of Dimension to calculated TraitScore
     * @throws IllegalArgumentException if responses are incomplete, invalid, or mismatched
     */
    Map<Dimension, TraitScore> calculateScores(List<Response> responses, List<Question> questions);

    /**
     * Identifies the human-readable strategy name.
     */
    String getStrategyName();
}
