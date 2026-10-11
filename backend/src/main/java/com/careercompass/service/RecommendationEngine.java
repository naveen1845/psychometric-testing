package com.careercompass.service;

import com.careercompass.model.CareerProfile;
import com.careercompass.model.CareerRecommendation;
import com.careercompass.model.TraitScore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Rule-Based Career Recommendation Engine.
 *
 * Implements deterministic weighted-average matching:
 * - Computes career match score = Sum(Dimension Score * Career Weight) / Sum(Career Weights).
 * - Sorts career profiles in descending order of match score.
 * - Extracts and returns the top 3 recommended careers with complete descriptions,
 *   skills, matching rationales, and beginner learning suggestions.
 */
@Service
public class RecommendationEngine {

    public static final int TOP_RECOMMENDATIONS_COUNT = 3;

    /**
     * Recommends the top 3 careers matching the student's normalized dimension scores.
     *
     * @param traitScores The 5 calculated dimension scores
     * @param careerProfiles Predefined career profiles from database
     * @return Top 3 ranked CareerRecommendation instances
     */
    public List<CareerRecommendation> recommendCareers(List<TraitScore> traitScores,
                                                        List<CareerProfile> careerProfiles) {
        if (traitScores == null || traitScores.isEmpty()) {
            throw new IllegalArgumentException("Trait scores cannot be null or empty.");
        }
        if (careerProfiles == null || careerProfiles.isEmpty()) {
            throw new IllegalArgumentException("Career profiles cannot be null or empty.");
        }

        // Internal helper class to hold profile and computed match score
        class ScoredCareer {
            final CareerProfile profile;
            final int matchScore;

            ScoredCareer(CareerProfile profile, int matchScore) {
                this.profile = profile;
                this.matchScore = matchScore;
            }
        }

        List<ScoredCareer> scoredList = new ArrayList<>();

        for (CareerProfile profile : careerProfiles) {
            int matchScore = calculateCareerMatchScore(traitScores, profile);
            scoredList.add(new ScoredCareer(profile, matchScore));
        }

        // Sort descending by match score; tie-breaker: alphabetical career name
        scoredList.sort(Comparator
                .comparingInt((ScoredCareer sc) -> sc.matchScore).reversed()
                .thenComparing(sc -> sc.profile.getCareerName()));

        // Take top 3 recommendations and assign rank orders (1, 2, 3)
        List<CareerRecommendation> recommendations = new ArrayList<>();
        int limit = Math.min(TOP_RECOMMENDATIONS_COUNT, scoredList.size());

        for (int i = 0; i < limit; i++) {
            ScoredCareer scored = scoredList.get(i);
            CareerProfile p = scored.profile;
            int rank = i + 1;

            CareerRecommendation rec = new CareerRecommendation(
                    p.getId(),
                    p.getCareerName(),
                    scored.matchScore,
                    rank,
                    p.getDescription(),
                    p.getRelevantSkills(),
                    p.getMatchingExplanation(),
                    p.getLearningActivities()
            );
            recommendations.add(rec);
        }

        return recommendations;
    }

    /**
     * Calculates the weighted average match score for a single career profile:
     * Career Match Score = Sum(Dimension Score * Career Weight) / Sum(Career Weights)
     *
     * @param traitScores The 5 calculated dimension scores
     * @param profile The career profile with its 5 weights
     * @return Match score rounded to nearest whole percentage (0..100)
     */
    public int calculateCareerMatchScore(List<TraitScore> traitScores, CareerProfile profile) {
        int totalWeight = profile.getTotalWeight();
        if (totalWeight <= 0) {
            return 0;
        }

        double weightedSum = 0.0;
        for (TraitScore ts : traitScores) {
            int weight = profile.getWeightForDimension(ts.getDimension());
            weightedSum += ((double) ts.getNormalizedScore() * (double) weight);
        }

        double average = weightedSum / (double) totalWeight;
        int rounded = (int) Math.round(average);

        // Clamp between 0 and 100
        return Math.max(0, Math.min(100, rounded));
    }
}
