package com.careercompass.service;

import com.careercompass.model.Dimension;
import com.careercompass.model.Question;
import com.careercompass.model.Response;
import com.careercompass.model.TraitScore;
import com.careercompass.strategy.ScoringStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates the OOP concept of POLYMORPHISM:
 * - ScoreCalculator interacts strictly through the ScoringStrategy interface reference.
 * - Dynamic method dispatch ensures the appropriate concrete strategy (e.g. LikertScoringStrategy)
 *   is invoked at runtime without coupling the calculator to concrete implementation details.
 */
@Service
public class ScoreCalculator {

    private final ScoringStrategy scoringStrategy;

    /**
     * Constructor injection demonstrating dependency on the abstraction (interface).
     */
    @Autowired
    public ScoreCalculator(ScoringStrategy scoringStrategy) {
        if (scoringStrategy == null) {
            throw new IllegalArgumentException("ScoringStrategy must not be null.");
        }
        this.scoringStrategy = scoringStrategy;
    }

    /**
     * Computes trait scores for all dimensions using the configured polymorphic strategy.
     *
     * @param responses Submitted user answers
     * @param questions Reference question database records
     * @return Ordered list of TraitScore objects (ordered according to Dimension enum)
     */
    public List<TraitScore> computeTraitScores(List<Response> responses, List<Question> questions) {
        // Polymorphic call: scoringStrategy.calculateScores
        Map<Dimension, TraitScore> scoreMap = scoringStrategy.calculateScores(responses, questions);

        List<TraitScore> traitScores = new ArrayList<>();
        for (Dimension d : Dimension.values()) {
            TraitScore score = scoreMap.get(d);
            if (score != null) {
                traitScores.add(score);
            }
        }
        return traitScores;
    }

    public ScoringStrategy getScoringStrategy() {
        return scoringStrategy;
    }
}
